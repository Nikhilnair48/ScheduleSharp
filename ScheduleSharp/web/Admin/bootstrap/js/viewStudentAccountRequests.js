/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('.selectall').click(function () {
    if ($(this).is(':checked')) {
        $('div input').prop('checked', true);
    } else {
        $('div input').prop('checked', false);
    }
});

var data = new Array();
var i = 0;
function useAjax(data) {
    var j, param = "";
    // IF THERE ARE MULTIPLE REQUESTS - SEPARATE REQUEST BY "-"
    for (j = 0; j < data.length; j++) {
        param += data[j] + "-";
    }

    var url = "http://localhost:8080/ScheduleSharp/AcceptStudent?IDs=" + param;
    // USE AJAX TO CALL THE SERVLET, AND PASS THE IDs
    req = new XMLHttpRequest();
    req.open("GET", url, false);
    req.send(null);

    if (req.readyState === 4 && req.status === 200) {
        alert("Student(s) accepted!");
        location.reload();  // RELOAD PAGE TO SHOW UPDATED STUDENT REQUEST TABLE
    }
}

$(document).on('click', '#acceptSelected', function () {
    // GRAB INFORMATION FROM THE TABLE
    $('#accountRequestsTable').find('tr').each(function () {
        var row = $(this);
        // FIND WHICH CHECKBOXES WERE TICKED
        if (row.find('input[type="checkbox"]').is(':checked')) {
            data[i] = row.attr('id');   // ADD TO DATA ARRAY
            i++;
        }
    });

    // SEND DATA OVER TO ACCEPT STUDENT REQUEST(S)
    useAjax(data);
});

$(document).ready(function () {
    $('.result').DataTable();
});