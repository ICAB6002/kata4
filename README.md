# Kata 4 - Servicio web con Javalin

## Objetivo

En esta kata he hecho un servicio web pequeño con Javalin sobre un catálogo de películas. El servicio tiene rutas HTTP que devuelven las películas en formato JSON.

La idea era practicar puertos y adaptadores: separar la lógica del programa (dominio y casos de uso) de la parte de HTTP (Javalin) y de la conversión a JSON (Gson). Así los casos de uso no dependen de ninguna librería y se pueden probar sin arrancar el servidor.

## Cómo compilar y ejecutar

1. Abrir el proyecto en IntelliJ IDEA. Al detectar el `pom.xml`, descarga las dependencias.
2. Compilar con `mvn compile` o desde IntelliJ con *Build > Build Project*.
3. Ejecutar la clase `software.ulpgc.katas.service.Main`. El servicio arranca en el puerto 8081 y en la consola sale:
   ```
   Listening on http://localhost:8081/
   ```
4. Probar las rutas desde el navegador o con `curl` (los ejemplos están más abajo).
5. Para ejecutar los tests: `mvn test`, o clic derecho en `src/test/java` > *Run 'All Tests'*.

## Dependencias y versión de JDK

- JDK 27
- Javalin 7.2.4: para crear el servidor y las rutas.
- Gson 2.15.0: para convertir las películas a JSON.
- slf4j-simple 2.0.20: para que Javalin muestre sus mensajes en la consola (por ejemplo, en qué puerto arranca).
- JUnit 5.13.4 y maven-surefire-plugin 3.5.3: para los tests.

## Estructura y clases principales

```
service/
├── Main.java
├── application/
│   ├── UseCase.java
│   ├── domain/        Movie, MovieNotFoundException
│   ├── ports/         MovieRepository, MovieSerializer
│   └── usecases/      GetMovieUseCase, ListMoviesUseCase
└── infrastructure/adapters/
    ├── EndPoint.java
    ├── InMemoryMovieRepository.java
    ├── JsonMovieSerializer.java
    └── endpoints/     MovieEndPoint, MoviesEndPoint
```

- `Movie`: es el modelo, un record con id, título, director, año y nota.
- `MovieNotFoundException`: la excepción que se lanza cuando se pide una película que no existe.
- `MovieRepository` y `MovieSerializer`: son los puertos. Dicen lo que necesita la aplicación (obtener películas y pasarlas a texto) sin decir cómo se hace.
- `GetMovieUseCase` y `ListMoviesUseCase`: los casos de uso. Uno busca una película por id y el otro devuelve la lista, filtrada por año si se indica.
- `InMemoryMovieRepository`: guarda las películas de ejemplo en una lista en memoria.
- `JsonMovieSerializer`: convierte las películas a JSON usando Gson. Es la única clase que usa Gson.
- `MovieEndPoint` y `MoviesEndPoint`: los manejadores de Javalin. Leen los datos de la petición, llaman al caso de uso y ponen el código de estado de la respuesta.
- `Main`: crea todas las piezas, registra las rutas y arranca el servidor.

## Rutas del servicio

El servicio escucha en el puerto **8081**.

| Método | Ruta | Datos que recibe | Respuesta |
|---|---|---|---|
| GET | `/movies` | `year` (opcional, en la URL como `?year=2010`) | 200 con la lista de películas en JSON |
| GET | `/movies/{id}` | `id` de la película en la ruta | 200 con la película en JSON |

Errores que devuelve:
- **400** si el `id` o el `year` no son un número.
- **404** si se pide una película que no existe.

Los errores se devuelven como texto con un mensaje que explica qué ha pasado.

### Ejemplos

En Windows hay que usar `curl.exe` en lugar de `curl`. La opción `-i` sirve para ver también el código de estado.

