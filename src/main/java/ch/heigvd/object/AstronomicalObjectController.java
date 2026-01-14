package ch.heigvd.object;

import io.javalin.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AstronomicalObjectController {

    private final ConcurrentMap<Integer, AstronomicalObject> objects;
    private final AtomicInteger next_id = new AtomicInteger(1);

    public AstronomicalObjectController(ConcurrentMap<Integer, AstronomicalObject> objects) {
        this.objects = objects;
    }

    public void create(Context ctx) {
        AstronomicalObject payload =
                ctx.bodyValidator(AstronomicalObject.class)
                        .check(obj -> obj.name() != null, "Missing object name")
                        .check(obj -> obj.type() != null, "Missing object type")
                        .check(obj -> obj.diameter() != 0, "Missing object diameter")
                        .check(obj -> obj.mass() != 0.d, "Missing object's mass")
                        .check(obj -> obj.escape_velocity() != 0.d, "Missing object's escape velocity")
                        .check(obj -> obj.surface_temperature() != 0.d, "Missing object's surface temperature")
                        .check(obj -> obj.created_by() != null, "Missing object's creator")
                        .get();

        for (AstronomicalObject object : objects.values()) {
            if (payload.name().equalsIgnoreCase(object.name())) {
                throw new ConflictResponse();
            }
        }

        AstronomicalObject stored = new AstronomicalObject(
                next_id.getAndIncrement(),
                payload.name(),
                payload.type(),
                payload.diameter(),
                payload.mass(),
                payload.escape_velocity(),
                payload.surface_temperature(),
                payload.created_by()
        );

        objects.put(stored.id(), stored);

        ctx.status(HttpStatus.CREATED).json(stored);
    }

    public void getAll(Context ctx) {
        List<AstronomicalObject> result = new ArrayList<>();

        Integer diameterGe = optInt(ctx, "diameter_ge");
        Integer diameterLe = optInt(ctx, "diameter_le");
        Double massGe = optDouble(ctx, "mass_ge");
        Double massLe = optDouble(ctx, "mass_le");
        Double escapeGe = optDouble(ctx, "escape_velocity_ge");
        Double escapeLe = optDouble(ctx, "escape_velocity_le");
        Double tempGe = optDouble(ctx, "surface_temperature_ge");
        Double tempLe = optDouble(ctx, "surface_temperature_le");

        String type = ctx.queryParam("type");
        String createdBy = ctx.queryParam("created_by");

        for (AstronomicalObject obj : objects.values()) {

            if (type != null && !type.equalsIgnoreCase(obj.type())) continue;
            if (createdBy != null && !createdBy.equals(obj.created_by())) continue;

            if (diameterGe != null && obj.diameter() < diameterGe) continue;
            if (diameterLe != null && obj.diameter() > diameterLe) continue;

            if (massGe != null && obj.mass() < massGe) continue;
            if (massLe != null && obj.mass() > massLe) continue;

            if (escapeGe != null && obj.escape_velocity() < escapeGe) continue;
            if (escapeLe != null && obj.escape_velocity() > escapeLe) continue;

            if (tempGe != null && obj.surface_temperature() < tempGe) continue;
            if (tempLe != null && obj.surface_temperature() > tempLe) continue;

            result.add(obj);
        }

        ctx.status(HttpStatus.OK).json(result);
    }

    private static Integer optInt(Context ctx, String name) {
        String raw = ctx.queryParam(name);
        if (raw == null) return null;
        try {
            return Integer.valueOf(raw);
        } catch (NumberFormatException e) {
            throw new BadRequestResponse("Query param '" + name + "' must be an integer");
        }
    }

    private static Double optDouble(Context ctx, String name) {
        String raw = ctx.queryParam(name);
        if (raw == null) return null;
        try {
            return Double.valueOf(raw);
        } catch (NumberFormatException e) {
            throw new BadRequestResponse("Query param '" + name + "' must be a number");
        }
    }

    public void getOne(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        AstronomicalObject object = objects.get(id);
        if (object == null) throw new NotFoundResponse();
        ctx.status(HttpStatus.OK).json(object);
    }

    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        AstronomicalObject existing = objects.get(id);
        if (existing == null) throw new NotFoundResponse();

        AstronomicalObject payload =
                ctx.bodyValidator(AstronomicalObject.class)
                        .check(obj -> obj.name() != null, "Missing object name")
                        .check(obj -> obj.type() != null, "Missing object type")
                        .check(obj -> obj.diameter() != 0, "Missing object diameter")
                        .check(obj -> obj.mass() != 0.d, "Missing object's mass")
                        .check(obj -> obj.escape_velocity() != 0.d, "Missing object's escape velocity")
                        .check(obj -> obj.surface_temperature() != 0.d, "Missing object's surface temperature")
                        .check(obj -> obj.created_by() != null, "Missing object's creator")
                        .get();

        // Conflict only if another object (different id) has the same name
        for (AstronomicalObject obj : objects.values()) {
            if (!obj.id().equals(id) && payload.name().equalsIgnoreCase(obj.name())) {
                throw new ConflictResponse();
            }
        }

        AstronomicalObject updated = new AstronomicalObject(
                id,
                payload.name(),
                payload.type(),
                payload.diameter(),
                payload.mass(),
                payload.escape_velocity(),
                payload.surface_temperature(),
                payload.created_by()
        );

        objects.put(id, updated);
        ctx.status(HttpStatus.OK).json(updated);
    }

    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();
        AstronomicalObject removed = objects.remove(id);
        if (removed == null) throw new NotFoundResponse();

        ctx.status(HttpStatus.NO_CONTENT);
    }
}
