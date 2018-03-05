<%@ page import="db.dao.ChatDao" %>
<%@ page import="db.dao.daoImpl.ChatDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ChatDetail" %>
<%@ page import="model.response.GetChatsResponse" %>
<%@ page import="model.ChatItem" %>
<%@ page import="db.dao.AuthDao" %>
<%@ page import="db.dao.daoImpl.AuthDaoImpl" %>
<%@ page import="model.UserInfo" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: shkbhbb
  Date: 3/3/18
  Time: 10:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>shakibgram-admin</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../js/logic.js"></script>
</head>
<body>
<div id="header">
    <div class="logo">
        <h1>شکیب گرام</h1>
    </div>
</div>
<div id="container">
    <div class="sidebar">
        <ul id="nav">
            <li>
                <p style="cursor:pointer" id="usersNav">کاربران</p>
            </li>
            <li>
                <p id="chatNav" style="cursor:pointer">چت ها</p>
            </li>
        </ul>
    </div>
    <div class=" chats" id="chatList">
        <%
            ChatDao chatDao = new ChatDaoImpl();
            List<ChatItem> chatItems = chatDao.getAllChats();
        %>
        <ul id="chat-nav">
            <% for (int i = 0; i < chatItems.size(); i++) {%>
            <li>
                <p class="chat-item"><%= chatItems.get(i).getUsername() %>
                    <br/>
                    <%= chatItems.get(i).getOthername() %>
                </p>
            </li>
            <%}%>
        </ul>
    </div>

    <div id="content" class="content">
        <div class="users" id="userList">
            <%
                AuthDao authDao = new AuthDaoImpl();
                List<UserInfo> userInfos = authDao.getAllUsers();
            %>
            <ul id="user-nav">
                <% for (int i = 0; i < userInfos.size(); i++) {%>
                <li>
                    <p class="user-item"> نام : <%= userInfos.get(i).getName() %>
                        <br>
                        نام کاربری : <%= userInfos.get(i).getUserName() %>
                        <br>
                        ایمیل : <%= userInfos.get(i).getEmailAddress() %>
                        <br>
                        <% if (userInfos.get(i).getRegisterDate() != null) {%>
                        تاریخ ثبت نام : <%= userInfos.get(i).getRegisterDate().toString() %>
                        <br>
                        <%}%>
                        <% if (userInfos.get(i).getLastActivityDate() != null) {%>
                        تاریخ آخرین فعالیت : <%= userInfos.get(i).getLastActivityDate().toString() %>
                        <br>
                        <%}%>
                    </p>
                </li>
                <%}%>
            </ul>
        </div>
    </div>

</div>
</body>
</html>