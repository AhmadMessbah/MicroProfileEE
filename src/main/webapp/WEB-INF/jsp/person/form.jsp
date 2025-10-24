<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? 'ویرایش شخص' : 'افزودن شخص جدید'}</title>
    <style>
        body {
            font-family: 'Tahoma', Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
            direction: rtl;
        }
        .container {
            max-width: 600px;
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
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #2c3e50;
        }
        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group input:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
        }
        .form-group textarea {
            height: 80px;
            resize: vertical;
        }
        .form-actions {
            text-align: center;
            margin-top: 30px;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        .required {
            color: #e74c3c;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>${isEdit ? 'ویرایش شخص' : 'افزودن شخص جدید'}</h1>
            <a href="${pageContext.request.contextPath}/person/" class="btn btn-secondary">بازگشت</a>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/person/${isEdit ? 'edit/' : ''}${isEdit ? person.id : ''}">
            <div class="form-group">
                <label for="firstName">نام <span class="required">*</span></label>
                <input type="text" id="firstName" name="firstName" value="${person.firstName}" required>
            </div>

            <div class="form-group">
                <label for="lastName">نام خانوادگی <span class="required">*</span></label>
                <input type="text" id="lastName" name="lastName" value="${person.lastName}" required>
            </div>

            <div class="form-group">
                <label for="email">ایمیل</label>
                <input type="email" id="email" name="email" value="${person.email}">
            </div>

            <div class="form-group">
                <label for="phoneNumber">شماره تلفن</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" value="${person.phoneNumber}">
            </div>

            <div class="form-group">
                <label for="address">آدرس</label>
                <textarea id="address" name="address">${person.address}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${isEdit ? 'به‌روزرسانی' : 'ایجاد'}</button>
                <a href="${pageContext.request.contextPath}/person/" class="btn btn-secondary">انصراف</a>
            </div>
        </form>
    </div>
</body>
</html>
