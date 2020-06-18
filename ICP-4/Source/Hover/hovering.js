//JavaScript file to display the hovered image
//This function displays image when hovered over it
function upDate(previewPic) {
    console.log(previewPic);
    $('#image').css('background-image', 'url(' + previewPic.src + ')');
    $('#image').html(previewPic.alt);

}
//This function resetsto default when not hovered over the image
function unDo() {
    $('#image').css('background-image','none');
    $('#image').html("Hover over an image below to display here.");
}