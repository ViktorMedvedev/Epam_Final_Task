$(window).on('load', function () {

    $('.img-container').find('img').each(function () {
        let imgClass = (this.width / this.height >= 1) ? 'wide' : 'tall';
        $(this).addClass(imgClass);
    });


    $('.order-card_img-container').find('img').each(function () {
        let imgClass = (this.width / this.height >= 1) ? 'wide' : 'tall';
        $(this).addClass(imgClass);
    });


    $('.rating span').click(function () {
        let rating = Math.abs(5 - $(this).index());
        let form = $(this).parent().parent();
        let hidden1 = $(form).find('input[name="rating"]');
        let hidden2 = $(form).find('input[name="command"]');
        hidden1.val(rating);
        hidden2.val("update-rating");
        $(form).submit();
    });
});