# 🚀 راهنمای اجرای پروژه MFT Plus در IntelliJ IDEA

## 📋 **مراحل پیکربندی IntelliJ**

### **1. تنظیمات پروژه**
- **Project SDK**: Java 17
- **Language Level**: 17
- **Maven**: 3.8+
- **TomEE**: 10.0+

### **2. تنظیمات TomEE Server**
1. **Run Configuration** → **TomEE Server**
2. **Application Server**: انتخاب TomEE 10.0+
3. **Deploy**: `mftplus-1.0-SNAPSHOT.war`
4. **Context Path**: `/mftplus`

### **3. تنظیمات پایگاه داده**
```sql
-- اجرای اسکریپت database_setup.sql
mysql -u root -p < database_setup.sql
```

### **4. تنظیمات VM Options**
```
-Dtomee.configuration=classpath:tomee.xml
-Djakarta.enterprise.inject.scope.annotation.Singleton.enabled=true
-Djakarta.enterprise.inject.scope.annotation.ApplicationScoped.enabled=true
```

### **5. تنظیمات Environment Variables**
```
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USERNAME=root
MYSQL_PASSWORD=root123
```

## 🔧 **مشکلات رایج و راه‌حل‌ها**

### **مشکل 1: CDI Error**
```
jakarta.enterprise.inject.UnsatisfiedResolutionException
```
**راه‌حل:**
- بررسی فایل `beans.xml`
- بررسی annotation های `@ApplicationScoped`
- بررسی فایل `microprofile-config.properties`

### **مشکل 2: Database Connection Error**
```
java.sql.SQLException: Access denied
```
**راه‌حل:**
- بررسی رمز عبور MySQL
- بررسی تنظیمات `tomee.xml`
- بررسی دسترسی کاربر root

### **مشکل 3: REST Client Error**
```
Api type [com.mftplus.simcard.client.PersonClient] is not found
```
**راه‌حل:**
- بررسی annotation `@RegisterRestClient`
- بررسی فایل `microprofile-config.properties`
- بررسی URL های REST Client

## 📊 **تست عملکرد**

### **1. تست صفحه اصلی**
```
http://localhost:8080/mftplus/
```

### **2. تست API اشخاص**
```
GET http://localhost:8080/mftplus/api/persons
POST http://localhost:8080/mftplus/api/persons
```

### **3. تست API سیم‌کارت‌ها**
```
GET http://localhost:8080/mftplus/api/simcards
POST http://localhost:8080/mftplus/api/simcards
```

### **4. تست WebSocket**
```
ws://localhost:8080/mftplus/admin/websocket
```

## 🎯 **نکات مهم**

1. **فایل `tomee.xml`** در `src/main/resources/` قرار دارد
2. **فایل `beans.xml`** در `src/main/webapp/WEB-INF/` قرار دارد
3. **فایل `microprofile-config.properties`** در `src/main/resources/META-INF/` قرار دارد
4. **تمام فایل‌های پیکربندی** در پروژه قرار دارند و نیازی به کپی کردن نیست

## 🚀 **اجرای پروژه**

1. **کامپایل**: `mvn clean compile`
2. **تست**: `mvn test`
3. **بیلد**: `mvn package`
4. **اجرا**: Run Configuration در IntelliJ

---

**🎉 پروژه آماده اجرا در IntelliJ است!**
