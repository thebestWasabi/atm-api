## Проект "ATM-Api"
Веб-интерфейс для проекта не подразумевается. 

Так же подключил Flyway для миграции (V1__chema.sql и V2__demo_data.sql лежат в resources.db.migration). В корге проекта лежит файл accounts.http, в нем прописаны нужные запросы.

В приложении можно положить деньги на аккаунт, снять деньги с аккаунта, перевести деньги с одного аккаунта на другой. Так же все операции/транзакции записываются в таблицу operation_list. У аккаунта по id можно вызвать историю транзакций с фильтрацией по датам (max :: min) или без фильтрации по датам. Обработка ошибок присутствует(все выводиться в ответах в json-формате).

В базе данных 2-е таблицы /account и operation_list/

account:
id | name | amount
:--|:----:|-------:
1 | Maxim | 1000
2 | Vadim | 1000
3 | Vladimir | 1000
4 | Daria | 1000

operation_list:
id | account_id | amount | transaction_type | created_at
:--|:----------:|:------:|:----------------:|:---------:
1 | 5 | 250.00 | DEPOSIT | 2023-05-10 10:52:48.413162

---

__GET localhost:8080/api/v1/accounts__
Получение списка всех аккаунтов. Пример ответа:
```
[
    {
        "id": 3,
        "name": "Vladimir",
        "amount": 2670
    },
    {
        "id": 2,
        "name": "Vadim",
        "amount": 500
    }
]

```

__GET localhost:8080/api/v1/accounts/2__
Получить аккаунт по ID. Пример ответа:
```
{
    "id": 1,
    "name": "Maxim",
    "amount": 700
}
```
Если аккаунта с запрошенным ID нет в базе данных, то в ответе будет status 404 Not Found
```
Аккаунт с таким id не найден
```

### Создать аккаунт
__POST localhost:8080/api/v1/accounts__
```
{
  "email": "maxim23@ya.ru"
}
```

### Изменить email
__PUT localhost:8080/api/v1/accounts/3__
```
{
  "email": "olyacotleta@icloud.ru"
}
```

### Удалить аккаунт по id
__DELETE localhost:8080/api/v1/accounts/6__

__PUT localhost:8080/api/v1/transactions/take__
Вывести деньги с аккаунта. Пример с запросом в теле (выводим деньги с акка(id=1) на сумму 300):
```
{
    "accountId": 1,
    "amount": 300
}
```
Если денег на счете не хватает, то ответ будет (status 200 OK, так как ошибки нет):
```
На балансе не достаточно средств
```

__PUT localhost:8080/api/v1/transactions/put__
Положить деньги на счет. Пример запроса в теле:
```
{
    "accountId": 2,
    "amount": 20000
}
```

__PUT localhost:8080/api/v1/transactions/transfer__
Перевести деньги с одного счета на другой. Пример запроса в теле:
```
{
  "senderAccountId": 1,
  "receiverAccountId": 2,
  "amount": 50
}
```

__GET localhost:8080/api/v1/operations?accountId=5__
Получить операции по аккаунту за все время

__GET localhost:8080/api/v1/operations?accountId=6&startDate=10-05-2023&endDate=10-05-2023__
Получить операции по аккаунту с сортировкой по дате
