<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Production Detail Plan</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 40px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #333;
        }
        th {
            background-color: #333;
            color: #fff;
            padding: 10px;
        }
        td {
            padding: 10px;
            text-align: center;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Production Detail Plan</h2>
    <c:forEach var="plan" items="${plans}">
        <div>
            <h3>Plan: ${plan.name}</h3>
            <table>
                <thead>
                    <tr>
                        <th>Start Date</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Shift</th>
                        <th>Quantity</th>
                     
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="campain" items="${plan.campains}">
                        <c:forEach var="schedual" items="${campain.schedualCampains}">
                            <tr>
                                <td>${plan.start}</td>
                                <td>${campain.product.id}</td>
                                <td>${campain.product.name}</td>
                                <td>${schedual.shift}</td>
                                <td>${campain.quantity}</td>
                                
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:forEach>
</div>
</body>
</html>
