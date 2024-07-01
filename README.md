# Сервис регистрации кандидатов

Метод регистрации доступен по `http://localhost:8080/api/candidate/register`

В теле запроса нужно передать объект:

```
{
    "last_name": "Кузьмичев",
    "first_name": "Даниил",
    "email": "dr-kuzmichev-1@example.ru",
    "role": "Разработчик Java"
}
```

Также доступен интерфейс сваггера по `http://localhost:8080/api/swagger-ui/index.html`