import java.io.*;
import java.util.*;

public class CourseEnrollmentSystem {
    static final String FILE_NAME = "users.csv";
    static HashMap<String, User> users = new HashMap<>();
    static HashMap<String, List<String>> userCourses = new HashMap<>();
    static String currentUser = null;

    // Список доступных курсов
    static List<String[]> availableCourses = List.of(
            new String[]{"COM 101", "Introduction to Engineering and Computer Science"},
            new String[]{"COM 103", "Programming Languages I"},
            new String[]{"MAT 601", "Calculus I"},
            new String[]{"MAT 603", "Algebra and Geometry I"},
            new String[]{"MDE 131", "Physical Education I"},
            new String[]{"MDE 402", "Philosophy"},
            new String[]{"ELT 122", "Foreign Language I"},
            new String[]{"COM 141", "Programming Languages II"},
            new String[]{"COM 800", "Programming Language Course Work"},
            new String[]{"MAT 151", "Discrete Mathematics I"},
            new String[]{"MAT 602", "Calculus II"},
            new String[]{"MDE 136", "Physical Education II"},
            new String[]{"MDE 205", "Russian Language"},
            new String[]{"ELT 123", "Foreign Language II"}
    );

    public static void main(String[] args) {
        loadUsersFromCSV();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = safeIntInput(scanner);
            scanner.nextLine(); // Clear input

            if (choice == 1) {
                System.out.print("Enter your name: ");
                String name = scanner.nextLine().trim();

                System.out.print("Enter your email: ");
                String email = scanner.nextLine().trim();

                if (!isValidEmail(email)) {
                    System.out.println("Invalid email format.");
                    continue;
                }

                if (users.containsKey(email)) {
                    System.out.println("A user with this email already exists.");
                } else {
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    users.put(email, new User(name, email));
                    userCourses.put(email, new ArrayList<>());
                    saveUsersToCSV();
                    System.out.println("Registration successful!");

                    // Call the course registration process after registration
                    registerForCourse(scanner, email);
                }
            } else if (choice == 2) {
                System.out.print("Enter your email: ");
                String email = scanner.nextLine().trim();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                if (users.containsKey(email)) {
                    System.out.println("Login successful! Welcome, " + email);
                    currentUser = email;
                    userMenu(scanner);
                } else {
                    System.out.println("User not found.");
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    static void loadUsersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    users.put(email, new User(name, email));
                    List<String> courses = new ArrayList<>();
                    if (parts.length == 3 && !parts[2].trim().isEmpty()) {
                        courses = Arrays.asList(parts[2].split(";"));
                    }
                    userCourses.put(email, new ArrayList<>(courses));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    static void saveUsersToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String email : users.keySet()) {
                User user = users.get(email);
                List<String> courses = userCourses.getOrDefault(email, new ArrayList<>());
                String coursesString = String.join(";", courses);
                pw.println(user.getName() + "," + email + "," + coursesString);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Регистрация на курс
    static void registerForCourse(Scanner scan, String email) {
        List<String> courses = userCourses.getOrDefault(email, new ArrayList<>());

        // Печать всех доступных курсов
        System.out.println("Available Courses:");
        for (int i = 0; i < availableCourses.size(); i++) {
            String[] course = availableCourses.get(i);
            System.out.println((i + 1) + ". " + course[0] + ": " + course[1]);
        }

        // Ввод от пользователя
        while (true) {
            System.out.print("Enter the number of the course you want to register for (0 to skip): ");
            int courseChoice = safeIntInput(scan);
            if (courseChoice == 0) {
                System.out.println("Skipping course registration.");
                break;
            }

            if (courseChoice > 0 && courseChoice <= availableCourses.size()) {
                String[] selectedCourse = availableCourses.get(courseChoice - 1);
                String courseCode = selectedCourse[0];
                String courseName = selectedCourse[1];

                // Проверяем, записан ли уже пользователь на этот курс
                if (courses.contains(courseCode)) {
                    System.out.println("You are already registered for this course.");
                } else {
                    courses.add(courseCode);
                    userCourses.put(email, courses);
                    saveUsersToCSV();
                    System.out.println("You have successfully registered for: " + courseCode + ": " + courseName);
                    break;  // После успешной регистрации выходим из цикла
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void userMenu(Scanner scan) {
        while (true) {
            List<String> courses = userCourses.getOrDefault(currentUser, new ArrayList<>());
            System.out.println("\nWelcome, " + users.get(currentUser).getName() + "!");
            System.out.println("1. Enroll in a course");
            System.out.println("2. Drop a course");
            System.out.println("3. Show my courses");
            System.out.println("4. Log out");
            System.out.println("5. Change email");
            System.out.println("6. Search for a course");
            System.out.println("7. Change password");
            System.out.println("8. Delete account");
            System.out.println("9. View all available courses");

            int choice = safeIntInput(scan);
            scan.nextLine();

            switch (choice) {
                case 1:
                    registerForCourse(scan, currentUser);  // Вызываем функцию регистрации на курс
                    break;

                case 2:
                    if (courses.isEmpty()) {
                        System.out.println("You are not enrolled in any courses.");
                    } else {
                        System.out.print("Enter course name to drop: ");
                        String toRemove = scan.nextLine();
                        if (courses.remove(toRemove)) {
                            saveUsersToCSV();
                            System.out.println("You have dropped: " + toRemove);
                        } else {
                            System.out.println("You are not enrolled in that course.");
                        }
                    }
                    break;

                case 3:
                    if (courses.isEmpty()) {
                        System.out.println("You are not enrolled in any courses.");
                    } else {
                        System.out.println("Your current courses: " + courses);
                    }
                    break;

                case 4:
                    currentUser = null;
                    System.out.println("You have logged out.");
                    return;

                case 5:
                    System.out.print("Enter new email: ");
                    String newEmail = scan.nextLine().trim();
                    if (!isValidEmail(newEmail)) {
                        System.out.println("Invalid email format.");
                        break;
                    }
                    if (users.containsKey(newEmail)) {
                        System.out.println("This email is already in use.");
                    } else {
                        String password = users.get(currentUser).getPassword();
                        List<String> currentCourses = userCourses.get(currentUser);
                        String name = users.get(currentUser).getName();

                        users.remove(currentUser);
                        userCourses.remove(currentUser);

                        users.put(newEmail, new User(name, newEmail));
                        userCourses.put(newEmail, currentCourses);
                        currentUser = newEmail;
                        saveUsersToCSV();
                        System.out.println("Email changed to: " + currentUser);
                    }
                    break;

                case 9: // View all available courses
                    System.out.println("Available Courses:");
                    for (String[] courseItem : availableCourses) {
                        System.out.println(courseItem[0] + ": " + courseItem[1]);
                    }
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static int safeIntInput(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.println("Please enter a number.");
            scan.next();
            System.out.print("Try again: ");
        }
        return scan.nextInt();
    }

    static class User {
        private String name;
        private String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return null;
        }
    }
}
