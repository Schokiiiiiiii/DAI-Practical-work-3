package ch.heigvd.object;

public class Object {

    // ATTRIBUTES
    private Integer id;
    private String name;
    private String type;
    private int diameter;
    private double mass;
    private double escape_velocity;
    private double surface_temperature;
    private String created_by;

    public Object(Integer id, String name, String type, int diameter, double mass, double escape_velocity,
                  double surface_temperature, String created_by) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.diameter = diameter;
        this.mass = mass;
        this.escape_velocity = escape_velocity;
        this.surface_temperature = surface_temperature;
        this.created_by = created_by;
    }
}
