# REST API проекта "Метеорология. Температура воздуха в г. Москве"

Реализован API, регистрирующий показания температуры воздуха и информацию о дожде (идет дождь в момент отправки показания или нет).

Сервис позволяет регистрировать новые сенсоры, отправлять показания сенсоров на сервер.

Клиент для сервиса реализован в отдельном репозитории https://github.com/priorgnewb/meteorology-rest-client

Обмен между сервисами реализован посредством RestTemplate.

Стэк технологий Spring Boot, Spring Data JPA, PostgreSQL, Rest API

## Инструкция по установке

**1. Клонирование репозитория**

```bash
git clone https://github.com/priorgnewb/meteorology-rest-app.git
```

**2. Создание базы данных PostgreSQL**

```bash
create database meteorology_db
```
- run `sql/meteorology_create_tables.sql`

**3. Настройка подключения к PostgreSQL**

+ открыть `src/main/resources/application.properties`
+ указать для `spring.datasource.username`, `spring.datasource.password` и других параметров значения, используемые вами в PostgreSQL

**4. Запуск проекта в IntellijIDEA**

**4.1. Импорт проекта в IntellijIDEA**

`File -> New -> Project -> From existing sources`

и указать путь к `pom.xml`

**4.2. Запуск проекта**

Запустить main метод класса MeteorologyRestApp

**4.3. Тестирование Rest APIs**

Тестирование API удобно выполнять в Postman или в другом REST-клиенте, приложение доступно по адресу `http://localhost:8080`

Сервис предоставляет следующие APIs:

| Метод | URL | Описание | Валидный шаблон для запроса | 
| ------ | --- | ----------- | --------------------------- |
| GET    | /sensors | Отобразить список всех сенсоров |  |
| GET    | /sensors/{sensor_name} | Отобразить сенсор отдельно |  |
| POST    | /sensors/registration | Зарегистрировать новый сенсор | [JSON](#add_sensor) |
| GET    | /measurements | Отобразить список всех показаний сенсоров |  |
| GET    | /measurements/{id} | Отобразить показание сенсора отдельно |  |
| POST    | /measurements/add | Добавить показание сенсора | [JSON](#add_measurement) |
| DELETE    | /measurements/{id} | Удалить показание сенсора |  |
| GET    | /measurements/rainyDaysCount | Отобразить количество показаний с дождем |  |

## Валидные шаблоны запросов в формате JSON

##### <a id="add_sensor">Зарегистрировать новый сенсор -> /sensors/registration</a>
```json
{
    "name": "Moscow_5"
}
```

##### <a id="add_measurement">Добавить показание сенсора -> /measurements/add</a>
```json
{
    "value": 19.2,
    "raining": true,
    "sensor": {
        "name": "Moscow_1"
    }
}
```