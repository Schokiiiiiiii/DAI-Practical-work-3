package ch.heigvd;

// CONTROLLERS
import ch.heigvd.object.ObjectController;
import ch.heigvd.user.UserController;

// JAVALIN
import io.javalin.Javalin;

public class Main {

    public static final int PORT = 8080;

    public static void main(String[] args) {

        // create app
        Javalin app = Javalin.create();

        // Database
        // TODO add database structures

        // Controllers
        UserController userController = new UserController();
        ObjectController objectController = new ObjectController();

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