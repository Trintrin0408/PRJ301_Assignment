<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VTT Home Page</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .main-container {
            display: flex;
            height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #333;
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            justify-content: space-between;
        }
        .sidebar img {
            width: 150px;
            height: 150px;
            border-radius: 5px;
            margin-bottom: 10px; /* Reduced margin to bring img closer to the username */
        }
        .sidebar h2 {
            font-size: 22px;
            margin-bottom: 5px; /* Reduced space between admin and hr */
        }
        .sidebar hr {
            width: 100%;
            border: 1px solid #555;
            margin: 10px 0; /* Adjusted margin to bring the hr closer */
        }
        .sidebar a {
            color: #fff;
            text-decoration: none;
            font-size: 18px;
            margin-bottom: 20px;
        }
        .sidebar a:hover {
            color: #ccc;
        }
        .content {
            flex-grow: 1;
            background-color: #f4f4f4;
            padding: 50px;
            text-align: center;
        }
        .content h1 {
            font-size: 48px; /* Increased font size for 'Work space' */
            margin-bottom: 10px; /* Reduced margin for closer placement to the hr */
        }
        .content hr {
            margin: 20px auto; /* Centered HR */
            width: 80%; /* Set width to center horizontally */
            border: 1px solid #ccc;
        }
        .boards {
            display: flex;
            justify-content: space-around;
        }
        .board {
            width: 300px;
            height: 200px;
            padding: 0;
            background-color: #fff;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            position: relative;
            overflow: hidden;
            text-align: left;
            cursor: pointer;
            transition: transform 0.3s ease; /* Smooth zoom effect */
        }
        .board:hover {
            transform: scale(1.05); /* Slight zoom effect on hover */
        }
        .board img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 15px;
            transition: opacity 0.3s ease; /* Smooth opacity transition */
        }
        .board:hover img {
            opacity: 0.7; /* Slightly darken the image on hover */
        }
        .board a {
            position: absolute;
            top: 10px;
            left: 10px;
            font-size: 22px;
            color: white;
            font-weight: bold;
            text-shadow: 2px 2px 5px rgba(0,0,0,0.7);
            pointer-events: none; /* Make sure text doesn't interfere with clicking the board */
        }
    </style>
</head>
<body>
    <div class="main-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-top">
                <img src="../view/img/avatar-trang-4.jpg" alt="Admin Image"/>
                <h2>${sessionScope.account.username}</h2>
            </div>
            <hr>
            <div class="sidebar-bottom">
                <a href="/logout">Logout</a>
            </div>
        </div>
        
        <!-- Main content -->
        <div class="content">
            <h1>Work space</h1>
            <hr> <!-- Added HR between the header and the board -->
            <div class="boards">
                <!-- Board item 1 -->
                <div class="board" onclick="window.location.href='/plan';">
                    <img src="../view/img/space1.jpg" alt="Plan">
                    <a>Plan</a>
                </div>
                <!-- Board item 2 -->
                <div class="board" onclick="window.location.href='/detailplan';">
                    <img src="../view/img/images.jpg" alt="Detail Plan">
                    <a>Detail Plan</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
