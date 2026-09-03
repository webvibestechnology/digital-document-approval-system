# 📋 Intern Task Plan — Digital Document Approval System
### Duration: 30 Days | Stack: Spring Boot · Hibernate · Angular · MySQL

> **Project Overview:** A full-stack web application where users can upload documents, route them through an approval workflow, and track their status in real time.
>
> **Current State:** The project skeleton is fully scaffolded — all files and folders exist but every class/component is empty. The intern's job is to implement everything from scratch following the existing structure.
>
> **Tech Stack:**
> - **Backend:** Spring Boot 4.1.1, Spring Data JPA (Hibernate), Spring Security (JWT), Lombok, Java 17
> - **Frontend:** Angular (standalone components), TypeScript, RxJS
> - **Database:** MySQL (`digital_document_approval` schema)

---

## 🗂️ Project File Map (Quick Reference)

```
Backend/documentapproval/src/main/java/com/documentapproval/
├── entity/             → JPA entities (DB table mappings)
│   ├── User.java
│   ├── Role.java
│   ├── Department.java
│   ├── Document.java
│   ├── Approval.java
│   └── Notification.java
├── repository/         → Spring Data JPA interfaces
│   ├── UserRepository.java
│   ├── DocumentRepository.java
│   ├── ApprovalRepository.java
│   ├── DepartmentRepository.java
│   └── NotificationRepository.java
├── dto/                → Request/Response data transfer objects
│   ├── LoginRequest.java, RegisterRequest.java
│   ├── DocumentRequest.java, DocumentResponse.java
│   ├── ApprovalRequest.java
│   ├── DepartmentRequest.java
│   ├── UserRequest.java, UserResponse.java
│   └── NotificationResponse.java
├── services/           → Business logic layer
│   ├── AuthService.java
│   ├── UserService.java
│   ├── DocumentService.java
│   ├── ApprovalService.java
│   ├── DepartmentService.java
│   ├── NotificationService.java
│   └── ReportService.java
├── controller/         → REST API endpoints
│   ├── AuthController.java
│   ├── UserController.java
│   ├── DocumentController.java
│   ├── ApprovalController.java
│   ├── DepartmentController.java
│   ├── NotificationController.java
│   ├── AdminController.java
│   └── ReportController.java
├── security/           → JWT security layer
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
├── exception/          → Error handling
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── BadRequestException.java
├── util/               → Utility classes
│   ├── FileUploadUtil.java
│   └── ValidationUtil.java
└── config/             → Spring configuration (SecurityConfig to be created here)

Database/
└── dbsql.sql           → Add all CREATE TABLE statements here

Frontend/digital-document-approval-frontend/src/app/
├── models/             → TypeScript interfaces
│   ├── user.ts, document.ts, approval.ts, notification.ts
├── services/           → Angular HTTP services
│   ├── auth.ts, user.ts, document.ts, approval.ts, notification.ts
├── interceptors/       → HTTP interceptors
│   ├── auth-interceptor.ts
│   └── error-interceptor.ts
├── guards/             → Route guards
│   ├── auth-guard.ts, admin-guard.ts, role-guard.ts
└── components/         → UI components
    ├── login/, register/, dashboard/, document/
    ├── approval/, notification/, profile/
    ├── navbar/, sidebar/
```

---

## Week 1 — Foundation: Database, Entities & Project Setup (Days 1–6)

---

### ✅ Day 1 — Environment Setup & Database Schema Design

**Goal:** Get the development environment running and design the full database schema.

**Tasks:**

1. **Set up the backend**
   - Open `Backend/documentapproval/` in IntelliJ or VS Code
   - Create `src/main/resources/application.properties` with MySQL connection:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/digital_document_approval
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
     ```
   - Verify `DocumentapprovalApplication.java` has the `@SpringBootApplication` annotation and `main()` method. Add it if missing.

2. **Set up the frontend**
   - Open terminal in `Frontend/digital-document-approval-frontend/`
   - Run `npm install` to restore packages
   - Run `ng serve` to verify the Angular app starts on `http://localhost:4200`

3. **Design the database schema in `Database/dbsql.sql`**
   - Write `CREATE TABLE` statements for all 6 tables:
     - `roles` (id, name)
     - `departments` (id, name, description)
     - `users` (id, username, email, password, role_id FK, department_id FK, created_at)
     - `documents` (id, title, description, file_path, file_type, status [PENDING/APPROVED/REJECTED/DRAFT], uploaded_by FK, created_at, updated_at)
     - `approvals` (id, document_id FK, approver_id FK, status [PENDING/APPROVED/REJECTED], comments, action_date)
     - `notifications` (id, user_id FK, message, is_read, created_at)
   - Run the SQL script against your local MySQL to create the database

**Files to work on:**
- `Backend/documentapproval/src/main/resources/application.properties` *(create this file)*
- `Backend/documentapproval/src/main/java/com/documentapproval/DocumentapprovalApplication.java`
- `Database/dbsql.sql`

---

### ✅ Day 2 — JPA Entities: User, Role, Department

**Goal:** Implement the first three JPA entity classes that map to database tables.

**What is an entity?** A Java class annotated with `@Entity` that represents a database table. Hibernate automatically maps its fields to columns.

**Tasks:**

1. **Implement `entity/Role.java`**
   ```java
   @Entity @Table(name = "roles") @Data @NoArgsConstructor @AllArgsConstructor
   ```
   - Fields: `Long id`, `String name` (e.g., "ADMIN", "USER", "APPROVER")
   - Use `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` on `id`

2. **Implement `entity/Department.java`**
   ```java
   @Entity @Table(name = "departments") @Data @NoArgsConstructor @AllArgsConstructor
   ```
   - Fields: `Long id`, `String name`, `String description`

3. **Implement `entity/User.java`**
   ```java
   @Entity @Table(name = "users") @Data @NoArgsConstructor @AllArgsConstructor
   ```
   - Fields: `Long id`, `String username`, `String email`, `String password`
   - Relationships:
     - `@ManyToOne @JoinColumn(name = "role_id")` → `Role role`
     - `@ManyToOne @JoinColumn(name = "department_id")` → `Department department`
   - `LocalDateTime createdAt` with `@Column(name = "created_at")`

**Files to work on:**
- `Backend/.../entity/Role.java`
- `Backend/.../entity/Department.java`
- `Backend/.../entity/User.java`

**Imports needed:** `jakarta.persistence.*`, `lombok.*`, `java.time.LocalDateTime`

---

### ✅ Day 3 — JPA Entities: Document, Approval, Notification

**Goal:** Implement the remaining three entities and define entity relationships.

**Tasks:**

1. **Create a `DocumentStatus` enum** inside the `entity` package:
   - Values: `DRAFT`, `PENDING`, `APPROVED`, `REJECTED`

