<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Production Plan</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 8px;
                border: 1px solid #ddd;
                text-align: center;
            }
            th {
                background-color: #333;
                color: white;
            }
        </style>
    </head>
    <body>
        <form action="update" method="POST">
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
            
             <table border="1px">
                <tr>
                    <td>Product</td>
                    <td>Quantity</td>
                    <td>Cost</td>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                    <tr>
                        <td>${p.name}<input type="hidden" value="${p.id}" name="pid"/></td>

                        <!-- Tạo biến quantityKey và costKey -->
                        <c:set var="quantityKey">
                            quantity${p.id}
                        </c:set>
                        <c:set var="quantityParam" value="${param[quantityKey]}" />

                        <c:set var="costKey">
                            cost${p.id}
                        </c:set>
                        <c:set var="costParam" value="${param[costKey]}" />

                        <td>
                            <input type="text" name="quantity${p.id}" 
                                   value="${not empty quantityError ? '' : quantityParam}" 
                                   class="${not empty quantityError ? 'error' : ''}" />
                            <c:if test="${not empty quantityError}">
                                <div class="error-message">${quantityError}</div>
                            </c:if>
                        </td>
                        <td>
                            <input type="text" name="cost${p.id}" 
                                   value="${not empty costError ? '' : costParam}" 
                                   class="${not empty costError ? 'error' : ''}" />
                            <c:if test="${not empty costError}">
                                <div class="error-message">${costError}</div>
                            </c:if>
                        </td>
                    </tr>   
                </c:forEach>
            </table>
            <br/>

                   <table>
                <tr>
                    <th>Date</th>
                    <th>Product Name</th>
                    <th>K1</th>
                    <th>K2</th>
                    <th>K3</th>
                </tr>

                <!-- Loop through each date in dateList first to display data by date -->
                <c:forEach items="${dateList}" var="date" varStatus="status">
                    <!-- Loop through each PlanCampain for each date -->
                    <c:forEach items="${requestScope.plan.campains}" var="p">
                        <tr>
                            <td><input type="date" name="schedualDate${p.product.id}_${status.index}" value="${date}" readonly/></td>
                            <td>${p.product.name}</td>

                            <!-- Loop through each SchedualCampain in p.schedualCampains to display quantities for K1, K2, K3 -->
                            <c:forEach items="${p.schedualCampains}" var="s" varStatus="shiftStatus">
                                <c:choose>
                                    <c:when test="${shiftStatus.index == 0}">
                                        <td><input type="number" name="shiftK1${p.product.id}_${status.index}" value="${s.quantity}" /></td>
                                    </c:when>
                                    <c:when test="${shiftStatus.index == 1}">
                                        <td><input type="number" name="shiftK2${p.product.id}_${status.index}" value="${s.quantity}" /></td>
                                    </c:when>
                                    <c:when test="${shiftStatus.index == 2}">
                                        <td><input type="number" name="shiftK3${p.product.id}_${status.index}" value="${s.quantity}" /></td>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>



            <button type="button" onclick="window.location.href='plan'">Back</button>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
