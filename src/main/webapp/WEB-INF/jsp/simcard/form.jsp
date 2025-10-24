<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? 'ویرایش سیم‌کارت' : 'افزودن سیم‌کارت جدید'}</title>
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
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #27ae60;
            box-shadow: 0 0 5px rgba(39, 174, 96, 0.3);
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
        .help-text {
            font-size: 12px;
            color: #6c757d;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>${isEdit ? 'ویرایش سیم‌کارت' : 'افزودن سیم‌کارت جدید'}</h1>
            <a href="${pageContext.request.contextPath}/simcard/" class="btn btn-secondary">بازگشت</a>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/simcard/${isEdit ? 'edit/' : ''}${isEdit ? simCard.id : ''}">
            <div class="form-group">
                <label for="phoneNumber">شماره تلفن <span class="required">*</span></label>
                <input type="tel" id="phoneNumber" name="phoneNumber" value="${simCard.phoneNumber}" 
                       pattern="09[0-9]{9}" required>
                <div class="help-text">شماره تلفن باید با 09 شروع شده و 11 رقم باشد</div>
            </div>

            <div class="form-group">
                <label for="iccid">شماره ICCID <span class="required">*</span></label>
                <input type="text" id="iccid" name="iccid" value="${simCard.iccid}" 
                       minlength="19" maxlength="20" required>
                <div class="help-text">شماره ICCID باید 19 یا 20 رقم باشد</div>
            </div>

            <div class="form-group">
                <label for="operator">اپراتور</label>
                <input type="text" id="operator" name="operator" value="${simCard.operator}" 
                       maxlength="50">
            </div>

            <div class="form-group">
                <label for="status">وضعیت <span class="required">*</span></label>
                <select id="status" name="status" required>
                    <option value="ACTIVE" ${simCard.status == 'ACTIVE' ? 'selected' : ''}>فعال</option>
                    <option value="INACTIVE" ${simCard.status == 'INACTIVE' ? 'selected' : ''}>غیرفعال</option>
                    <option value="SUSPENDED" ${simCard.status == 'SUSPENDED' ? 'selected' : ''}>معلق</option>
                    <option value="EXPIRED" ${simCard.status == 'EXPIRED' ? 'selected' : ''}>منقضی شده</option>
                </select>
            </div>

            <div class="form-group">
                <label for="personId">شناسه شخص</label>
                <input type="number" id="personId" name="personId" value="${simCard.personId}" min="1">
                <div class="help-text">شناسه شخص مالک سیم‌کارت (اختیاری)</div>
            </div>

            <div class="form-group">
                <label for="balance">موجودی</label>
                <input type="number" id="balance" name="balance" value="${simCard.balance}" 
                       step="0.01" min="0">
                <div class="help-text">موجودی سیم‌کارت به تومان</div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${isEdit ? 'به‌روزرسانی' : 'ایجاد'}</button>
                <a href="${pageContext.request.contextPath}/simcard/" class="btn btn-secondary">انصراف</a>
            </div>
        </form>
    </div>
</body>
</html>
