# 🗓️ Intern Task Plan — 30 Days
## Project: Digital Document Approval System
### Technologies: Spring Boot | Hibernate | Angular | MySQL

---

> ### 👋 Hey Intern! Read This First
>
> This project is a **web application** where:
> - **Regular users** can upload documents (PDF, Word files, etc.)
> - **Approvers** can review those documents and approve or reject them
> - **Admins** can manage users, departments, and see reports
> - **Everyone** gets notifications when something happens to their document
>
> ### ✅ Progress Checkpoint — What Is Already Done
>
> The following files are **already fully implemented** (verified by code review):
>
> **Backend (Spring Boot):**
> - All 7 Entity classes (`User`, `Role`, `Department`, `Document`, `Approval`, `Notification`, `DocumentStatus`)
> - All 6 Repository interfaces
> - All 9 DTO classes
> - All 7 Security files (`JwtService`, `JwtAuthenticationFilter`, `CustomUserDetailsService`, `SecurityConfig`)
> - All 3 Exception classes + `GlobalExceptionHandler`
> - Both utility classes (`FileUploadUtil`, `ValidationUtil`)
> - All 7 Service classes (`AuthService`, `UserService`, `DocumentService`, `ApprovalService`, `DepartmentService`, `NotificationService`, `ReportService`)
> - All 8 Controller classes (`AuthController`, `UserController`, `DocumentController`, `ApprovalController`, `DepartmentController`, `NotificationController`, `AdminController`, `ReportController`)
>
> **Frontend (Angular):**
> - All 4 TypeScript model interfaces
> - All 5 Angular services (`AuthService`, `DocumentService`, `ApprovalService`, `NotificationService`, `UserService`)
> - Both interceptors (`authInterceptor`, `errorInterceptor`)
> - Both guards (`authGuard`, `adminGuard`)
> - `app.routes.ts` — all routes defined
> - `app.config.ts` — interceptors and router registered
> - `app.ts` + `app.html` — layout with navbar, sidebar, router-outlet
> - Login component — `.ts` and `.html` ✅
> - Register component — `.ts` and `.html` ✅
> - Dashboard component — `.ts` and `.html` ✅
> - Document component — `.ts` and `.html` ✅
> - Navbar component — `.ts` and `.html` ✅
> - Sidebar component — `.ts` and `.html` ✅
> - Approval component — `.ts` and `.html` ✅
> - Notification component — `.ts` and `.html` ✅
>
> ### ⚠️ What Still Needs Work
>
> 1. **Backend compile errors** — Lombok is not generating getters/setters because `@Data` is on classes but Lombok annotation processing may not be configured in the IDE. All "cannot find symbol" errors (like `getEmail()`, `setTitle()`) are caused by this one issue.
> 2. **`application.properties`** — Not created yet. Backend cannot start without it.
> 3. **`Database/dbsql.sql`** — Only has `CREATE DATABASE` line. Tables not created yet.
> 4. **`profile.html`** — Only contains a placeholder line, needs to be properly built.
> 5. **CSS files** — All component CSS files are empty. App has no styling.
> 6. **`role-guard.ts`** — File exists but not implemented.
> 7. **Admin panel component** — Not yet created (no `components/admin/` folder exists).
>
> **Start from Day 1 of this plan and complete all tasks in order.**
>
> **Two parts to this project:**
> - 📁 **Backend** → `Backend/documentapproval/` (Java, Spring Boot)
> - 📁 **Frontend** → `Frontend/digital-document-approval-frontend/` (TypeScript, Angular)
> - 🗄️ **Database** → `Database/dbsql.sql` (MySQL)
>
> **How the two parts talk to each other:**
> Angular (frontend) sends HTTP requests → Spring Boot (backend) handles them → backend reads/writes to MySQL → backend sends response back to Angular

---

## 📁 Complete File Map — Know Where Everything Is

```
Backend/documentapproval/src/main/java/com/documentapproval/
│
├── entity/                      ← Java classes that map to database tables
│   ├── User.java                  (maps to 'users' table)
│   ├── Role.java                  (maps to 'roles' table)
│   ├── Department.java            (maps to 'departments' table)
│   ├── Document.java              (maps to 'documents' table)
│   ├── Approval.java              (maps to 'approvals' table)
│   ├── Notification.java          (maps to 'notifications' table)
│   └── DocumentStatus.java        (enum: DRAFT, PENDING, APPROVED, REJECTED)
│
├── repository/                  ← Interfaces for database operations
│   ├── UserRepository.java
│   ├── DocumentRepository.java
│   ├── ApprovalRepository.java
│   ├── DepartmentRepository.java
│   └── NotificationRepository.java
│
├── dto/                         ← Simple data classes (no logic)
│   ├── LoginRequest.java          (data received when user logs in)
│   ├── RegisterRequest.java       (data received when user registers)
│   ├── DocumentRequest.java       (data received when uploading document)
│   ├── DocumentResponse.java      (data sent back when returning document info)
│   ├── ApprovalRequest.java       (data received when approving/rejecting)
│   ├── DepartmentRequest.java     (data received when creating department)
│   ├── UserRequest.java           (data received when updating user)
│   ├── UserResponse.java          (data sent back when returning user info)
│   └── NotificationResponse.java  (data sent back for notifications)
│
├── services/                    ← Business logic (the "brain" of the app)
│   ├── AuthService.java           (handles register and login)
│   ├── UserService.java           (handles user operations)
│   ├── DocumentService.java       (handles document upload/fetch/delete)
│   ├── ApprovalService.java       (handles approve/reject logic)
│   ├── DepartmentService.java     (handles department CRUD)
│   ├── NotificationService.java   (handles creating and reading notifications)
│   └── ReportService.java         (handles statistics/reports)
│
├── controller/                  ← REST API endpoints (URLs the frontend calls)
│   ├── AuthController.java        (URLs: /api/auth/register, /api/auth/login)
│   ├── UserController.java        (URLs: /api/users/...)
│   ├── DocumentController.java    (URLs: /api/documents/...)
│   ├── ApprovalController.java    (URLs: /api/approvals/...)
│   ├── DepartmentController.java  (URLs: /api/departments/...)
│   ├── NotificationController.java(URLs: /api/notifications/...)
│   ├── AdminController.java       (URLs: /api/admin/...)
│   └── ReportController.java      (URLs: /api/reports/...)
│
├── security/                    ← JWT token and authentication code
│   ├── JwtService.java            (creates and reads JWT tokens)
│   ├── JwtAuthenticationFilter.java (checks token on every request)
│   └── CustomUserDetailsService.java (loads user from DB for Spring Security)
│
├── exception/                   ← Error handling
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   └── GlobalExceptionHandler.java
│
├── config/                      ← Spring Security configuration
│   └── SecurityConfig.java        (CREATE THIS FILE — it doesn't exist yet)
│
└── util/                        ← Helper utilities
    ├── FileUploadUtil.java        (saves files to disk)
    └── ValidationUtil.java        (validates email format, etc.)

Frontend/digital-document-approval-frontend/src/app/
│
├── models/                      ← TypeScript interfaces (data shapes)
│   ├── user.ts
│   ├── document.ts
│   ├── approval.ts
│   └── notification.ts
│
├── services/                    ← Angular services (HTTP calls to backend)
│   ├── auth.ts                    (login, register, token management)
│   ├── document.ts                (upload, get, delete documents)
│   ├── approval.ts                (submit, process approvals)
│   ├── notification.ts            (get, mark read)
│   └── user.ts                    (get users)
│
├── interceptors/                ← Auto-process every HTTP request/response
│   ├── auth-interceptor.ts        (adds token to every outgoing request)
│   └── error-interceptor.ts       (handles error responses globally)
│
├── guards/                      ← Protect routes from unauthorized access
│   ├── auth-guard.ts              (blocks pages if not logged in)
│   ├── admin-guard.ts             (blocks pages if not admin)
│   └── role-guard.ts              (blocks pages based on role)
│
└── components/                  ← UI pages and components
    ├── login/                     (login page)
    ├── register/                  (register page)
    ├── dashboard/                 (home page after login)
    ├── document/                  (document upload and list)
    ├── approval/                  (pending approvals for approvers)
    ├── notification/              (notification list)
    ├── profile/                   (user profile)
    ├── navbar/                    (top navigation bar)
    └── sidebar/                   (left side navigation links)
```

---

---

> ### 📌 How to Read This Plan
> - **✅ DONE** — This task is already complete. Read it to understand what was built.
> - **🔧 TODO** — This task needs to be done. Follow the instructions carefully.
> - **⚠️ FIX** — This task has code already written but it has errors that need to be fixed.

---

# 📅 WEEK 1 — Environment Setup, Database & Entities (Days 1–6)

---

## ⚠️ Day 1 — Set Up Your Development Environment & Create the Database

**Status: PARTIALLY DONE — `application.properties` missing, database tables not created**

The backend application file (`DocumentapprovalApplication.java`) and frontend packages are set up. However two critical things are still missing that will stop everything from working.

---

### 🔧 Task 1 — Create `application.properties` (MUST DO — backend won't start without this)

Create a new file at this exact path: `Backend/documentapproval/src/main/resources/application.properties`

Add the following settings inside it:
- **Database URL:** `spring.datasource.url=jdbc:mysql://localhost:3306/digital_document_approval`
- **Database username:** `spring.datasource.username=root` (replace with your MySQL username)
- **Database password:** `spring.datasource.password=YOUR_MYSQL_PASSWORD`
- **Auto-create tables:** `spring.jpa.hibernate.ddl-auto=update` — Hibernate will auto-create/update tables from your entity classes
- **Show SQL in console:** `spring.jpa.show-sql=true` — helpful for seeing what queries Hibernate runs
- **MySQL dialect:** `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect`
- **Server port:** `server.port=8080`
- **File upload folder:** `file.upload.dir=uploads/` — where uploaded documents will be saved
- **JWT secret:** `jwt.secret=mySecretKeyForJWT1234567890abcdefghijklmno` — must be at least 32 characters
- **JWT expiry:** `jwt.expiration=86400000` — 24 hours in milliseconds

After creating this file, click Run in IntelliJ. The backend should start on port 8080. Check the console — you should see `Tomcat started on port(s): 8080`.

---

### 🔧 Task 2 — Create the Database Tables (MUST DO — app has no tables yet)

Only the `CREATE DATABASE` line exists in `Database/dbsql.sql`. You need to add all 6 table definitions.

Open `Database/dbsql.sql` and write the SQL to create all tables, then run it in MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS digital_document_approval;
USE digital_document_approval;

-- Table 1: Roles
-- Stores the 3 user types: ADMIN, USER, APPROVER
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Table 2: Departments
-- Groups of users e.g. HR, Finance, IT
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- Table 3: Users
-- Every person who uses the system
-- role_id → which role (ADMIN/USER/APPROVER)
-- department_id → which department they belong to
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
-- Every uploaded document
-- status: DRAFT, PENDING, APPROVED, REJECTED
-- uploaded_by → who uploaded it (foreign key to users)
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
-- Each time a document is reviewed, a row is added here
-- document_id → which document was reviewed
-- approver_id → who reviewed it
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
-- Each notification sent to a user
-- is_read → has the user seen this?
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert the 3 default roles (run once after creating tables)
INSERT IGNORE INTO roles (name) VALUES ('ADMIN'), ('USER'), ('APPROVER');
```

After running this SQL, go to MySQL Workbench and verify:
- 6 tables exist in the `digital_document_approval` database
- The `roles` table has 3 rows (ADMIN, USER, APPROVER)

---

### 🔧 Task 3 — Fix the Lombok Compile Errors (CRITICAL — backend won't compile without this)

When you try to run the backend you will see many errors like:
- `cannot find symbol: method getEmail()`
- `cannot find symbol: method setTitle()`
- `variable userRepository not initialized in the default constructor`

**Root cause:** These methods are generated by Lombok (`@Data` annotation), but IntelliJ needs Lombok annotation processing enabled to use them.

**How to fix in IntelliJ IDEA:**
1. Go to **File → Settings** (or `Ctrl + Alt + S`)
2. Navigate to **Build, Execution, Deployment → Compiler → Annotation Processors**
3. Check the checkbox: **"Enable annotation processing"**
4. Click **OK**
5. Go to **Build → Rebuild Project**
6. The errors should disappear

**Also verify the Lombok plugin is installed:**
1. Go to **File → Settings → Plugins**
2. Search for "Lombok"
3. If not installed, install it and restart IntelliJ

**✅ Done when:** The project builds without errors and the backend starts on port 8080.

**📂 Files worked on today:**
- `Backend/.../src/main/resources/application.properties` ← CREATE NEW
- `Database/dbsql.sql` ← ADD TABLE SQL
- IntelliJ settings (enable annotation processing)

---

### 🔧 Task 1 — Set Up the Backend

1. Open IntelliJ IDEA → File → Open → select the folder `Backend/documentapproval/`
2. Wait for Maven to download all dependencies (check the progress bar at the bottom)
3. Create a new file at this path: `src/main/resources/application.properties`
   - This file is where you tell Spring Boot how to connect to MySQL and configure the app
   - Add the following settings:
     - **Database URL:** `spring.datasource.url=jdbc:mysql://localhost:3306/digital_document_approval`
     - **Database username:** `spring.datasource.username=root` (or your MySQL username)
     - **Database password:** `spring.datasource.password=YOUR_PASSWORD`
     - **Auto-create tables:** `spring.jpa.hibernate.ddl-auto=update` — this tells Hibernate to automatically create/update tables based on your entity classes
     - **Show SQL:** `spring.jpa.show-sql=true` — lets you see the SQL queries Hibernate runs in the console (helpful for debugging)
     - **MySQL dialect:** `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect`
     - **App port:** `server.port=8080`
