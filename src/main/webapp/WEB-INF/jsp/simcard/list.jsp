<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت سیم‌کارت‌ها</title>
    <style>
        body {
            font-family: 'Tahoma', Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
            direction: rtl;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #27ae60;
        }
        .header h1 {
            color: #2c3e50;
            margin: 0;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 5px;
            font-size: 14px;
        }
        .btn-primary {
            background-color: #27ae60;
            color: white;
        }
        .btn-primary:hover {
            background-color: #229954;
        }
        .btn-info {
            background-color: #17a2b8;
            color: white;
        }
        .btn-info:hover {
            background-color: #138496;
        }
        .btn-warning {
            background-color: #f39c12;
            color: white;
        }
        .btn-warning:hover {
            background-color: #e67e22;
        }
        .btn-danger {
            background-color: #e74c3c;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c0392b;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: right;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #27ae60;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
        }
        .actions {
            white-space: nowrap;
        }
        .actions .btn {
            margin: 2px;
            padding: 5px 10px;
            font-size: 12px;
        }
        .status-active {
            color: #27ae60;
            font-weight: bold;
        }
        .status-inactive {
            color: #e74c3c;
            font-weight: bold;
        }
        .status-suspended {
            color: #f39c12;
            font-weight: bold;
        }
        .status-expired {
            color: #95a5a6;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>مدیریت سیم‌کارت‌ها</h1>
            <div>
                <a href="${pageContext.request.contextPath}/simcard/new" class="btn btn-primary">افزودن سیم‌کارت جدید</a>
                <a href="${pageContext.request.contextPath}/admin" class="btn btn-info">صفحه ادمین</a>
                <a href="${pageContext.request.contextPath}/person/" class="btn btn-info">مدیریت اشخاص</a>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>

        <c:choose>
            <c:when test="${not empty simCards}">
                <table>
                    <thead>
                        <tr>
                            <th>شناسه</th>
                            <th>شماره تلفن</th>
                            <th>شماره ICCID</th>
                            <th>اپراتور</th>
                            <th>وضعیت</th>
                            <th>شناسه شخص</th>
                            <th>موجودی</th>
                            <th>تاریخ ایجاد</th>
                            <th>عملیات</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="simCard" items="${simCards}">
                            <tr>
                                <td>${simCard.id}</td>
                                <td>${simCard.phoneNumber}</td>
                                <td>${simCard.iccid}</td>
                                <td>${simCard.operator}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${simCard.status == 'ACTIVE'}">
                                            <span class="status-active">فعال</span>
                                        </c:when>
                                        <c:when test="${simCard.status == 'INACTIVE'}">
                                            <span class="status-inactive">غیرفعال</span>
                                        </c:when>
                                        <c:when test="${simCard.status == 'SUSPENDED'}">
                                            <span class="status-suspended">معلق</span>
                                        </c:when>
                                        <c:when test="${simCard.status == 'EXPIRED'}">
                                            <span class="status-expired">منقضی شده</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${simCard.status}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${simCard.personId}</td>
                                <td>${simCard.balance}</td>
                                <td>${simCard.createdAt}</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/simcard/edit/${simCard.id}" class="btn btn-warning">ویرایش</a>
                                    <a href="${pageContext.request.contextPath}/simcard/delete/${simCard.id}" 
                                       class="btn btn-danger" 
                                       onclick="return confirm('آیا از حذف این سیم‌کارت اطمینان دارید؟')">حذف</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 50px; color: #7f8c8d;">
                    <h3>هیچ سیم‌کارتی ثبت نشده است</h3>
                    <p>برای افزودن سیم‌کارت جدید روی دکمه "افزودن سیم‌کارت جدید" کلیک کنید</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
