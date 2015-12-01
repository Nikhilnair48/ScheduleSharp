/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).on('click', '#schoolIDForBlocks', function () {
    //schoolIDForBlocks
    var id = document.getElementById("schoolIDBlocks");
    alert(id.value);
});

$(document).ready(function () {
    $('.result').DataTable();
    var scheduleBlock = [];
});
var buttons = document.querySelectorAll('#newtable tbody .mybutton');
var id = 0;
var i = 0;
var info = new Array();     // ARRAY WILL STORE VALUES TO BE DISPLAYED IN MODAL

// TERRIBLE WAY TO STORE VALUES TO BE SENT TO SERVLET
var schoolID = 0;
var sName = "";
var semester = 0;
var days = 0;
var period = 0;
var lunchRange = "";

function modifySchoolServlet() {

    var url = "http://localhost:8080/ScheduleSharp/modifySchool?SchoolName=" + sName
            + "&Semester=" + semester + "&Days=" + days + "&Period=" + period
            + "&lunchRange=" + lunchRange + "&SchoolID=" + schoolID;
    req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if (req.readyState === 4 && req.status === 200) {
        alert("Successfully modified school!");
        location.reload();
    }
}

// 'CHANGE' BUTTON CLICKED
$(document).on('click', '#changeButton', function () {
    sName = $('#SchoolName').val();
    semester = $('#Semester').val();
    days = $("#Days").val();
    period = $("#Period").val();
    lunchRange = $("#LunchRange").val();
    modifySchoolServlet();
});

//  GRAB INFO FROM TABLE TO DISPLAY ON MODAL
function callThis() {
    var temp = document.getElementById("newtable").rows[id].cells.length;

    for (var j = 0; j < temp - 1; j++) {   // -1 because we don't want the innerHTML of the 'MODIFY' button
        info[j] = document.getElementById("newtable").rows[id].cells.item(j).innerHTML;
    }
    // TERRIBLE WAY TO DIPLAY INFO IN MODAL
    schoolID = info[0];
    $("#SchoolName").val(info[1] + "");
    $("#Semester").val(info[2] + "");
    $("#Days").val(info[3] + "");
    $("#Period").val(info[4] + "");
    $("#LunchRange").val(info[5] + "");
    $("#editModal").modal("show").addClass("fade");
}

// ADDING A LISTENER TO EVERY BUTTON
function buttonClicked(e) {
    var button = this,
            cell = button.parentElement,
            row = cell.parentElement,
            firstCell = row.querySelector('td');
    id = firstCell.innerHTML;
    callThis();
}
// ADDING A LISTENER TO EVERY BUTTON
for (var i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener('click', buttonClicked);
}


function validateSchoolName() {
    var school = document.getElementById("SchoolName");
    var message = document.getElementById("schoolMessage");
    var url = "http://localhost:8080/ScheduleSharp/modifySchoolValidation?name=" + school.value + "&ID=" + schoolID;
    var req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if (req.readyState === 4 && req.status === 200) {
        if (req.responseText !== "ok") {
            message.innerHTML = req.responseText;
            school.focus();
        } else {
            message.innerHTML = "";
        }
    }
}