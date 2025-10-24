<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت اشخاص</title>
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
            border-bottom: 2px solid #3498db;
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
            background-color: #3498db;
            color: white;
        }
        .btn-primary:hover {
            background-color: #2980b9;
        }
        .btn-success {
            background-color: #27ae60;
            color: white;
        }
        .btn-success:hover {
            background-color: #229954;
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
        .btn-info {
            background-color: #17a2b8;
            color: white;
        }
        .btn-info:hover {
            background-color: #138496;
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
            background-color: #3498db;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>مدیریت اشخاص</h1>
            <div>
                <a href="${pageContext.request.contextPath}/person/new" class="btn btn-primary">افزودن شخص جدید</a>
                <a href="${pageContext.request.contextPath}/admin" class="btn btn-info">صفحه ادمین</a>
                <a href="${pageContext.request.contextPath}/simcard/" class="btn btn-success">مدیریت سیم‌کارت‌ها</a>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>

        <c:choose>
            <c:when test="${not empty persons}">
                <table>
                    <thead>
                        <tr>
                            <th>شناسه</th>
                            <th>نام</th>
                            <th>نام خانوادگی</th>
                            <th>ایمیل</th>
                            <th>شماره تلفن</th>
                            <th>آدرس</th>
                            <th>تاریخ ایجاد</th>
                            <th>عملیات</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="person" items="${persons}">
                            <tr>
                                <td>${person.id}</td>
                                <td>${person.firstName}</td>
                                <td>${person.lastName}</td>
                                <td>${person.email}</td>
                                <td>${person.phoneNumber}</td>
                                <td>${person.address}</td>
                                <td>${person.createdAt}</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/person/edit/${person.id}" class="btn btn-warning">ویرایش</a>
                                    <a href="${pageContext.request.contextPath}/person/simcards/${person.id}" class="btn btn-info">مشاهده سیم‌کارت‌ها</a>
                                    <a href="${pageContext.request.contextPath}/person/delete/${person.id}" 
                                       class="btn btn-danger" 
                                       onclick="return confirm('آیا از حذف این شخص اطمینان دارید؟')">حذف</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 50px; color: #7f8c8d;">
                    <h3>هیچ شخصی ثبت نشده است</h3>
                    <p>برای افزودن شخص جدید روی دکمه "افزودن شخص جدید" کلیک کنید</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
