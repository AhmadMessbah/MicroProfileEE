# 🚀 MFT Plus - سیستم مدیریت اشخاص و سیم‌کارت‌ها

## 📋 **معرفی پروژه**

**MFT Plus** یک سیستم مدیریت اشخاص و سیم‌کارت‌ها است که با استفاده از **Jakarta EE 10** و **TomEE 10** پیاده‌سازی شده است. این پروژه شامل دو مایکروسرویس جداگانه است که با استفاده از **MicroProfile REST Client** با یکدیگر ارتباط برقرار می‌کنند.

## ✨ **ویژگی‌های کلیدی**

### 🏗️ **معماری مایکروسرویس**
- **Person Microservice**: مدیریت اطلاعات اشخاص
- **SimCard Microservice**: مدیریت اطلاعات سیم‌کارت‌ها
- **REST Client Communication**: ارتباط بین مایکروسرویس‌ها
- **JTA Transactions**: مدیریت تراکنش‌های توزیع شده

### 🎯 **قابلیت‌های اصلی**
- ✅ **CRUD کامل** برای اشخاص و سیم‌کارت‌ها
- ✅ **JSP UI** با طراحی زیبا و RTL
- ✅ **WebSocket** برای به‌روزرسانی زنده
- ✅ **Global Exception Handler**
- ✅ **Bean Validation**
- ✅ **Lombok** برای کاهش کد
- ✅ **Logging** کامل با SLF4J + Logback
- ✅ **Unit Tests** جامع

### 🔧 **تکنولوژی‌های استفاده شده**
- **Jakarta EE 10** (TomEE 10)
- **JPA/Hibernate** با MySQL
- **MicroProfile REST Client**
- **JTA Transactions**
- **CDI (Contexts and Dependency Injection)**
- **WebSocket API**
- **JSP/Servlet**
- **Lombok**
- **SLF4J + Logback**
- **JUnit 5 + Mockito + AssertJ**

## 🏗️ **ساختار پروژه**

```
mftplus/
├── src/main/java/com/mftplus/
│   ├── person/                    # مایکروسرویس اشخاص
│   │   ├── entity/Person.java
│   │   ├── repository/PersonRepository.java
│   │   ├── service/PersonService.java
│   │   ├── servlet/PersonServlet.java
│   │   ├── rest/PersonRestAPI.java
│   │   ├── client/SimCardClient.java
│   │   └── dto/PersonDTO.java
│   ├── simcard/                   # مایکروسرویس سیم‌کارت‌ها
│   │   ├── entity/SimCard.java
│   │   ├── entity/SimCardStatus.java
│   │   ├── repository/SimCardRepository.java
│   │   ├── service/SimCardService.java
│   │   ├── servlet/SimCardServlet.java
│   │   ├── rest/SimCardRestAPI.java
│   │   ├── client/PersonClient.java
│   │   └── dto/SimCardDTO.java
│   ├── admin/                     # پنل ادمین
│   │   ├── servlet/AdminServlet.java
│   │   └── websocket/AdminWebSocket.java
│   └── common/                    # کلاس‌های مشترک
│       └── exception/GlobalExceptionHandler.java
├── src/test/java/com/mftplus/     # تست‌های واحد
├── src/main/resources/
│   ├── META-INF/
│   │   ├── persistence.xml
│   │   └── microprofile-config.properties
│   └── logback.xml
├── src/main/webapp/
│   ├── index.jsp                  # صفحه اصلی
│   ├── WEB-INF/
│   │   ├── web.xml
│   │   ├── beans.xml
│   │   └── jsp/                   # صفحات JSP
├── pom.xml                        # Maven Configuration
├── tomee.xml                      # TomEE Configuration
├── database_setup.sql            # اسکریپت پایگاه داده
└── README.md                      # این فایل
```

## 🚀 **راهنمای اجرا**

### **پیش‌نیازها**
- **Java 17+**
- **Maven 3.8+**
- **MySQL 8.0+**
- **TomEE 10.0+**

### **مراحل نصب و اجرا**

#### **1. نصب MySQL**
```bash
# نصب MySQL Server
# ایجاد کاربر root با رمز root123
# فعال‌سازی MySQL Service
```

#### **2. ایجاد پایگاه داده**
```bash
# اجرای اسکریپت database_setup.sql
mysql -u root -p < database_setup.sql
```

#### **3. پیکربندی TomEE**
```bash
# کپی کردن tomee.xml به conf/ directory TomEE
cp tomee.xml $TOMEE_HOME/conf/
```

#### **4. کامپایل و بیلد پروژه**
```bash
mvn clean compile
mvn package
```

#### **5. اجرای تست‌ها**
```bash
mvn test
```

