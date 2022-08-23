

let count = 1;

$('.more-btn').on('click', function(){
    count++ ;
    $('.room-box').removeClass('list10');
    $('.room-box').css('display','none');
    for (var i = 0; i < count*8; i++) {
         $('.room-box').eq(i).css('display','block');
     }


});
