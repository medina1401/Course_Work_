# COURSE ENROLLMENT SYSTEM

---

## PRESENTATION

[Course Enrollment System Presentation](https://www.canva.com/design/DAGlKkYJ3vk/On2gqeaPPtAbVbwUoloxHw/edit?utm_content=DAGlKkYJ3vk&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

## Student Name: Satimbaeva Medina

---

## Description

The Course Enrollment System is a Java-based console application designed to manage student registrations and course enrollments. It provides a simple and user-friendly interface where students can create accounts, securely log in, browse available courses, enroll or drop courses, and manage their account settings. The system emphasizes data persistence using a CSV file for storing user credentials and enrollment data, ensuring all changes are saved between sessions.

---

## Objectives

### Student Account Management
Efficient handling of student data:

- Register, Login, Update, Delete.

![Image alt](https://github.com/medina1401/Course_Work_/blob/main/Снимок%20экрана%202025-04-22%20в%2015.17.44.png)

---

### Course Enrollment
Smooth interaction with course data:

- Enroll, Drop, View, Search.

![Image alt](https://github.com/medina1401/Course_Work_/blob/main/Снимок%20экрана%202025-04-22%20в%2015.18.29.png)

---

### Data Persistence
Reliable storage across sessions:

- All user and enrollment data is saved in users.csv.

- CSV format ensures data is easily readable and editable.

![Image alt](https://github.com/medina1401/Course_Work_/blob/main/Снимок%20экрана%202025-04-22%20в%2015.19.03.png)

---

### Reporting and Analysis
Quick insights and stats:

- View total registered user count.

- View all available courses.

![Image alt](https://github.com/medina1401/Course_Work_/blob/main/Снимок%20экрана%202025-04-22%20в%2015.19.47.png)

---

### Error Handling
Robust handling of edge cases:

- Rejects invalid emails and inputs.

- Prevents duplicate registrations.

- Displays messages for failed operations (e.g., login failure, duplicate course).

- Prevents crashing with try-catch safeguards.

![Image alt](https://github.com/medina1401/Course_Work_/blob/main/Снимок%20экрана%202025-04-22%20в%2015.20.40.png)

---

## Project Requirement List

### 1. Create Student Account

- Register new students with name, email, and password.

- Validate email format and uniqueness.

- Store student data and course enrollments in memory and persist to users.csv.

### 2. Read/View Student Accounts

- Show total number of registered students.

- Display welcome message upon login.

- Handle login with correct email-password validation.

### 3. Update Student Info

- Allow email change with validation and duplication check.

- Enable password change after verifying the current password.

- Persist updated user data to file.

### 4. Delete Student Account

- Prompt confirmation before deleting an account.

- Remove both user data and their enrollments.

- Persist changes to users.csv.

### 5. Enroll in Course

- Display a list of available courses.

- Allow selection of a course by number.

- Prevent duplicate enrollments.

- Save enrollment data to file.

### 6. Drop Course

- Allow students to drop specific courses.

- Support dropping all enrolled courses with confirmation.

- Persist updates to file.

### 7. View Enrollments

- Display list of courses currently enrolled by the user.

- Handle empty enrollment list scenarios.

### 8. Search Courses

- Search available courses by keyword (case-insensitive).

- Display matching results clearly.

### 9. View All Available Courses

- Show complete list of predefined courses at any time.

### 10. Data Persistence

- Save all user data and course enrollments in users.csv using CSV format.

- Load user data and enrolled courses from CSV at program startup.

---

## Documentation

### Data Structures
`HashMap<String, User> users` – Stores registered users. Key = email, Value = `User` object.

`HashMap<String, List<String>> userCourses` – Maps each user's email to a list of enrolled course names.

`List<String[]> availableCourses` – Stores all available courses (each as a string array or string).

---

###  File Paths and Constants
`FILE_NAME = "users.csv"` – The path to the CSV file storing user and course registration data.

---

### Error Handling
Implemented using try-catch blocks:

 - For file operations (`IOException`)
 
 - For numeric input conversion (`safeIntInput`)
 
 - General error prevention (e.g., when reading CSV)
 
Example:

```java
try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

    // Read file

} catch (IOException e) {

    System.out.println("Error reading file: " + e.getMessage());

}
```

---

###  Switch Statements
Used for handling menus:

- `main()` – Main menu
- `userMenu()` – Menu for logged-in users
- Each case calls a specific operation (e.g., register, login, enroll in course, view courses)

---

### String Processing for CSV
#### Serialization (Object → CSV String):

`String coursesString = String.join(";", courses);`

`pw.println(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + coursesString);`

#### Deserialization (CSV String → Object):

`String[] parts = line.split(",", 4);`

`// name, email, password, courses[]`

---

### File Import/Export
#### Importing Users from CSV:

```java
try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

    String line;
    
    while ((line = br.readLine()) != null) {
    
        String[] parts = line.split(",", 4);
        
        // Parse and add to users and userCourses
        
    }
} catch (IOException e) {

    System.out.println("Error reading file: " + e.getMessage());
    
}
```

#### Exporting Users to CSV:

```java
try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

    for (String email : users.keySet()) {
    
        User user = users.get(email);
        
        List<String> courses = userCourses.getOrDefault(email, new ArrayList<>());
        
        String coursesString = String.join(";", courses);
        
        pw.println(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + coursesString);
        
    }
} catch (IOException e) {

    System.out.println("Error writing to file: " + e.getMessage());
    
}
```

The try-with-resources block ensures that files are automatically closed after reading or writing.




























