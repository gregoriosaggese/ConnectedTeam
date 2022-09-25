$('#size').hover( function () {
    $('#drag').fadeOut(500);
});

$('#size').mousemove( function () {
    $('#device').css('width', $(this).val());
});

$('#size').change(function () {
    $('#device').css('width', $(this).val());
});