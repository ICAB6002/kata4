package software.ulpgc.katas.service.application.domain;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(int id) {
        super("No existe ninguna película con id " + id);
    }
}