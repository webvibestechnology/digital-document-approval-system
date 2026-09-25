-- Digital Document Approval System — Database Schema
-- Run this once in MySQL Workbench: Database → Run SQL Script
-- Change 'root' password in application.properties to match your MySQL setup

CREATE DATABASE IF NOT EXISTS digital_document_approval;
USE digital_document_approval;

-- Table 1: Roles
-- Stores the 3 types of users: ADMIN, USER, APPROVER
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Table 2: Departments
-- Departments group users together (e.g., HR, Finance, IT)
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- Table 3: Users
-- Every person who uses the system has a row here
-- role_id links to the roles table (what type of user)
-- department_id links to the departments table (which department)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT,
    department_id BIGINT,
    created_at DATETIME,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Table 4: Documents
-- Every uploaded document has a row here
-- uploaded_by links to the users table (who uploaded it)
-- status can be: DRAFT, PENDING, APPROVED, REJECTED
CREATE TABLE IF NOT EXISTS documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    file_path VARCHAR(500),
    file_type VARCHAR(50),
    status VARCHAR(50) DEFAULT 'PENDING',
    uploaded_by BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (uploaded_by) REFERENCES users(id)
);

-- Table 5: Approvals
-- Every time a document is reviewed, a row is added here
-- document_id: which document was reviewed
-- approver_id: which user reviewed it
-- status: APPROVED or REJECTED
CREATE TABLE IF NOT EXISTS approvals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document_id BIGINT,
    approver_id BIGINT,
    status VARCHAR(50),
    comments TEXT,
    action_date DATETIME,
    FOREIGN KEY (document_id) REFERENCES documents(id),
    FOREIGN KEY (approver_id) REFERENCES users(id)
);

-- Table 6: Notifications
-- Every notification sent to a user has a row here
-- is_read: has the user seen this notification?
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert the 3 default roles (INSERT IGNORE skips if rows already exist)
INSERT IGNORE INTO roles (name) VALUES ('ADMIN'), ('USER'), ('APPROVER');
