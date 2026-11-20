package software.ulpgc.katas.service.application.domain;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(int id) {
        super("La película con id " + id + " no está en el catálogo");
    }
}