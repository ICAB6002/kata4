package software.ulpgc.katas.service.infrastructure.adapters.endpoints;

import io.javalin.http.Context;
import software.ulpgc.katas.service.application.UseCase;
import software.ulpgc.katas.service.application.domain.MovieNotFoundException;
import software.ulpgc.katas.service.infrastructure.adapters.EndPoint;

public class MovieEndPoint implements EndPoint {
    private final UseCase useCase;
    private final Context context;

    private MovieEndPoint(UseCase useCase, Context context) {
        this.useCase = useCase;
        this.context = context;
    }

    public static EndPoint use(UseCase useCase, Context context) {
        return new MovieEndPoint(useCase, context);
    }

    @Override
    public void execute() {
        String id = context.pathParam("id");
        try {
            String json = useCase.execute(id);
            context.status(200).contentType("application/json").result(json);
        } catch (NumberFormatException e) {
            context.status(400).contentType("text/plain; charset=utf-8").result("Id no válido: '" + id + "'. Debe ser un número entero");
        } catch (MovieNotFoundException e) {
            context.status(404).contentType("text/plain; charset=utf-8").result(e.getMessage());
        }
    }
}