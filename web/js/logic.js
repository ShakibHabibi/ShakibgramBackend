$(document).ready(function () {
    $("#usersNav").click(function () {
        $("#chatList").hide();
        $("#userList").show();
        $("#content").css('margin-right', 150 + 'px');
        $("#usersNav").addClass("selected");
        $("#chatNav").removeClass("selected");
    });
    $("#chatNav").click(function () {
        $("#chatList").show();
        $("#userList").hide();
        $("#content").css('margin-right', 350 + 'px');
        $("#chatNav").addClass("selected");
        $("#usersNav").removeClass("selected");
    });
});