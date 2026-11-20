package software.ulpgc.katas.service.infrastructure.adapters.endpoints;

import io.javalin.http.Context;
import software.ulpgc.katas.service.application.UseCase;
import software.ulpgc.katas.service.infrastructure.adapters.EndPoint;

public class MoviesEndPoint implements EndPoint {
    private final UseCase useCase;
    private final Context context;

    private MoviesEndPoint(UseCase useCase, Context context) {
        this.useCase = useCase;
        this.context = context;
    }

    public static EndPoint use(UseCase useCase, Context context) {
        return new MoviesEndPoint(useCase, context);
    }

    @Override
    public void execute() {
        String year = context.queryParam("year");
        try {
            context.contentType("application/json").result(useCase.execute(year));
        } catch (NumberFormatException e) {
            context.status(400).result("El año debe ser un número: " + year);
        }
    }
}