#### **6. استقرار در TomEE**
```bash
# کپی کردن WAR file به webapps directory
cp target/mftplus-1.0-SNAPSHOT.war $TOMEE_HOME/webapps/
```

#### **7. راه‌اندازی TomEE**
```bash
# Windows
$TOMEE_HOME/bin/catalina.bat run

# Linux/Mac
$TOMEE_HOME/bin/catalina.sh run
```

#### **8. دسترسی به سیستم**
- **صفحه اصلی**: http://localhost:8080/mftplus/
- **مدیریت اشخاص**: http://localhost:8080/mftplus/person
- **مدیریت سیم‌کارت‌ها**: http://localhost:8080/mftplus/simcard
- **پنل ادمین**: http://localhost:8080/mftplus/admin

## 🧪 **تست‌ها**

### **اجرای تست‌های واحد**
```bash
mvn test
```

### **تست‌های موجود**
- **PersonTest**: تست Entity Person
- **SimCardTest**: تست Entity SimCard
- **SimCardStatusTest**: تست Enum SimCardStatus
- **PersonDTOTest**: تست DTO Person

## 📊 **API Endpoints**

### **Person Service**
- `GET /api/persons` - لیست تمام اشخاص
- `GET /api/persons/{id}` - دریافت شخص با ID
- `POST /api/persons` - ایجاد شخص جدید
- `PUT /api/persons/{id}` - به‌روزرسانی شخص
- `DELETE /api/persons/{id}` - حذف شخص
- `GET /api/persons/count` - تعداد اشخاص

### **SimCard Service**
- `GET /api/simcards` - لیست تمام سیم‌کارت‌ها
- `GET /api/simcards/{id}` - دریافت سیم‌کارت با ID
- `POST /api/simcards` - ایجاد سیم‌کارت جدید
- `PUT /api/simcards/{id}` - به‌روزرسانی سیم‌کارت
- `DELETE /api/simcards/{id}` - حذف سیم‌کارت
- `GET /api/simcards/count` - تعداد سیم‌کارت‌ها

## 🔧 **پیکربندی**

### **پایگاه داده**
- **Person DB**: `jdbc:mysql://localhost:3306/person_db`
- **SimCard DB**: `jdbc:mysql://localhost:3306/simcard_db`
- **Username**: `root`
- **Password**: `root123`

### **REST Client**
- **Person Client**: `http://localhost:8080/mftplus/person/api`
- **SimCard Client**: `http://localhost:8080/mftplus/simcard/api`

## 📝 **لاگ‌ها**

### **فایل‌های لاگ**
- **Console**: خروجی کنسول
- **File**: `logs/mftplus.log`

### **سطوح لاگ**
- **INFO**: اطلاعات عمومی
- **DEBUG**: جزئیات عملیات
- **WARN**: هشدارها
- **ERROR**: خطاها

## 🎯 **Best Practices**

### **کد**
- استفاده از **Lombok** برای کاهش boilerplate
- **Bean Validation** برای اعتبارسنجی
- **Global Exception Handler** برای مدیریت خطاها
- **JTA Transactions** برای یکپارچگی داده‌ها

### **معماری**
- **Separation of Concerns** با لایه‌های مجزا
- **Dependency Injection** با CDI
- **RESTful API** برای ارتباط بین سرویس‌ها
- **Microservice Architecture** برای مقیاس‌پذیری

## 🐛 **عیب‌یابی**

### **مشکلات رایج**
1. **CDI Error**: بررسی فایل `beans.xml`
2. **Database Connection**: بررسی تنظیمات MySQL
3. **REST Client**: بررسی فایل `microprofile-config.properties`
4. **Port Conflict**: بررسی پورت 8080

### **لاگ‌های مفید**
```bash
# بررسی لاگ‌های TomEE
tail -f $TOMEE_HOME/logs/catalina.out

# بررسی لاگ‌های اپلیکیشن
tail -f logs/mftplus.log
```

## 📚 **مستندات بیشتر**

- **Jakarta EE 10**: https://jakarta.ee/specifications/
- **TomEE 10**: https://tomee.apache.org/
- **MicroProfile**: https://microprofile.io/
- **Lombok**: https://projectlombok.org/

## 👥 **مشارکت**

برای مشارکت در پروژه:
1. Fork کنید
2. Branch جدید ایجاد کنید
3. تغییرات را commit کنید
4. Pull Request ارسال کنید

## 📄 **مجوز**

این پروژه تحت مجوز MIT منتشر شده است.

---

**🎉 MFT Plus - سیستم مدیریت اشخاص و سیم‌کارت‌ها با Jakarta EE 10**