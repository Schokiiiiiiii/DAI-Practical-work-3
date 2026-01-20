package ch.heigvd.user;

import ch.heigvd.object.AstronomicalObject;
import io.javalin.http.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class UserController {

  private final ConcurrentMap<String, User> users;
  private final ConcurrentMap<String, LocalDateTime> usersCache;
  private final ConcurrentMap<Integer, AstronomicalObject> objects;
  private static final String ALL_USERS_KEY = "__ALL_USERS__";

  public UserController(
      ConcurrentMap<String, User> users,
      ConcurrentMap<String, LocalDateTime> usersCache,
      ConcurrentMap<Integer, AstronomicalObject> objects) {
    this.users = users;
    this.usersCache = usersCache;
    this.objects = objects;
  }

  public void create(Context ctx) {

    // validate user
    User newUser = validateBody(ctx);

    // look for any other similar names
    for (User user : users.values())
      if (newUser.username().equalsIgnoreCase(user.username())) throw new ConflictResponse();

    // create
    newUser =
        new User(
            newUser.username(),
            newUser.email(),
            newUser.date_of_birth(),
            newUser.diploma(),
            newUser.biography());

    // add user to database
    users.put(newUser.username(), newUser);

    // cache
    LocalDateTime now = LocalDateTime.now();
    usersCache.put(newUser.username(), now);
    usersCache.remove(ALL_USERS_KEY);

    // send status and user back
    ctx.header("Last-Modified", now.toString());
    ctx.status(HttpStatus.CREATED);
    ctx.json(newUser);
  }

  public void getAll(Context ctx) {

    // put all users into a list
    List<User> result = new ArrayList<>(users.values());

    // cache
    LocalDateTime lastKnown =
        ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);

    if (lastKnown != null
        && usersCache.containsKey(ALL_USERS_KEY)
        && usersCache.get(ALL_USERS_KEY).equals(lastKnown)) {
      throw new NotModifiedResponse();
    }

    LocalDateTime now =
        usersCache.containsKey(ALL_USERS_KEY) ? usersCache.get(ALL_USERS_KEY) : LocalDateTime.now();
    usersCache.putIfAbsent(ALL_USERS_KEY, now);

    // return the list of users
    ctx.header("Last-Modified", now.toString());
    ctx.json(result);
  }

  public void getOne(Context ctx) {

    // get username from parameter
    String username = ctx.pathParamAsClass("username", String.class).get();

    // cache
    LocalDateTime lastKnown =
        ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);

    if (lastKnown != null
        && usersCache.containsKey(username)
        && usersCache.get(username).equals(lastKnown)) {
      throw new NotModifiedResponse();
    }

    // retrieve user
    User user = users.get(username);

    // check if not found
    if (user == null) {
      throw new NotFoundResponse();
    }

    // cache
    LocalDateTime now =
        usersCache.containsKey(username) ? usersCache.get(username) : LocalDateTime.now();
    usersCache.putIfAbsent(username, now);

    // send user back
    ctx.header("Last-Modified", now.toString());
    ctx.json(user);
  }

  public void update(Context ctx) {

    // get username from parameter
    String username = ctx.pathParamAsClass("username", String.class).get();

    // cache
    LocalDateTime lastKnown =
        ctx.headerAsClass("If-Unmodified-Since", LocalDateTime.class).getOrDefault(null);

    if (lastKnown != null
        && usersCache.containsKey(username)
        && !usersCache.get(username).equals(lastKnown)) {
      throw new PreconditionFailedResponse();
    }

    // check the user exists in the database
    if (!users.containsKey(username)) {
      throw new NotFoundResponse();
    }

    // get updated user
    User updateUser = validateBody(ctx);

    // check the username is unique
    for (User user : users.values()) {
      if (!user.username().equals(username)
          && updateUser.username().equalsIgnoreCase(user.username())) {
        throw new ConflictResponse();
      }
    }

    // put user inside the database
    users.put(username, updateUser);

    // cache
    LocalDateTime now = LocalDateTime.now();
    usersCache.put(username, now);
    usersCache.remove(ALL_USERS_KEY);

    // return update user
    ctx.header("Last-Modified", now.toString());
    ctx.json(updateUser);
  }

  public void delete(Context ctx) {

    // retrieve username from path parameter
    String username = ctx.pathParamAsClass("username", String.class).get();

    // cache
    LocalDateTime lastKnown =
        ctx.headerAsClass("If-Unmodified-Since", LocalDateTime.class).getOrDefault(null);

    if (lastKnown != null
        && usersCache.containsKey(username)
        && !usersCache.get(username).equals(lastKnown)) {
      throw new PreconditionFailedResponse();
    }

    // can't find user
    if (!users.containsKey(username)) {
      throw new NotFoundResponse();
    }

    // remove user on astronomical objects
    for (Map.Entry<Integer, AstronomicalObject> entry : objects.entrySet()) {
      AstronomicalObject obj = entry.getValue();

      if (username.equals(obj.created_by())) {
        AstronomicalObject updated = obj.copyWithNullCreator();

        // replace the object
        objects.replace(entry.getKey(), obj, updated);
      }
    }

    // remove user from database
    users.remove(username);
    usersCache.remove(username);
    usersCache.remove(ALL_USERS_KEY);

    // send status
    ctx.status(HttpStatus.NO_CONTENT);
  }

  private User validateBody(Context ctx) {
    return ctx.bodyValidator(User.class)
        .check(obj -> obj.username() != null, "Missing username")
        .check(obj -> obj.email() != null, "Missing email")
        .check(obj -> obj.date_of_birth() != null, "Missing date of birth")
        .check(obj -> obj.diploma() != null, "Missing diploma")
        .check(obj -> obj.biography() != null, "Missing biography")
        .get();
  }
}
