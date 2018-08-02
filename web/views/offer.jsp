<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Заказать</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../js/main.js"></script>

    <style>

    </style>
</head>
<body>
<div class="order-card-container">
    <div class="order-card">
        <div class="order-card_img-container">
            <img class="blured" src="../res/img/background.png">
            <img class="photo" src="../res/img/background.png">
        </div>
        <div class="order-card_info-container">
            <p class="all-info offer-info">
            <table class="offer-table">
                <tbody>
                    <tr>
                        <td>Название:</td>
                        <td><input placeholder="Название"></td>
                    </tr>
                    <tr>
                        <td>Стиль:</td>
                        <td>
                            <select>
                                <option>A</option>
                                <option>B</option>
                                <option>C</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Размер:</td>
                        <td><select>
                            <option>A</option>
                            <option>B</option>
                            <option>C</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>Цена:</td>
                        <td><input placeholder="Цена" type="number"></td>
                    </tr>
                </tbody>
            </table>

            </p>
            <form>
                <input type="submit" value="Подтвердить"/>
            </form>
            <form>
                <input  class="gray" type="submit" value="Отказать"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>