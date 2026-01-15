package ch.heigvd.object;

public record AstronomicalObject (
    Integer id,
    String name,
    String type,
    int diameter,
    double mass,
    double escape_velocity,
    double surface_temperature,
    String created_by
){}
