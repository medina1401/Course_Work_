🟪COURSE ENROLLMENT SYSTEM🟪



⬜️OVERVIEW⬜️

The Course Enrollment System is a Java-based console application that allows users to register, log in, and manage their course enrollments.
It provides functionality for users to enroll in courses, view and drop their current courses, update their email or password, and search available courses. 
The system stores user information and course enrollments in a CSV file, making it easy to persist data between sessions.


⬜️FEATURES⬜️

1)Register: 

▪️Create a new user account with name, email, and password.

2)Login: 

▪️Log in using a valid email and password.

3)Course Enrollment: 

▪️Enroll in available courses.

4)Drop Course: 

▪️Drop an enrolled course.

5)Show My Courses: 

▪️View all courses the user is currently enrolled in.

6)Update Email: 

▪️Change the registered email address.

7)Update Password: 

▪️Change the account password after verification.

8)Search Courses: 

▪️Search available courses using keywords.

9)Delete Account: 

▪️Remove user account and associated course enrollments.

10)View All Courses: 

▪️Display a list of all available courses.

11)User Count: 

▪️View the total number of registered users.

12)Data Persistence: 

▪️All user and course data is saved in users.csv.


⬜️PREREQUIREMENTS⬜️

▪️Java Development Kit (JDK) version 8 or higher

▪️users.csv file in the root directory (created automatically if not found)


⬜️RUNNING THE APPLICATION⬜️

1)Clone or Download the project files.

2)Compile the program:

▪️javac CourseEnrollmentSystem.java

3)Run the application:

▪️java CourseEnrollmentSystem

4)Interact via console to register, login, and manage courses.


⬜️CSV FILE FORMAT⬜️

▪️Each user is stored in users.csv in the following format:

name,email,password,course1;course2;...

▪️Example:

Alice Johnson,alice@example.com,securepass123,Calculus I;Philosophy


⬜️FUNCTIONS AND METHODS⬜️

1)Registration:

▪️Validates email format.

▪️Ensures email is unique before registering.

▪️rompts user to enroll in a course after registration.

2)Login:

▪️Authenticates user by email and password.

3)Enroll in Course:

▪️Displays a list of available courses.

▪️User can choose a course to enroll in.

4)Drop a Course:

▪️Removes a selected course from the user's enrolled list.

5)Show My Courses:

▪️Displays all courses the user is currently enrolled in.

6)Change Email:

▪️Updates email after checking validity and uniqueness.

7)Change Password:

▪️Verifies current password before allowing a change.

8)Delete Account:

▪️Removes user and their courses from the system.

9)Search Courses:

▪️Filters and displays available courses by keyword.


⬜️ERROR HENDLING⬜️

▪️Invalid emails are rejected during registration or update.

▪️Duplicate emails are prevented.

▪️Invalid or non-numeric input is caught gracefully.

▪️Trying to enroll in a course already taken or drop one not enrolled in shows appropriate messages.

▪️Account operations (change, delete) are guarded with checks and confirmations.


⬜️SAMPLE TEST CASES⬜️

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
