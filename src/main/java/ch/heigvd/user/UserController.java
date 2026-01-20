package ch.heigvd.user;

import ch.heigvd.object.AstronomicalObject;
import io.javalin.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class UserController {

    private final ConcurrentMap<String, User> users;
    private final ConcurrentMap<Integer, AstronomicalObject> objects;

    public UserController(ConcurrentMap<String, User> users, ConcurrentMap<Integer, AstronomicalObject> objects) {
        this.users = users;
        this.objects = objects;
    }

    public void create(Context ctx) {

        // validate user
        User newUser = validateBody(ctx);

        // look for any other similar names
        for (User user : users.values())
            if (newUser.username().equalsIgnoreCase(user.username()))
                throw new ConflictResponse();

        // create
        newUser = new User(
                newUser.username(),
                newUser.email(),
                newUser.date_of_birth(),
                newUser.diploma(),
                newUser.biography()
        );

        // add user to database
        users.put(newUser.username(), newUser);

        // send status and user back
        ctx.status(HttpStatus.CREATED);
        ctx.json(newUser);
    }

    public void getAll(Context ctx) {

        // put all users into a list
        List<User> result = new ArrayList<>(users.values());

        // return the list of users
        ctx.json(result);
    }

    public void getOne(Context ctx) {

        // get username from parameter
        String username = ctx.pathParamAsClass("username", String.class).get();

        // retrieve user inside the database
        User user = users.get(username);

        // check if not found
        if (user == null) {
            throw new NotFoundResponse();
        }

        // send user back
        ctx.json(user);
    }

    public void update(Context ctx) {

        // get username from parameter
        String username = ctx.pathParamAsClass("username", String.class).get();

        // check the user exists in the database
        if (!users.containsKey(username)) {
            throw new NotFoundResponse();
        }

        // get updated user
        User updateUser = validateBody(ctx);

        // check the username is unique
        for (User user : users.values()) {
            if (updateUser.username().equalsIgnoreCase(user.username())) {
                throw new ConflictResponse();
            }
        }

        // put user inside the database
        users.put(username, updateUser);

        // return updated user
        ctx.json(updateUser);
    }

    public void delete(Context ctx) {

        // retrieve username from path parameter
        String username = ctx.pathParamAsClass("username", String.class).get();

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
