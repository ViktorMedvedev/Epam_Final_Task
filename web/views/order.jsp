<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Заказать</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../js/main.js"></script>
</head>
<body>
<div class="order-card-container">
    <div class="order-card">
        <div class="order-card_img-container">
            <img class="blured" src="../res/img/background.png">
            <img class="photo" src="../res/img/background.png">
        </div>
        <div class="order-card_info-container">
            <p class="title">
                НАЗВАНИЕ
            </p>
            <p class="all-info">
                <span class="info-line">
                    <span class="key">Стиль</span>
                    <span class="value">Готика</span>
                </span>
                <span class="info-line">
                    <span class="key">Размер</span>
                    <span class="value">как раз на тво мелкий хуй</span>
                </span>
                <span class="info-line">
                    <span class="key">Цена</span>
                    <span class="value">1000 и 1 гривна</span>
                </span>
                <span class="info-line">
                    <span class="key">Рейтинг</span>
                    <span class="value">3.2</span>
                </span>

            </p>
            <form>
                <div class="rating">
                    <span>☆</span><span>☆</span><span>☆</span><span>☆</span><span>☆</span>
                </div>
                <input type="hidden">
            </form>
            <form>
                <input type="submit" value="Заказать"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>