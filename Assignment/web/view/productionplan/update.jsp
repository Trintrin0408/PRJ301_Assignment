<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Production Plan</title>
    </head>
    <body>
        <form action="update" method="POST">
            <!-- Hidden field to hold the plan ID -->
            <input type="hidden" name="id" value="${requestScope.plan.id}" />

            Plan title: <input type="text" name="name" value="${requestScope.plan.name}"/> <br/>
            From: <input type="date" name="from" value="${requestScope.plan.start}"/> 
            To: <input type="date" name="to" value="${requestScope.plan.end}"/> <br/>

            Workshop: 
            <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}" ${requestScope.plan.dept.id eq d.id ? "selected=\"selected\"" : ""}>
                        ${d.name}
                    </option>
                </c:forEach>
            </select> 
            <br/>

            <table border="1">
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
<!--                    <th>Cost</th>-->
                </tr>

                <c:forEach items="${requestScope.plan.campains}" var="p">
                    <a>${p.product.name}</a>
                    <tr>
                        <!-- Hidden input to hold product ID -->
                        <td>${p.product.name} <input type="hidden" name="pid" value="${p.product.id}" /></td>
                        <td>
                            <input type="number" name="quantity${p.product.id}" 
                                   value="${!empty p.quantity ? p.quantity : ''}" />
                        </td>
<!--                        <td>
                            <input type="number" step="0.01" name="cost${p.id}" 
                                   value="${!empty requestScope.planCampains[p.quantity] ? requestScope.planCampains[p.product.id].cost : ''}" />
                        </td>-->

                    </tr>
                </c:forEach>
            </table>
 <button type="button" onclick="window.location.href='plan'">Back</button>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