4. Open `DocumentapprovalApplication.java` — it should have `@SpringBootApplication` annotation on the class and a standard `main()` method. If missing, add them.
5. Click the green Run button — check the console. You should see `Tomcat started on port(s): 8080`. If you see errors, check your MySQL password and database name.

---

### 🔧 Task 2 — Set Up the Frontend

1. Open a terminal (Command Prompt or VS Code terminal)
2. Navigate to: `Frontend/digital-document-approval-frontend/`
3. Run `npm install` — this downloads all Angular packages listed in `package.json` (takes 2–5 minutes first time)
4. After it finishes, run `ng serve`
5. Open browser and go to `http://localhost:4200` — you should see a blank/empty page. That's fine — we haven't built any pages yet.

---

### 🔧 Task 3 — Create the Database Schema

Open `Database/dbsql.sql` and write the SQL to create the database and all 6 tables. Then run it in MySQL Workbench (Database → Run SQL Script).

```sql
CREATE DATABASE digital_document_approval;
USE digital_document_approval;

-- Table 1: Roles
-- Stores the 3 types of users: ADMIN, USER, APPROVER
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Table 2: Departments
-- Departments group users together (e.g., HR, Finance, IT)
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- Table 3: Users
-- Every person who uses the system has a row here
-- role_id links to the roles table (what type of user)
-- department_id links to the departments table (which department)
CREATE TABLE users (
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
CREATE TABLE documents (
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
-- status: did they APPROVE or REJECT it?
CREATE TABLE approvals (
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
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert the 3 default roles
-- Do this AFTER creating the tables
INSERT INTO roles (name) VALUES ('ADMIN'), ('USER'), ('APPROVER');
```

**✅ Done when:** Open MySQL Workbench, expand your database — you should see 6 tables and the roles table should have 3 rows.

**📂 Files worked on today:**
- `Backend/.../DocumentapprovalApplication.java`
- `Backend/.../src/main/resources/application.properties` ← CREATED NEW
- `Database/dbsql.sql`

---

## ✅ Day 2 — Entity Classes: User, Role, Department — DONE

**Status: COMPLETE — All 3 entity files are fully implemented.**

Open and read these files to understand what was built. You do not need to write any code today for these files, but make sure you understand what each annotation does — you will be tested on this.

- `entity/Role.java` — Maps to the `roles` table. Has `id` and `name` fields. Uses `@Entity`, `@Table`, `@Data`, `@Id`, `@GeneratedValue`.
- `entity/Department.java` — Maps to the `departments` table. Has `id`, `name`, `description`.
- `entity/User.java` — Maps to the `users` table. Has `id`, `username`, `email`, `password`, relationships to `Role` and `Department` using `@ManyToOne` and `@JoinColumn`, and `createdAt`.

**What you should understand after reading these files:**
- Why `@ManyToOne` is used on the `role` field (many users → one role)
- What `@JoinColumn(name = "role_id")` does — it defines which column in the `users` table holds the foreign key
- Why `@Data` is needed — it generates all getters and setters automatically via Lombok
- Why `@GeneratedValue(strategy = GenerationType.IDENTITY)` is on the `id` field

**📂 Files to READ today (no changes needed):**
- `entity/Role.java`
- `entity/Department.java`
- `entity/User.java`

A JPA Entity is a regular Java class that Hibernate (the ORM library) automatically maps to a database table.

- `@Entity` tells Hibernate: "this class = a database table"
- `@Table(name = "users")` tells Hibernate which table name to use
- `@Id` marks the primary key field
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` makes the ID auto-increment (like MySQL's `AUTO_INCREMENT`)
- `@Column(name = "column_name")` maps a field to a specific column name
- `@ManyToOne` defines a relationship where many rows in this table link to one row in another table
- `@JoinColumn(name = "foreign_key_column")` specifies which column holds the foreign key

### 🧠 What is Lombok?

Lombok is a library that automatically generates boilerplate code for you at compile time:
- `@Data` generates: `getters`, `setters`, `equals()`, `hashCode()`, `toString()`
- `@NoArgsConstructor` generates: empty constructor `User() {}`
- `@AllArgsConstructor` generates: constructor with all fields `User(id, username, email, ...)`

**You must import `lombok.*` and `jakarta.persistence.*` in each entity file.**

---

### 📄 File 1 — Open `entity/Role.java`

This class maps to the `roles` table in the database.

**Add these annotations on the class:**
- `@Entity` — marks this as a JPA entity
- `@Table(name = "roles")` — maps to the `roles` table
- `@Data` — Lombok generates getters/setters
- `@NoArgsConstructor` — Lombok generates empty constructor
- `@AllArgsConstructor` — Lombok generates full constructor

**Add these fields inside the class:**
- `Long id` — the primary key. Annotate with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- `String name` — stores "ADMIN", "USER", or "APPROVER". No special annotations needed.

---

### 📄 File 2 — Open `entity/Department.java`

This class maps to the `departments` table.

**Add class annotations:** `@Entity`, `@Table(name = "departments")`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`

**Add these fields:**
- `Long id` — annotate with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- `String name` — department name like "HR" or "Finance"
- `String description` — optional description of the department

---

### 📄 File 3 — Open `entity/User.java`

This is the most complex entity because it has relationships to Role and Department.

**Add class annotations:** `@Entity`, `@Table(name = "users")`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`

**Add these fields:**
- `Long id` — annotate with `@Id` and `@GeneratedValue`
- `String username`
- `String email`
- `String password` — this will be stored as a hashed value (never plain text)
- `Role role` — this is a relationship field. Annotate with:
  - `@ManyToOne` — means "many users can have one role"
  - `@JoinColumn(name = "role_id")` — the foreign key column in the users table
- `Department department` — another relationship field. Annotate with:
  - `@ManyToOne` — many users can belong to one department
  - `@JoinColumn(name = "department_id")`
- `LocalDateTime createdAt` — use `@Column(name = "created_at")` to match the exact column name in the table

**Important imports needed:**
- `jakarta.persistence.*` for all `@Entity`, `@Table`, `@Id`, `@ManyToOne`, etc.
- `lombok.*` for `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`
- `java.time.LocalDateTime`

**📂 Files worked on today:**
- `entity/Role.java`
- `entity/Department.java`
- `entity/User.java`

---

## ✅ Day 3 — Entity Classes: DocumentStatus (Enum), Document, Approval, Notification

### 🧠 What is an Enum?

An enum (enumeration) is a special Java type for a fixed set of constants. Instead of storing random strings like "pending" or "PENDING" or "Pending" in the database, an enum forces the value to always be one of the defined options.

**When to use:** Whenever a field can only have a limited number of known values — like document status.

---

### 📄 First — Create NEW file `entity/DocumentStatus.java`

- Create a new Java file called `DocumentStatus` in the `entity` package
- Make it an `enum` (not a `class`)
- Add these 4 values: `DRAFT`, `PENDING`, `APPROVED`, `REJECTED`
- A document starts as `DRAFT` when saved without submitting, becomes `PENDING` when submitted for approval, then either `APPROVED` or `REJECTED` after the approver acts on it.

---

### 📄 File 1 — Open `entity/Document.java`

**Add class annotations:** `@Entity`, `@Table(name = "documents")`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`

**Add these fields:**
- `Long id` — annotate with `@Id` and `@GeneratedValue`
- `String title`
- `String description`
- `String filePath` — use `@Column(name = "file_path")` to match the column name. This stores the path on disk where the file is saved (e.g., `"uploads/report.pdf"`)
- `String fileType` — stores file extension like "pdf" or "docx". Use `@Column(name = "file_type")`
- `DocumentStatus status` — this uses the enum you just created. Annotate with `@Enumerated(EnumType.STRING)` so it saves as text like "PENDING" instead of a number
- `User uploadedBy` — relationship to User. Use `@ManyToOne` and `@JoinColumn(name = "uploaded_by")`
- `LocalDateTime createdAt` — use `@Column(name = "created_at")`
- `LocalDateTime updatedAt` — use `@Column(name = "updated_at")`

**Add these lifecycle methods:**
- A method annotated with `@PrePersist` — this runs automatically just before a new record is saved to the database. Inside it, set `createdAt = LocalDateTime.now()`
- A method annotated with `@PreUpdate` — this runs automatically just before an existing record is updated. Inside it, set `updatedAt = LocalDateTime.now()`
- Name the methods anything you like, e.g., `onCreate()` and `onUpdate()`

---

### 📄 File 2 — Open `entity/Approval.java`

**Add class annotations:** `@Entity`, `@Table(name = "approvals")`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`

**Add these fields:**
- `Long id` — `@Id` and `@GeneratedValue`
- `Document document` — `@ManyToOne` and `@JoinColumn(name = "document_id")`. This links the approval to the document being reviewed.
- `User approver` — `@ManyToOne` and `@JoinColumn(name = "approver_id")`. This links the approval to the person who reviewed it.
- `DocumentStatus status` — use `@Enumerated(EnumType.STRING)`. Will be APPROVED or REJECTED.
- `String comments` — the approver's written feedback
- `LocalDateTime actionDate` — when the approval/rejection happened. Use `@Column(name = "action_date")`

---

### 📄 File 3 — Open `entity/Notification.java`

**Add class annotations:** `@Entity`, `@Table(name = "notifications")`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`

**Add these fields:**
- `Long id` — `@Id` and `@GeneratedValue`
- `User user` — `@ManyToOne` and `@JoinColumn(name = "user_id")`. Who receives this notification.
- `String message` — the notification text, e.g., "Your document 'Q1 Report' was approved"
- `boolean isRead` — has the user seen this? Defaults to `false`. Use `@Column(name = "is_read")`
- `LocalDateTime createdAt` — use `@Column(name = "created_at")`

**📂 Files worked on today:**
- `entity/DocumentStatus.java` ← CREATED NEW
- `entity/Document.java`
- `entity/Approval.java`
- `entity/Notification.java`

---

## ✅ Day 4 — Repository Interfaces and DTOs

### 🧠 What is a Spring Data JPA Repository?

A repository is a Java `interface` (not a class) that extends `JpaRepository<Entity, IdType>`. Spring automatically creates the implementation for you — you never have to write SQL manually.

**Built-in methods you get for free:** `findAll()`, `findById(id)`, `save(entity)`, `delete(entity)`, `count()`

**Custom methods using method naming:** Spring reads your method name and generates SQL automatically:
- `findByEmail(String email)` → Spring generates: `SELECT * FROM users WHERE email = ?`
- `findByUploadedBy(User user)` → Spring generates: `SELECT * FROM documents WHERE uploaded_by = ?`
- `existsByEmail(String email)` → Spring generates: `SELECT COUNT(*) FROM users WHERE email = ?` and returns boolean

### 🧠 What is a DTO?

DTO = Data Transfer Object. It is a plain Java class with only fields, and uses Lombok `@Data` for getters/setters. No business logic.

**Why use DTOs instead of entities directly?**
- You don't want to expose all entity fields to the outside world (e.g., never send the `password` field in responses)
- Request DTOs receive incoming data from the frontend
- Response DTOs control exactly what data is sent back

---

### 📄 Repository Files — Open each and write:

**`repository/UserRepository.java`**
- Interface that extends `JpaRepository<User, Long>`
- Add method: `Optional<User> findByEmail(String email)` — used to find a user when they log in
- Add method: `boolean existsByEmail(String email)` — used during registration to check if email is already taken
- Spring generates the SQL automatically from the method names

**`repository/DocumentRepository.java`**
- Extends `JpaRepository<Document, Long>`
- Add method: `List<Document> findByUploadedBy(User user)` — get all documents by a specific user
- Add method: `List<Document> findByStatus(DocumentStatus status)` — filter documents by status

**`repository/ApprovalRepository.java`**
- Extends `JpaRepository<Approval, Long>`
- Add method: `List<Approval> findByApprover(User approver)` — get all approvals by a specific approver
- Add method: `List<Approval> findByDocument(Document document)` — get all approvals for a document
- Add method: `Optional<Approval> findByDocumentAndApprover(Document doc, User approver)` — find one specific approval record

