<h1>🗺️ Xmap-API (бэкенд приложения карты спотов)</h1>

<h2>❓ Хэлпа разработчику (FAQ)</h2>

<h3>👉 Проблемы в postgresql:</h3>
<h4>Не работает генерация UUID в postgres:</h4>
1) Для БД должны быть добавлено расширение:
```postgresql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";  -- для сгенерированных UUID
```
2) Чтобы оно поставилось должен быть установлен contrib пакет:
```bash
# для 14 версии это выглядит так
sudo dnf install postgresql14-contrib
```

<h3>👉 Как запустить с нужным application.yml?</h3>
P.S. можно создавать несколько конфигов для приложения.</br>
Основной - application.yml, остальные - application-<имя_профиля>.yml
1) Задание профиля в команде (для сервака):
```bash
java -jar myapp.jar --spring.profiles.active=имя_профиля
# например
java -jar /home/viktor/xmap-api/Xmap-API-0.0.1-SNAPSHOT.jar --spring.profiles.active=remote_server
```

2) Задание профиля в Intellij IDEA:
   - Перейдите в Run → Edit Configurations.
   - Выберите вашу конфигурацию.
   - В поле VM options добавьте:
     ```bash
     -Dspring.profiles.active=имя_профиля
     ```
     
3) Включение профилей в другой профиль:
в application-<имя_профиля>.yml:
```yml
spring:
  profiles:
    active: имя_профиля
```

<h3>👉 Как создаются задачи/ветки/PR:</h3>
<p>Задача создается в issues. Название задачи - предложение на английском (чем короче, тем лучше). У issues есть порядковый номер с итератором, как я понял он у pull requests и issues почему-то общий, поэтому "номера веток" идут с пропусками.</p>
<p>В issue -> development -> create branch создается ветка, название генерится из номера issue и названия, вместо проблеов ставятся знаки "тире"</p>
<p>Можно прикреплять issues к project. Думаю, пока это не нужно, но там прикольная canban доска и может устанавливать сроки.</p>

<h3>👉 Как открыть Swagger документацию:</h3>
<p>http://localhost:8080/swagger-ui/index.html</p>

<h2>❓ Код-стайл</h2>
<h3>👉Логирование:</h3>
<ul>
  <li>Использовать внедрение логгера через Lombok (Аннотировать компонент <code>@Log4j2</code>)</li>
  <li>Используйте snake_case для переменных</li>
  <li>Записывайте переменные в квадратных скобках ('[', ']')</li>
  <li>Пишите двоеточие (':') перед блоком переменных</li>
  <li><code>log.trace()</code>, <code>log.debug()</code>, <code>log.info()</code> - информация, <code>log.warn()</code> - предупреждения, <code>log.error()</code> - критические ошибки</li>
</ul>

```java
log.info("Информация об изображении создана: [image_info_id = '{}']", imageInfo.getId());
```

<h3>👉Нейминг:</h3>
<p>Максимальное количество символов в строке - 130. По дефолту IntelliJ IDEA устанавливает ограничительную линию на 120 (перенастроить).</p>
<h4>👉Как называть даты в БД:</h4>

- inserted_at (дата вставки/добавления/создания строки в БД, тут речь не про дату создания объект в настоящем мире)
- updated_at (дата обновления строки в БД)
- deleted_at (дата удаления строки из БД, строки не удаляем, а создаем колонку boolean deleted)
- uploaded_at (дата выгрузки, например, файла в s3)
- created_at (если речь про дату создания объекта в физическом мире)

<h4>👉Как называть ключи JSON:</h4>
<p>(snake_case)</p>

```javascript
"some_key_name" : "value" 
```

<p>Многие библиотеки JSON (в Spring Framework), такие как Jackson, поддерживают формат camelCase из коробки.</p>
<p>Но было решено, что snake_case более удобен для чтения.</p>

<h4>👉Как называть ключи в структурах данных например в HashMap ("key_name", "value"):</h4>
<p>snake_case</p>

