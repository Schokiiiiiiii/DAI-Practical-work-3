package ch.heigvd.object;

public record AstronomicalObject(
    Integer id,
    String name,
    String type,
    int diameter,
    double mass,
    double escape_velocity,
    double surface_temperature,
    String created_by) {
  public AstronomicalObject copyWithNullCreator() {
    return new AstronomicalObject(
        this.id(),
        this.name(),
        this.type(),
        this.diameter(),
        this.mass(),
        this.escape_velocity(),
        this.surface_temperature(),
        null);
  }
}