**`repository/DepartmentRepository.java`**
- Extends `JpaRepository<Department, Long>`
- No extra methods needed — the default `findAll()`, `save()`, `findById()`, `deleteById()` are enough

**`repository/NotificationRepository.java`**
- Extends `JpaRepository<Notification, Long>`
- Add method: `List<Notification> findByUser(User user)` — all notifications for a user
- Add method: `List<Notification> findByUserAndIsReadFalse(User user)` — only the UNREAD ones. Note: `IsReadFalse` in the method name → Spring generates `WHERE is_read = false`

---

### 📄 DTO Files — Open each and add fields with `@Data` annotation:

**`dto/LoginRequest.java`** — data received when user submits login form
- `String email`
- `String password`

**`dto/RegisterRequest.java`** — data received when user submits registration form
- `String username`
- `String email`
- `String password`
- `String roleName` — the role they want (e.g. "USER")
- `Long departmentId` — which department they belong to

**`dto/DocumentRequest.java`** — data received when uploading a document (the text fields; the actual file comes separately as `MultipartFile`)
- `String title`
- `String description`

**`dto/DocumentResponse.java`** — data SENT BACK when frontend asks for document info
- `Long id`
- `String title`
- `String description`
- `String status` — e.g. "PENDING"
- `String uploadedByUsername` — just the username, not the whole User object
- `String filePath`
- `String createdAt`

**`dto/ApprovalRequest.java`** — data received when approver submits their decision
- `Long documentId` — which document they are reviewing
- `String status` — "APPROVED" or "REJECTED"
- `String comments` — their feedback

**`dto/UserRequest.java`** — data received when updating a user
- `String username`
- `String email`
- `Long roleId`
- `Long departmentId`

**`dto/UserResponse.java`** — data SENT BACK when frontend asks for user info
- `Long id`
- `String username`
- `String email`
- `String role` — just the role name, not the whole Role object
- `String department` — just the department name

**`dto/NotificationResponse.java`** — data SENT BACK for notifications
- `Long id`
- `String message`
- `boolean isRead`
- `String createdAt`

**`dto/DepartmentRequest.java`** — data received when creating/updating a department
- `String name`
- `String description`

**📂 Files worked on today:**
- All 5 files in `repository/`
- All 9 files in `dto/`

---

## ✅ Day 5 — JWT Security Setup

### 🧠 How JWT Authentication Works (Step by Step)

