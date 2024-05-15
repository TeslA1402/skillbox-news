# NEWS

## Описание

Веб-приложение «Новостной сервис»

## Функции

* создавать пользователей и управлять ими,
* создавать категории новостей и управлять ими,
* создавать новости и управлять ими,
* создавать комментарии для новостей и управлять ими.

## Сборка

Сборка производится с помощью Gradle

```shell
gradle build
```

## Запуск программы

### Поднять базу

```shell
cd ./infra; docker-compose up -d; cd ../
```

### Запустить приложение

```shell
java -jar .\build\libs\skillbox-news-0.0.1-SNAPSHOT.jar
```

### Swagger

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
