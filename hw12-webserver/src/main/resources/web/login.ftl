<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 50px; text-align: center;">
    <h2>Вход в панель администратора</h2>

    <#if hasError>
        <p style="color: red; font-weight: bold;">Неверный логин или пароль!</p>
    </#if>

    <form action="/login" method="POST" style="display: inline-block; text-align: left; background: white; padding: 30px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1);">
        <label>Логин:</label><br>
        <input type="text" name="login" required style="width: 250px; padding: 8px; margin: 8px 0 15px 0;"><br>

        <label>Пароль:</label><br>
        <input type="password" name="password" required style="width: 250px; padding: 8px; margin: 8px 0 20px 0;"><br>

        <button type="submit" style="width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">Войти</button>
    </form>
</body>
</html>
