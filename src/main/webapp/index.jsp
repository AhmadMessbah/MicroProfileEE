<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>سیستم مدیریت اشخاص و سیم‌کارت‌ها</title>
    <style>
        body {
            font-family: 'Tahoma', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            direction: rtl;
        }
        .container {
            background: white;
            padding: 50px;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 600px;
            width: 90%;
        }
        .logo {
            font-size: 48px;
            margin-bottom: 20px;
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 10px;
            font-size: 28px;
        }
        .subtitle {
            color: #7f8c8d;
            margin-bottom: 40px;
            font-size: 16px;
        }
        .services-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        .service-card {
            background: #f8f9fa;
            padding: 30px;
            border-radius: 15px;
            border: 2px solid transparent;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        .service-card:hover {
            border-color: #3498db;
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }
        .service-card h3 {
            color: #2c3e50;
            margin-bottom: 15px;
            font-size: 20px;
        }
        .service-card p {
            color: #6c757d;
            margin-bottom: 20px;
            line-height: 1.6;
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            font-weight: bold;
            transition: all 0.3s ease;
        }
        .btn-primary {
            background-color: #3498db;
            color: white;
        }
        .btn-primary:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
        }
        .btn-success {
            background-color: #27ae60;
            color: white;
        }
        .btn-success:hover {
            background-color: #229954;
            transform: translateY(-2px);
        }
        .btn-danger {
            background-color: #e74c3c;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c0392b;
            transform: translateY(-2px);
        }
        .admin-section {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            margin-top: 30px;
        }
        .admin-section h3 {
            margin-bottom: 15px;
            font-size: 22px;
        }
        .admin-section p {
            margin-bottom: 20px;
            opacity: 0.9;
        }
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-top: 30px;
        }
        .feature {
            background: rgba(255,255,255,0.1);
            padding: 15px;
            border-radius: 10px;
            text-align: center;
        }
        .feature h4 {
            margin-bottom: 10px;
            font-size: 16px;
        }
        .feature p {
            font-size: 12px;
            opacity: 0.8;
            margin: 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">📱</div>
        <h1>سیستم مدیریت اشخاص و سیم‌کارت‌ها</h1>
        <p class="subtitle">مایکروسرویس‌های جداگانه با JTA و REST Client</p>
        
        <div class="services-grid">
            <div class="service-card" onclick="location.href='${pageContext.request.contextPath}/person/'">
                <h3>👤 مدیریت اشخاص</h3>
                <p>مدیریت کامل اطلاعات اشخاص شامل نام، ایمیل، شماره تلفن و آدرس با عملیات CRUD</p>
                <a href="${pageContext.request.contextPath}/person/" class="btn btn-primary">ورود به بخش اشخاص</a>
            </div>
            
            <div class="service-card" onclick="location.href='${pageContext.request.contextPath}/simcard/'">
                <h3>📱 مدیریت سیم‌کارت‌ها</h3>
                <p>مدیریت سیم‌کارت‌ها شامل شماره تلفن، ICCID، اپراتور و وضعیت با عملیات CRUD</p>
                <a href="${pageContext.request.contextPath}/simcard/" class="btn btn-success">ورود به بخش سیم‌کارت‌ها</a>
            </div>
        </div>
        
        <div class="admin-section">
            <h3>🔧 پنل ادمین</h3>
            <p>مشاهده زنده اطلاعات اشخاص و سیم‌کارت‌ها با WebSocket</p>
            <a href="${pageContext.request.contextPath}/admin" class="btn btn-danger">ورود به پنل ادمین</a>
            
            <div class="features">
                <div class="feature">
                    <h4>🔄 به‌روزرسانی زنده</h4>
                    <p>WebSocket برای نمایش تغییرات در زمان واقعی</p>
                </div>
                <div class="feature">
                    <h4>📊 آمار کلی</h4>
                    <p>نمایش تعداد اشخاص و سیم‌کارت‌ها</p>
                </div>
                <div class="feature">
                    <h4>👥 کاربران متصل</h4>
                    <p>نمایش تعداد کاربران متصل به سیستم</p>
                </div>
            </div>
        </div>
        
        <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee;">
            <h4 style="color: #2c3e50; margin-bottom: 15px;">ویژگی‌های سیستم</h4>
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 10px; font-size: 12px; color: #6c757d;">
                <div>✅ Jakarta EE 10</div>
                <div>✅ JTA Transactions</div>
                <div>✅ MySQL Database</div>
                <div>✅ REST Client</div>
                <div>✅ WebSocket</div>
                <div>✅ JSP UI</div>
                <div>✅ Global Exception</div>
                <div>✅ Bean Validation</div>
            </div>
        </div>
    </div>
</body>
</html>