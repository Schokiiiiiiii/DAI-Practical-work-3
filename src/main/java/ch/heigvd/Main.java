package ch.heigvd;

// CONTROLLERS
import ch.heigvd.object.AstronomicalObject;
import ch.heigvd.object.AstronomicalObjectController;
import ch.heigvd.user.User;
import ch.heigvd.user.UserController;
import io.javalin.Javalin;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Main {

  public static final int PORT = 8080;

  public static void main(String[] args) {

    // Create app
    Javalin app =
        Javalin.create(
            config -> {
              config.validation.register(LocalDateTime.class, LocalDateTime::parse);
            });

    // Database
    ConcurrentMap<String, User> users = new ConcurrentHashMap<>();
    ConcurrentMap<Integer, AstronomicalObject> objects = new ConcurrentHashMap<>();

    // Cache
    ConcurrentMap<String, LocalDateTime> usersCache = new ConcurrentHashMap<>();
    ConcurrentMap<Integer, LocalDateTime> objectsCache = new ConcurrentHashMap<>();

    // Controllers
    UserController userController = new UserController(users, usersCache, objects);
    AstronomicalObjectController objectController =
        new AstronomicalObjectController(objects, objectsCache, users);

    // User routes
    app.post("/user", userController::create);
    app.get("/user", userController::getAll);
    app.get("/user/{username}", userController::getOne);
    app.put("/user/{username}", userController::update); // can be patch or put to chose
    app.delete("/user/{username}", userController::delete);

    // Object routes
    app.post("/object", objectController::create);
    app.get("/object", objectController::getAll);
    app.get("/object/{id}", objectController::getOne);
    app.put("/object/{id}", objectController::update);
    app.delete("/object/{id}", objectController::delete);

    app.start(PORT);
  }
}
