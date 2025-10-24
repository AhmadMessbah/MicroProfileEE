# 🚀 راهنمای کامل اجرای پروژه MFT Plus

## 📋 **فهرست مطالب**
1. [پیش‌نیازها](#پیش‌نیازها)
2. [نصب و پیکربندی](#نصب-و-پیکربندی)
3. [ایجاد پایگاه داده](#ایجاد-پایگاه-داده)
4. [کامپایل و بیلد](#کامپایل-و-بیلد)
5. [اجرای تست‌ها](#اجرای-تست‌ها)
6. [استقرار در TomEE](#استقرار-در-tomee)
7. [راه‌اندازی سیستم](#راه‌اندازی-سیستم)
8. [تست عملکرد](#تست-عملکرد)
9. [عیب‌یابی](#عیب‌یابی)

## 🔧 **پیش‌نیازها**

### **نرم‌افزارهای مورد نیاز**
- **Java 17+** (JDK)
- **Maven 3.8+**
- **MySQL 8.0+**
- **TomEE 10.0+**
- **IntelliJ IDEA** (اختیاری)

### **بررسی نسخه‌ها**
```bash
# بررسی Java
java -version

# بررسی Maven
mvn -version

# بررسی MySQL
mysql --version
```

## 🛠️ **نصب و پیکربندی**

### **1. نصب Java 17**
```bash
# Windows - دانلود از Oracle یا OpenJDK
# Linux
sudo apt update
sudo apt install openjdk-17-jdk

# Mac
brew install openjdk@17
```

### **2. نصب Maven**
```bash
# Windows - دانلود از Apache Maven
# Linux
sudo apt install maven

# Mac
brew install maven
```

### **3. نصب MySQL**
```bash
# Windows - دانلود از MySQL Website
# Linux
sudo apt install mysql-server

# Mac
brew install mysql
```

### **4. نصب TomEE**
```bash
# دانلود TomEE 10.0+ از Apache Website
# استخراج در مسیر مناسب
# تنظیم متغیر محیطی TOMEE_HOME
```

## 🗄️ **ایجاد پایگاه داده**

### **1. راه‌اندازی MySQL**
```bash
# Windows
net start mysql

# Linux
sudo systemctl start mysql
sudo systemctl enable mysql

# Mac
brew services start mysql
```

### **2. ورود به MySQL**
```bash
mysql -u root -p
# رمز: root123
```

### **3. اجرای اسکریپت پایگاه داده**
```bash
# اجرای فایل database_setup.sql
mysql -u root -p < database_setup.sql
```

### **4. بررسی ایجاد پایگاه داده**
```sql
SHOW DATABASES;
USE person_db;
SHOW TABLES;
USE simcard_db;
SHOW TABLES;
```

## 🔨 **کامپایل و بیلد**

### **1. کلون پروژه**
```bash
git clone <repository-url>
cd mftplus
```

### **2. کامپایل پروژه**
```bash
# پاک کردن و کامپایل
mvn clean compile

# بیلد کامل
mvn clean package
```

### **3. بررسی فایل‌های تولید شده**
```bash
# بررسی target directory
ls -la target/

# بررسی WAR file
ls -la target/*.war
```

## 🧪 **اجرای تست‌ها**

### **1. اجرای تمام تست‌ها**
```bash
mvn test
```

### **2. اجرای تست‌های خاص**
```bash
# تست Person
mvn test -Dtest=PersonTest

# تست SimCard
mvn test -Dtest=SimCardTest

# تست DTO
mvn test -Dtest=PersonDTOTest
```

### **3. گزارش تست‌ها**
```bash
# تولید گزارش HTML
mvn test jacoco:report

# مشاهده گزارش
open target/site/jacoco/index.html
```

## 🚀 **استقرار در TomEE**

### **1. پیکربندی TomEE**
```bash
# کپی کردن فایل پیکربندی
cp tomee.xml $TOMEE_HOME/conf/

# بررسی فایل پیکربندی
cat $TOMEE_HOME/conf/tomee.xml
```

### **2. استقرار WAR**
```bash
# کپی کردن WAR file
cp target/mftplus-1.0-SNAPSHOT.war $TOMEE_HOME/webapps/

# بررسی استقرار
ls -la $TOMEE_HOME/webapps/
```

### **3. بررسی لاگ‌ها**
```bash
# بررسی لاگ‌های TomEE
tail -f $TOMEE_HOME/logs/catalina.out
```

## 🎯 **راه‌اندازی سیستم**

### **1. راه‌اندازی TomEE**
```bash
# Windows
$TOMEE_HOME/bin/catalina.bat run

# Linux/Mac
$TOMEE_HOME/bin/catalina.sh run
```

### **2. بررسی وضعیت**
```bash
# بررسی پورت 8080
netstat -an | grep 8080

# تست اتصال
curl http://localhost:8080/mftplus/
```

### **3. دسترسی به سیستم**
- **صفحه اصلی**: http://localhost:8080/mftplus/
- **مدیریت اشخاص**: http://localhost:8080/mftplus/person
- **مدیریت سیم‌کارت‌ها**: http://localhost:8080/mftplus/simcard
- **پنل ادمین**: http://localhost:8080/mftplus/admin

## ✅ **تست عملکرد**

### **1. تست صفحه اصلی**
```bash
curl -I http://localhost:8080/mftplus/
# باید HTTP 200 برگرداند
```

### **2. تست API اشخاص**
```bash
# دریافت لیست اشخاص
curl http://localhost:8080/mftplus/api/persons

# ایجاد شخص جدید
curl -X POST http://localhost:8080/mftplus/api/persons \
  -H "Content-Type: application/json" \
  -d '{"firstName":"احمد","lastName":"محمدی"}'
```

### **3. تست API سیم‌کارت‌ها**
```bash
# دریافت لیست سیم‌کارت‌ها
curl http://localhost:8080/mftplus/api/simcards

# ایجاد سیم‌کارت جدید
curl -X POST http://localhost:8080/mftplus/api/simcards \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber":"09123456789","iccid":"1234567890123456789"}'
```

### **4. تست WebSocket**
```bash
# اتصال به WebSocket
wscat -c ws://localhost:8080/mftplus/admin/websocket
```

## 🐛 **عیب‌یابی**

### **مشکلات رایج و راه‌حل‌ها**

#### **1. خطای CDI**
```
jakarta.enterprise.inject.UnsatisfiedResolutionException
```
**راه‌حل:**
- بررسی فایل `beans.xml`
- بررسی annotation های `@ApplicationScoped`
- بررسی فایل `microprofile-config.properties`

#### **2. خطای اتصال پایگاه داده**
```
java.sql.SQLException: Access denied
```
**راه‌حل:**
- بررسی رمز عبور MySQL
- بررسی تنظیمات `tomee.xml`
- بررسی دسترسی کاربر root

#### **3. خطای REST Client**
```
Api type [com.mftplus.simcard.client.PersonClient] is not found
```
**راه‌حل:**
- بررسی annotation `@RegisterRestClient`
- بررسی فایل `microprofile-config.properties`
- بررسی URL های REST Client

#### **4. خطای پورت**
```
Address already in use: bind
```
**راه‌حل:**
- بررسی فرآیندهای استفاده کننده از پورت 8080
- تغییر پورت در `tomee.xml`
- راه‌اندازی مجدد TomEE

### **لاگ‌های مفید**
```bash
# لاگ‌های TomEE
tail -f $TOMEE_HOME/logs/catalina.out

# لاگ‌های اپلیکیشن
tail -f logs/mftplus.log

# لاگ‌های MySQL
tail -f /var/log/mysql/error.log
```

### **دستورات عیب‌یابی**
```bash
# بررسی فرآیندهای Java
jps -l

# بررسی پورت‌ها
netstat -tulpn | grep 8080

# بررسی حافظه
free -h

# بررسی دیسک
df -h
```

## 📊 **مانیتورینگ**

### **1. مانیتورینگ سیستم**
```bash
# CPU و حافظه
top

# دیسک
iostat -x 1

# شبکه
netstat -i
```

### **2. مانیتورینگ اپلیکیشن**
```bash
# JVM Statistics
jstat -gc <pid> 1s

# Thread Dump
jstack <pid>

# Memory Dump
jmap -dump:format=b,file=heap.hprof <pid>
```

## 🔄 **به‌روزرسانی**

### **1. به‌روزرسانی کد**
```bash
git pull origin main
mvn clean package
cp target/mftplus-1.0-SNAPSHOT.war $TOMEE_HOME/webapps/
```

### **2. به‌روزرسانی پایگاه داده**
```bash
# اجرای migration scripts
mysql -u root -p < migration.sql
```

### **3. راه‌اندازی مجدد**
```bash
# توقف TomEE
$TOMEE_HOME/bin/catalina.sh stop

# راه‌اندازی مجدد
$TOMEE_HOME/bin/catalina.sh start
```

## 📚 **منابع بیشتر**

- **Jakarta EE 10 Documentation**: https://jakarta.ee/specifications/
- **TomEE Documentation**: https://tomee.apache.org/documentation/
- **MicroProfile Documentation**: https://microprofile.io/
- **MySQL Documentation**: https://dev.mysql.com/doc/

---

**🎉 موفق باشید! سیستم MFT Plus آماده استفاده است.**