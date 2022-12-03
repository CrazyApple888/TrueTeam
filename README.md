# TrueTeam

# Как поднять backend sevice?
## В терминале:
1. docker run --name postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432  -d postgres
2. docker run --name pgadmin -e PGADMIN_DEFAULT_PASSWORD=admin -e PGADMIN_DEFAULT_EMAIL=admin@mail.ru -p 5050:80 -d dpage/pgadmin4
3. docker ps -a
4. docker inspect postgres-CONTAINER-ID | grep IPAddress

## В браузере:

5. Заходим на localhost:5050
6. login: admin@mail.ru password: admin
7. add server

    7.1 Вводим имя сервера, например TrueTeam
    
    7.2 Перехоидм в Connection
    
    7.3 address - вставить из пункта 4
    
    7.4 port 5432
    
    7.5 username: admin
    
    7.6 password: admin
    
    7.7 save

8. Правой кнопкой по созданному слева trueteam -> create -> database
9. Название - trueteam -> save
10. Правой кнопкой по созданной БД trueteam
11. Query tool
12. Применяем скрипт создания БД из /backend/src/main/resources/database/create.sql

    По умолчанию создается 2 пользователя
    
    Логин: b@bk.ru
    
    Пароль: 1!Qwertyu
    
    Для пользователя a@bk.ru пароль безвозвратно утерян 🙂

## В приложении:

13. В application.yml меняем

    username: admin
    
    password: admin
    
    url: jdbc:postgresql://localhost:5432/trueteam
    
14. Ставим ключ для api 2 gis в geo-api.key

## В idea:

15. mvn compile (Справа maven -> Lifecycle -> compile)
16. Заходим в src/main/kotlin/Application.kt и зеленую стрелочку
