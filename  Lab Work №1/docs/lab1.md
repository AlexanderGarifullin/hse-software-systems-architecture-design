# Стейкхолдеры
* **Разработчики задач**: заинтересованы в легком способе сгенерировать качественные тесты для задач. Хотят минимизировать ручную работу по созданию тестов.
* **Студенты (те, кто решают задачи)**: заинтересованы в качественных задачах (тесты входят в понятие качества задач).

# Функциональные требования
1. **Доступ в систему**:
   1. Регистрация новых пользователей.
   2. Аутентификация и авторизация пользователей.

2. **Задачи**:
   1. Возможность загрузить данные задачи в систему (условие задачи; входные/выходные данные + их ограничения; ограничения по времени и памяти (максимум 5 секунд и 256 мегабайт); авторское решение - код программы на C++20; теги к задаче)
   2. Возможность удалить данные о задаче.
   3. Возможность изменить данные о задаче.
   4. Возможность посмотреть данные о задаче.

3. **Тесты**:
   1. Возможность сгенерировать разнообразные тесты к задаче, покрывающие как можно больше частных случаев.
   2. Тесты должны успешно выполняться на авторском решении (проходить ограничения по времени и памяти).
   3. Возможность самостоятельно добавить тесты к задаче.
   4. Возможность добавить описание к каждому тесту.
   5. Возможность посмотреть тесты к задаче.
   6. Возможность изменить тесты к задаче.
   7. Возможность удалить тесты из задачи.
   8. Возможность скачать из системы набор тестов для задачи в ZIP архив. При этом каждый тест - это отдельный txt файл.
   9. Возможность загрузить в систему набор тестов из ZIP архива. При этом каждый тест - это отдельный txt файл.

# Диаграмма вариантов использования для функциональных требований

## Диаграмма вариантов использования для входа в систему
![Use case login](../assets/1_use_case_login.png)

```PlantUML
@startuml

left to right direction

actor "Пользователь" as user

rectangle Вход {
  usecase "Зарегистрироваться" as registation
  usecase "Авторизоваться" as authorizatin
}

user --> registation
user --> authorizatin

@enduml
```

## Диаграмма вариантов использования для задач
![Use case login](../assets/1_use_case_problems.png)

```PlantUML
@startuml

left to right direction

actor "Пользователь" as user

rectangle Задачи {
usecase "Добавить задачу" as createProblem

usecase "Добавить авторское решение" as createSolutin
usecase "Добавить входные/выходные данные" as createVariables
usecase "Добавить ограничения времени и памяти" as createLimits
usecase "Добавить теги к задаче" as createTags

usecase "Изменить задачу" as updateProblem

usecase "Изменить авторское решение" as updateSolutin
usecase "Изменить входные/выходные данные" as updateVariables
usecase "Изменить ограничения времени и памяти" as updateLimits
usecase "Изменить теги к задаче" as updateTags

usecase "Посмотреть задачу" as readProblem
usecase "Удалить задачу" as deleteProblem
}

user --> createProblem
user --> readProblem
user --> updateProblem
user --> deleteProblem


createProblem ..> createSolutin: включает
createTags ..> createProblem: расширяет
createProblem ..> createVariables: включает
createProblem ..> createLimits: включает


updateTags ..> updateProblem: расширяет
updateLimits ..> updateProblem: расширяет
updateVariables ..> updateProblem: расширяет
updateSolutin ..> updateProblem: расширяет


@enduml
```
## Диаграмма вариантов использования для тестов к задачам

![Use case login](../assets/1_use_case_tests.png)

```PlantUML
@startuml

left to right direction
![1_use_case_problems.png](..%2Fassets%2F1_use_case_problems.png)
actor "Пользователь" as user

rectangle Тесты {
usecase "Сгенерировать тесты" as generateTest
usecase "Проверить тесты" as checkTest
usecase "Добавить тест" as createTest
usecase "Посмотреть тест" as readTest
usecase "Изменить тест" as updateTest
usecase "Удалить тест" as deleteTest
usecase "Скачать тесты" as downloadTest
usecase "Загрузить тесты" as uploadTest
}


user --> generateTest
user --> checkTest
user --> createTest
user --> readTest
user --> updateTest
user --> deleteTest
user --> downloadTest
user --> uploadTest

@enduml
```

# Предложения
1. Для платформы важна производительность, чтобы сгенерированные тесты выполнялись как можно быстрее и пользователь не ждал слишком долго.
2. В MVP не будет интерактивных задач.
3. В MVP принимается только авторские решения на языке C++20. В дальнейшем можно добавить другие популярные языки программирования (Python 3.10, Java21). 
4. Нет смысла использовать монолит, должно быть несколько серверов, где тесты буду выполняться на авторском решении.
5. Для более сложных задач и качественных тестов можно будет подключить ИИ. В MVP можно ограничиться более простыми способами генерации тестов.

# Нефункциональные требования



