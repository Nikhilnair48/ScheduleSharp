/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var rangeLunch = 0;
function validateSchoolName() {
    var school = document.getElementById("SchoolName");
    var message = document.getElementById("schoolMessage");
    var url = "http://localhost:8080/ScheduleSharp/addSchoolValidation?name=" + school.value;
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

function validateSemester() {
    var semester = $('#Semester');
    var message = document.getElementById("semesterMessage");
    if (semester.length == 0 || $(semester).val() == "")
    {
        message.innerHTML = "Semester: Please select one of the choices.";
        semester.focus();
    } else {
        message.innerHTML = "";
    }
}

function validateDays() {
    var days = $('#Days');
    var message = document.getElementById("daysMessage");
    if (days.length == 0 || $(days).val() == "") {
        message.innerHTML = "Days Per Week : Please select one of the choices.";
        days.focus();
    } else {
        message.innerHTML = "";
    }
}

function validatePeriods() {
    var periods = $('#Period');
    var message = document.getElementById("periodsMessage");
    if (periods.length == 0 || $(periods).val() == "") {
        message.innerHTML = "Periods : Please select one of the choices.";
        periods.focus();
    } else {
        message.innerHTML = "";
    }
}

function validateLunchRange1() {
    var rangeLunch1 = $('#RangeLunch1');
    var message = document.getElementById("rangeLunch1Message");
    if (rangeLunch1.length == 0 || $(rangeLunch1).val() == "") {
        message.innerHTML = "Lunch Range (1) : Please select one of the choices.";
        rangeLunch1.focus();
    } else {
        message.innerHTML = "";
        rangeLunch = rangeLunch1;
    }
}

function validateLunchRange2() {

    var rangeLunch2 = $('#RangeLunch2');
    var message = document.getElementById("rangeLunch2Message");
    if (rangeLunch2.length == 0 || $(rangeLunch2).val() == "") {
        message.innerHTML = "Lunch Range (2) : Please select one of the choices.";
        messageFinal = "Error: Please review the information again.";
        rangeLunch2.focus();
    } else if ($(rangeLunch2).val() < rangeLunch.value) {
        message.innerHTML = "Lunch Range (2) : Please select a value higher than the first one.";
        messageFinal = "Error: Please review the information again.";
        rangeLunch2.focus();
    } else {
        messageFinal = "";
        message.innerHTML = "";
    }
}

// DYNAMICALLY SET THE VALUES FOR THE DROPDOWN
$(document).ready(function () {
    $("#Semester").append("<option>" + "</option>");
    $("#Days").append("<option>" + "</option>");
    $("#Period").append("<option>" + "</option>");
    $("#RangeLunch1").append("<option>" + "</option>");
    $("#RangeLunch2").append("<option>" + "</option>");

    for (i = 1; i <= 9; i++) {
        if (i <= 7) {
            $("#Days").append("<option>" + i + "</option>");
            if (i <= 4) {
                $("#Semester").append("<option>" + i + "</option>");
            }
        }

        $("#Period").append("<option>" + i + "</option>");
        $("#RangeLunch1").append("<option>" + i + "</option>");
        $("#RangeLunch2").append("<option>" + i + "</option>");
    }
});

var check = 1;
function validateSchoolNameLastTime() {
    var school = document.getElementById("SchoolName");
    var url = "http://localhost:8080/ScheduleSharp/addSchoolValidation?name=" + school.value;
    var req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if (req.readyState === 4 && req.status === 200) {
        if (req.responseText !== "ok") {
            alert("School already exists!");
            check = -1;
        } else {
            check = 1;
        }
    }
}

function sendDataToServlet() {
    validateSchoolNameLastTime();       // CONCURRENY ATTEMPT - FAILURE CONFIRMED
    var sName = document.getElementById("SchoolName");
    var days = document.getElementById("Days");
    var rLunch1 = document.getElementById("RangeLunch1");
    var rLunch2 = document.getElementById("RangeLunch2");
    var semester = document.getElementById("Semester");
    var period = document.getElementById("Period");

    if (check === 1) {      // ALL PRIOR TESTS HAVE PASSED, I.E, VALIDATION
        var url = "http://localhost:8080/ScheduleSharp/AddSchool?SchoolName=" + sName.value
                + "&Semester=" + semester.value + "&Days=" + days.value + "&Period=" + period.value
                + "&RangeLunch1=" + rLunch1.value + "&RangeLunch2=" + rLunch2.value;
        req = new XMLHttpRequest();
        req.open("GET", url, false);
        req.send(null);
        if (req.readyState === 4 && req.status === 200) {
            alert("Successfully added!");
            location.reload();
        } else {
            alert(req.readyState + " " + req.status)
        }
    }
}

// ADDS INFO TO DB
function validateForm() {
    sendDataToServlet();
}

$(document).ready(function () {
    $('.result').DataTable();
});