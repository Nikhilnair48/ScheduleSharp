/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function deleteAcc() {
    var ID = document.getElementById("StudentID");

    var url = "http://localhost:8080/ScheduleSharp/DeleteStudentAcc?StudentID=" + ID.value;

    req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if (req.readyState === 4 && req.status === 200) {
        if (req.responseText != "ok") {
            alert("Student Account wasn't deleted");
        }
        else {
            alert("Student Account deleted!");
        }
    }
}
$(document).ready(function () {
    $('.result').DataTable();
});