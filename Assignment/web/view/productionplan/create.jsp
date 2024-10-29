<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Production Plan</title>
        <style>
            /* Kiểu cho ô nhập khi có lỗi */
            input.error, select.error {
                border: 1px solid red;
                background-color: #fff6f6;
            }

            /* Kiểu cho thông báo lỗi */
            .error-message {
                color: red;
                font-size: 0.9em;
                margin-top: 4px;
            }
        </style>
    </head>
    <body>
        <h2>Create Production Plan</h2>

        <!-- Nút Back -->
       

        <form action="create" method="POST">
            <!-- Tên kế hoạch -->
            <label for="name">Plan title: <span style="color: red;">*</span></label>
            <input type="text" name="name" value="${not empty nameError ? '' : param.name}" class="${not empty nameError ? 'error' : ''}" />
            <c:if test="${not empty nameError}">
                <div class="error-message">${nameError}</div>
            </c:if>
            <br/>

            <!-- Ngày bắt đầu -->
            <label for="from">From: <span style="color: red;">*</span></label>
            <input type="date" name="from" value="${not empty fromError || not empty dateError ? '' : param.from}" class="${not empty fromError || not empty dateError ? 'error' : ''}" />
            <c:if test="${not empty fromError}">
                <div class="error-message">${fromError}</div>
            </c:if>
            <br/>

            <!-- Ngày kết thúc -->
            <label for="to">To: <span style="color: red;">*</span></label>
            <input type="date" name="to" value="${not empty toError || not empty dateError ? '' : param.to}" class="${not empty toError || not empty dateError ? 'error' : ''}" />
            <c:if test="${not empty toError}">
                <div class="error-message">${toError}</div>
            </c:if>
            <c:if test="${not empty dateError}">
                <div class="error-message">${dateError}</div>
            </c:if>
            <br/>

            <!-- Chọn Workshop -->
            <label for="did">Workshop: <span style="color: red;">*</span></label>
            <select name="did" class="${not empty workshopError ? 'error' : ''}">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}" ${param.did == d.id ? 'selected' : ''}>${d.name}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty workshopError}">
                <div class="error-message">${workshopError}</div>
            </c:if>
            <br/>

            <!-- Bảng sản phẩm với số lượng và chi phí -->
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

            <!-- Lỗi chung nếu không có sản phẩm hợp lệ -->
            <c:if test="${not empty productError}">
                <div class="error-message">${productError}</div>
            </c:if>
            <br/>
 <button type="button" onclick="window.location.href='plan'" style="margin-left: 10px;">Back</button>
            <!-- Nút Lưu kế hoạch -->
            <input type="submit" name="Save" value="Save"/>
        </form>

        <!-- Thông báo thành công -->
        <c:if test="${not empty successMessage}">
            <div style="color: green; font-size: 1em; margin-top: 10px;"><strong>${successMessage}</strong></div>
        </c:if>
    </body>
</html>
