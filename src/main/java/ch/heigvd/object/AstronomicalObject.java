package ch.heigvd.object;

public class AstronomicalObject {

    private final Integer id;
    private final String name;
    private final String type;
    private final int diameter;
    private final double mass;
    private final double escape_velocity;
    private final double surface_temperature;
    private String created_by;

    AstronomicalObject(Integer id,
                       String name,
                       String type,
                       int diameter,
                       double mass,
                       double escape_velocity,
                       double surface_temperature,
                       String created_by) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.diameter = diameter;
        this.mass = mass;
        this.escape_velocity = escape_velocity;
        this.surface_temperature = surface_temperature;
        this.created_by = created_by;
    }

    public Integer id() { return id; }
    public String name() { return name; }
    public String type() { return type; }
    public int diameter() { return diameter; }
    public double mass() { return mass; }
    public double escape_velocity() { return escape_velocity; }
    public double surface_temperature() { return surface_temperature; }
    public String created_by() { return created_by; }

    public void setCreatedByToNull() {
        created_by = null;
    }
}
