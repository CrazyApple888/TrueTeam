# TrueTeam
Проект для хакатона от Альфа банка. 
# Описание кейса
Сделать мобильное приложение и бекенд часть для приоритезации дисконтных карт пользователя в зависимости от геолокации
# Figma
Ссылка на фигму с концептом дизайна и use-cases: https://www.figma.com/file/RQOkBDHBiwMXwh1FLiv7sa/AlfaHackaton?node-id=0%3A1&t=fNRWqLMEXnONfb3G-1
# Backend
## Результаты тестирования
docs/README.md
## Документация API реализована при помощи swagger. Endpoint по умолчанию: /swagger-ui/index.html
Примеры документации:
<img width="1440" alt="Снимок экрана 2022-12-05 в 17 55 56" src="https://user-images.githubusercontent.com/62795541/205648568-b6ce87cc-64a6-4a09-a6e3-a4947e1b4f2e.png">
<img width="1440" alt="Снимок экрана 2022-12-05 в 17 56 05" src="https://user-images.githubusercontent.com/62795541/205648579-5153a78f-fc38-4ae5-8cc2-44111927ebbf.png">

## Как поднять backend sevice?
### В терминале:
1. docker run --name postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432  -d postgres
2. docker run --name pgadmin -e PGADMIN_DEFAULT_PASSWORD=admin -e PGADMIN_DEFAULT_EMAIL=admin@mail.ru -p 5050:80 -d dpage/pgadmin4
3. docker ps -a
4. docker inspect postgres-CONTAINER-ID | grep IPAddress

### В браузере:

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

### В приложении:

13. В application.yml меняем

    username: admin
    
    password: admin
    
    url: jdbc:postgresql://localhost:5432/trueteam
    
14. Ставим ключ для api 2 gis в geo-api.key

### В idea:

15. mvn compile (Справа maven -> Lifecycle -> compile)
16. Заходим в src/main/kotlin/Application.kt и зеленую стрелочку
