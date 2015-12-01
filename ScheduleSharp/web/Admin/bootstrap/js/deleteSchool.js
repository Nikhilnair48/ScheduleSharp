/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function deleteSchool() {
    var ID = document.getElementById("SchoolID");
    var url = "http://localhost:8080/ScheduleSharp/DeleteSchool?SchoolID=" + ID.value;

    req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if (req.readyState === 4 && req.status === 200) {
        // MESAGE NOT WORKING - DELETESCHOOL SERVLET REDIRECTS TO SELECTALLSCHOOLS
        // SERVLET IN ORDER TO UPDATE TABLE, AND THEN IS REDIRECTED BACK TO THIS JS FILE
        // PRIORITY - NOT NOW.
        if (req.responseText != "ok") {
            alert("School wasn't deleted");
        }
        else {
            alert("School deleted!");
        }
    }
}

$(document).ready(function () {
    $('.result').DataTable();
});