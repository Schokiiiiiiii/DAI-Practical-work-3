package ch.heigvd.user;

// JAVALIN
import io.javalin.http.*;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentMap;

public class UserController {

    private final ConcurrentMap<String, User> users;
    private final ConcurrentMap<String, LocalDateTime> usersCache;
    private static final String ALL_USERS_KEY = "__ALL_USERS__";

    public UserController(ConcurrentMap<String, User> users, ConcurrentMap<String, LocalDateTime> usersCache) {
        this.users = users;
        this.usersCache = usersCache;
    }

    public void create(Context ctx) {

        User newUser = validateBody(ctx);

        for (User user : users.values())
            if (newUser.username().equalsIgnoreCase(user.email()))
                throw new ConflictResponse();

        newUser = new User(
                newUser.username(),
                newUser.email(),
                newUser.date_of_birth(),
                newUser.diploma(),
                newUser.biography(),
                0,
                0
        );

        users.put(newUser.username(), newUser);

        LocalDateTime now = LocalDateTime.now();
        usersCache.put(newUser.username(), now);
        usersCache.remove(ALL_USERS_KEY);

        ctx.header("Last-Modified", now.toString());
        ctx.status(HttpStatus.CREATED);
        ctx.json(newUser);
    }

    public void getAll(Context ctx) {
        LocalDateTime lastKnown = ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);

        if (lastKnown != null && usersCache.containsKey(ALL_USERS_KEY)
                && usersCache.get(ALL_USERS_KEY).equals(lastKnown)) {
            throw new NotModifiedResponse();
        }

        LocalDateTime now = usersCache.containsKey(ALL_USERS_KEY)
                ? usersCache.get(ALL_USERS_KEY)
                : LocalDateTime.now();
        usersCache.putIfAbsent(ALL_USERS_KEY, now);

        ctx.header("Last-Modified", now.toString());
        ctx.json(users);
    }

    public void getOne(Context ctx) {
        String username = ctx.pathParamAsClass("username", String.class).get();

        LocalDateTime lastKnown = ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);

        if (lastKnown != null && usersCache.containsKey(username)
                && usersCache.get(username).equals(lastKnown)) {
            throw new NotModifiedResponse();
        }

        User user = users.get(username);

        if (user == null) {
            throw new NotFoundResponse();
        }

        LocalDateTime now = usersCache.containsKey(username)
                ? usersCache.get(username)
                : LocalDateTime.now();
        usersCache.putIfAbsent(username, now);

        ctx.header("Last-Modified", now.toString());
        ctx.json(user);
    }

    public void update(Context ctx) {
        String username = ctx.pathParamAsClass("username", String.class).get();

        LocalDateTime lastKnown = ctx.headerAsClass("If-Unmodified-Since", LocalDateTime.class).getOrDefault(null);

        if (lastKnown != null && usersCache.containsKey(username)
                && !usersCache.get(username).equals(lastKnown)) {
            throw new PreconditionFailedResponse();
        }

        if (!users.containsKey(username)) {
            throw new NotFoundResponse();
        }

        User updateUser = validateBody(ctx);

        for (User user : users.values()) {
            if (!user.username().equals(username)
                    && updateUser.username().equalsIgnoreCase(user.username())) {
                throw new ConflictResponse();
            }
        }

        users.put(username, updateUser);

        LocalDateTime now = LocalDateTime.now();
        usersCache.put(username, now);
        usersCache.remove(ALL_USERS_KEY);

        ctx.header("Last-Modified", now.toString());
        ctx.json(updateUser);
    }

    public void delete(Context ctx) {
        String username = ctx.pathParamAsClass("username", String.class).get();

        LocalDateTime lastKnown = ctx.headerAsClass("If-Unmodified-Since", LocalDateTime.class).getOrDefault(null);

        if (lastKnown != null && usersCache.containsKey(username)
                && !usersCache.get(username).equals(lastKnown)) {
            throw new PreconditionFailedResponse();
        }

        if (!users.containsKey(username)) {
            throw new NotFoundResponse();
        }

        users.remove(username);
        usersCache.remove(username);
        usersCache.remove(ALL_USERS_KEY);

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
