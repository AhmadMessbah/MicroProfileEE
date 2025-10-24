-- Database Setup Script for MFT Plus Microservices
-- Run this script in MySQL to create the required databases and user

-- Create databases
CREATE DATABASE IF NOT EXISTS person_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS simcard_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user (if not exists)
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'root123';

-- Grant privileges
GRANT ALL PRIVILEGES ON person_db.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON simcard_db.* TO 'root'@'localhost';

-- Flush privileges
FLUSH PRIVILEGES;

-- Show created databases
SHOW DATABASES LIKE '%_db';

-- Show user privileges
SHOW GRANTS FOR 'root'@'localhost';
