/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function ()
{
    $("#notification-link").click(function ()
    {
        $("#notification-container").fadeToggle(300);
        $("#notification_count").fadeOut("slow");
        return false;
    });

    //Document Click hiding the popup 
    $(document).click(function ()
    {
        $("#notification-container").hide();
    });

    //Popup on click
    $("#notification-container").click(function ()
    {
        return false;
    });

    $("#notification-footer").click(function () {
        window.location.href = '/recruitment-system/user?op=listNotification';
    });

    for (i = 1; i <= 5; i++) {
        $("#notification-content-" + i).click(function () {
            a_href = $(this).attr('href');
            window.location.href = a_href;
        });
    }
});
