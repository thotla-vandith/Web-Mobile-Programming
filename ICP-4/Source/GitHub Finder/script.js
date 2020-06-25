//JavaScript File for The GitHub user Finder
function getGithubInfo(user) {
    //Acquiring user information fro API 
    var username='https://api.github.com/users/'+user;
    console.log(username);
    $.ajax({
        type: "GET",
        url: username,
        dataType: 'json',
    }).done(function(data){
        showUser(data);
    }).fail(function(){
        console.log("ERROR!! SOMETHING WENT WRONG");
        noSuchUser(user);
    });
}
//showUser function to display the user information
function showUser(user) {
    var giturl='https://www.github.com/'+user;
    //assigning element by id to the respective parameters
    document.getElementById('gitimage').src=user.avatar_url;
    document.getElementById('gitname').innerText=user.name;
    document.getElementById('gitid').innerText=user.id;
    document.getElementById('giturl').href=user.url;
    document.getElementById('giturl').innerText=user.html_url;
    document.getElementById('twitter').innerText=user.twitter_username;
    document.getElementById('gitrepo').innerText=user.public_repos;
    document.getElementById('gitgist').innerText=user.public_gists;
    document.getElementById('gitfollowers').innerText=user.followers;
    document.getElementById('gitfollowing').innerText=user.following;
    document.getElementById('gitbio').innerText=user.bio;
    document.getElementById('gitcreated').innerText=user.created_at;
    document.getElementById('gitupdated').innerText=user.updated_at;
}
//noSuchUser function displays message if user not found
function noSuchUser(username) {
    if(data.message == "Not Found" || username == '') {
        alert("NOT FOUND!!");
    }
}
//Function from the source code given in class
$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //checking if the enter key is pressed
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
