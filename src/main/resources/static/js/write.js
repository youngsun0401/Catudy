var result = $('input[name=post_category]');

$('input[name=post_category]').click(function(e) {
    
    var nValue = $('input[name=post_category]:checked').val();
    $('.label-btn').removeClass('check');
    $('.' + nValue).addClass('check');


});