2. **Implement `entity/Document.java`**
   - Fields: `Long id`, `String title`, `String description`, `String filePath`, `String fileType`, `DocumentStatus status`
   - `@ManyToOne @JoinColumn(name = "uploaded_by")` → `User uploadedBy`
   - `LocalDateTime createdAt`, `LocalDateTime updatedAt`
   - Add `@PrePersist` to auto-set `createdAt` and `@PreUpdate` to auto-set `updatedAt`

3. **Implement `entity/Approval.java`**
   - Fields: `Long id`, `String comments`, `LocalDateTime actionDate`
   - `@ManyToOne @JoinColumn(name = "document_id")` → `Document document`
   - `@ManyToOne @JoinColumn(name = "approver_id")` → `User approver`
   - `DocumentStatus status` (reuse the same enum)

4. **Implement `entity/Notification.java`**
   - Fields: `Long id`, `String message`, `boolean isRead`, `LocalDateTime createdAt`
   - `@ManyToOne @JoinColumn(name = "user_id")` → `User user`

**Files to work on:**
- `Backend/.../entity/Document.java`
- `Backend/.../entity/Approval.java`
- `Backend/.../entity/Notification.java`
- Create: `Backend/.../entity/DocumentStatus.java` (enum)

---

### ✅ Day 4 — Repositories & DTOs

**Goal:** Implement all Spring Data JPA repositories and DTO classes for request/response transfer.

**What is a repository?** An interface extending `JpaRepository<Entity, ID>`. Spring auto-generates SQL queries.

**Tasks:**

1. **Implement all 5 repositories** — each extends `JpaRepository`:

   - `UserRepository.java`:
     ```java
     Optional<User> findByEmail(String email);
     Optional<User> findByUsername(String username);
     boolean existsByEmail(String email);
     ```

   - `DocumentRepository.java`:
     ```java
     List<Document> findByUploadedBy(User user);
     List<Document> findByStatus(DocumentStatus status);
     ```

   - `ApprovalRepository.java`:
     ```java
     List<Approval> findByDocument(Document document);
     List<Approval> findByApprover(User approver);
     Optional<Approval> findByDocumentAndApprover(Document doc, User approver);
     ```

   - `DepartmentRepository.java`: just extend `JpaRepository<Department, Long>`

   - `NotificationRepository.java`:
     ```java
     List<Notification> findByUserAndIsReadFalse(User user);
     List<Notification> findByUser(User user);
     ```

2. **Implement all DTOs** — plain Java classes with Lombok `@Data`:

   - `LoginRequest.java`: `String email`, `String password`
   - `RegisterRequest.java`: `String username`, `String email`, `String password`, `String roleName`, `Long departmentId`
   - `DocumentRequest.java`: `String title`, `String description` *(file will come via multipart)*
   - `DocumentResponse.java`: `Long id`, `String title`, `String description`, `String status`, `String uploadedByUsername`, `String filePath`, `String createdAt`
   - `ApprovalRequest.java`: `Long documentId`, `String status`, `String comments`
   - `DepartmentRequest.java`: `String name`, `String description`
   - `UserRequest.java`: `String username`, `String email`, `Long roleId`, `Long departmentId`
   - `UserResponse.java`: `Long id`, `String username`, `String email`, `String role`, `String department`
   - `NotificationResponse.java`: `Long id`, `String message`, `boolean isRead`, `String createdAt`

**Files to work on:**
- All 5 files in `Backend/.../repository/`
- All 9 files in `Backend/.../dto/`

---

### ✅ Day 5 — JWT Security Setup

**Goal:** Implement JWT-based stateless authentication — the most critical infrastructure piece.

**Why JWT?** The backend needs to issue a token on login; Angular sends it with every request via Authorization header.

**First add the JWT dependency to `pom.xml`:**
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Tasks:**

1. **Implement `security/JwtService.java`** — annotate with `@Service`
   - Add to `application.properties`: `jwt.secret=yourBase64SecretKeyHere` and `jwt.expiration=86400000`
   - Method `generateToken(UserDetails userDetails)` → returns JWT string
   - Method `extractUsername(String token)` → returns email from token
   - Method `isTokenValid(String token, UserDetails userDetails)` → returns boolean
   - Use `Jwts.builder()` / `Jwts.parserBuilder()` from the jjwt library

2. **Implement `security/CustomUserDetailsService.java`** — implements `UserDetailsService`
   - Inject `UserRepository`
   - Override `loadUserByUsername(String email)`:
     - Find user by email using `userRepository.findByEmail(email)`
     - Return a `org.springframework.security.core.userdetails.User` built from the entity
     - Throw `UsernameNotFoundException` if not found

3. **Implement `security/JwtAuthenticationFilter.java`** — extends `OncePerRequestFilter`
   - Inject `JwtService` and `CustomUserDetailsService`
   - In `doFilterInternal()`:
     - Read the `Authorization` header
     - If it starts with "Bearer ", extract the token
     - Validate the token and set `SecurityContextHolder` authentication

**Files to work on:**
- `Backend/.../security/JwtService.java`
- `Backend/.../security/CustomUserDetailsService.java`
- `Backend/.../security/JwtAuthenticationFilter.java`
- `Backend/.../pom.xml` (add dependencies)
- `Backend/.../src/main/resources/application.properties`

---

### ✅ Day 6 — Security Config & Exception Handling

**Goal:** Wire up Spring Security configuration and global error handling.

**Tasks:**

