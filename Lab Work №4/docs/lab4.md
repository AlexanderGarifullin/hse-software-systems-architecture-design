# Изменения в БД + Описание работы
После лабораторной работы 3 я немного пересмотрел свою базу данных. Итоговая схема у меня вот такая:
![d_container](../assets/finaldb.png)

В рамках текущей лабораторной работы я рассмотрю основные методы API пользователей для работы с задачами. В частности я
рассмотрю CRUD для задач и тегов к задачам. Для упрощения все методы указаны без авторизации.

# Документация по API

## 1 Создание новой задачи для пользователя (createTask)
### Метод: `POST`
### URL: `/tasks/user/{userId}`
### Описание:
Создает новую задачу для пользователя с указанным `userId`.
### Параметры запроса:
1. `userId` (обязательный): Идентификатор пользователя, для которого создается задача. Тип параметра: `Long`.
2. Тело запроса: JSON-объект с данными новой задачи. Пример:
```json
{
    "name":"mom",
    "statement": null,
    "timeLimit": null,
    "memoryLimit": null,
    "solve": null
}
```
### Ответы:
1. `200 OK:` Тело ответа: Данные созданной задачи. Пример:
```json
{
   "id": 2,
   "name": "mom",
   "statement": null,
   "timeLimit": null,
   "memoryLimit": null,
   "solve": null
}
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если пользователь с указанным `userId` не найден. Пример:
```text
User not found
```

## 2 Получение задач пользователя (getTasksByUser)
### Метод: `GET`
### URL: `/tasks/user/{userId}`
### Описание:
Возвращает список задач, принадлежащих пользователю с указанным `userId`.
### Параметры запроса:
1. `userId` (обязательный): Идентификатор пользователя, чьи задачи необходимо получить. Тип параметра: `Long`.
### Ответы:
1. `200 OK`: Тело ответа: Список задач, ассоциированных с пользователем. Пример:
```json
[
   {
      "id": 1,
      "name": "dad",
      "statement": null,
      "timeLimit": null,
      "memoryLimit": null,
      "solve": null
   }
]
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если пользователь с указанным `userId` не найден. Пример:
```text
User not found
```

## 3 Получение задачи по ID (getTaskById)
### Метод: `GET`
### URL: `/tasks/{taskId}`
### Описание:
Возвращает задачу по её уникальному идентификатору.
### Параметры запроса:
1. `userId` (обязательный): Идентификатор пользователя, чьи задачи необходимо получить. Тип параметра: `Long`.
### Ответы:
1. `200 OK`: Тело ответа: Данные задачи по указанному `taskId`. Пример:
```json
{
   "id": 1,
   "name": "dad",
   "statement": null,
   "timeLimit": null,
   "memoryLimit": null,
   "solve": null
}
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если задача с указанным `taskId` не найдена. Пример:
```text
Task not found
```

## 4 Обновление существующей задачи (updateTask)
### Метод: `PUT`
### URL: `/tasks/{taskId}`
### Описание:
Обновляет данные задачи по её идентификатору.
### Параметры запроса:
1. `taskId` (обязательный): Идентификатор задачи, которую нужно обновить. Тип параметра: `Long`.
2. Тело запроса: JSON-объект с данными новой задачи. Пример:
```json
{
    "name":"new mom",
    "statement": null,
    "timeLimit": null,
    "memoryLimit": null,
    "solve": null
}
```

### Ответы:
1. `200 OK`: Тело ответа: Данные обновленной задачи. Пример:
```json
{
   "id": 1,
   "name":"new mom",
   "statement": null,
   "timeLimit": null,
   "memoryLimit": null,
   "solve": null
}
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если задача с указанным `taskId` не найдена. Пример:
```text
Task not found
```

## 5 Удаление задачи (deleteTask)
### Метод: `DELETE`
### URL: `/tasks/{taskId}`
### Описание:
Удаляет задачу по её идентификатору.
### Параметры запроса:
1. `taskId` (обязательный): Идентификатор задачи, которую нужно удалить. Тип параметра: `Long`.
### Ответы:
1. `204 No Content`: Тело ответа: Пустой ответ, указывающий на успешное удаление задачи.
2. `404 Not Found`: Сообщение об ошибке, если задача с указанным `taskId` не найдена. Пример:
```text
Task not found
```

