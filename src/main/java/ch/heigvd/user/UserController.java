package ch.heigvd.user;

// JAVALIN
import io.javalin.http.*;

import java.util.concurrent.ConcurrentMap;

public class UserController {

    private final ConcurrentMap<String, User> users;

    public UserController(ConcurrentMap<String, User> users) {
        this.users = users;
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

        ctx.status(HttpStatus.CREATED);
        ctx.json(newUser);
    }

    public void getAll(Context ctx) {
        // TODO implement filters
        ctx.json(users);
    }

    public void getOne(Context ctx) {

        String username = ctx.pathParamAsClass("username", String.class).get();

        User user = users.get(username);

        if (user == null) {
            throw new NotFoundResponse();
        }

        ctx.json(user);
    }

    public void update(Context ctx) {

        String username = ctx.pathParamAsClass("username", String.class).get();

        if (!users.containsKey(username)) {
            throw new NotFoundResponse();
        }

        User updateUser = validateBody(ctx);

        for (User user : users.values()) {
            if (updateUser.username().equalsIgnoreCase(user.username())) {
                throw new ConflictResponse();
            }
        }

        users.put(username, updateUser);

        ctx.json(updateUser);
    }

    public void delete(Context ctx) {

        String username = ctx.pathParamAsClass("username", String.class).get();

        if (!users.containsKey(username)) {
            throw new NotFoundResponse();
        }

        users.remove(username);

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