**Todas las películas**
```
curl.exe -i http://localhost:8081/movies
```
```
HTTP/1.1 200 OK
Content-Type: application/json

[{"id":1,"title":"El viaje de Chihiro","director":"Hayao Miyazaki","year":2001,"rating":8.6},{"id":2,"title":"Parásitos","director":"Bong Joon-ho","year":2019,"rating":8.5},{"id":3,"title":"Interstellar","director":"Christopher Nolan","year":2014,"rating":8.7},{"id":4,"title":"El laberinto del fauno","director":"Guillermo del Toro","year":2006,"rating":8.2},{"id":5,"title":"Origen","director":"Christopher Nolan","year":2010,"rating":8.8}]
```

**Películas de un año**
```
curl.exe -i "http://localhost:8081/movies?year=2010"
```
```
HTTP/1.1 200 OK
Content-Type: application/json

[{"id":5,"title":"Origen","director":"Christopher Nolan","year":2010,"rating":8.8}]
```

**Un año sin películas**
```
curl.exe -i "http://localhost:8081/movies?year=1900"
```
```
HTTP/1.1 200 OK
Content-Type: application/json

[]
```

**Una película por id**
```
curl.exe -i http://localhost:8081/movies/3
```
```
HTTP/1.1 200 OK
Content-Type: application/json

{"id":3,"title":"Interstellar","director":"Christopher Nolan","year":2014,"rating":8.7}
```

**Película que no existe**
```
curl.exe -i http://localhost:8081/movies/99
```
```
HTTP/1.1 404 Not Found
Content-Type: text/plain;charset=utf-8

La película con id 99 no está en el catálogo
```

**Id que no es un número**
```
curl.exe -i http://localhost:8081/movies/abc
```
```
HTTP/1.1 400 Bad Request
Content-Type: text/plain;charset=utf-8

Id no válido: 'abc'. Debe ser un número entero
```

**Año que no es un número**
```
curl.exe -i "http://localhost:8081/movies?year=abc"
```
```
HTTP/1.1 400 Bad Request
Content-Type: text/plain;charset=utf-8

Año no válido: 'abc'. Debe ser un número entero
```

## Vídeo

Enlace al vídeo (máximo 7 minutos): https://youtu.be/X18xhnYT7Ww

## Cómo he repetido la kata

He hecho el servicio tres veces desde cero, cada vez en una rama distinta creada desde `master`. En las tres he seguido los mismos pasos (dependencias, dominio y puertos, casos de uso, adaptadores, endpoints y Main) y he mantenido el mismo dominio, las mismas rutas y la misma separación de responsabilidades.

- `first-try`: la primera versión.
- `second-try`: igual, pero cambiando los datos de ejemplo (otras películas).
- `third-try`: igual, pero cambiando los mensajes de validación. Por ejemplo, el 404 pasa de "No existe ninguna película con id 99" a "La película con id 99 no está en el catálogo".
- `develop`: parte de `third-try` y añade los tests y este README. Después se ha unido a `master`, que tiene la versión final.

Mientras repetía la kata corregí dos fallos: las respuestas de error salían marcadas como JSON aunque eran texto, y las tildes de los mensajes se veían mal en el navegador. Ahora solo las respuestas correctas van como `application/json`, y los errores van como texto en UTF-8.

## Verificación

### Tests

He hecho tests de los dos casos de uso sin arrancar el servidor. Crean el caso de uso con el repositorio en memoria y el serializador JSON y comprueban:

- `GetMovieUseCaseTest`: que una película que existe se devuelve en JSON, que un id que no existe lanza `MovieNotFoundException` y que un id que no es un número da error.
- `ListMoviesUseCaseTest`: que sin año devuelve las 5 películas, que con el año 2010 devuelve solo Origen, que con un año sin películas devuelve `[]` y que un año que no es un número da error.

Resultado: los 7 tests pasan.

### Pruebas con curl

Con el servicio arrancado he probado cada ruta con una petición correcta y también las peticiones con errores. Todas dan el resultado esperado (las salidas son las de la sección de ejemplos):

- `GET /movies` → 200, las 5 películas
- `GET /movies?year=2010` → 200, solo Origen
- `GET /movies?year=1900` → 200, `[]`
- `GET /movies/3` → 200, Interstellar
- `GET /movies/99` → 404
- `GET /movies/abc` → 400
- `GET /movies?year=abc` → 400