1. **Create `config/SecurityConfig.java`** *(new file in the empty `config/` folder)*
   - Annotate with `@Configuration @EnableWebSecurity`
   - Define a `SecurityFilterChain` bean:
     - Disable CSRF (REST API doesn't need it)
     - Permit `/api/auth/**` without authentication
     - Require authentication for all other requests
     - Add `JwtAuthenticationFilter` before `UsernamePasswordAuthenticationFilter`
     - Configure CORS to allow `http://localhost:4200`
   - Define `BCryptPasswordEncoder` bean
   - Define `AuthenticationManager` bean

2. **Implement `exception/ResourceNotFoundException.java`**
   - Extend `RuntimeException`
   - Constructor: `ResourceNotFoundException(String message)`

3. **Implement `exception/BadRequestException.java`**
   - Extend `RuntimeException`
   - Constructor: `BadRequestException(String message)`

4. **Implement `exception/GlobalExceptionHandler.java`**
   - Annotate with `@RestControllerAdvice`
   - Handle `ResourceNotFoundException` → return `404` with error message
   - Handle `BadRequestException` → return `400` with error message
   - Handle generic `Exception` → return `500`
   - Return responses as `Map<String, String>` with keys `"error"` and `"message"`

5. **Implement utility helpers:**
   - `util/ValidationUtil.java`: static method `isValidEmail(String email)` using regex
   - `util/FileUploadUtil.java`: static method `saveFile(String uploadDir, String filename, MultipartFile file)` that writes file to disk and returns the saved file path

**Files to work on:**
- `Backend/.../config/SecurityConfig.java` *(create new file)*
- `Backend/.../exception/ResourceNotFoundException.java`
- `Backend/.../exception/BadRequestException.java`
- `Backend/.../exception/GlobalExceptionHandler.java`
- `Backend/.../util/ValidationUtil.java`
- `Backend/.../util/FileUploadUtil.java`

---

## Week 2 — Backend Services & REST APIs (Days 7–13)

---

### ✅ Day 7 — Auth Service & Auth Controller

**Goal:** Implement user registration and login endpoints — the entry point to the entire system.

**Tasks:**

1. **Implement `services/AuthService.java`** — annotate with `@Service`
   - Inject: `UserRepository`, `BCryptPasswordEncoder`, `JwtService`, `AuthenticationManager`, `RoleRepository` *(you'll need to create `RoleRepository` — extends `JpaRepository<Role, Long>` and add `Optional<Role> findByName(String name)`)*
   - Method `register(RegisterRequest request)` → `Map<String, String>`:
     - Check if email already exists (`userRepository.existsByEmail`) → throw `BadRequestException` if so
     - Find role by name from request
     - Hash password using `passwordEncoder.encode()`
     - Save new `User` entity
     - Return `{"message": "User registered successfully"}`
   - Method `login(LoginRequest request)` → `Map<String, String>`:
     - Use `authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(...))`
     - Load user details via `CustomUserDetailsService`
     - Generate JWT using `jwtService.generateToken()`
     - Return `{"token": "...", "role": "...", "username": "..."}`

2. **Implement `controller/AuthController.java`** — annotate with `@RestController @RequestMapping("/api/auth")`
   - Inject `AuthService`
   - `POST /api/auth/register` → calls `authService.register()` → returns `ResponseEntity`
   - `POST /api/auth/login` → calls `authService.login()` → returns `ResponseEntity` with token

**Files to work on:**
- `Backend/.../services/AuthService.java`
- `Backend/.../controller/AuthController.java`
- Create: `Backend/.../repository/RoleRepository.java`

**Test with Postman:**
- POST `http://localhost:8080/api/auth/register` with JSON body
- POST `http://localhost:8080/api/auth/login` — should return JWT token

---

### ✅ Day 8 — User Service & User Controller

**Goal:** Implement user profile and admin user management APIs.

**Tasks:**

1. **Implement `services/UserService.java`** — annotate with `@Service`
   - Inject: `UserRepository`
   - Method `getAllUsers()` → `List<UserResponse>` — map each `User` entity to `UserResponse` DTO
   - Method `getUserById(Long id)` → `UserResponse` — throw `ResourceNotFoundException` if not found
   - Method `updateUser(Long id, UserRequest request)` → `UserResponse` — find by id, update fields, save
   - Method `deleteUser(Long id)` → void — find by id, delete

2. **Implement `controller/UserController.java`** — `@RestController @RequestMapping("/api/users")`
   - Inject `UserService`
   - `GET /api/users` → `getAllUsers()` — protected (ADMIN only)
   - `GET /api/users/{id}` → `getUserById(id)`
   - `PUT /api/users/{id}` → `updateUser(id, request)`
   - `DELETE /api/users/{id}` → `deleteUser(id)` — ADMIN only

3. **Implement `controller/AdminController.java`** — `@RestController @RequestMapping("/api/admin")`
   - `GET /api/admin/users` → delegates to `userService.getAllUsers()`
   - `GET /api/admin/dashboard-stats` → returns counts: total users, total documents, pending approvals *(inject DocumentRepository and ApprovalRepository for counts)*

**Files to work on:**
- `Backend/.../services/UserService.java`
- `Backend/.../controller/UserController.java`
- `Backend/.../controller/AdminController.java`

---

### ✅ Day 9 — Department Service & Controller

**Goal:** Implement department CRUD — departments group users and route documents.

**Tasks:**

1. **Implement `services/DepartmentService.java`** — `@Service`
   - Inject: `DepartmentRepository`
   - `getAllDepartments()` → `List<Department>`
   - `getDepartmentById(Long id)` → `Department` (throw `ResourceNotFoundException` if not found)
   - `createDepartment(DepartmentRequest request)` → `Department`
   - `updateDepartment(Long id, DepartmentRequest request)` → `Department`
   - `deleteDepartment(Long id)` → void

2. **Implement `controller/DepartmentController.java`** — `@RestController @RequestMapping("/api/departments")`
   - `GET /api/departments` → get all (public/authenticated)
   - `GET /api/departments/{id}` → get one
   - `POST /api/departments` → create (ADMIN only)
   - `PUT /api/departments/{id}` → update (ADMIN only)
   - `DELETE /api/departments/{id}` → delete (ADMIN only)

**Files to work on:**
- `Backend/.../services/DepartmentService.java`
- `Backend/.../controller/DepartmentController.java`

---

### ✅ Day 10 — Document Service (Upload & Fetch)

**Goal:** Implement document upload and retrieval — the core feature of the system.

**Tasks:**

1. **Implement `services/DocumentService.java`** — `@Service`
   - Inject: `DocumentRepository`, `UserRepository`, `FileUploadUtil`
   - Add to `application.properties`: `file.upload.dir=uploads/`

   - `uploadDocument(DocumentRequest request, MultipartFile file, String uploaderEmail)` → `DocumentResponse`:
     - Find the uploading user by email
     - Use `FileUploadUtil.saveFile()` to save the file to disk
     - Create a `Document` entity with status `PENDING`
     - Save and return as `DocumentResponse`

   - `getAllDocuments()` → `List<DocumentResponse>` — for ADMIN

   - `getDocumentsByUser(String email)` → `List<DocumentResponse>` — for logged-in user

   - `getDocumentById(Long id)` → `DocumentResponse`

   - `getDocumentsByStatus(DocumentStatus status)` → `List<DocumentResponse>`

   - `deleteDocument(Long id)` → void

2. **Implement `controller/DocumentController.java`** — `@RestController @RequestMapping("/api/documents")`
   - `POST /api/documents/upload` → accepts `@RequestPart DocumentRequest` + `@RequestPart MultipartFile file`
   - `GET /api/documents` → get all (ADMIN)
   - `GET /api/documents/my` → get current user's documents (use `@AuthenticationPrincipal`)
   - `GET /api/documents/{id}` → get one
   - `GET /api/documents/status/{status}` → filter by status
   - `DELETE /api/documents/{id}` → delete

**Files to work on:**
- `Backend/.../services/DocumentService.java`
- `Backend/.../controller/DocumentController.java`

---

### ✅ Day 11 — Approval Service & Controller

**Goal:** Implement the approval workflow — approvers can approve or reject documents.

**Tasks:**

1. **Implement `services/ApprovalService.java`** — `@Service`
   - Inject: `ApprovalRepository`, `DocumentRepository`, `UserRepository`, `NotificationService`

   - `submitForApproval(Long documentId, String approverEmail)` → `Approval`:
     - Find document, set status to `PENDING`
     - Create an `Approval` record with `PENDING` status
     - Trigger a notification to the approver
     - Save and return

   - `processApproval(ApprovalRequest request, String approverEmail)` → `Approval`:
     - Find the `Approval` record for the document + approver
     - Update `Approval.status` to APPROVED or REJECTED
     - Update `Document.status` accordingly
     - Trigger a notification to the document uploader
     - Save and return

   - `getPendingApprovalsByApprover(String approverEmail)` → `List<Approval>`

   - `getApprovalHistoryForDocument(Long documentId)` → `List<Approval>`

2. **Implement `controller/ApprovalController.java`** — `@RestController @RequestMapping("/api/approvals")`
   - `POST /api/approvals/submit` → submit document for approval
   - `POST /api/approvals/process` → approve or reject (`ApprovalRequest` body)
   - `GET /api/approvals/pending` → get pending approvals for current approver
   - `GET /api/approvals/document/{documentId}` → get approval history

**Files to work on:**
- `Backend/.../services/ApprovalService.java`
- `Backend/.../controller/ApprovalController.java`

---

### ✅ Day 12 — Notification Service & Report Service

**Goal:** Implement in-app notifications and reporting endpoints.

**Tasks:**

1. **Implement `services/NotificationService.java`** — `@Service`
   - Inject: `NotificationRepository`, `UserRepository`
   - `createNotification(User user, String message)` → `Notification` — saves a new notification
   - `getUserNotifications(String email)` → `List<NotificationResponse>` — all notifications for user
   - `getUnreadNotifications(String email)` → `List<NotificationResponse>` — only unread
   - `markAsRead(Long notificationId)` → void — find by id, set `isRead = true`, save
   - `markAllAsRead(String email)` → void — find all unread for user, mark all as read

2. **Implement `controller/NotificationController.java`** — `@RestController @RequestMapping("/api/notifications")`
   - `GET /api/notifications` → all notifications for current user
   - `GET /api/notifications/unread` → unread count and list
   - `PUT /api/notifications/{id}/read` → mark one as read
   - `PUT /api/notifications/read-all` → mark all as read

3. **Implement `services/ReportService.java`** — `@Service`
   - Inject: `DocumentRepository`, `ApprovalRepository`, `UserRepository`
   - `getSummaryReport()` → `Map<String, Object>` with:
     - Total documents by status (counts)
     - Total users, total departments
     - Approvals this month
   - `getDocumentStatusBreakdown()` → `Map<String, Long>` (status → count)

4. **Implement `controller/ReportController.java`** — `@RestController @RequestMapping("/api/reports")`
   - `GET /api/reports/summary` → calls `reportService.getSummaryReport()`
   - `GET /api/reports/document-status` → calls `reportService.getDocumentStatusBreakdown()`

**Files to work on:**
- `Backend/.../services/NotificationService.java`
- `Backend/.../controller/NotificationController.java`
- `Backend/.../services/ReportService.java`
- `Backend/.../controller/ReportController.java`

---

### ✅ Day 13 — Backend Testing & Postman Collection

**Goal:** Test all APIs end-to-end and ensure the backend is stable.

**Tasks:**

1. **Create a Postman collection** with requests for every endpoint:
   - Auth: register, login
   - Documents: upload, get all, get by user, get by status, delete
   - Approvals: submit, process, get pending, get history
   - Notifications: get all, mark read
   - Reports: summary, document-status breakdown
   - Departments: CRUD
   - Users: CRUD

2. **Fix any issues found** — common problems to watch for:
   - CORS errors → check `SecurityConfig.java` CORS configuration
   - 403 Forbidden → check endpoint security rules in `SecurityConfig`
   - File upload errors → check `FileUploadUtil.java` and `application.properties` upload dir
   - Entity relationships not loading → check if `FetchType.LAZY` vs `EAGER` is set correctly

3. **Write a basic unit test** in `src/test/` for `AuthService`:
   - Test that `register()` throws `BadRequestException` when email already exists
   - Use `@ExtendWith(MockitoExtension.class)` and `@Mock` for `UserRepository`

**Files to work on:**
- All controller and service files (bug fixes)
- `Backend/.../src/test/java/.../AuthServiceTest.java` *(create new)*

---

## Week 3 — Angular Frontend Core (Days 14–20)

---

### ✅ Day 14 — Angular Models, Services & HTTP Setup

**Goal:** Define TypeScript models and implement Angular HTTP services that talk to the backend.

**Tasks:**

1. **Implement TypeScript models in `src/app/models/`:**

   - `user.ts`:
     ```typescript
     export interface User { id: number; username: string; email: string; role: string; department: string; }
     export interface LoginRequest { email: string; password: string; }
     export interface RegisterRequest { username: string; email: string; password: string; roleName: string; departmentId: number; }
     export interface AuthResponse { token: string; role: string; username: string; }
     ```

   - `document.ts`:
     ```typescript
     export interface Document { id: number; title: string; description: string; status: string; uploadedByUsername: string; filePath: string; createdAt: string; }
     export interface DocumentRequest { title: string; description: string; }
     ```

   - `approval.ts`:
     ```typescript
     export interface Approval { id: number; documentId: number; approverUsername: string; status: string; comments: string; actionDate: string; }
     export interface ApprovalRequest { documentId: number; status: string; comments: string; }
     ```

   - `notification.ts`:
     ```typescript
     export interface Notification { id: number; message: string; isRead: boolean; createdAt: string; }
     ```

2. **Implement `services/auth.ts`** — rename class to `AuthService`:
   - Inject `HttpClient`
   - `login(req: LoginRequest)` → `Observable<AuthResponse>` — POST `/api/auth/login`
   - `register(req: RegisterRequest)` → `Observable<any>` — POST `/api/auth/register`
   - `logout()` → clears localStorage token
   - `getToken()` → returns token from localStorage
   - `isLoggedIn()` → returns `!!this.getToken()`
   - `getUserRole()` → returns role stored in localStorage

3. **Implement `services/document.ts`** — rename class to `DocumentService`:
   - `uploadDocument(title: string, description: string, file: File)` → `Observable<Document>` — POST with `FormData`
   - `getMyDocuments()` → `Observable<Document[]>` — GET `/api/documents/my`
   - `getAllDocuments()` → `Observable<Document[]>` — GET `/api/documents`
   - `deleteDocument(id: number)` → `Observable<any>`

4. **Implement `services/approval.ts`** → rename to `ApprovalService`:
   - `getPendingApprovals()` → `Observable<Approval[]>`
   - `processApproval(req: ApprovalRequest)` → `Observable<Approval>`

5. **Implement `services/notification.ts`** → rename to `NotificationService`:
   - `getNotifications()` → `Observable<Notification[]>`
   - `markAsRead(id: number)` → `Observable<any>`
   - `markAllAsRead()` → `Observable<any>`

6. **Add `environment.ts`** in `src/environments/`:
   ```typescript
   export const environment = { apiUrl: 'http://localhost:8080' };
   ```
   Use `environment.apiUrl` as the base URL in all services.

**Files to work on:**
- `Frontend/.../src/app/models/user.ts`
- `Frontend/.../src/app/models/document.ts`
- `Frontend/.../src/app/models/approval.ts`
- `Frontend/.../src/app/models/notification.ts`
- `Frontend/.../src/app/services/auth.ts`
- `Frontend/.../src/app/services/document.ts`
- `Frontend/.../src/app/services/approval.ts`
- `Frontend/.../src/app/services/notification.ts`
- Create: `Frontend/.../src/environments/environment.ts`

---

### ✅ Day 15 — HTTP Interceptors & Route Guards

**Goal:** Automatically attach JWT to every request and protect routes from unauthorized access.

**Tasks:**

1. **Implement `interceptors/auth-interceptor.ts`** — rename class to `AuthInterceptor`:
   - Implements `HttpInterceptor`
   - In `intercept()`:
     - Get token using `authService.getToken()`
     - If token exists, clone the request and add `Authorization: Bearer <token>` header
     - Pass the modified request through

2. **Implement `interceptors/error-interceptor.ts`** — rename to `ErrorInterceptor`:
   - Implements `HttpInterceptor`
   - In `intercept()`:
     - Use `catchError` from RxJS
     - If error status is `401` → call `authService.logout()` and navigate to `/login`
     - If error status is `403` → show an alert or navigate to an unauthorized page
     - Re-throw other errors

3. **Implement `guards/auth-guard.ts`** — rename to `AuthGuard`:
   - Implements `CanActivate`
   - Check `authService.isLoggedIn()` → if true, allow; if false, redirect to `/login`

4. **Implement `guards/admin-guard.ts`** → rename to `AdminGuard`:
   - Check `authService.getUserRole() === 'ADMIN'`

5. **Implement `guards/role-guard.ts`** → rename to `RoleGuard`:
   - Implement `CanActivate` with `ActivatedRouteSnapshot`
   - Read `data['roles']` from route config
   - Check if current user's role is in the allowed roles array

6. **Register interceptors in `app.config.ts`:**
   ```typescript
   providers: [
     provideHttpClient(withInterceptorsFromDi()),
     { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
     { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
   ]
   ```

**Files to work on:**
- `Frontend/.../src/app/interceptors/auth-interceptor.ts`
- `Frontend/.../src/app/interceptors/error-interceptor.ts`
- `Frontend/.../src/app/guards/auth-guard.ts`
- `Frontend/.../src/app/guards/admin-guard.ts`
- `Frontend/.../src/app/guards/role-guard.ts`
- `Frontend/.../src/app/app.config.ts`

---

### ✅ Day 16 — Login & Register Components

**Goal:** Build the authentication UI — the first screens a user sees.

**Tasks:**

1. **Implement `components/login/login.ts`** — rename class to `LoginComponent`:
   - Inject `AuthService`, `Router`, `FormBuilder`
   - Create a `FormGroup` with `email` and `password` fields using `Validators.required` and `Validators.email`
   - `onSubmit()` method:
     - Call `authService.login()`
     - On success: save token/role/username to `localStorage`, navigate to `/dashboard`
     - On error: set an error message string to display in the template

2. **Implement `components/login/login.html`:**
   - A centered card layout with the app logo/title
   - Email input field bound to `loginForm.get('email')`
   - Password input with show/hide toggle
   - Error message display with `*ngIf`
   - Submit button (disable when form is invalid or loading)
   - Link to register page

3. **Implement `components/register/register.ts`** — rename to `RegisterComponent`:
   - Form fields: `username`, `email`, `password`, `confirmPassword`, `departmentId`
   - Inject `AuthService`, `DepartmentService` (create this service — GET `/api/departments`)
   - `ngOnInit()`: load departments for the dropdown
   - Validate password match before submit
   - `onSubmit()`: call `authService.register()`, on success navigate to `/login`

4. **Implement `components/register/register.html`:**
   - Form with all fields
   - Department dropdown populated from API
   - Password match validation error display
   - Link back to login

**Files to work on:**
- `Frontend/.../components/login/login.ts`
- `Frontend/.../components/login/login.html`
- `Frontend/.../components/login/login.css`
- `Frontend/.../components/register/register.ts`
- `Frontend/.../components/register/register.html`
- `Frontend/.../components/register/register.css`
- Create: `Frontend/.../services/department.ts` (new service for department API calls)

---

### ✅ Day 17 — Navbar, Sidebar & App Routing

**Goal:** Build the navigation shell and configure all app routes.

**Tasks:**

1. **Implement `components/navbar/navbar.ts`** — rename to `NavbarComponent`:
   - Inject `AuthService`, `NotificationService`, `Router`
   - `username` property set from localStorage
   - `unreadCount` for notification badge — call `notificationService.getNotifications()` to count unread
   - `logout()` method — calls `authService.logout()`, navigates to `/login`

2. **Implement `components/navbar/navbar.html`:**
   - App title/logo on the left
   - On the right: notification bell with unread badge count, username display, logout button
   - Use `*ngIf` to show only when logged in

3. **Implement `components/sidebar/sidebar.ts`** — rename to `SidebarComponent`:
   - Properties: `isAdmin` (checks if role is ADMIN), `isApprover` (role is APPROVER)
   - Navigation links array with `{ label, route, icon, roles }` structure

4. **Implement `components/sidebar/sidebar.html`:**
   - Vertical nav links: Dashboard, My Documents, Upload Document, Pending Approvals, Notifications, Reports (admin only), Users (admin only)
   - Use `routerLinkActive="active"` for active state highlighting

5. **Configure `app.routes.ts`** with all application routes:
   ```typescript
   { path: '', redirectTo: 'login', pathMatch: 'full' },
   { path: 'login', component: LoginComponent },
   { path: 'register', component: RegisterComponent },
   { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
   { path: 'documents', component: DocumentComponent, canActivate: [AuthGuard] },
   { path: 'approvals', component: ApprovalComponent, canActivate: [AuthGuard] },
   { path: 'notifications', component: NotificationComponent, canActivate: [AuthGuard] },
   { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
   { path: '**', redirectTo: 'login' }
   ```

6. **Update `app.html`** to include `<app-navbar>`, `<app-sidebar>`, and `<router-outlet>`.

**Files to work on:**
- `Frontend/.../components/navbar/navbar.ts` & `.html` & `.css`
- `Frontend/.../components/sidebar/sidebar.ts` & `.html` & `.css`
- `Frontend/.../src/app/app.routes.ts`
- `Frontend/.../src/app/app.html`
- `Frontend/.../src/app/app.ts`

---

### ✅ Day 18 — Dashboard Component

**Goal:** Build the main dashboard — the landing page after login with key stats.

**Tasks:**

1. **Implement `components/dashboard/dashboard.ts`** — rename to `DashboardComponent`:
   - Inject `DocumentService`, `ApprovalService`, `NotificationService`, `AuthService`
   - Properties: `totalDocuments`, `pendingDocuments`, `approvedDocuments`, `rejectedDocuments`, `recentDocuments: Document[]`, `pendingApprovals: Approval[]`
   - `ngOnInit()`: load all data by calling services
   - `isAdmin` computed property from `authService.getUserRole()`

2. **Implement `components/dashboard/dashboard.html`:**
   - Top stats cards row: "Total Documents", "Pending", "Approved", "Rejected" — each showing a count
   - Section "Recent Documents" — table with last 5 documents showing title, status (with colored badge), date
   - If `isAdmin`, show an additional "Pending Approvals" table
   - Use `*ngFor` for lists and `[ngClass]` for status badge colors (green=approved, red=rejected, yellow=pending)

3. **Implement `components/dashboard/dashboard.css`:**
   - Stats card styles: white background, shadow, rounded corners, colored icon
   - Status badge styles: color coded pill badges

**Files to work on:**
- `Frontend/.../components/dashboard/dashboard.ts`
- `Frontend/.../components/dashboard/dashboard.html`
- `Frontend/.../components/dashboard/dashboard.css`

---

### ✅ Day 19 — Document Component (Upload & List)

**Goal:** Build the document management page — upload documents and view the list.

**Tasks:**

1. **Implement `components/document/document.ts`** — rename to `DocumentComponent`:
   - Inject `DocumentService`, `AuthService`
   - Properties: `documents: Document[]`, `showUploadForm: boolean`, `selectedFile: File | null`, `uploadForm: FormGroup`
   - `ngOnInit()`: call `loadDocuments()`
   - `loadDocuments()`: calls `documentService.getMyDocuments()` (or `getAllDocuments()` for admin)
   - `onFileSelected(event: Event)`: extracts file from the input event, sets `selectedFile`
   - `uploadDocument()`: creates `FormData`, calls `documentService.uploadDocument()`, reloads list on success
   - `deleteDocument(id: number)`: confirms, calls service, removes from list
   - `getStatusClass(status: string)`: returns CSS class string based on status

2. **Implement `components/document/document.html`:**
   - "Upload Document" button that toggles `showUploadForm`
   - Upload form (shown when `showUploadForm` is true):
     - Title input, Description textarea, File input (`accept=".pdf,.doc,.docx"`)
     - Submit and Cancel buttons
   - Documents table with columns: Title, Description, Status (badge), Upload Date, Actions (Download/Delete)
   - Empty state message when no documents

3. **`components/document/document.css`:** Style the upload form panel and table.

**Files to work on:**
- `Frontend/.../components/document/document.ts`
- `Frontend/.../components/document/document.html`
- `Frontend/.../components/document/document.css`

---

### ✅ Day 20 — Approval Component

**Goal:** Build the approval management page for approvers to review and act on documents.

**Tasks:**

1. **Implement `components/approval/approval.ts`** — rename to `ApprovalComponent`:
   - Inject `ApprovalService`, `AuthService`
   - Properties: `pendingApprovals: Approval[]`, `selectedApproval: Approval | null`, `comments: string`
   - `ngOnInit()`: call `approvalService.getPendingApprovals()`
   - `openApprovalModal(approval: Approval)`: sets `selectedApproval` and shows modal
   - `approve(documentId: number)`: calls `approvalService.processApproval()` with status `APPROVED`
   - `reject(documentId: number)`: calls `approvalService.processApproval()` with status `REJECTED`
   - After action: remove from list and show a success message

2. **Implement `components/approval/approval.html`:**
   - Table showing pending documents: Document Title, Submitted By, Submitted Date
   - "Review" button per row that opens a modal
   - Modal dialog showing document details, comments textarea, Approve (green) and Reject (red) buttons
   - Empty state message for "No pending approvals"

**Files to work on:**
- `Frontend/.../components/approval/approval.ts`
- `Frontend/.../components/approval/approval.html`
- `Frontend/.../components/approval/approval.css`

---

## Week 4 — Polish, Admin Features & Final Testing (Days 21–30)

---

### ✅ Day 21 — Notification Component & Profile Component

**Goal:** Build the notifications page and user profile/settings page.

**Tasks:**

1. **Implement `components/notification/notification.ts`** — rename to `NotificationComponent`:
   - Inject `NotificationService`
   - Properties: `notifications: Notification[]`, `unreadCount: number`
   - `ngOnInit()`: load all notifications
   - `markAsRead(id: number)`: calls service, updates local list
   - `markAllAsRead()`: calls service, marks all as read in local list

2. **Implement `components/notification/notification.html`:**
   - "Mark All as Read" button (disabled if none unread)
   - Notification list: each item shows message, timestamp, and unread indicator dot
   - Unread items have a highlighted background; read items are normal
   - `*ngFor` over notifications, `[ngClass]` for read/unread styling

3. **Implement `components/profile/profile.ts`** — rename to `ProfileComponent`:
   - Inject `UserService` (add `getUserById()` method in the service), `AuthService`
   - Load current user's info on init
   - Form for updating username/email
   - `updateProfile()` method that calls user service

4. **Implement `components/profile/profile.html`:**
   - User avatar placeholder (initials-based circle)
   - Display current role and department (read-only)
   - Editable fields for username and email
   - Save button

**Files to work on:**
- `Frontend/.../components/notification/notification.ts` & `.html` & `.css`
- `Frontend/.../components/profile/profile.ts` & `.html` & `.css`

---

### ✅ Day 22 — Admin Panel: User Management

**Goal:** Build the admin section for managing all users.

**Tasks:**

1. **Create a new component `src/app/components/admin/`** (the scaffold doesn't have this yet — create the folder and files):
   - `admin.ts` — `AdminComponent`
   - `admin.html`
   - `admin.css`

2. **Implement `AdminComponent`:**
   - Inject `UserService` (add `getAllUsers()`, `deleteUser()` to the service if not done)
   - Table of all users: Username, Email, Role, Department, Actions
   - "Delete" button per row with a confirmation dialog
   - "Change Role" button (bonus: inline dropdown to change role)

3. **Add route for admin:** In `app.routes.ts`:
   ```typescript
   { path: 'admin', component: AdminComponent, canActivate: [AuthGuard, AdminGuard] }
   ```

4. **Add "Admin" link in `sidebar.html`** — visible only when `isAdmin` is true.

**Files to work on:**
- Create: `Frontend/.../components/admin/admin.ts`, `admin.html`, `admin.css`
- `Frontend/.../src/app/app.routes.ts`
- `Frontend/.../components/sidebar/sidebar.html`

---

### ✅ Day 23 — Document Status Workflow & Submit for Approval (UI)

**Goal:** Complete the end-to-end approval flow visible from the UI — submit a document for approval directly.

**Tasks:**

1. **Add "Submit for Approval" to `document.html`:**
   - For documents in `DRAFT` or re-submit scenarios, add a "Submit for Approval" button
   - In `document.ts`, add `submitForApproval(documentId: number)` method:
     - Opens a small modal asking for the approver (dropdown of users with APPROVER role)
     - Calls `approvalService.submitForApproval(documentId, approverEmail)`
     - Reloads the document list

2. **Add approver user fetching:**
   - In `user.ts` service, add `getUsersByRole(role: string)` → GET `/api/users?role=ROLE`
   - In `UserController.java` backend, add `?role` query parameter filter to the `getAllUsers()` endpoint

3. **Add document status badge color system** consistently across all components:
   - `PENDING` → yellow/orange
   - `APPROVED` → green
   - `REJECTED` → red
   - `DRAFT` → gray

4. **Test full flow:** Upload → Submit for Approval → Approve/Reject → Check document status updated.

**Files to work on:**
- `Frontend/.../components/document/document.ts` & `.html`
- `Frontend/.../services/user.ts`
- `Backend/.../controller/UserController.java` (add role filter)

---

### ✅ Day 24 — Reports Page

**Goal:** Build a basic reports/analytics page using data from the report API.

**Tasks:**

1. **Create `src/app/components/reports/`** (new — scaffold doesn't have it):
   - `reports.ts`, `reports.html`, `reports.css`

2. **Create `services/report.ts`** (new service):
   - `getSummary()` → GET `/api/reports/summary`
   - `getDocumentStatusBreakdown()` → GET `/api/reports/document-status`

3. **Implement `ReportsComponent`:**
   - Summary stats cards: Total Documents, Total Users, Total Departments, Approvals This Month
   - Document status breakdown as a visual representation (even simple colored progress bars or a table is fine)
   - Date range note (all-time stats)

4. **Add route and sidebar link:**
   - Route: `{ path: 'reports', component: ReportsComponent, canActivate: [AuthGuard, AdminGuard] }`
   - Sidebar link (admin-only)

**Files to work on:**
- Create: `Frontend/.../components/reports/reports.ts`, `reports.html`, `reports.css`
- Create: `Frontend/.../services/report.ts`
- `Frontend/.../src/app/app.routes.ts`
- `Frontend/.../components/sidebar/sidebar.html`

---

### ✅ Day 25 — UI Styling & Responsiveness

**Goal:** Polish the overall look and feel — consistent theme, spacing, and mobile responsiveness.

**Tasks:**

1. **Define a global design system in `src/styles.css`:**
   - CSS custom properties (variables):
     ```css
     :root {
       --primary: #2563eb;
       --success: #16a34a;
       --danger: #dc2626;
       --warning: #d97706;
       --bg: #f8fafc;
       --card-bg: #ffffff;
       --text: #1e293b;
       --border: #e2e8f0;
     }
     ```
   - Base resets: box-sizing, font-family (use a Google Font like Inter or Roboto)
   - Utility classes: `.badge`, `.btn`, `.btn-primary`, `.btn-danger`, `.card`

2. **Style the app layout** in `app.css`:
   - Sidebar fixed on the left (250px width)
   - Main content area fills remaining space
   - Navbar fixed at top

3. **Make all components responsive:**
   - Use CSS Grid or Flexbox for dashboard stats cards (wrap on small screens)
   - Tables should scroll horizontally on mobile (`overflow-x: auto`)
   - Sidebar should collapse to a hamburger menu on screens < 768px

4. **Consistent component styling:**
   - All tables: same header style, row hover effect
   - All forms: same input focus styles, consistent spacing
   - All buttons: consistent sizing, hover/focus states

**Files to work on:**
- `Frontend/.../src/styles.css`
- `Frontend/.../src/app/app.css`
- All individual component CSS files

---

### ✅ Day 26 — Form Validation & User Feedback

**Goal:** Add proper validation messages and loading/success/error feedback throughout the app.

**Tasks:**

1. **Add a shared `AlertComponent` or use a simple alert service:**
   - Create `src/app/shared/alert/alert.ts` and `alert.html`
   - It shows a dismissible success (green) or error (red) message
   - Expose via a simple `AlertService` with `showSuccess(msg)` and `showError(msg)` methods

2. **Add validation to all forms:**
   - `LoginComponent`: Show "Email is required", "Invalid email format", "Password is required"
   - `RegisterComponent`: Show "Passwords do not match", "Username is required"
   - `DocumentComponent` upload form: "Title is required", "File is required"
   - `ApprovalComponent`: "Comments are required when rejecting"

3. **Add loading spinners:**
   - Each component that fetches data: show a spinner while `isLoading = true`
   - Disable submit buttons while API calls are in-flight
   - Add a simple CSS spinner or use Angular CDK

4. **Handle empty states:**
   - "No documents found" message with an upload CTA
   - "No pending approvals" with an informational message
   - "No notifications" message

**Files to work on:**
- Create: `Frontend/.../src/app/shared/alert/alert.ts` & `alert.html`
- All component `.ts` and `.html` files (add validation and loading states)

---

### ✅ Day 27 — File Download & Document Preview

**Goal:** Allow users to download uploaded documents; add a basic preview for PDFs.

**Tasks:**

1. **Backend — Add file serving endpoint:**
   In `DocumentController.java`, add:
   ```
   GET /api/documents/{id}/download
   ```
   - Read the file from `filePath` stored in the `Document` entity
   - Return as `ResponseEntity<Resource>` with appropriate `Content-Type` and `Content-Disposition: attachment` headers
   - Use `org.springframework.core.io.Resource` and `UrlResource`

2. **Backend — Add endpoint for viewing (inline):**
   ```
   GET /api/documents/{id}/view
   ```
   - Same as download but `Content-Disposition: inline` (browser opens instead of downloading)

3. **Frontend — Wire up download button:**
   - In `DocumentService`, add `downloadDocument(id: number)` → calls the download endpoint and triggers browser download
   - In `document.html`, "Download" button in the Actions column calls this method

4. **Frontend — Document detail modal (bonus):**
   - Clicking the document title opens a modal
   - Shows full document details (title, description, status, submitted by, history)
   - For PDFs: embed an `<iframe>` pointing to the view endpoint

**Files to work on:**
- `Backend/.../controller/DocumentController.java`
- `Frontend/.../services/document.ts`
- `Frontend/.../components/document/document.ts` & `.html`

---

### ✅ Day 28 — Security Hardening & Error Handling Polish

**Goal:** Tighten security rules and ensure all edge cases are handled gracefully.

**Tasks:**

1. **Backend security review:**
   - Ensure users can only access their OWN documents (not other users' documents) — check service layer
   - Only ADMIN can delete users or access all documents
   - Approvers can only process approvals assigned to them — validate in `ApprovalService`
   - Add `@PreAuthorize("hasRole('ADMIN')")` annotations where needed in controllers
   - Add Spring Security's method-level security: `@EnableMethodSecurity` in `SecurityConfig`

2. **Input validation on backend DTOs:**
   - Add `@NotBlank`, `@Email`, `@Size` annotations from `jakarta.validation.constraints` to all DTO fields
   - Ensure `@Valid` is used on `@RequestBody` in controllers
   - The `GlobalExceptionHandler` should catch `MethodArgumentNotValidException` and return field-level error messages

3. **Frontend error handling:**
   - In `ErrorInterceptor`: handle 500 errors with a generic "Something went wrong" alert
   - In components: show specific messages from API error responses (extract from `error.error.message`)
   - Prevent double-submission by disabling buttons after first click

4. **Token expiry handling:**
   - In `AuthService.isLoggedIn()`: decode the JWT token and check the `exp` claim
   - If expired, clear localStorage and redirect to login

**Files to work on:**
- `Backend/.../controller/*.java` (add `@PreAuthorize` annotations)
- `Backend/.../dto/*.java` (add validation annotations)
- `Backend/.../config/SecurityConfig.java` (enable method security)
- `Backend/.../exception/GlobalExceptionHandler.java` (handle validation errors)
- `Frontend/.../interceptors/error-interceptor.ts`
- `Frontend/.../services/auth.ts`

---

### ✅ Day 29 — End-to-End Testing & Bug Fixing

**Goal:** Run the full application and test every user journey end-to-end.

**Tasks:**

1. **Test all user roles manually:**

   **As Admin:**
   - Register/Login → view dashboard stats
   - Manage departments (create, edit, delete)
   - View all documents
   - View all users, delete a user
   - View reports page

   **As Regular User:**
   - Register → Login → dashboard
   - Upload a document
   - Submit document for approval
   - View notifications when approval is processed
   - View document status changes

   **As Approver:**
   - Login → see pending approvals on dashboard
   - Navigate to Approvals page → approve one, reject one with comments
   - Verify notifications are created for the document uploader

2. **Bug fix list** — document and fix any bugs found during testing

3. **Check console errors** — fix all TypeScript errors and Angular console warnings

4. **Performance check:**
   - Ensure no duplicate API calls on page load
   - Use `ngOnDestroy` and unsubscribe from Observables to prevent memory leaks

**Files to work on:**
- Any file with bugs found during testing

---

### ✅ Day 30 — Final Cleanup, Documentation & Submission

**Goal:** Clean up the codebase, write documentation, and prepare for submission/demo.

**Tasks:**

1. **Code cleanup:**
   - Remove all `console.log()` statements from Angular code
   - Remove unused imports in all files
   - Ensure consistent code formatting (run Prettier on Angular: `npx prettier --write src/`)
   - Add comments to complex methods in backend services

2. **Update `Database/dbsql.sql`** with the final complete schema including all `CREATE TABLE` statements and any seed data (e.g., default roles: ADMIN, USER, APPROVER)

3. **Write `README.md`** at the root of the project:
   - Project description
   - Tech stack
   - How to run backend (Maven command, required env vars)
   - How to run frontend (`ng serve`)
   - How to set up the database
   - Default login credentials for testing

4. **Final demo preparation:**
   - Create 2-3 test accounts (Admin, Approver, Regular User) in the database
   - Upload 3-4 sample documents
   - Have at least 1 approved and 1 rejected document ready to show
   - Test on a clean browser (incognito) to simulate first-time user experience

5. **Commit and push all work:**
   ```
   git add .
   git commit -m "feat: complete Digital Document Approval System implementation"
   git push origin main
   ```

**Files to work on:**
- Root `README.md` *(create)*
- `Database/dbsql.sql` (finalize)
- All files (final cleanup pass)

---

## 📊 30-Day Summary

| Days | Focus Area | Key Deliverables |
|------|-----------|-----------------|
| 1–3  | DB & Entities | Schema designed, all 6 JPA entities implemented |
| 4    | Repositories & DTOs | 5 repositories + 9 DTOs done |
| 5–6  | Security Setup | JWT auth, SecurityConfig, exception handling |
| 7–9  | Auth & Core APIs | Auth, User, Department REST endpoints working |
| 10–12 | Business Logic APIs | Document upload, Approval workflow, Notifications, Reports |
| 13   | Backend Testing | Postman collection, unit tests, bug fixes |
| 14–15 | Angular Foundation | Models, services, interceptors, guards |
| 16–17 | Auth UI | Login, Register, Navbar, Sidebar, Routing |
| 18–20 | Core UI Pages | Dashboard, Documents, Approvals |
| 21–22 | More UI Pages | Notifications, Profile, Admin panel |
| 23–24 | Advanced Features | Submit for approval flow, Reports page |
| 25–26 | Polish | Styling, responsiveness, validation, feedback |
| 27–28 | Advanced & Security | File download, security hardening |
| 29–30 | Testing & Submission | End-to-end test, cleanup, docs, commit |

---

## 💡 Tips for the Intern

1. **Always run the backend first**, then the frontend. Backend on port `8080`, frontend on `4200`.
2. **JWT token flow:** Login → backend returns token → Angular stores in `localStorage` → `AuthInterceptor` adds it to every request header → backend `JwtAuthenticationFilter` validates it.
3. **Lombok:** Use `@Data` (generates getters/setters/equals/hashCode), `@NoArgsConstructor`, `@AllArgsConstructor` on entities and DTOs to avoid boilerplate.
4. **When stuck on a 403 error:** Check the `SecurityConfig` whitelist. The route probably needs to be permitted.
5. **When stuck on a CORS error:** Check that `SecurityConfig` allows `http://localhost:4200` as an origin.
6. **Angular standalone components:** Each component must import the modules it uses (e.g., `FormsModule`, `RouterModule`, `CommonModule`) in its own `imports: []` array since there's no `AppModule`.
7. **Commit daily** to avoid losing work: `git add . && git commit -m "day X: description"`
