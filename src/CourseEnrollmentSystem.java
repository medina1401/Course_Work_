import java.io.*;
import java.util.*;

public class CourseEnrollmentSystem {
    static final String FILE_NAME = "users.csv";
    static HashMap<String, User> users = new HashMap<>();
    static HashMap<String, List<String>> userCourses = new HashMap<>();
    static String currentUser = null;

    static List<String[]> availableCourses = List.of(
            new String[]{"Introduction to Engineering and Computer Science"},
            new String[]{"Programming Languages I"},
            new String[]{"Calculus I"},
            new String[]{"Algebra and Geometry I"},
            new String[]{"Physical Education I"},
            new String[]{"Philosophy"},
            new String[]{"Foreign Language I"},
            new String[]{"Programming Languages II"},
            new String[]{"Programming Language Course Work"},
            new String[]{"Discrete Mathematics I"},
            new String[]{"Calculus II"},
            new String[]{"Physical Education II"},
            new String[]{"Russian Language"},
            new String[]{"Foreign Language II"}
    );

    public static void main(String[] args) {
        loadUsersFromCSV();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Show total number of users");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = safeIntInput(scanner);
            scanner.nextLine(); // clear

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
                    users.put(email, new User(name, email, password));
                    userCourses.put(email, new ArrayList<>());
                    saveUsersToCSV();
                    System.out.println("Registration successful!");
                    registerForCourse(scanner, email);
                }
            } else if (choice == 2) {
                System.out.print("Enter your email: ");
                String email = scanner.nextLine().trim();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (users.containsKey(email) && users.get(email).getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + email);
                    currentUser = email;
                    userMenu(scanner);
                } else {
                    System.out.println("Invalid email or password.");
                }
            } else if (choice == 3) {
                System.out.println("Total registered users: " + users.size());
            } else if (choice == 4) {
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
                String[] parts = line.split(",", 4);
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    String password = parts[2].trim();
                    users.put(email, new User(name, email, password));
                    List<String> courses = new ArrayList<>();
                    if (parts.length == 4 && !parts[3].trim().isEmpty()) {
                        courses = Arrays.asList(parts[3].split(";"));
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
                pw.println(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + coursesString);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    static void registerForCourse(Scanner scan, String email) {
        List<String> courses = userCourses.getOrDefault(email, new ArrayList<>());
        System.out.println("Available Courses:");
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.println((i + 1) + ". " + availableCourses.get(i)[0]);
        }

        while (true) {
            System.out.print("Enter the number of the course you want to register for (0 to skip): ");
            int courseChoice = safeIntInput(scan);
            if (courseChoice == 0) {
                System.out.println("Skipping course registration.");
                break;
            }
            if (courseChoice > 0 && courseChoice <= availableCourses.size()) {
                String courseName = availableCourses.get(courseChoice - 1)[0];
                if (courses.contains(courseName)) {
                    System.out.println("You are already registered for this course.");
                } else {
                    courses.add(courseName);
                    userCourses.put(email, courses);
                    saveUsersToCSV();
                    System.out.println("You have successfully registered for: " + courseName);
                    break;
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
                    registerForCourse(scan, currentUser);
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
                        User current = users.get(currentUser);
                        List<String> currentCourses = userCourses.get(currentUser);
                        users.remove(currentUser);
                        userCourses.remove(currentUser);
                        users.put(newEmail, new User(current.getName(), newEmail, current.getPassword()));
                        userCourses.put(newEmail, currentCourses);
                        currentUser = newEmail;
                        saveUsersToCSV();
                        System.out.println("Email changed to: " + currentUser);
                    }
                    break;
                case 6:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scan.nextLine().toLowerCase();
                    System.out.println("Matching courses:");
                    for (String[] course : availableCourses) {
                        if (course[0].toLowerCase().contains(keyword)) {
                            System.out.println(course[0]);
                        }
                    }
                    break;
                case 7:
                    System.out.print("Enter your current password: ");
                    String currentPass = scan.nextLine();
                    if (users.get(currentUser).getPassword().equals(currentPass)) {
                        System.out.print("Enter new password: ");
                        String newPass = scan.nextLine();
                        users.get(currentUser).setPassword(newPass);
                        saveUsersToCSV();
                        System.out.println("Password changed successfully.");
                    } else {
                        System.out.println("Incorrect current password.");
                    }
                    break;
                case 9:
                    System.out.println("Available Courses:");
                    for (String[] course : availableCourses) {
                        System.out.println(course[0]);
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
        private String password;

        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String newPass) {
            this.password = newPass;
        }
    }
}