## 6 Создание нового тега для задачи (createTag)
### Метод: `POST`
### URL: `/tags/task/{taskId}`
### Описание:
Создает новый тег для задачи с указанным `taskId`.
### Параметры запроса:
1. `taskId` (обязательный): Идентификатор задачи, для которой нужно создать тег. Тип параметра: `Long`.
2. Тело запроса: JSON-объект с данными нового тега. Пример:
```json
{
  "name":"my tag"
}
```
### Ответы:
1. `200 OK`: Тело ответа: Данные созданного тега. Пример:
```json
{
  "id": 2,
  "name": "my tag"
}
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если задача с указанным `taskId` не найдена. Пример:
```text
Task not found
```

## 7 Получить теги по задаче (getTagsByTask)
### Метод: `GET`
### URL: `/tags/task/{taskId}`
### Описание:
Возвращает список тегов, ассоциированных с задачей по ее идентификатору.
### Параметры запроса:
1. `taskId` (обязательный): Идентификатор задачи, для которой нужно получить теги. Тип параметра: `Long`.
### Ответы:
1. `200 OK`: Тело ответа: Список тегов, ассоциированных с задачей. Пример:
```json
[
  {
    "id": 1,
    "name": "tag"
  },
  {
    "id": 2,
    "name": "my tag"
  }
]
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если задача с указанным `taskId` не найдена. Пример:
```text
Task not found
```

## 8 Обновление существующего тега (updateTag)
### Метод: `PUT`
### URL: `/tags/{tagId}`
### Описание:
Обновляет данные тега по его идентификатору.
### Параметры запроса:
1. `taskId` (обязательный): Идентификатор тега, который нужно обновить. Тип параметра: `Long`.
2. Тело запроса: JSON-объект с данными нового тега. Пример:
```json
{
  "name": "updated tag"
}
```
### Ответы:
1. `200 OK`: Тело ответа: Данные обновленного тега. Пример:
```json
{
  "id": 1,
  "name": "updated tag"
}
```
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если тег с указанным `tagId` не найден. Пример:
```text
Task not found
```

## 9 Удаление тега (deleteTag)
### Метод: DELETE
### URL: /tags/{tagId}
### Описание:
Удаляет тег по его идентификатору.
### Параметры запроса:
1. `tagId` (обязательный): Идентификатор тега, который нужно удалить. Тип параметра: `Long`.
### Ответы:
1. `204 No Content`: Тело ответа: Пустой ответ, указывающий на успешное удаление задачи.
2. `404 Not Found`: Тело ответа: Сообщение об ошибке, если тег с указанным `tagId` не найден. Пример:
```text
Tag not found
```
# Тестирование с Postman
Для тестирования я создал environment для коллекции.
## OK (200 и 204)
В environment я занес константу `USER_ID` для имитации пользователя.
### 1 Создание новой задачи для пользователя (createTask)
1. Скриншот с запросом и ответом:
   ![good_createTask_screen](../assets/good/screens/good_createTask_screen.png)
2. Тесты:
   ![good_createTask_test](../assets/good/tests/good_createTask_test.png)

### 2 Получение задач пользователя (getTasksByUser)
1. Скриншот с запросом и ответом:
   ![good_getTasksByUser_screen](../assets/good/screens/good_getTasksByUser_screen.png)
2. Тесты:
   ![good_getTasksByUser_test](../assets/good/tests/good_getTasksByUser_test.png)

### 3 Получение задачи по ID (getTaskById)
1. Скриншот с запросом и ответом:
   ![good_getTaskById_screen](../assets/good/screens/good_getTaskById_screen.png)
2. Тесты:
   ![good_getTaskById_test](../assets/good/tests/good_getTaskById_test.png)

### 4 Обновление существующей задачи (updateTask)
1. Скриншот с запросом и ответом:
   ![good_updateTask_screen](../assets/good/screens/good_updateTask_screen.png)
2. Тесты:
   ![good_updateTask_test](../assets/good/tests/good_updateTask_test.png)

### 5 Создание нового тега для задачи (createTag)
1. Скриншот с запросом и ответом:
   ![good_createTag_screen](../assets/good/screens/good_createTag_screen.png)
2. Тесты:
   ![good_createTag_test](../assets/good/tests/good_createTag_test.png)

### 6 Получить теги по задаче (getTagsByTask)
1. Скриншот с запросом и ответом:
   ![good_getTagsByTask_screen](../assets/good/screens/good_getTagsByTask_screen.png)
2. Тесты:
   ![good_getTagsByTask_test](../assets/good/tests/good_getTagsByTask_test.png)

### 7 Обновление существующего тега (updateTag)
1. Скриншот с запросом и ответом:
   ![good_updateTag_screen](../assets/good/screens/good_updateTag_screen.png)
