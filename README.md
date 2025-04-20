Course Enrollment System

Overview

The Course Enrollment System is a Java-based console application that allows users to register, log in, and manage their course enrollments.
It provides functionality for users to enroll in courses, view and drop their current courses, update their email or password, and search available courses. 
The system stores user information and course enrollments in a CSV file, making it easy to persist data between sessions.

Features

Register: Create a new user account with name, email, and password.
Login: Log in using a valid email and password.
Course Enrollment: Enroll in available courses.
Drop Course: Drop an enrolled course.
Show My Courses: View all courses the user is currently enrolled in.
Update Email: Change the registered email address.
Update Password: Change the account password after verification.
Search Courses: Search available courses using keywords.
Delete Account: Remove user account and associated course enrollments.
View All Courses: Display a list of all available courses.
User Count: View the total number of registered users.
Data Persistence: All user and course data is saved in users.csv.
Prerequisites

Java Development Kit (JDK) version 8 or higher
users.csv file in the root directory (created automatically if not found)
Running the Application

Clone or Download the project files.
Compile the program:
javac CourseEnrollmentSystem.java
Run the application:
java CourseEnrollmentSystem
Interact via console to register, login, and manage courses.
CSV File Format

Each user is stored in users.csv in the following format:

name,email,password,course1;course2;...
Example:

Alice Johnson,alice@example.com,securepass123,Calculus I;Philosophy
Functions and Methods

Registration
Validates email format.
Ensures email is unique before registering.
Prompts user to enroll in a course after registration.
Login
Authenticates user by email and password.
Enroll in Course
Displays a list of available courses.
User can choose a course to enroll in.
Drop a Course
Removes a selected course from the user's enrolled list.
Show My Courses
Displays all courses the user is currently enrolled in.
Change Email
Updates email after checking validity and uniqueness.
Change Password
Verifies current password before allowing a change.
Delete Account
Removes user and their courses from the system.
Search Courses
Filters and displays available courses by keyword.
Error Handling

Invalid emails are rejected during registration or update.
Duplicate emails are prevented.
Invalid or non-numeric input is caught gracefully.
Trying to enroll in a course already taken or drop one not enrolled in shows appropriate messages.
Account operations (change, delete) are guarded with checks and confirmations.

Sample Test Cases

✅ Test Case 1: Register with Valid Email
Input:

Name: Satimbaeva Medina  
Email: satimbaevamedina@gmail.com  
Password: securepass123  
Enroll in: 1 (Calculus I)  
Expected Output:

Registration successful!  
You have successfully registered for: Calculus I
❌ Test Case 2: Register with Invalid Email
Input:

Email: satimbaevamedinagmail.com  
Expected Output:

Invalid email format.
✅ Test Case 3: Login with Correct Credentials
Input:

Email: satimbaevamedina@gmail.com  
Password: securepass123  
Expected Output:

Login successful! Welcome, satimbaevamedina@gmail.com
❌ Test Case 4: Login with Incorrect Password
Expected Output:

Invalid email or password.
✅ Test Case 5: Enroll in a Course Already Taken
Expected Output:

You are already registered for this course.
✅ Test Case 6: Change Password with Correct Current Password
Expected Output:

Password changed successfully.
❌ Test Case 7: Change Password with Incorrect Current Password
Expected Output:

Incorrect current password.
✅ Test Case 8: Delete Account
Expected Output:

User account deleted successfully.
