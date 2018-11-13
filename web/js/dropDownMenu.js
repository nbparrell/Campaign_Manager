function toggleChild(listElement) {
    if (document.getElementById(listElement).classList.contains("show")) {
        document.getElementById(listElement).classList.remove('show');
        document.getElementById(listElement).style.display = "none";
    } else {
        document.getElementById(listElement).classList.toggle("show");
        document.getElementById(listElement).style.display = "block";
    }
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (event) {

    var tab;
    if (event.target.matches('#accountBtn')) {
        tab = 'account';
    } else if (event.target.matches('#dataBtn')) {
        tab = 'data';
    } else if (event.target.matches('#insertBtn')) {
        tab = 'insert';
    } else if (event.target.matches('#tutorialBtn')) {
        tab = 'tutorial';
    } else if ((!event.target.matches('#dataBtn') && !event.target.matches('#accountBtn') && !event.target.matches('#insertBtn') && !event.target.matches('#tutorialBtn'))) {
        tab = 'none';
    }
    try {
        var dropdowns = document.getElementsByClassName("dataListContent");
        for (var i = 0; i < dropdowns.length; i++) {
            console.log(dropdowns[i]);
            if (!dropdowns[i].classList.contains(tab)) {
                dropdowns[i].classList.remove('show');
                dropdowns[i].style.display = "none";
            }
        }
    } catch (error) {
        console.error(error);
    }
}