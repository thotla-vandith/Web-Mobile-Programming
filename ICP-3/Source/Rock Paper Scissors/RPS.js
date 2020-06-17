//Rock Paper Scissors JavaScript File
//This program is for the computer to choose random number
function computer_option() {
    const rdmNum = Math.floor(Math.random()*600);
    if (rdmNum < 200) return "r";
    if (rdmNum < 400 && rdmNum >= 200) return "p";
    else return "s";
}
//Converting r, p, s to rock, paper and scissors
function convertToWord(letter) {
    if (letter === "r") return "ROCK";
    if (letter === "s") return "SCISSORS";
    else return "PAPER";
}
//three function here used to decide win, lose, and draw.
function win(userOption, computerOption) {
    document.querySelector(".results>p").innerHTML =
        " You chose: " + convertToWord(userOption) + ". Computer chose: " + convertToWord(computerOption) + ". YOU WIN! Wanna Play again.";
}
function lose(userOption, computerOption) {
    document.querySelector(".results>p").innerHTML =
        " You chose: " + convertToWord(userOption) + ". Computer chose: " + convertToWord(computerOption) + ". YOU LOST! Wanna Play Again.";
}
function draw(userOption, computerOption) {
    document.querySelector(".results>p").innerHTML =
        " You chose: " + convertToWord(userOption) + ". Computer chose: " + convertToWord(computerOption) + ". IT'S A DRAW! Wanna Play Again.";
}
//Main function here
function rule(userOption) {
    const computerOption = computer_option();
    if (userOption + computerOption === "rs") {
        win(userOption, computerOption);
    } else if (userOption + computerOption === "pr") {
        win(userOption, computerOption);
    } else if (userOption + computerOption === "sp") {
        win(userOption, computerOption);
    } else if (userOption + computerOption === "rp") {
        lose(userOption, computerOption);
    } else if (userOption + computerOption === "ps") {
        lose(userOption, computerOption);
    } else if (userOption + computerOption === "sr") {
        lose(userOption, computerOption);
    } else {
        draw(userOption, computerOption);
    }
}
//Acquiring the user input
function main() {
    document.getElementById("rock").addEventListener("click", function () {
        rule("r");
    });
    document.getElementById("paper").addEventListener("click", function () {
        rule("p");
    });
    document.getElementById("scissors").addEventListener("click", function () {
        rule("s");
    })
}
main();