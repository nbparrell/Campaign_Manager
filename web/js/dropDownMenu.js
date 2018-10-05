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
    if ((!event.target.matches('#dataBtn') && event.target.matches('#accountBtn')) && document.getElementById("dataListShow").classList.contains('show')) {
        document.getElementById("dataListShow").classList.remove('show');
        document.getElementById("dataListShow").style.display = "none";
    } else if ((event.target.matches('#dataBtn') && !event.target.matches('#accountBtn')) && document.getElementById("accountListShow").classList.contains('show')) {
        document.getElementById("accountListShow").classList.remove('show');
        document.getElementById("accountListShow").style.display = "none";
    } else if ((!event.target.matches('#dataBtn') && !event.target.matches('#accountBtn'))) {
        var dropdowns = document.getElementsByClassName("dataListContent");
         var i;
         for (i = 0; i < dropdowns.length; i++) {
         var openDropdown = dropdowns[i];
         if (openDropdown.classList.contains('show')) {
         openDropdown.classList.remove('show');
         document.getElementById("accountListShow").style.display = "none";
         document.getElementById("dataListShow").style.display ="none";
         }
         }
    }
}