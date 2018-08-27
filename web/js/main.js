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
function checkPass()
{
    var pass1 = document.getElementById('pass1');
    var pass2 = document.getElementById('pass2');
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    if(pass1.value === pass2.value){
        pass2.style.backgroundColor = goodColor;
    }else{
        pass2.style.backgroundColor = badColor;
    }
}