```java
Map<String, String>; someMap = new HashMap<>();
someMap.put("key_name_in_snake_case", "value");
```

<h4>👉Как называть пути роутов (эндпоинтов):</h4>
<p>kebab-case</p>

```java
@RequestMapping("/api/v1/image-service") //для всего контроллера

@PostMapping("/spot-image/{id}") //для метода
```

<h4>👉Как называть @PathVariable и @PathParam ключи?:</h4>
```java
@GetMapping("/products/{category}/{subCategory}")
public ResponseEntity<List<Product>> getProductsByCategory(
        @PathVariable("category") String category,
        @PathVariable("subCategory") String subCategory,
        @PathParam("somePathParam") String somePathParam) {
}
```

<h4>👉Размеры методов и классов:</h4>
<p>Максимальная длина методов - 100 строк. Максимальная длина класса - 1000 строк.</p>
<p>Если размер метода или класса превышает эти значения, новая бизнес-логика может быть добавлена только в виде вызовов методов других функций.</p>
<p>Но лучше внимательно рефакторить!</p>

<h4>👉Структура пакетов:</h4>
<p>Когда появляется более одного контроллера, сервиса, репозитория, модели, DTO и т.д., мы объединяем их в один пакет.</p>

<h3>👉 Внедрение зависимостей с использованием Lombok:</h3>
<p>Используйте @RequiredArgsConstructor и только его. 1 конструктор для внедрения зависимостей (без аннотации @Autowired).</p>
<p>Lombok сгенерирует конструктор, который принимает все необходимые зависимости, помеченные из переменных final или @NonNull.</p>

<h3>👉 Swagger V.3</h3>
<div>
  <ul>
  <li>
    <b>@Operation</b>: Эта аннотация используется для документирования отдельных операций в вашем контроллере. Она позволяет предоставить подробное описание операции, включая краткое описание (summary), подробное описание (description), теги (tags), параметры запроса, схему запроса и ответа.

        ```java
          @Operation(
            summary = "Get user by ID",
            description = "Returns a single user by ID",
            parameters = {
                @Parameter(name = "userId", description = "User ID", in = ParameterIn.PATH)
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "Successful operation", 
                  content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404", description = "User not found")
            },
            tags = {"User Operations"}
            )
          ```
          
  </li>
  <li>
      <b>@Parameter</b>: Используется для описания параметров запроса. Например, параметры пути, параметры запроса, параметры заголовков и т. д.

        ```java
          @Parameter(name = "userId", description = "User ID", in = ParameterIn.PATH)
          ```
          
  </li>
  <li>
    <b>@RequestBody</b>: Определяет тело запроса для операции. Используется для описания тела запроса для операций типа POST, PUT, и др.

        ```java
          @RequestBody(description = "User details", required = true, content = @Content(mediaType = "application/json"))
          ```
          
  </li>
  <li>
    <b>@ApiResponse</b>: Описывает возможные ответы от операции. Указывает код состояния HTTP, описание и схему ответа.

        ```java
          @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", 
            content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found")
            })
          ```
          
  </li>
  <li>
    <b>@Hidden</b>: Скрывает операцию в документации Swagger. Полезно, например, если вы хотите временно исключить операцию из документации.

        ```java
          @Hidden
          ```
          
  </li>
  <li>
    <b>@Tag</b>: Используется для группировки операций в документации Swagger по тегам. Это удобно для организации и структурирования больших API.

        ```java
          @Tag(name = "User Operations", description = "Endpoints for user-related operations")
          ```
          
  </li>
  
  </ul>
</div>

<h3>👉 Работа с access и refresh токенами</h3>
<div>
  <h4>Как кодировать/декодировать?</h4>
  <img src='https://github.com/ShulV/Xmap/blob/main/readme-images/doc/jwt_io_example.jpg' width='60%'>
</div>

<h2>❓ Некоторые пока что нерешенные проблемы приложения:</h2>
<p>...</p>
