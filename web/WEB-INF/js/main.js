$(window).on('load', function () {

    $('.img-container').find('img').each(function () {
        let imgClass = (this.width / this.height > 1) ? 'wide' : 'tall';
        $(this).addClass(imgClass);
    });

    $('.order-card_img-container').find('img.blured').each(function () {
        let imgClass = (this.width / this.height > 1) ? 'wide' : 'tall';
        $(this).addClass(imgClass);
    });

    $('.order-card_img-container').find('img').each(function () {
        let imgClass = (this.width / this.height > 1) ? 'wide' : 'tall';
        $(this).addClass(imgClass);
    });


    $('.rating span').click(function () {
        let rating = Math.abs(5 - $(this).index());
        let form = $(this).parent().parent();
        let hidden = $(form).find('input[type=hidden]');
        hidden.val(rating);
        $(form).submit();
    })
});