<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Панель администратора</title>
</head>
<body style="font-family: Arial, sans-serif; margin: 40px; background-color: #f8f9fa;">
    <div style="float: right;">
        <a href="/login?logout=true" style="color: #dc3545; text-decoration: none; font-weight: bold;">Выйти</a>
    </div>

    <h1>Система управления клиентами</h1>
    <hr>

    <h3>Создать нового клиента</h3>
    <form action="/admin" method="POST" style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); max-width: 500px; min-width: 400px;">
        <label>Имя клиента:</label><br>
        <input type="text" name="name" required style="width: 95%; padding: 8px; margin: 6px 0 12px 0;"><br>

        <label>Улица (Адрес):</label><br>
        <input type="text" name="street" style="width: 95%; padding: 8px; margin: 6px 0 12px 0;"><br>

        <label>Телефоны (через запятую):</label><br>
        <input type="text" name="phones" placeholder="111, 222" style="width: 95%; padding: 8px; margin: 6px 0 18px 0;"><br>

        <button type="submit" style="padding: 10px 20px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">Создать клиента</button>
    </form>

    <br>
    <h3>Список зарегистрированных клиентов</h3>
    <table style="width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 4px rgba(0,0,0,0.05); border: 1px solid #dee2e6;">
        <thead>
            <tr style="background-color: #e9ecef; text-align: left;">
                <th style="padding: 12px; border: 1px solid #dee2e6;">ID</th>
                <th style="padding: 12px; border: 1px solid #dee2e6;">Имя</th>
                <th style="padding: 12px; border: 1px solid #dee2e6;">Адрес</th>
                <th style="padding: 12px; border: 1px solid #dee2e6;">Телефоны</th>
            </tr>
        </thead>
        <tbody>
            <#list clients as client>
            <tr>
                <td style="padding: 12px; border: 1px solid #dee2e6;">${client.id}</td>
                <td style="padding: 12px; border: 1px solid #dee2e6;">${client.name}</td>
                <td style="padding: 12px; border: 1px solid #dee2e6;"><#if client.address??>${client.address.street}<#else>-</#if></td>
                <td style="padding: 12px; border: 1px solid #dee2e6;">
                    <#if client.phones?? && client.phones?size &gt; 0>
                        <#list client.phones as phone>${phone.number}<#if phone_has_next>, </#if></#list>
                    <#else>
                        -
                    </#if>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>
