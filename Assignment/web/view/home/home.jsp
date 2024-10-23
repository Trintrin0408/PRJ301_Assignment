<%-- 
    Document   : home.jsp
    Created on : Oct 23, 2024, 11:50:06 PM
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VTT Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            display: flex;
        }
        .sidebar {
            width: 200px;
            padding: 20px;
        }
        .content {
            flex-grow: 1;
            padding: 20px;
        }
        .button {
            margin: 20px 0;
        }
        a {
            text-decoration: none;
            color: blue;
            margin-right: 10px;
        }
        .board {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .board img {
            width: 50px;
            height: 50px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="sidebar">
        <h2>My Workspaces</h2>
        <p>VTT</p>
    </div>

    <div class="content">
        <h1>VTT Dashboard</h1>
        <div class="boards">
            <div class="board">
                <a href="/plan">Plan</a>
                <img src="path-to-your-image.jpg" alt="Plan Image">
            </div>
            <div class="board">
                <a href="/detailplan">Tạo bảng mới</a>
                <button class="button">Tạo mới bảng</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
