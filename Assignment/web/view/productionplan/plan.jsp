<%-- 
    Document   : plan
    Created on : Oct 25, 2024, 1:23:29 PM
    Author     : ASUS
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plan Page</title>
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background-image: url('../view/img/crab_nebula.jpg'); /* Add galaxy background */
                background-size: cover;
                color: white;
            }

            /* Sidebar container */
            .sidebar {
                width: 200px;
                background-color: #333;
                height: 100vh;
                position: fixed;
                top: 0;
                left: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 20px 0;
            }

            /* User profile image */
            .sidebar img {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                margin-bottom: 20px;
                object-fit: cover; /* Make sure the image is fully displayed */
            }

            /* Username display */
            .sidebar p {
                color: white;
                font-size: 18px; /* Increase font size for username */
                font-weight: bold; /* Make username bold */
                margin: 10px 0;
            }

            /* Sidebar links */
            .sidebar a {
                text-decoration: none;
                color: white;
                padding: 15px 0; /* Adjust padding for better spacing */
                width: 100%;
                text-align: center;
                transition: background 0.3s;
                font-size: 16px; /* Increase font size for links */
            }

            /* Hover effect for sidebar links */
            .sidebar a:hover {
                background-color: #575757;
            }

            /* Button style */
            .sidebar button {
                background-color: #fff;
                border: none;
                color: black; /* Changed to black for better visibility */
                font-weight: bold; /* Make the button text bold */
                padding: 15px 0;
                display: inline-block;
                font-size: 18px; /* Increase font size for the button */
                margin: 20px 0;
                cursor: pointer;
                border-radius: 50%;
                width: 60px; /* Adjust button size */
                height: 60px; /* Adjust button size */
                transition: background 0.3s;
            }

            /* Button hover effect */
            .sidebar button:hover {
                background-color: #45a049; /* Change button hover color */
            }

            /* Plan board container */
            .plan-board {
                margin-left: 220px;
                display: flex;
                justify-content: space-around;
                padding-top: 50px;
            }

            /* Plan columns (status) */
            .status-column {
                width: 20%;
                background-color: white;
                color: black;
                border-radius: 10px;
                padding: 20px;
                text-align: center;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .status-column h3 {
                border-bottom: 1px solid gray;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }

            .status-column p {
                font-size: 18px;
                margin: 10px 0;
            }
            .sidebar hr {
                width: 80%; /* Adjust width */
                border: 1px solid #575757; /* Adjust color */
                margin: 20px 0; /* Add spacing around the line */
            }

        </style>
    </head>
    <body>

        <div class="sidebar">
            <img src="../view/img/avatar-trang-4.jpg" alt="profile-pic">
            <p>${sessionScope.account.username}</p>
            <hr/>
            <a href="<%= request.getContextPath() %>/productionplan/home">Home</a>
            <a href="<%= request.getContextPath() %>/productionplan/detailplan">Detail Plan</a>
            <a href="<%= request.getContextPath() %>/productionplan/create">+ Add plan</a>
            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        </div>

        <div class="plan-board">
            <div class="status-column">
                <h3>To do</h3>
                <!-- Display plans with status "To do" -->
                <c:forEach items="${requestScope.planListToDo}" var="plan">
                    <p>
                        <a href="update?id=${plan.id}" style="color: black; text-decoration: none;">
                            ${plan.name}
                        </a>
                    </p>
                </c:forEach>
            </div>
            <div class="status-column">
                <h3>Doing</h3>
                <!-- Display plans with status "Doing" -->
                <c:forEach items="${requestScope.planListDoing}" var="plan">
                    <p>
                        <a href="update?id=${plan.id}" style="color: black; text-decoration: none;">
                            ${plan.name}
                        </a>
                    </p>
                </c:forEach>
            </div>
            <div class="status-column">
                <h3>Done</h3>
                <!-- Display plans with status "Done" -->
                <c:forEach items="${requestScope.planListDone}" var="plan">
                    <p>
                        <a href="update?id=${plan.id}" style="color: black; text-decoration: none;">
                            ${plan.name}
                        </a>
                    </p>
                </c:forEach>
            </div>
            <div class="status-column">
                <h3>Late</h3>
                <!-- Display plans with status "Late" -->
                <c:forEach items="${requestScope.planListLate}" var="plan">
                    <p>
                        <a href="update?id=${plan.id}" style="color: black; text-decoration: none;">
                            ${plan.name}
                        </a>
                    </p>
                </c:forEach>
            </div>
        </div>


    </body>
</html>