1. User sends their email and password to `POST /api/auth/login`
2. Backend verifies the credentials against the database
3. Backend creates a JWT token — a string like `eyJhbGciOiJIUzI1NiJ9...` — and sends it back
4. The frontend stores this token in `localStorage`
5. For every future API request, the frontend automatically adds the header: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...`
6. The backend reads this header, validates the token, and knows who is making the request
7. No need to log in again for each request — the token acts as a pass

### 🧠 The Three Security Files and What They Do

- **`JwtService`** — Creates tokens and reads information from tokens
- **`CustomUserDetailsService`** — When Spring Security needs to know who a user is (by email), it calls this class to load the user from the database
- **`JwtAuthenticationFilter`** — Runs on EVERY incoming request before the controller. Reads the token from the header, validates it, and tells Spring Security "this request is from user X"

---

### 🔧 First — Add Dependencies to `pom.xml`

Open `pom.xml` and add these dependencies inside the `<dependencies>` section. These are the JWT and Spring Security libraries:

- `spring-boot-starter-security` (from `org.springframework.boot`) — adds Spring Security to the project
- `jjwt-api` version `0.11.5` (from `io.jsonwebtoken`) — the JWT API
- `jjwt-impl` version `0.11.5` (from `io.jsonwebtoken`) — JWT implementation, use `<scope>runtime</scope>`
- `jjwt-jackson` version `0.11.5` (from `io.jsonwebtoken`) — JWT JSON support, use `<scope>runtime</scope>`

After adding, click the Maven refresh button in IntelliJ to download the new libraries.

### 🔧 Then — Add to `application.properties`

Add two new properties:
- `jwt.secret` — a long random string (at least 32 characters) used to sign tokens. Example: `mySecretKeyForJWT1234567890abcdef`
- `jwt.expiration` — token expiry in milliseconds. Set to `86400000` (that's 24 hours)

---

### 📄 File 1 — Open `security/JwtService.java`

- Annotate the class with `@Service` so Spring manages it as a bean
- Read `jwt.secret` from properties using `@Value("${jwt.secret}")`
- Read `jwt.expiration` from properties using `@Value("${jwt.expiration}")`

**Write method `generateToken(UserDetails userDetails)`:**
- This method creates and returns a JWT token string
- Use `Jwts.builder()` from the jjwt library to build the token
- Set the subject to the user's username (their email)
- Set the issued-at date to `new Date()`
- Set the expiration to `new Date(System.currentTimeMillis() + expiration)`
- Sign with `SignatureAlgorithm.HS256` using your secret key
- Call `.compact()` at the end to get the final token string

**Write method `extractUsername(String token)`:**
- This reads the email from inside a token
- Use `Jwts.parserBuilder()` to parse the token
- Set the signing key and call `.parseClaimsJws(token).getBody().getSubject()`
- Return the subject (which is the email)

**Write method `isTokenValid(String token, UserDetails userDetails)`:**
- Extract the username from the token
- Check: is the extracted username equal to `userDetails.getUsername()`?
- Check: is the token NOT expired? (compare expiration date to current date)
- Return `true` only if both checks pass

**Helper: `getSigningKey()`** — converts your `jwt.secret` string into a cryptographic `Key` object using `Keys.hmacShaKeyFor(secret.getBytes())`

---

### 📄 File 2 — Open `security/CustomUserDetailsService.java`

- Make the class `implement UserDetailsService` (from `org.springframework.security.core.userdetails`)
- Annotate with `@Service`
- Inject `UserRepository` using Lombok `@RequiredArgsConstructor` or `@Autowired`

**Override method `loadUserByUsername(String email)`:**
- This method is called by Spring Security when it needs to load a user
- The parameter is named `username` but in our system it's actually the email
- Use `userRepository.findByEmail(email)` to find the user
- If not found, throw `UsernameNotFoundException` with a helpful message
- If found, use `org.springframework.security.core.userdetails.User.builder()` to build and return a `UserDetails` object:
  - `.username(user.getEmail())`
  - `.password(user.getPassword())` — already hashed
  - `.roles(user.getRole().getName())` — e.g., "ADMIN"
  - `.build()`

---

### 📄 File 3 — Open `security/JwtAuthenticationFilter.java`

- Make the class `extend OncePerRequestFilter` (Spring ensures this runs exactly once per request)
- Annotate with `@Component` so Spring detects it automatically
- Inject `JwtService` and `CustomUserDetailsService`

**Override method `doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)`:**

Follow these steps inside the method:
1. Read the `"Authorization"` header from the request: `request.getHeader("Authorization")`
2. Check if the header is null OR doesn't start with `"Bearer "` — if so, just call `filterChain.doFilter(request, response)` and return (skip this filter for this request)
3. Extract the token: take the header string and remove the first 7 characters (`"Bearer "`) using `.substring(7)`
4. Use `jwtService.extractUsername(token)` to get the email from the token
5. Check that the email is not null AND that `SecurityContextHolder.getContext().getAuthentication()` is null (meaning we haven't authenticated this request yet)
6. If both checks pass, load the user: `userDetailsService.loadUserByUsername(email)`
7. If `jwtService.isTokenValid(token, userDetails)` returns `true`:
   - Create a `UsernamePasswordAuthenticationToken` with userDetails and their authorities
   - Set it in the SecurityContext: `SecurityContextHolder.getContext().setAuthentication(authToken)`
8. At the very end (always), call `filterChain.doFilter(request, response)` to continue the request chain

**📂 Files worked on today:**
- `pom.xml` (added 4 dependencies)
- `application.properties` (added jwt.secret and jwt.expiration)
- `security/JwtService.java`
- `security/CustomUserDetailsService.java`
- `security/JwtAuthenticationFilter.java`

---

## ✅ Day 6 — Security Configuration & Exception Handling

### 🧠 What is SecurityConfig?

`SecurityConfig` is the central configuration class that tells Spring Security:
- Which URLs are public (no login needed) vs protected (login required)
- What authentication mechanism to use (JWT tokens in our case)
- How to encode passwords (BCrypt hashing)
- What CORS policy to apply (which origins can call our API)

The `config/` folder is currently empty — you need to create `SecurityConfig.java` inside it.

---

### 📄 File 1 — Create NEW `config/SecurityConfig.java`

**Annotations on the class:**
- `@Configuration` — tells Spring this is a configuration class
- `@EnableWebSecurity` — enables Spring Security
- `@EnableMethodSecurity` — allows using `@PreAuthorize` on individual methods later
- `@RequiredArgsConstructor` (Lombok) — auto-injects final fields

**Fields to inject (declare as `private final`):**
- `JwtAuthenticationFilter jwtAuthFilter`
- `CustomUserDetailsService userDetailsService`

**Write a `SecurityFilterChain` bean method** (annotated with `@Bean`):
- Disable CSRF: `.csrf(csrf -> csrf.disable())` — REST APIs don't need CSRF protection because they use tokens instead of cookies
- Enable CORS: `.cors(cors -> cors.configurationSource(corsConfigurationSource()))` — refer to the CORS method below
- Set session to stateless: `.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))` — we don't use server sessions, we use JWT tokens
- Configure URL permissions:
  - Permit all requests to `/api/auth/**` without authentication — this allows anyone to call register and login
  - Require authentication for all other requests
- Add your JWT filter BEFORE Spring's default `UsernamePasswordAuthenticationFilter`: use `.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`
- Return `http.build()`

**Write a `BCryptPasswordEncoder` bean method:**
- Annotate with `@Bean`
- Return `new BCryptPasswordEncoder()`
- BCrypt is a strong password hashing algorithm. We never store plain passwords.

**Write an `AuthenticationManager` bean method:**
- Annotate with `@Bean`
- Accept `AuthenticationConfiguration config` as parameter
- Return `config.getAuthenticationManager()`

**Write a CORS configuration source method:**
- Create a `CorsConfiguration` object
- Set allowed origins: `List.of("http://localhost:4200")` — only allow the Angular app to call this API
- Set allowed methods: `List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")`
- Set allowed headers: `List.of("*")` — allow all headers
- Set `allowCredentials(true)`
- Register this config for all paths `"/**"` using `UrlBasedCorsConfigurationSource`
- Return the source

---

### 📄 File 2 — Open `exception/ResourceNotFoundException.java`

- Make the class `extend RuntimeException`
- Add a constructor that takes a `String message` parameter
- Inside the constructor, call `super(message)` to pass the message to the parent class
- Use this exception whenever something is NOT FOUND in the database (e.g., user not found, document not found)
- Example: `throw new ResourceNotFoundException("Document not found with id: " + id)`

---

### 📄 File 3 — Open `exception/BadRequestException.java`

- Same pattern as above — extends `RuntimeException`
- Constructor takes `String message`, calls `super(message)`
- Use this when the user sends INVALID DATA (e.g., email already registered, password too short)
- Example: `throw new BadRequestException("Email is already registered!")`

---

### 📄 File 4 — Open `exception/GlobalExceptionHandler.java`

- Annotate the class with `@RestControllerAdvice` — this makes it handle exceptions from ALL controllers
- Think of it as a central "catch block" for the whole application

**Write handler method for `ResourceNotFoundException`:**
- Annotate with `@ExceptionHandler(ResourceNotFoundException.class)`
- Return type: `ResponseEntity<Map<String, String>>`
- Inside: return `ResponseEntity.status(404).body(Map.of("error", ex.getMessage()))`

**Write handler method for `BadRequestException`:**
- Annotate with `@ExceptionHandler(BadRequestException.class)`
- Return HTTP 400: `ResponseEntity.status(400).body(Map.of("error", ex.getMessage()))`

**Write handler for `MethodArgumentNotValidException`** (for field validation errors):
- Annotate with `@ExceptionHandler(MethodArgumentNotValidException.class)`
- Loop through `ex.getBindingResult().getFieldErrors()` to collect all field error messages
- Return HTTP 400 with a map of field names to error messages

**Write a generic handler for `Exception`:**
- Annotate with `@ExceptionHandler(Exception.class)`
- Return HTTP 500: `ResponseEntity.status(500).body(Map.of("error", "Something went wrong: " + ex.getMessage()))`

---

### 📄 File 5 — Open `util/ValidationUtil.java`

- Create a `public static boolean isValidEmail(String email)` method
- Use a regex pattern to validate email format
- The pattern should check for: local part + `@` + domain + `.` + TLD
- Use `Pattern.matches(regex, email)` to test it
- Return `true` if valid, `false` if not

---

### 📄 File 6 — Open `util/FileUploadUtil.java`

- Create a `public static String saveFile(String uploadDir, String originalFilename, MultipartFile file)` method
- Inside:
  - Create a `Path` object pointing to the upload directory using `Paths.get(uploadDir)`
  - Create the directory if it doesn't exist: `Files.createDirectories(uploadPath)`
  - Build the destination path: `uploadPath.resolve(originalFilename)`
  - Copy the file bytes: `Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING)`
  - Return the file path as a string: `destPath.toString()`
- This method will be called from `DocumentService` when a user uploads a file

**📂 Files worked on today:**
- `config/SecurityConfig.java` ← CREATED NEW
- `exception/ResourceNotFoundException.java`
- `exception/BadRequestException.java`
- `exception/GlobalExceptionHandler.java`
- `util/ValidationUtil.java`
- `util/FileUploadUtil.java`

---

# 📅 WEEK 2 — Backend Services & REST APIs (Days 7–13)

---

## ✅ Day 7 — Auth Service & Auth Controller (Register & Login)

### 🧠 How the Service + Controller Pattern Works

- **Controller** — receives the HTTP request, extracts data, calls the service
- **Service** — contains the actual business logic (the rules and decisions)
- **Controller never talks to the database directly** — it always goes through the service
- **Service never knows about HTTP** — it just works with Java objects

This separation makes the code organized and easy to test.

---

### 📄 First — Create NEW `repository/RoleRepository.java`

This repository is needed by AuthService to find roles by name during registration.

- Create an interface that extends `JpaRepository<Role, Long>`
- Add one custom method: `Optional<Role> findByName(String name)` — used to find the role by name e.g. `findByName("USER")`

---

### 📄 Open `services/AuthService.java`

- Annotate with `@Service`
- Add `@RequiredArgsConstructor` (Lombok) to inject dependencies
- Declare these as `private final` fields (Lombok auto-injects them):
  - `UserRepository userRepository`
  - `RoleRepository roleRepository`
  - `BCryptPasswordEncoder passwordEncoder`
  - `JwtService jwtService`
  - `AuthenticationManager authManager`
  - `CustomUserDetailsService userDetailsService`

**Write method `register(RegisterRequest request)` — returns `Map<String, String>`:**

Step 1 — Check if email is already taken:
- Call `userRepository.existsByEmail(request.getEmail())`
- If it returns `true`, throw `new BadRequestException("Email is already registered!")`

Step 2 — Find the Role:
- Call `roleRepository.findByName(request.getRoleName())`
- If not found, throw `new BadRequestException("Role not found: " + request.getRoleName())`

Step 3 — Create and save the User:
- Create `new User()`
- Set `username`, `email`, `role`, `createdAt`
- For password: `user.setPassword(passwordEncoder.encode(request.getPassword()))` — NEVER save plain text passwords
- Call `userRepository.save(user)`

Step 4 — Return success:
- Return `Map.of("message", "Registration successful!")`

---

**Write method `login(LoginRequest request)` — returns `Map<String, String>`:**

Step 1 — Authenticate credentials:
- Call `authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()))`
- If the credentials are wrong, Spring automatically throws `BadCredentialsException` — you don't need to handle this manually

Step 2 — Load user details and generate token:
- Call `userDetailsService.loadUserByUsername(request.getEmail())`
- Call `jwtService.generateToken(userDetails)` to get the token string

Step 3 — Get the user entity for role/username:
- Call `userRepository.findByEmail(request.getEmail()).get()` to get the `User` entity

Step 4 — Return token and user info:
- Return `Map.of("token", token, "role", user.getRole().getName(), "username", user.getUsername())`

---

### 📄 Open `controller/AuthController.java`

- Annotate with `@RestController` — combines `@Controller` + `@ResponseBody` (auto-converts return values to JSON)
- Annotate with `@RequestMapping("/api/auth")` — all endpoints in this controller start with `/api/auth`
- Inject `AuthService` using `@RequiredArgsConstructor`

**Write endpoint `POST /api/auth/register`:**
- Method annotation: `@PostMapping("/register")`
- Parameter: `@RequestBody RegisterRequest request` — Spring reads the JSON body and converts it to the object
- Call `authService.register(request)`
- Return `ResponseEntity.ok(result)` — this sends HTTP 200 with the result as JSON

**Write endpoint `POST /api/auth/login`:**
- Method annotation: `@PostMapping("/login")`
- Parameter: `@RequestBody LoginRequest request`
- Call `authService.login(request)`
- Return `ResponseEntity.ok(result)`

**🧪 Test these endpoints in Postman:**
1. POST `http://localhost:8080/api/auth/register`
   - Body (JSON): `{ "username": "john", "email": "john@test.com", "password": "password123", "roleName": "USER" }`
   - Expected: `{ "message": "Registration successful!" }`
2. POST `http://localhost:8080/api/auth/login`
   - Body (JSON): `{ "email": "john@test.com", "password": "password123" }`
   - Expected: `{ "token": "eyJ...", "role": "USER", "username": "john" }`

**📂 Files worked on today:**
- `repository/RoleRepository.java` ← CREATED NEW
- `services/AuthService.java`
- `controller/AuthController.java`

---

## ✅ Day 8 — User Service & Controller

**Goal:** Build APIs to get, update, and delete users. Admins use these.

---

### 📄 Open `services/UserService.java`

- Annotate with `@Service` and add `@RequiredArgsConstructor`
- Inject `UserRepository`

**Write a private helper method `toUserResponse(User user)` — returns `UserResponse`:**
- This converts a `User` entity (database object) into a `UserResponse` DTO (API response object)
- Create a new `UserResponse` object
- Set each field manually: `response.setId(user.getId())`, `response.setUsername(user.getUsername())`, etc.
- For role: `user.getRole() != null ? user.getRole().getName() : ""` (null check in case role is not set)
- For department: same null check pattern
- Return the response object
- **Why this method?** You'll call it in multiple places, so putting it in one place avoids code duplication

**Write method `getAllUsers()` — returns `List<UserResponse>`:**
- Call `userRepository.findAll()` — gets all users as a list
- Use Java streams to convert each `User` to `UserResponse`:
  - `.stream()` — converts list to a stream
  - `.map(this::toUserResponse)` — applies your helper method to each user
  - `.collect(Collectors.toList())` — collects results back into a list
- Return the result

**Write method `getUserById(Long id)` — returns `UserResponse`:**
- Call `userRepository.findById(id)` — returns `Optional<User>`
- If empty, throw `new ResourceNotFoundException("User not found with id: " + id)`
- If found, call `toUserResponse(user)` and return it

**Write method `updateUser(Long id, UserRequest request)` — returns `UserResponse`:**
- Find user by id (throw exception if not found)
- Update fields: `user.setUsername(request.getUsername())`, `user.setEmail(request.getEmail())`
- Call `userRepository.save(user)` to persist changes
- Return `toUserResponse(updatedUser)`

**Write method `deleteUser(Long id)` — returns `void`:**
- Find user by id (throw exception if not found)
- Call `userRepository.deleteById(id)`

---

### 📄 Open `controller/UserController.java`

- Annotate with `@RestController` and `@RequestMapping("/api/users")`
- Inject `UserService`

**Add these endpoints:**
- `GET /api/users` — calls `userService.getAllUsers()`, returns `ResponseEntity<List<UserResponse>>`
- `GET /api/users/{id}` — use `@PathVariable Long id`, calls `userService.getUserById(id)`
- `PUT /api/users/{id}` — use `@PathVariable Long id` and `@RequestBody UserRequest request`, calls `userService.updateUser(id, request)`
- `DELETE /api/users/{id}` — calls `userService.deleteUser(id)`, returns `ResponseEntity.noContent().build()` (HTTP 204)

**📂 Files worked on today:**
- `services/UserService.java`
- `controller/UserController.java`

---

## ✅ Day 9 — Department Service & Controller

**Goal:** Build CRUD (Create, Read, Update, Delete) for departments. The pattern here is the same as UserService — follow it closely.

---

### 📄 Open `services/DepartmentService.java`

- Annotate with `@Service` and `@RequiredArgsConstructor`
- Inject `DepartmentRepository`

**Write these 5 methods:**

`getAllDepartments()` — returns `List<Department>`
- Call `departmentRepository.findAll()` and return the result

`getDepartmentById(Long id)` — returns `Department`
- Use `departmentRepository.findById(id)`, throw `ResourceNotFoundException` if empty

`createDepartment(DepartmentRequest request)` — returns `Department`
- Create `new Department()`
- Set `name` and `description` from the request
- Call `departmentRepository.save(department)` and return saved result

`updateDepartment(Long id, DepartmentRequest request)` — returns `Department`
- Find existing department by id (throw exception if not found)
- Update its `name` and `description`
- Save and return

`deleteDepartment(Long id)` — returns `void`
- Confirm the department exists (find by id first)
- Call `departmentRepository.deleteById(id)`

---

### 📄 Open `controller/DepartmentController.java`

- Annotate with `@RestController` and `@RequestMapping("/api/departments")`
- Inject `DepartmentService`

**Add these 5 endpoints:**
- `GET /api/departments` — returns all departments
- `GET /api/departments/{id}` — returns one department by id
- `POST /api/departments` — creates a new department, use `@RequestBody DepartmentRequest`
- `PUT /api/departments/{id}` — updates an existing department
- `DELETE /api/departments/{id}` — deletes a department, returns HTTP 204

**📂 Files worked on today:**
- `services/DepartmentService.java`
- `controller/DepartmentController.java`

---

## ✅ Day 10 — Document Service & Controller (Upload & Fetch)

**Goal:** Implement document uploading and retrieval — the core feature of the whole system.

---

**Add to `application.properties`:**
- `file.upload.dir=uploads/` — this is where uploaded files will be stored on the server

---

### 📄 Open `services/DocumentService.java`

- Annotate with `@Service` and `@RequiredArgsConstructor`
- Inject: `DocumentRepository`, `UserRepository`
- Read `file.upload.dir` property using `@Value("${file.upload.dir}")`

**Write private helper method `toDocumentResponse(Document doc)` — returns `DocumentResponse`:**
- Similar to the User helper — manually map each field from entity to DTO
- For `uploadedByUsername`: `doc.getUploadedBy().getUsername()`
- For `createdAt`: convert `LocalDateTime` to a readable string using `.toString()`

**Write method `uploadDocument(DocumentRequest request, MultipartFile file, String uploaderEmail)` — returns `DocumentResponse`:**

Step 1 — Find who is uploading:
- `userRepository.findByEmail(uploaderEmail)` — throw `ResourceNotFoundException` if not found

Step 2 — Save the file to disk:
- Call `FileUploadUtil.saveFile(uploadDir, file.getOriginalFilename(), file)` — this returns the saved file path
- Wrap in try-catch for `IOException`

Step 3 — Create and save the Document entity:
- Create `new Document()`, set all fields including `uploadedBy`, `status = DocumentStatus.PENDING`
- Call `documentRepository.save(document)`

Step 4 — Return as DTO:
- Call and return `toDocumentResponse(savedDocument)`

**Write method `getAllDocuments()` — returns `List<DocumentResponse>`:**
- `documentRepository.findAll()` → stream → map to response → collect

**Write method `getDocumentsByUser(String email)` — returns `List<DocumentResponse>`:**
- Find user by email
- `documentRepository.findByUploadedBy(user)` → stream → map → collect

**Write method `getDocumentById(Long id)` — returns `DocumentResponse`:**
- Find by id, throw exception if not found, return as response DTO

**Write method `getDocumentsByStatus(String status)` — returns `List<DocumentResponse>`:**
- Convert the status string to enum: `DocumentStatus.valueOf(status.toUpperCase())`
- `documentRepository.findByStatus(documentStatus)` → map → return

**Write method `deleteDocument(Long id)` — returns `void`:**
- Find by id (throw exception if not found), then delete

---

### 📄 Open `controller/DocumentController.java`

- Annotate with `@RestController`, `@RequestMapping("/api/documents")`
- Inject `DocumentService`

**Add these endpoints:**

`POST /api/documents/upload` — file upload endpoint:
- Use `@PostMapping("/upload")`
- Parameters: `@RequestPart("data") DocumentRequest request` and `@RequestPart("file") MultipartFile file` and `@AuthenticationPrincipal UserDetails userDetails`
- `@AuthenticationPrincipal` automatically injects the currently logged-in user's details (extracted from the JWT token by the filter)
- Pass `userDetails.getUsername()` as the uploader email to the service

`GET /api/documents` — all documents (admin):
- Returns `List<DocumentResponse>`

`GET /api/documents/my` — current user's documents:
- Use `@AuthenticationPrincipal UserDetails userDetails`
- Pass email to `documentService.getDocumentsByUser()`

`GET /api/documents/{id}` — single document:
- Use `@PathVariable Long id`

`GET /api/documents/status/{status}` — filter by status:
- Use `@PathVariable String status`

`DELETE /api/documents/{id}` — delete a document:
- Returns `ResponseEntity.noContent().build()` on success

**📂 Files worked on today:**
- `services/DocumentService.java`
- `controller/DocumentController.java`

---

## ✅ Day 11 — Approval Service & Controller

**Goal:** Implement the approve/reject workflow — the most business-critical logic in the system.

---

### 📄 Open `services/ApprovalService.java`

- Annotate with `@Service` and `@RequiredArgsConstructor`
- Inject: `ApprovalRepository`, `DocumentRepository`, `UserRepository`, `NotificationService`

**Write method `submitForApproval(Long documentId, String approverEmail)` — returns `Approval`:**

Step 1 — Find the document:
- Use `documentRepository.findById(documentId)`, throw exception if not found

Step 2 — Find the approver:
- Use `userRepository.findByEmail(approverEmail)`, throw exception if not found

Step 3 — Update document status:
- Set `document.setStatus(DocumentStatus.PENDING)`
- Save the document

Step 4 — Create an Approval record:
- Create `new Approval()`
- Set document, approver, status = `DocumentStatus.PENDING`, actionDate = now

Step 5 — Notify the approver:
- Call `notificationService.createNotification(approver, "You have a new document to review: " + document.getTitle())`

Step 6 — Save and return the approval

---

**Write method `processApproval(ApprovalRequest request, String approverEmail)` — returns `Approval`:**

Step 1 — Find the approver and document (throw exceptions if not found)

Step 2 — Find the existing Approval record:
- Use `approvalRepository.findByDocumentAndApprover(document, approver)`
- If not found, throw `ResourceNotFoundException`

Step 3 — Update the Approval:
- Set status to the requested value: `DocumentStatus.valueOf(request.getStatus())`
- Set comments: `approval.setComments(request.getComments())`
- Set actionDate: `LocalDateTime.now()`

Step 4 — Update the Document status to match:
- `document.setStatus(DocumentStatus.valueOf(request.getStatus()))`
- Save the document

Step 5 — Notify the document uploader:
- `notificationService.createNotification(document.getUploadedBy(), "Your document '" + document.getTitle() + "' was " + request.getStatus())`

Step 6 — Save the approval and return it

---

**Write method `getPendingApprovalsByApprover(String approverEmail)` — returns `List<Approval>`:**
- Find approver by email
- Return `approvalRepository.findByApprover(approver)`
- Filter the result to only return approvals where `status == DocumentStatus.PENDING`

**Write method `getApprovalHistoryForDocument(Long documentId)` — returns `List<Approval>`:**
- Find document by id
- Return `approvalRepository.findByDocument(document)`

---

### 📄 Open `controller/ApprovalController.java`

- `@RestController`, `@RequestMapping("/api/approvals")`
- Inject `ApprovalService`

**Add these endpoints:**
- `POST /api/approvals/submit` — accepts `documentId` and `approverEmail` as request params or body, calls `submitForApproval`
- `POST /api/approvals/process` — accepts `@RequestBody ApprovalRequest`, uses `@AuthenticationPrincipal` for approver email
- `GET /api/approvals/pending` — uses `@AuthenticationPrincipal` to get current approver's email
- `GET /api/approvals/document/{documentId}` — returns approval history

**📂 Files worked on today:**
- `services/ApprovalService.java`
- `controller/ApprovalController.java`

---

## ✅ Day 12 — Notification Service, Report Service & Admin Controller

### 📄 Open `services/NotificationService.java`

- `@Service`, `@RequiredArgsConstructor`
- Inject: `NotificationRepository`, `UserRepository`

**Write method `createNotification(User user, String message)` — called internally, no return needed:**
- Create `new Notification()`
- Set user, message, `isRead = false`, `createdAt = LocalDateTime.now()`
- Save using `notificationRepository.save(notification)`

**Write method `getUserNotifications(String email)` — returns `List<NotificationResponse>`:**
- Find user by email
- `notificationRepository.findByUser(user)` → stream → convert each to `NotificationResponse` DTO → collect

**Write method `getUnreadNotifications(String email)` — returns `List<NotificationResponse>`:**
- Find user by email
- Use `notificationRepository.findByUserAndIsReadFalse(user)` → convert → return

**Write method `markAsRead(Long notificationId)` — returns `void`:**
- Find notification by id (throw exception if not found)
- `notification.setRead(true)`
- Save the notification

**Write method `markAllAsRead(String email)` — returns `void`:**
- Find user by email
- Get all unread: `notificationRepository.findByUserAndIsReadFalse(user)`
- Loop through each and set `isRead = true`
- Save all using `notificationRepository.saveAll(notifications)`

---

### 📄 Open `controller/NotificationController.java`

- `@RestController`, `@RequestMapping("/api/notifications")`
- Inject `NotificationService`
- Use `@AuthenticationPrincipal` to get the current user's email in each method

**Endpoints:**
- `GET /api/notifications` — returns all notifications for current user
- `GET /api/notifications/unread` — returns only unread notifications
- `PUT /api/notifications/{id}/read` — marks one notification as read using `@PathVariable Long id`
- `PUT /api/notifications/read-all` — marks all as read for current user

---

### 📄 Open `services/ReportService.java`

- `@Service`, `@RequiredArgsConstructor`
- Inject: `DocumentRepository`, `UserRepository`

**Write method `getSummaryReport()` — returns `Map<String, Object>`:**
- Build a map with these key-value pairs:
  - `"totalDocuments"` → `documentRepository.count()`
  - `"totalUsers"` → `userRepository.count()`
  - `"pendingDocuments"` → `documentRepository.findByStatus(DocumentStatus.PENDING).size()`
  - `"approvedDocuments"` → `documentRepository.findByStatus(DocumentStatus.APPROVED).size()`
  - `"rejectedDocuments"` → `documentRepository.findByStatus(DocumentStatus.REJECTED).size()`
- Return the map

---

### 📄 Open `controller/ReportController.java`

- `@RestController`, `@RequestMapping("/api/reports")`
- Add: `GET /api/reports/summary` — calls `reportService.getSummaryReport()`

---

### 📄 Open `controller/AdminController.java`

- `@RestController`, `@RequestMapping("/api/admin")`
- Inject `UserService` and `ReportService`
- Add: `GET /api/admin/users` — returns all users
- Add: `GET /api/admin/dashboard-stats` — returns the summary report
- Add `@PreAuthorize("hasRole('ADMIN')")` on the class (or each method) to restrict access to admins only

**📂 Files worked on today:**
- `services/NotificationService.java`
- `controller/NotificationController.java`
- `services/ReportService.java`
- `controller/ReportController.java`
- `controller/AdminController.java`

---

## ✅ Day 13 — Test All Backend APIs End-to-End

**Goal:** Before starting the frontend, make absolutely sure every backend API works correctly.

Open Postman and test these scenarios in order:

**Step 1 — Register & Login:**
- Register 3 users: one with role ADMIN, one with USER, one with APPROVER
- Login with each and save their tokens

**Step 2 — Document Upload (use USER's token in headers):**
- Upload a PDF or Word document using the `/api/documents/upload` endpoint
- Verify the file is saved in the `uploads/` folder on your computer
- Check the document appears in the database

**Step 3 — Approval Flow:**
- Submit the uploaded document for approval (assign to the APPROVER user)
- Login as APPROVER, call `/api/approvals/pending` — verify the document appears
- Process the approval with status "APPROVED" and some comments
- Check the document status changed to APPROVED in the database

**Step 4 — Notifications:**
- After approval, check notifications for the USER — they should have a notification "Your document was APPROVED"
- Mark it as read
- Call unread endpoint again — should return empty now

**Step 5 — Reports (use ADMIN's token):**
- Call `/api/reports/summary` — check the counts are correct

**Common issues to check:**
- If you get `403 Forbidden`: the route needs to be permitted in `SecurityConfig.java` or you forgot to add the token header
- If you get `401 Unauthorized`: token is invalid or expired — login again to get a fresh token
- If file upload fails: check the `uploads/` directory exists and has write permissions

**📂 Files worked on today:**
- Any files that have bugs found during testing

---

# 📅 WEEK 3 — Angular Frontend (Days 14–20)

---

## ✅ Day 14 — TypeScript Models & Angular Services

### 🧠 What is a TypeScript Interface?

A TypeScript interface defines the "shape" of a data object. It tells the compiler what fields to expect. This helps catch mistakes early.

Example: If the backend sends `{ id: 1, title: "Report" }` and you try to access `.name`, TypeScript will show a compile error because the interface only has `title`.

### 🧠 What is an Angular Service?

An Angular service is a class with `@Injectable` that can be shared across multiple components. Services hold the HTTP request logic — components should NEVER call `HttpClient` directly.

Pattern: Component → calls Service method → Service sends HTTP request → gets response → Component uses the response data

---

### 📄 Open `models/user.ts`

Export these TypeScript interfaces:
- `User` — with fields: `id: number`, `username: string`, `email: string`, `role: string`, `department: string`
- `LoginRequest` — with fields: `email: string`, `password: string`
- `RegisterRequest` — with fields: `username: string`, `email: string`, `password: string`, `roleName: string`, `departmentId?: number`
- `AuthResponse` — with fields: `token: string`, `role: string`, `username: string`

### 📄 Open `models/document.ts`

Export:
- `Document` — fields: `id`, `title`, `description`, `status`, `uploadedByUsername`, `filePath`, `createdAt`
- `DocumentRequest` — fields: `title: string`, `description: string`

### 📄 Open `models/approval.ts`

Export:
- `Approval` — fields: `id`, `documentId`, `approverUsername`, `status`, `comments`, `actionDate`
- `ApprovalRequest` — fields: `documentId: number`, `status: string`, `comments: string`

### 📄 Open `models/notification.ts`

Export:
- `Notification` — fields: `id: number`, `message: string`, `isRead: boolean`, `createdAt: string`

---

### 📄 Open `services/auth.ts` — rename the class to `AuthService`

- Keep `@Injectable({ providedIn: 'root' })` — this means there is one shared instance across the whole app
- Inject `HttpClient` in the constructor: `constructor(private http: HttpClient) {}`
- Define `private apiUrl = 'http://localhost:8080/api/auth'`

**Write these methods:**

`login(email: string, password: string): Observable<AuthResponse>`
- Return `this.http.post<AuthResponse>(\`${this.apiUrl}/login\`, { email, password })`

`register(data: RegisterRequest): Observable<any>`
- Return `this.http.post(\`${this.apiUrl}/register\`, data)`

`saveToken(token: string, role: string, username: string): void`
- Use `localStorage.setItem()` to save all three values separately

`getToken(): string | null`
- Return `localStorage.getItem('token')`

`isLoggedIn(): boolean`
- Return `!!this.getToken()` — `!!` converts a value to boolean (non-empty string = true)

`getUserRole(): string`
- Return `localStorage.getItem('role') || ''`

`getUsername(): string`
- Return `localStorage.getItem('username') || ''`

`logout(): void`
- Call `localStorage.clear()` — removes token and all saved user info

---

### 📄 Open `services/document.ts` — rename class to `DocumentService`

- `private apiUrl = 'http://localhost:8080/api/documents'`

**Write these methods:**

`uploadDocument(title: string, description: string, file: File): Observable<Document>`
- Create `const formData = new FormData()`
- Add the JSON data part: `formData.append('data', new Blob([JSON.stringify({ title, description })], { type: 'application/json' }))`
- Add the file: `formData.append('file', file, file.name)`
- Return `this.http.post<Document>(\`${this.apiUrl}/upload\`, formData)`

`getMyDocuments(): Observable<Document[]>`
- Return `this.http.get<Document[]>(\`${this.apiUrl}/my\`)`

`getAllDocuments(): Observable<Document[]>`
- Return `this.http.get<Document[]>(this.apiUrl)`

`deleteDocument(id: number): Observable<any>`
- Return `this.http.delete(\`${this.apiUrl}/${id}\`)`

---

### 📄 Open `services/approval.ts` — rename to `ApprovalService`

- `private apiUrl = 'http://localhost:8080/api/approvals'`
- Write: `getPendingApprovals()`, `processApproval(request: ApprovalRequest)`, `submitForApproval(documentId: number, approverEmail: string)`
- Each method makes the appropriate HTTP call (GET or POST) to the matching endpoint

### 📄 Open `services/notification.ts` — rename to `NotificationService`

- `private apiUrl = 'http://localhost:8080/api/notifications'`
- Write: `getNotifications()`, `markAsRead(id: number)`, `markAllAsRead()`

### 📄 Create NEW `src/environments/environment.ts`

- Export `const environment = { apiUrl: 'http://localhost:8080' }`
- This is a good practice — if you change the backend URL, you only change it in one place

**📂 Files worked on today:**
- `models/user.ts`, `models/document.ts`, `models/approval.ts`, `models/notification.ts`
- `services/auth.ts`, `services/document.ts`, `services/approval.ts`, `services/notification.ts`
- `src/environments/environment.ts` ← CREATED NEW

---

## ✅ Day 15 — HTTP Interceptors & Route Guards

### 🧠 What is an HTTP Interceptor?

An interceptor is a function that runs automatically on EVERY HTTP request (outgoing) or every HTTP response (incoming). You register it once and it applies everywhere — you don't need to add the token manually in every service.

Two interceptors in this project:
- `auth-interceptor` — OUTGOING: adds JWT token to request header
- `error-interceptor` — INCOMING: handles error responses globally

### 🧠 What is a Route Guard?

A route guard runs before a route loads and can BLOCK access. Angular calls it and checks the return value:
- Returns `true` → allow access to the page
- Returns `false` → block access, usually redirect to login

Three guards in this project:
- `auth-guard` — is the user logged in?
- `admin-guard` — is the user an admin?
- `role-guard` — does the user have the required role?

---

### 📄 Open `interceptors/auth-interceptor.ts`

- The file exports an `HttpInterceptorFn` function
- Inside the function, use `inject(AuthService)` to get the auth service
- Call `authService.getToken()` to get the current token
- If token exists:
  - Clone the request and add the header: `req.clone({ setHeaders: { Authorization: \`Bearer ${token}\` } })`
  - Pass the cloned request to `next(clonedReq)`
- If token doesn't exist: pass the original request unchanged: `next(req)`

---

### 📄 Open `interceptors/error-interceptor.ts`

- Exports an `HttpInterceptorFn`
- Use `inject(Router)` and `inject(AuthService)`
- Call `next(req)` and pipe the result with `catchError((error: HttpErrorResponse) => { ... })`
- Inside catchError:
  - If `error.status === 401` — logout and navigate to `/login`
  - If `error.status === 403` — navigate to dashboard or show alert
  - For all errors — `return throwError(() => error)` to propagate the error to the component

---

### 📄 Open `guards/auth-guard.ts`

- Export a `CanActivateFn` function
- Inside, use `inject(AuthService)` and `inject(Router)`
- If `authService.isLoggedIn()` is `true` → return `true`
- If `false` → call `router.navigate(['/login'])` and return `false`

### 📄 Open `guards/admin-guard.ts`

- Same pattern as auth-guard
- Check `authService.getUserRole() === 'ADMIN'`
- If not admin → navigate to `/dashboard` and return `false`

### 📄 Open `guards/role-guard.ts`

- More flexible guard — check a list of allowed roles
- Use `inject(ActivatedRouteSnapshot)` — read `route.data['roles']` which is an array of allowed role names
- Check if `authService.getUserRole()` is included in that array
- Return `true` if yes, redirect and return `false` if no

---

### 📄 Update `app.config.ts`

- Add `provideRouter(routes)` — registers the routes
- Add `provideHttpClient(withInterceptors([authInterceptor, errorInterceptor]))` — registers both interceptors

**📂 Files worked on today:**
- `interceptors/auth-interceptor.ts`
- `interceptors/error-interceptor.ts`
- `guards/auth-guard.ts`
- `guards/admin-guard.ts`
- `guards/role-guard.ts`
- `app.config.ts`

---

## ✅ Day 16 — Login Page Component

### 🧠 Angular Standalone Components

Since Angular v15+, components can be standalone — meaning they don't need an `AppModule`. Each component declares its own imports directly in the `@Component` decorator's `imports: []` array.

**Common imports you'll need:**
- `FormsModule` — for `[(ngModel)]` two-way data binding in forms
- `CommonModule` — for `*ngIf` and `*ngFor` directives
- `RouterModule` — for `routerLink` navigation in templates

---

### 📄 Open `components/login/login.ts` — rename class to `LoginComponent`

**In the `@Component` decorator:**
- Set `standalone: true`
- Add to `imports`: `FormsModule`, `CommonModule`, `RouterModule`
- Keep `templateUrl` and `styleUrl` as they are

**Add these class properties:**
- `email = ''` — bound to the email input field
- `password = ''` — bound to the password input field
- `errorMessage = ''` — shown when login fails
- `isLoading = false` — used to disable the button while loading

**Inject in constructor:** `private authService: AuthService` and `private router: Router`

**Write method `onSubmit()`:**

Step 1 — Start loading:
- Set `isLoading = true` and `errorMessage = ''`

Step 2 — Call the login service:
- Call `this.authService.login(this.email, this.password)`
- Use `.subscribe({ next: ..., error: ... })` to handle the response

Step 3 — On success (`next` callback):
- Call `this.authService.saveToken(res.token, res.role, res.username)`
- Navigate to dashboard: `this.router.navigate(['/dashboard'])`

Step 4 — On error (`error` callback):
- Set `this.errorMessage = 'Invalid email or password. Please try again.'`
- Set `this.isLoading = false`

---

### 📄 Open `components/login/login.html`

**Structure the page as a centered login card:**

1. Outer div `class="login-page"` — fills the full viewport
2. Inner div `class="login-card"` — centered white box

**Inside the card:**
- App title heading: `<h2>📄 Document Approval System</h2>`
- Subheading: `<h3>Login</h3>`
- Error message div — only show it when `errorMessage` is not empty:
  - Use `*ngIf="errorMessage"` on the div
  - Display `{{ errorMessage }}` inside
  - Style it with red color
- A form with `(ngSubmit)="onSubmit()"`:
  - Email input: `type="email"`, `[(ngModel)]="email"`, `name="email"`, `required`, placeholder text
  - Password input: `type="password"`, `[(ngModel)]="password"`, `name="password"`, `required`, placeholder text
  - Submit button: `type="submit"`, `[disabled]="isLoading"`, text changes with `{{ isLoading ? 'Logging in...' : 'Login' }}`
- Register link at the bottom: `<a routerLink="/register">Don't have an account? Register here</a>`

---

### 📄 Open `components/login/login.css`

Write styles for:
- `.login-page` — full screen height, flexbox to center content
- `.login-card` — white background, padding, rounded corners, shadow, fixed max-width (e.g. 400px)
- Input fields — full width, padding, border, rounded corners, focus style
- Submit button — full width, blue background, white text, padding
- Error div — red text or red background with padding
- Register link — centered, smaller font

**📂 Files worked on today:**
- `components/login/login.ts`
- `components/login/login.html`
- `components/login/login.css`

---

## ✅ Day 17 — Register Page Component

**Goal:** Build the register form. This is very similar to the login page — follow the same patterns.

---

### 📄 Open `components/register/register.ts` — rename to `RegisterComponent`

**Add properties:**
- `username`, `email`, `password`, `confirmPassword` — all strings, start as `''`
- `roleName = 'USER'` — default role, could be a dropdown later
- `errorMessage = ''`
- `successMessage = ''`
- `isLoading = false`

**Inject:** `AuthService`, `Router`

**Write method `onSubmit()`:**

Step 1 — Validate passwords match:
- Check `this.password !== this.confirmPassword`
- If they don't match, set `this.errorMessage = 'Passwords do not match!'` and return early

Step 2 — Build the request object:
- Create `const data: RegisterRequest = { username, email, password, roleName }`

Step 3 — Call `authService.register(data)`:
- On success: set `successMessage = 'Registration successful! Please login.'`, then navigate to `/login` after 2 seconds using `setTimeout(() => this.router.navigate(['/login']), 2000)`
- On error: extract the error message from `error.error.error` (or similar) and set `errorMessage`

---

### 📄 Open `components/register/register.html`

Build a form card similar to the login card:
- Username input — `[(ngModel)]="username"`, `name="username"`
- Email input — `type="email"`, `[(ngModel)]="email"`
- Password input — `[(ngModel)]="password"`
- Confirm Password input — `[(ngModel)]="confirmPassword"`
- Password mismatch error message — shown with `*ngIf="password && confirmPassword && password !== confirmPassword"`
- Success message div — shown when `successMessage` is not empty
- Error message div — shown when `errorMessage` is not empty
- Submit button with loading state
- Link back to login: `Already have an account? <a routerLink="/login">Login here</a>`

**📂 Files worked on today:**
- `components/register/register.ts`
- `components/register/register.html`
- `components/register/register.css`

---

## ✅ Day 18 — Navbar, Sidebar & App Routing

**Goal:** Set up the navigation shell that wraps all pages, and configure routing so clicking links works.

---

### 📄 Open `components/navbar/navbar.ts` — rename to `NavbarComponent`

- Inject: `AuthService`, `Router`
- Add property `username = this.authService.getUsername()` — shows the logged-in user's name
- Write method `logout()`:
  - Call `this.authService.logout()`
  - Navigate to `/login`

### 📄 Open `components/navbar/navbar.html`

Create a top navigation bar `<nav>`:
- Left side: app name/logo
- Right side:
  - Welcome message: `Welcome, {{ username }}`
  - Logout button that calls `(click)="logout()"`

### 📄 Open `components/sidebar/sidebar.ts` — rename to `SidebarComponent`

- Inject `AuthService`
- Add property `isAdmin = this.authService.getUserRole() === 'ADMIN'`
- Add property `isApprover = this.authService.getUserRole() === 'APPROVER'`

### 📄 Open `components/sidebar/sidebar.html`

Create a vertical `<div class="sidebar">` with navigation links:
- Always visible: `<a routerLink="/dashboard">🏠 Dashboard</a>`
- Always visible: `<a routerLink="/documents">📄 My Documents</a>`
- For approvers: `<a routerLink="/approvals" *ngIf="isApprover || isAdmin">✅ Approvals</a>`
- Always visible: `<a routerLink="/notifications">🔔 Notifications</a>`
- Always visible: `<a routerLink="/profile">👤 My Profile</a>`
- Admin only: `<a routerLink="/admin" *ngIf="isAdmin">⚙️ Admin Panel</a>`
- Add `routerLinkActive="active"` to each link so the current page's link is highlighted

---

### 📄 Open `app.routes.ts`

Import all component classes and define the routes array:
- `{ path: '', redirectTo: 'login', pathMatch: 'full' }` — redirect root URL to login
- `{ path: 'login', component: LoginComponent }` — public page
- `{ path: 'register', component: RegisterComponent }` — public page
- `{ path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] }` — requires login
- `{ path: 'documents', component: DocumentComponent, canActivate: [authGuard] }` — requires login
- `{ path: 'approvals', component: ApprovalComponent, canActivate: [authGuard] }` — requires login
- `{ path: 'notifications', component: NotificationComponent, canActivate: [authGuard] }` — requires login
- `{ path: 'profile', component: ProfileComponent, canActivate: [authGuard] }` — requires login
- `{ path: '**', redirectTo: 'login' }` — unknown URLs redirect to login

### 📄 Open `app.html`

Replace the default content with the application layout:
- Add `<app-navbar>` at the top
- Create a `<div class="app-layout">` containing:
  - `<app-sidebar>` on the left
  - `<div class="main-content"><router-outlet></router-outlet></div>` on the right
- `<router-outlet>` is where Angular inserts the current page's component

### 📄 Open `app.ts`

- Add `NavbarComponent` and `SidebarComponent` to the `imports` array of the app component
- Add `RouterOutlet` to imports

**📂 Files worked on today:**
- `components/navbar/navbar.ts` & `.html` & `.css`
- `components/sidebar/sidebar.ts` & `.html` & `.css`
- `app.routes.ts`
- `app.html`
- `app.ts`

---

## ✅ Day 19 — Dashboard Component

**Goal:** Build the main home page with stats and recent documents.

---

### 📄 Open `components/dashboard/dashboard.ts` — rename to `DashboardComponent`

- `standalone: true`, import `CommonModule`, `RouterModule`
- Inject: `DocumentService`, `AuthService`

**Add these properties:**
- `isLoading = true`
- `totalDocuments = 0`
- `pendingCount = 0`
- `approvedCount = 0`
- `rejectedCount = 0`
- `recentDocuments: Document[] = []`

**In `ngOnInit()`:**
- Decide which service method to call based on role:
  - Admin: `documentService.getAllDocuments()`
  - Others: `documentService.getMyDocuments()`
- In the `.subscribe()`:
  - Set `totalDocuments = docs.length`
  - Count by status: `pendingCount = docs.filter(d => d.status === 'PENDING').length` (repeat for APPROVED, REJECTED)
  - Set `recentDocuments = docs.slice(0, 5)` — take only the last 5 for the recent section
  - Set `isLoading = false`

---

### 📄 Open `components/dashboard/dashboard.html`

**Layout:**

1. Loading state: `<div *ngIf="isLoading">Loading dashboard...</div>`
2. Main content: `<div *ngIf="!isLoading">`

**Stats cards row:**
- Wrap 4 cards in `<div class="stats-row">`
- Each card: `<div class="stat-card">` with a number `<h2>{{ totalDocuments }}</h2>` and label `<p>Total Documents</p>`
- Cards: Total Documents, Pending, Approved, Rejected

**Recent Documents section:**
- Heading: `<h3>Recent Documents</h3>`
- Table with columns: Title, Status, Date
- Loop with `*ngFor="let doc of recentDocuments"`
- Status column: `<span [class]="'badge ' + doc.status.toLowerCase()">{{ doc.status }}</span>` — applies CSS class based on status
- If no documents: `<p *ngIf="recentDocuments.length === 0">No documents found.</p>`

---

### 📄 Open `components/dashboard/dashboard.css`

Write styles for:
- `.stats-row` — use CSS Grid or Flexbox, 4 columns, gap between cards
- `.stat-card` — white background, rounded corners, padding, box shadow
- `.stat-card h2` — large font, primary color
- `.badge` — pill shape (border-radius, padding, font-weight)
- `.badge.pending` — yellow/orange colors
- `.badge.approved` — green colors
- `.badge.rejected` — red colors
- `.badge.draft` — grey colors

**📂 Files worked on today:**
- `components/dashboard/dashboard.ts`
- `components/dashboard/dashboard.html`
- `components/dashboard/dashboard.css`

---

## ✅ Day 20 — Documents Component (Upload & List)

**Goal:** Let users see all their documents and upload new ones.

---

### 📄 Open `components/document/document.ts` — rename to `DocumentComponent`

- `standalone: true`, import `CommonModule`, `FormsModule`
- Inject: `DocumentService`, `AuthService`

**Properties:**
- `documents: Document[] = []`
- `showUploadForm = false` — controls whether upload form is visible
- `isLoading = true`
- `isUploading = false`
- `title = ''`, `description = ''` — form fields
- `selectedFile: File | null = null` — the selected file
- `errorMessage = ''`, `successMessage = ''`

**Write method `loadDocuments()`:**
- Set `isLoading = true`
- Call `documentService.getMyDocuments()` (or `getAllDocuments()` for admin)
- In subscribe: set `documents`, set `isLoading = false`

**Write method `onFileSelected(event: Event)`:**
- Cast event target: `const input = event.target as HTMLInputElement`
- Check `input.files && input.files.length > 0`
- If yes: `this.selectedFile = input.files[0]`

**Write method `upload()`:**
- Validate: title is not empty AND selectedFile is not null
- Set `isUploading = true`
- Call `documentService.uploadDocument(this.title, this.description, this.selectedFile)`
- On success: set `successMessage`, clear form fields, set `showUploadForm = false`, call `loadDocuments()`
- On error: set `errorMessage`
- In both cases: `isUploading = false`

**Write method `deleteDocument(id: number)`:**
- `if (!confirm('Are you sure you want to delete this document?')) return`
- Call `documentService.deleteDocument(id)`
- On success: reload documents and show success message

---

### 📄 Open `components/document/document.html`

**Structure:**

1. Page header: `<div class="page-header">` with heading "My Documents" and an "Upload Document" button
   - Button calls `(click)="showUploadForm = !showUploadForm"`
   - Button text changes: `{{ showUploadForm ? 'Cancel' : '+ Upload Document' }}`

2. Upload form panel — `<div class="upload-panel" *ngIf="showUploadForm">`:
   - Title input — `[(ngModel)]="title"`, required
   - Description textarea — `[(ngModel)]="description"`
   - File input — `(change)="onFileSelected($event)"`, `accept=".pdf,.doc,.docx,.xlsx"`
   - Show selected file name if any: `<small *ngIf="selectedFile">Selected: {{ selectedFile.name }}</small>`
   - Submit button — `[disabled]="isUploading || !title || !selectedFile"`, shows "Uploading..." when loading

3. Success/error messages with `*ngIf`

4. Loading state: `<div *ngIf="isLoading">Loading documents...</div>`

5. Documents table `*ngIf="!isLoading && documents.length > 0"`:
   - Columns: Title, Description, Status (badge), Upload Date, Actions
   - Actions: Download button and Delete button per row
   - Delete button calls `deleteDocument(doc.id)`

6. Empty state `*ngIf="!isLoading && documents.length === 0"`:
   - Message: "No documents found. Click 'Upload Document' to get started!"

**📂 Files worked on today:**
- `components/document/document.ts`
- `components/document/document.html`
- `components/document/document.css`

---

# 📅 WEEK 4 — Complete Frontend & Polish (Days 21–30)

---

## ✅ Day 21 — Approval Component

**Goal:** Build the page where approvers can review and act on pending documents.

---

### 📄 Open `components/approval/approval.ts` — rename to `ApprovalComponent`

**Properties:**
- `pendingApprovals: any[] = []`
- `isLoading = true`
- `comments = ''` — the comment textarea value
- `processingId: number | null = null` — tracks which item is being processed

**Write `ngOnInit()`:** load pending approvals

**Write method `approve(documentId: number)`:**
- Set `processingId = documentId`
- Call `approvalService.processApproval({ documentId, status: 'APPROVED', comments: this.comments })`
- On success: show alert, reload approvals, clear comments
- On error: show error message

**Write method `reject(documentId: number)`:**
- Validate that `comments` is not empty — if empty, set `errorMessage = 'Comments are required when rejecting'` and return
- Same pattern as approve but with status `'REJECTED'`

**Write method `loadApprovals()`:**
- Calls `approvalService.getPendingApprovals()`, stores result in `pendingApprovals`

---

### 📄 Open `components/approval/approval.html`

**Structure:**

1. Page heading: "Pending Approvals"
2. Empty state `*ngIf="pendingApprovals.length === 0 && !isLoading"`: "No pending approvals 🎉"
3. Loading state
4. Approval cards — `*ngFor="let item of pendingApprovals"`:
   - Each card shows: Document Title (bold), "Submitted by: username", "Submitted on: date"
   - Comments textarea: `[(ngModel)]="comments"`, placeholder "Add your comments here..."
   - Error message for empty comments on rejection
   - Two action buttons:
     - `✅ Approve` button — green, `(click)="approve(item.documentId)"`, `[disabled]="processingId === item.documentId"`
     - `❌ Reject` button — red, `(click)="reject(item.documentId)"`, `[disabled]="processingId === item.documentId"`

**📂 Files worked on today:**
- `components/approval/approval.ts`
- `components/approval/approval.html`
- `components/approval/approval.css`

---

## ✅ Day 22 — Notification Component

### 📄 Open `components/notification/notification.ts` — rename to `NotificationComponent`

**Properties:**
- `notifications: Notification[] = []`
- `isLoading = true`
- `unreadCount = 0`

**Write `ngOnInit()`:**
- Load notifications, calculate `unreadCount = notifications.filter(n => !n.isRead).length`

**Write method `markRead(id: number)`:**
- Call `notificationService.markAsRead(id)`
- On success: find the notification in the local array and set `isRead = true`, recalculate `unreadCount`

**Write method `markAllRead()`:**
- Call `notificationService.markAllAsRead()`
- On success: set all `isRead = true` in the local array, set `unreadCount = 0`

---

### 📄 Open `components/notification/notification.html`

**Structure:**
1. Header row with: heading "🔔 Notifications", badge showing unread count, "Mark All as Read" button (disabled when `unreadCount === 0`)
2. Loading state
3. Empty state: "No notifications yet"
4. Notification list `*ngFor`:
   - Each item uses `[class.unread]="!n.isRead"` to add unread styling class
   - Show: message text, created date (smaller font)
   - Show "Mark as Read" button only for unread items: `*ngIf="!n.isRead"`, calls `markRead(n.id)`

**📂 Files worked on today:**
- `components/notification/notification.ts`
- `components/notification/notification.html`
- `components/notification/notification.css`

---

## ✅ Day 23 — Profile Component

### 📄 Open `components/profile/profile.ts` — rename to `ProfileComponent`

- Inject `AuthService`
- Add properties: `username = authService.getUsername()`, `role = authService.getUserRole()`
- You can extend this later to allow editing username — for now, read-only is fine

### 📄 Open `components/profile/profile.html`

- Create a centered card layout
- Show an avatar circle with the first letter of username: `{{ username.charAt(0).toUpperCase() }}`
- Display username, email, and role — all read-only
- Optional: Add an "Edit Profile" form that shows when a button is clicked

**📂 Files worked on today:**
- `components/profile/profile.ts`
- `components/profile/profile.html`
- `components/profile/profile.css`

---

## ✅ Day 24 — Admin Panel Component

**Goal:** Build a page for admins to see and manage all users.

Create a NEW folder `src/app/components/admin/` with 3 new files inside.

---

### 📄 Create `components/admin/admin.ts`

- Class: `AdminComponent`
- `standalone: true`, import `CommonModule`
- Inject: `UserService` (add `getAllUsers()` and `deleteUser(id)` methods there if not done already)
- Properties: `users: any[] = []`, `isLoading = true`
- Write `ngOnInit()` — loads all users
- Write `deleteUser(id: number)` — confirm first, then call service, reload list

### 📄 Create `components/admin/admin.html`

- Heading: "👥 User Management"
- Loading state
- Table with columns: ID, Username, Email, Role, Department, Actions
- Actions column: Delete button, which calls `deleteUser(user.id)` with red styling
- Show row count: `<p>Total users: {{ users.length }}</p>`

### Update `app.routes.ts`

- Add new route: `{ path: 'admin', component: AdminComponent, canActivate: [authGuard, adminGuard] }`
- This means: user must be logged in AND must be an admin to access this page

### Update `components/sidebar/sidebar.html`

- The admin link should already be there with `*ngIf="isAdmin"` — make sure the `routerLink="/admin"` is correct

**📂 Files worked on today:**
- `components/admin/admin.ts` ← CREATED NEW
- `components/admin/admin.html` ← CREATED NEW
- `components/admin/admin.css` ← CREATED NEW
- `app.routes.ts`

---

## ✅ Day 25 — Global Styles & App Layout CSS

**Goal:** Make the entire app look consistent and professional.

---

### 📄 Open `src/styles.css`

This file applies to every component in the app.

**Step 1 — Define CSS Variables (custom properties):**
- These variables let you change the color theme from one place
- Define inside `:root { }`:
  - `--primary-color: #2563eb` (blue — for buttons and links)
  - `--success-color: #16a34a` (green — for approved/success)
  - `--danger-color: #dc2626` (red — for rejected/delete/error)
  - `--warning-color: #d97706` (orange/yellow — for pending/warning)
  - `--bg-color: #f1f5f9` (light grey — page background)
  - `--card-bg: #ffffff` (white — card backgrounds)
  - `--text-primary: #1e293b` (dark grey — main text)
  - `--border-color: #e2e8f0` (light grey — borders)

**Step 2 — CSS Reset:**
- `* { box-sizing: border-box; margin: 0; padding: 0; }` — removes browser default spacing
- `body { font-family: 'Segoe UI', sans-serif; background-color: var(--bg-color); color: var(--text-primary); }`

**Step 3 — Reusable Utility Classes:**

Status badge classes:
- `.badge` — base style: `padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 600`
- `.badge.pending` — background: `#fef3c7` (light yellow), color: `#92400e`
- `.badge.approved` — background: `#dcfce7` (light green), color: `#166534`
- `.badge.rejected` — background: `#fee2e2` (light red), color: `#991b1b`
- `.badge.draft` — background: `#f1f5f9` (light grey), color: `#475569`

Button classes:
- `.btn` — base: `cursor: pointer; padding: 8px 16px; border-radius: 6px; border: none; font-weight: 500`
- `.btn-primary` — `background: var(--primary-color); color: white`
- `.btn-success` — `background: var(--success-color); color: white`
- `.btn-danger` — `background: var(--danger-color); color: white`
- All buttons: add `hover` state with slightly darker background and `disabled` state with `opacity: 0.6; cursor: not-allowed`

Card class:
- `.card` — `background: var(--card-bg); border-radius: 8px; padding: 20px; box-shadow: 0 1px 3px rgba(0,0,0,0.1)`

Table classes:
- `table { width: 100%; border-collapse: collapse; background: white; border-radius: 8px; }`
- `th { background: #f8fafc; padding: 12px 16px; text-align: left; font-weight: 600; }`
- `td { padding: 12px 16px; border-top: 1px solid var(--border-color); }`
- `tr:hover { background: #f8fafc; }`

Form classes:
- `.form-group { margin-bottom: 16px; }`
- `.form-group label { display: block; margin-bottom: 6px; font-weight: 500; }`
- `input, textarea, select { width: 100%; padding: 10px 12px; border: 1px solid var(--border-color); border-radius: 6px; }`
- `input:focus { outline: none; border-color: var(--primary-color); box-shadow: 0 0 0 3px rgba(37,99,235,0.1); }`

**Step 4 — App Layout CSS:**
- `.app-layout { display: flex; min-height: calc(100vh - 60px); }` (subtract navbar height)
- `.sidebar { width: 240px; background: white; border-right: 1px solid var(--border-color); padding: 20px 0; }`
- `.main-content { flex: 1; padding: 24px; overflow-y: auto; }`

**📂 Files worked on today:**
- `src/styles.css`
- `app.css`
- All component `.css` files (refactor to use the new variables and classes)

---

## ✅ Day 26 — Loading States, Error Messages & Form Validation

**Goal:** Make every part of the app give proper feedback — no more silent failures or empty screens.

---

### In EVERY component that loads data:

**In the TypeScript file:**
- Add `isLoading = true` property
- Add `errorMessage = ''` property
- In the service call, use this pattern:
  - `next:` callback — set your data, set `isLoading = false`
  - `error:` callback — set `errorMessage = 'Failed to load. Please try again.'`, set `isLoading = false`

**In the HTML template:**
- At the top: `<div class="loading" *ngIf="isLoading">⏳ Loading...</div>`
- Error message: `<div class="error-message" *ngIf="errorMessage">{{ errorMessage }}</div>`
- Main content wrapped in: `<div *ngIf="!isLoading && !errorMessage">...content...</div>`

---

### In EVERY form (login, register, upload document, process approval):

**Show field-level validation errors:**
- For required fields: `<span class="error-text" *ngIf="titleInput.invalid && titleInput.touched">Title is required</span>`
- For email format: `<span class="error-text" *ngIf="emailInput.errors?.['email']">Invalid email format</span>`
- Use template reference variables on inputs: `#titleInput="ngModel"`

**Disable submit button** when form is invalid:
- `[disabled]="form.invalid || isLoading"` on the submit button

**Prevent double submission:**
- Set `isLoading = true` immediately when submit is clicked, so the button becomes disabled
- Set it back to `false` in both success and error callbacks

---

### Add empty states for every list:

- Documents page: "No documents yet. Start by uploading your first document!" with an upload button
- Approvals page: "No pending approvals 🎉 All documents have been reviewed."
- Notifications page: "You're all caught up! No new notifications."

**📂 Files worked on today:**
- All component `.ts` and `.html` files

---

## ✅ Day 27 — File Download Feature

**Goal:** Let users download the documents they uploaded.

---

### 📄 Backend — Open `controller/DocumentController.java`

Add a new endpoint: `GET /api/documents/{id}/download`

**Inside the method:**
- Annotate with `@GetMapping("/{id}/download")`
- Use `@PathVariable Long id`
- Find the document by id using `documentRepository.findById(id)` (inject the repository)
- Get the file path from `document.getFilePath()`
- Create a `Path` object: `Path filePath = Paths.get(storedPath)`
- Create a Resource: `Resource resource = new UrlResource(filePath.toUri())`
- Check the resource exists: `resource.exists() && resource.isReadable()` — throw exception if not
- Build and return a `ResponseEntity<Resource>`:
  - Content type: `MediaType.APPLICATION_OCTET_STREAM`
  - Content disposition header: `"attachment; filename=\"" + filePath.getFileName().toString() + "\""`
  - Body: the resource object
  - Method imports needed: `org.springframework.core.io.Resource`, `org.springframework.core.io.UrlResource`, `org.springframework.http.HttpHeaders`, `java.nio.file.*`

---

### 📄 Frontend — Open `services/document.ts`

Add method `downloadDocument(id: number): void`:
- Simply open the download URL in a new browser tab
- Use: `window.open(\`${this.apiUrl}/${id}/download\`, '_blank')`
- The browser will handle the download automatically

---

### 📄 Open `components/document/document.html`

In the table's Actions column, add a Download button:
- Style it as a secondary button (blue outline or blue filled)
- Click handler: `(click)="documentService.downloadDocument(doc.id)"` OR create a wrapper method in the component
- Place it next to the Delete button

**📂 Files worked on today:**
- `Backend/.../controller/DocumentController.java`
- `Frontend/.../services/document.ts`
- `Frontend/.../components/document/document.html`

---

## ✅ Day 28 — Security Hardening & Input Validation

**Goal:** Make sure users can only do what they're supposed to do, and validate all input.

---

### 📄 Backend — Add input validation to DTO classes

Jakarta Bean Validation allows you to annotate DTO fields to enforce rules automatically. Add these annotations to the appropriate fields:

**In `dto/LoginRequest.java`:**
- `@NotBlank(message = "Email is required")` on `email`
- `@Email(message = "Invalid email format")` on `email`
- `@NotBlank(message = "Password is required")` on `password`

**In `dto/RegisterRequest.java`:**
- `@NotBlank` on `username`, `email`, `password`
- `@Email` on `email`
- `@Size(min = 6, message = "Password must be at least 6 characters")` on `password`

**In `dto/DocumentRequest.java`:**
- `@NotBlank(message = "Document title is required")` on `title`

**In controllers:** Wherever you have `@RequestBody`, add `@Valid` before it:
- `@Valid @RequestBody LoginRequest request`
- `@Valid @RequestBody RegisterRequest request`
- This triggers validation and throws `MethodArgumentNotValidException` if rules are violated
- Your `GlobalExceptionHandler` will catch this and return field-level error messages

---

### 📄 Backend — Service-level security checks

**In `services/DocumentService.java`:**
- In `deleteDocument(Long id, String requestingUserEmail)` (add the email parameter):
  - Find the document
  - Check `document.getUploadedBy().getEmail().equals(requestingUserEmail)` OR if the requester is admin
  - If neither, throw `new BadRequestException("You can only delete your own documents")`

**In `services/ApprovalService.java`:**
- In `processApproval()`, verify the logged-in user is actually the assigned approver
- Throw `BadRequestException` if someone else tries to process an approval not assigned to them

---

### 📄 Frontend — Improve error display

**In `interceptors/error-interceptor.ts`:**
- Extract the backend error message from the response body: `error.error?.error || error.message || 'An unexpected error occurred'`
- Show it in a more user-friendly way

**In all form components:**
- Display the specific error message returned from the backend (not just a generic one)
- For example, show "Email is already registered!" instead of just "Registration failed"

**📂 Files worked on today:**
- All `dto/*.java` files (add validation annotations)
- All controller files (add `@Valid`)
- `exception/GlobalExceptionHandler.java` (add handler for validation errors)
- `services/DocumentService.java` (security check)
- `services/ApprovalService.java` (security check)
- `interceptors/error-interceptor.ts`

---

## ✅ Day 29 — Full End-to-End Testing

**Goal:** Test the complete application from start to finish like a real user would.

---

### 🧪 Test Scenario 1 — Regular User Journey

1. Open `http://localhost:4200` in a browser
2. Click Register → fill in the form with role "USER" → submit
3. Login with the new account → you should land on Dashboard
4. Go to Documents page → click Upload → fill in title, description, pick a PDF file → upload
5. Verify: the document appears in the list with status "PENDING"
6. Click Download on the document → verify the file downloads correctly
7. Check Notifications → a notification should appear that your document was submitted
8. Check the Dashboard → document counts should be updated

### 🧪 Test Scenario 2 — Approver Journey

1. Register a second account with role "APPROVER" (use different email)
2. Login as the APPROVER
3. Go to Approvals page → the pending document from Scenario 1 should appear
4. Write a comment in the comments box
5. Click Approve → verify the approval success message appears
6. Login back as the USER → go to Documents page → status should now show "APPROVED"
7. Check Notifications as the USER → should see "Your document was APPROVED"

### 🧪 Test Scenario 3 — Admin Journey

1. Register a third account with role "ADMIN" (use different email)
2. Login as ADMIN
3. Go to Admin Panel → all 3 users should be listed
4. Go to Dashboard → stats should show correct totals
5. Try accessing `/admin` while logged in as USER → should redirect to login (guard is working)

### 🧪 Test Edge Cases

- Try logging in with wrong password → should see "Invalid email or password" error
- Try registering with an already-used email → should see "Email already registered" error
- Try uploading without selecting a file → submit button should be disabled
- Try rejecting an approval without writing comments → should see validation error
- Logout → try accessing `/dashboard` directly in URL → should redirect to login

**Write down every bug you find and fix them before Day 30.**

**📂 Files worked on today:**
- Any files with bugs found during testing

---

## ✅ Day 30 — Final Cleanup, Documentation & Submission

**Goal:** Clean, professional code ready to submit or demo.

---

### 🧹 Backend Cleanup Checklist

Go through every Java file and:
- [ ] Remove all `System.out.println()` debug statements
- [ ] Make sure every controller method returns the correct HTTP status code (200, 201, 204, 400, 404)
- [ ] Check that exception messages are helpful and specific (not generic "Error occurred")
- [ ] Verify `application.properties` has the correct database settings
- [ ] Make sure the `uploads/` folder exists in the backend root directory

### 🧹 Frontend Cleanup Checklist

Go through every TypeScript file and:
- [ ] Remove all `console.log()` debug statements
- [ ] Check browser console (press F12 → Console tab) — fix ALL red errors
- [ ] Test all navigation links — none should lead to a blank page
- [ ] Test login and logout — should work smoothly
- [ ] Verify all protected pages redirect to login when not authenticated
- [ ] Check all forms show validation errors correctly

### 🧹 Database Cleanup

- [ ] Update `Database/dbsql.sql` to include the final complete CREATE TABLE statements
- [ ] Add seed data SQL at the bottom for testing: 3 users (one per role), 2 departments, 1 sample document

---

### 📝 Write `README.md` at the Project Root

Create a new file `README.md` in the root folder of the project. Write:

**Section 1 — Project Description:**
- What this project does (2-3 sentences)
- Who it's for

**Section 2 — Tech Stack:**
- Backend: Spring Boot 4.x, Hibernate/JPA, Spring Security (JWT), Lombok
- Frontend: Angular, TypeScript, RxJS
- Database: MySQL

**Section 3 — How to Set Up:**
- Step 1: Create the MySQL database by running `Database/dbsql.sql`
- Step 2: Update `Backend/.../application.properties` with your MySQL password
- Step 3: Open backend in IntelliJ, run the application

**Section 4 — How to Run Frontend:**
- `cd Frontend/digital-document-approval-frontend`
- `npm install`
- `ng serve`
- Open `http://localhost:4200`

**Section 5 — Test Accounts:**
- Admin: email and password you created
- Approver: email and password
- User: email and password

---

### 🚀 Push to GitHub

```bash
git add .
git commit -m "feat: Complete Digital Document Approval System implementation"
git push origin main
```

---

## 🏆 What You Built in 30 Days

| Feature | Backend Done? | Frontend Done? |
|---------|--------------|---------------|
| User Registration & Login | JWT Auth API | Login & Register pages |
| Department Management | CRUD API | (Admin only via API) |
| Document Upload & List | File Upload API | Documents page with upload form |
| Document Download | Download endpoint | Download button in documents table |
| Approval Workflow | Submit & Process API | Approvals page |
| Notifications | Create & Read API | Notifications page |
| Admin User Management | Admin API | Admin Panel page |
| Reports & Stats | Summary API | Dashboard stats cards |
| Route Protection | Spring Security | Angular Guards |
| Error Handling | Global Exception Handler | Interceptors + Error messages |

---

## 💡 Quick Reference — Common Problems & Fixes

| Problem You See | What It Means | How to Fix |
|----------------|--------------|-----------|
| `403 Forbidden` error in Postman | The API endpoint requires authentication | Add `Authorization: Bearer YOUR_TOKEN` header in Postman |
| `403` even with token | The route is not permitted for your role | Check `SecurityConfig.java` and `@PreAuthorize` rules |
| `CORS error` in browser | Backend is blocking Angular requests | Add `http://localhost:4200` to allowed origins in `SecurityConfig.java` |
| Token not sent with request | Interceptor not registered | Check `authInterceptor` is in `app.config.ts` `withInterceptors([])` list |
| `Can't bind to ngModel` | FormsModule not imported | Add `FormsModule` to the component's `imports: []` array |
| White/blank page in Angular | A component has a TypeScript error | Open browser DevTools (F12 → Console) and read the error |
| Backend won't start | Database connection failed | Check `application.properties` — database URL, username, password |
| `npm install` fails | Node.js issue | Make sure Node.js is installed, try deleting `node_modules` folder and running again |
| Hibernate creates no tables | Entity not scanned | Check your entity classes have `@Entity` annotation and are in the right package |
| File upload returns 500 | Upload directory doesn't exist | Create the `uploads/` folder in your backend project root |

---

> **One Last Thing:** You will get stuck. That's 100% normal and expected. Every developer — junior and senior — Googles things every single day. When stuck:
> 1. Read the error message carefully — it usually tells you exactly what's wrong
> 2. Google the error message
> 3. Check Stack Overflow
> 4. Ask your mentor
>
> **The goal is not to memorize code. The goal is to understand how the pieces fit together.** 🚀
