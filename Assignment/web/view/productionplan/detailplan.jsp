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
        p {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Button Back -->
        <button onclick="goBack()" style="margin-bottom: 20px; background: #fff;">Back</button>
        
        <!-- Production Detail Plan Content -->
        <h2>Production Detail Plan</h2>
        <p>-----------------------------------------</p>
        <c:forEach var="plan" items="${plans}">
            <div>
                <h3>Plan: ${plan.name}</h3>
                <h4>Plan Id: ${plan.id}</h4>
                <h4>Workshop: ${plan.dept.name}</h4>
        
                <table>
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Product Name</th>
                            <th colspan="3">Shift</th>
                        </tr>
                        <tr>
                            <th></th>
                            <th></th>
                            <th>K1</th>
                            <th>K2</th>
                            <th>K3</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="campain" items="${plan.campains}">
                            <c:set var="lastDate" value="" />
                            <c:forEach var="schedual" items="${campain.schedualCampains}">
                                <c:if test="${schedual.date != lastDate}">
                                    <c:set var="quantityK1" value="" />
                                    <c:set var="quantityK2" value="" />
                                    <c:set var="quantityK3" value="" />
                                    <c:forEach var="innerSchedual" items="${campain.schedualCampains}">
                                        <c:if test="${innerSchedual.date == schedual.date}">
                                            <c:choose>
                                                <c:when test="${innerSchedual.shift == 1}">
                                                    <c:set var="quantityK1" value="${innerSchedual.quantity}" />
                                                </c:when>
                                                <c:when test="${innerSchedual.shift == 2}">
                                                    <c:set var="quantityK2" value="${innerSchedual.quantity}" />
                                                </c:when>
                                                <c:when test="${innerSchedual.shift == 3}">
                                                    <c:set var="quantityK3" value="${innerSchedual.quantity}" />
                                                </c:when>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                    <tr>
                                        <td><input type="text" name="date" value="${schedual.date}" /></td>
                                        <td><input type="text" name="productName" value="${campain.product.name}" /></td>
                                        <td><input type="number" name="quantityK1" value="${quantityK1}" /></td>
                                        <td><input type="number" name="quantityK2" value="${quantityK2}" /></td>
                                        <td><input type="number" name="quantityK3" value="${quantityK3}" /></td>
                                    </tr>
                                    <c:set var="lastDate" value="${schedual.date}" />
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <p>-----------------------------------------</p>
        </c:forEach>
    </div>

    <!-- JavaScript Function to Go Back -->
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>
