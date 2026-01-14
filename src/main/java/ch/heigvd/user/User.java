package ch.heigvd.user;

public record User (String username,
                    String email,
                    String date_of_birth,
                    String diploma,
                    String biography,
                    int nb_planet_created,
                    int nb_planet_lost) {}
