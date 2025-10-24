<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>سیم‌کارت‌های ${person.fullName}</title>
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
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .person-info {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 30px;
            border-right: 4px solid #3498db;
        }
        .person-info h3 {
            color: #2c3e50;
            margin-top: 0;
        }
        .person-info p {
            margin: 5px 0;
            color: #6c757d;
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
        .actions {
            white-space: nowrap;
        }
        .actions .btn {
            margin: 2px;
            padding: 5px 10px;
            font-size: 12px;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>سیم‌کارت‌های ${person.fullName}</h1>
            <div>
                <a href="${pageContext.request.contextPath}/person/" class="btn btn-secondary">بازگشت به لیست اشخاص</a>
                <a href="${pageContext.request.contextPath}/simcard/new" class="btn btn-primary">افزودن سیم‌کارت جدید</a>
            </div>
        </div>

        <div class="person-info">
            <h3>اطلاعات شخص</h3>
            <p><strong>نام:</strong> ${person.fullName}</p>
            <p><strong>ایمیل:</strong> ${person.email}</p>
            <p><strong>شماره تلفن:</strong> ${person.phoneNumber}</p>
            <p><strong>آدرس:</strong> ${person.address}</p>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
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
                                <td>${simCard.status}</td>
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
                    <h3>هیچ سیم‌کارتی برای این شخص ثبت نشده است</h3>
                    <p>برای افزودن سیم‌کارت جدید روی دکمه "افزودن سیم‌کارت جدید" کلیک کنید</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