2. Тесты:
   ![good_updateTag_test](../assets/good/tests/good_updateTag_test.png)

### 8 Удаление тега (deleteTag)
1. Скриншот с запросом и ответом:
   ![good_deleteTag_screen](../assets/good/screens/good_deleteTag_screen.png)
2. Тесты:
   ![good_deleteTag_test](../assets/good/tests/good_deleteTag_test.png)

### 9 Удаление задачи (deleteTask)
1. Скриншот с запросом и ответом:
   ![good_deleteTask_screen](../assets/good/screens/good_deleteTask_screen.png)
2. Тесты:
   ![good_deleteTask_test](../assets/good/tests/good_deleteTask_test.png)

## Not Found (404) 
В environment я занес константу `BAD_ID` для несуществующих данных. 

### 1 Создание новой задачи для пользователя (createTask)
1. Пользователь с указанным `userId` не найден.
2. Скриншот с запросом и ответом:
![bad_createTask_screen](../assets/bad/screens/bad_createTask_screen.png)
3. Тесты:
![bad_createTask_test](../assets/bad/tests/bad_createTask_test.png)

### 2 Получение задач пользователя (getTasksByUser)
1. Пользователь с указанным `userId` не найден.
2. Скриншот с запросом и ответом:
   ![bad_createTask_screen](../assets/bad/screens/bad_getTasksByUser_screen.png)
3. Тесты:
   ![bad_createTask_test](../assets/bad/tests/bad_getTasksByUser_test.png)

### 3 Получение задачи по ID (getTaskById)
1. Задача с указанным `taskId` не найдена.
2. Скриншот с запросом и ответом:
   ![bad_getTaskById_screen](../assets/bad/screens/bad_getTaskById_screen.png)
3. Тесты:
   ![bad_getTaskById_test](../assets/bad/tests/bad_getTaskById_test.png)

### 4 Обновление существующей задачи (updateTask)
1. Задача с указанным `taskId` не найдена.
2. Скриншот с запросом и ответом:
   ![bad_updateTask_screen](../assets/bad/screens/bad_updateTask_screen.png)
3. Тесты:
   ![bad_updateTask_test](../assets/bad/tests/bad_updateTask_test.png)

### 5 Удаление задачи (deleteTask)
1. Задача с указанным `taskId` не найдена.
2. Скриншот с запросом и ответом:
   ![bad_deleteTask_screen](../assets/bad/screens/bad_deleteTask_screen.png)
3. Тесты:
   ![bad_deleteTask_test](../assets/bad/tests/bad_deleteTask_test.png)

### 6 Создание нового тега для задачи (createTag)
1. Задача с указанным `taskId` не найдена.
2. Скриншот с запросом и ответом:
   ![bad_createTag_screen](../assets/bad/screens/bad_createTag_screen.png)
3. Тесты:
   ![bad_createTag_test](../assets/bad/tests/bad_createTag_test.png)

### 7 Получить теги по задаче (getTagsByTask)
1. Задача с указанным `taskId` не найдена.
2. Скриншот с запросом и ответом:
   ![bad_getTagsByTask_screen](../assets/bad/screens/bad_getTagsByTask_screen.png)
3. Тесты:
   ![bad_getTagsByTask_test](../assets/bad/tests/bad_getTagsByTask_test.png)

### 8 Обновление существующего тега (updateTag)
1. Тег с указанным `tagId` не найден.
2. Скриншот с запросом и ответом:
   ![bad_updateTag_screen](../assets/bad/screens/bad_updateTag_screen.png)
3. Тесты:
   ![bad_updateTag_test](../assets/bad/tests/bad_updateTag_test.png)

### 9 Удаление тега (deleteTag)
1. Тег с указанным `tagId` не найден.
2. Скриншот с запросом и ответом:
   ![bad_deleteTag_screen](../assets/bad/screens/bad_deleteTag_screen.png)
3. Тесты:
   ![bad_deleteTag_test](../assets/bad/tests/bad_deleteTag_test.png)

# Результаты автотестов в Postman
Запуск автотестов:
![auto_results](../assets/results/summary.png)
Результаты тестов:
![test_1](../assets/results/1_test.png)
![test_2](../assets/results/2_test.png)
![test_3](../assets/results/3_test.png)
![test_4](../assets/results/4_test.png)
![test_5](../assets/results/5_test.png)
![test_6](../assets/results/6_test.png)
![test_7](../assets/results/7_test.png)
