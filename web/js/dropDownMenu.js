function toggleChild() {
    if (document.getElementById("dataListShow").classList.contains("show")) {
        document.getElementById("dataListShow").classList.remove('show');
        document.getElementById("dataListShow").style.display = "none";
    } else {
        document.getElementById("dataListShow").classList.toggle("show");
        document.getElementById("dataListShow").style.display = "block";
    }
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (event) {
    if (!event.target.matches('.dataListButton')) {

        var dropdowns = document.getElementsByClassName("dataListContent");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
                document.getElementById("dataListShow").style.display = "none";
            }
        }
    }
}