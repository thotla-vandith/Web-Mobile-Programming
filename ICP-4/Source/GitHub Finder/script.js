//JavaScript File for The GitHub user Finder
function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)
    var username='https://api.github.com/users/'+user;
    console.log(username);
    $.ajax({
        type: "GET",
        url: username,
        dataType: 'json',
    }).done(function(data){
        showUser(data);
    }).fail(function(){
        console.log("ERROR!!");
        noSuchUser(user);
    });
}
//function to display the user information
function showUser(user) {
    document.getElementById('image').src=user.avatar_url;
    document.getElementById('name').innerText=user.name;
    document.getElementById('id').innerText=user.id;
    document.getElementById('giturl').href=user.url;
    document.getElementById('giturl').innerText=user.html_url;
    document.getElementById('gitrepo').innerText=user.public_repos;
    document.getElementById('gitfollowers').innerText=user.followers;
    document.getElementById('gitfollowing').innerText=user.following;
}
function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    if(data.message == "Not Found" || username == '') {
        alert("User Not Found");
    }
}
$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            getGithubInfo(username);
            //if the response is successful show the user's details

        }
    })
});
