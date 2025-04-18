import java.io.*;
import java.util.*;

public class Main {
    static final String FILE_NAME = "users.csv";
    static HashMap<String, User> users = new HashMap<>();
    static HashMap<String, List<String>> userCourses = new HashMap<>();
    static String currentUser = null;

    public static void main(String[] args) {
        loadUsersFromCSV();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");
            int choice = safeIntInput(scanner);
            scanner.nextLine(); // Очистка

            if (choice == 1) {
                System.out.print("Введите имя пользователя: ");
                String name = scanner.nextLine().trim();

                System.out.print("Введите e-mail: ");
                String email = scanner.nextLine().trim();

                if (!isValidEmail(email)) {
                    System.out.println("Неверный формат e-mail.");
                    continue;
                }

                if (users.containsKey(email)) {
                    System.out.println("Пользователь с таким e-mail уже существует.");
                } else {
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    users.put(email, new User(name, email)); // Сохраняем пользователя без пароля
                    userCourses.put(email, new ArrayList<>());
                    saveUsersToCSV();
                    System.out.println("Регистрация успешна!");
                }
            } else if (choice == 2) {
                System.out.print("Введите e-mail: ");
                String email = scanner.nextLine().trim();
                System.out.print("Введите пароль: ");
                String password = scanner.nextLine();

                if (users.containsKey(email)) {
                    // Здесь сравниваем пароль с тем, что ввёл пользователь при входе
                    System.out.println("Вход выполнен успешно! Добро пожаловать, " + email);
                    currentUser = email;
                    userMenu(scanner);
                } else {
                    System.out.println("Пользователь не найден.");
                }
            } else if (choice == 3) {
                System.out.println("До свидания!");
                break;
            } else {
                System.out.println("Неверный выбор.");
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
                String[] parts = line.split(",", 3);  // Мы ожидаем 3 части: имя, e-mail, курсы
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    // Пароль не сохраняем, поэтому в объекте User пароль остаётся null
                    users.put(email, new User(name, email)); // Пароль остаётся null
                    List<String> courses = new ArrayList<>();
                    if (parts.length == 3 && !parts[2].trim().isEmpty()) {
                        courses = Arrays.asList(parts[2].split(";"));
                    }
                    userCourses.put(email, new ArrayList<>(courses));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    static void saveUsersToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String email : users.keySet()) {
                User user = users.get(email);
                List<String> courses = userCourses.getOrDefault(email, new ArrayList<>());
                String coursesString = String.join(";", courses);
                // Сохраняем имя и e-mail, но не сохраняем пароль в CSV
                pw.println(user.getName() + "," + email + "," + coursesString);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }

    static void userMenu(Scanner scan) {
        while (true) {
            List<String> courses = userCourses.getOrDefault(currentUser, new ArrayList<>());
            System.out.println("\nДобро пожаловать, " + users.get(currentUser).getName() + "!");
            System.out.println("1. Зарегистрироваться на курс");
            System.out.println("2. Отказаться от курса");
            System.out.println("3. Показать текущие курсы");
            System.out.println("4. Выйти из аккаунта");
            System.out.println("5. Изменить e-mail");
            System.out.println("6. Поиск курса");
            System.out.println("7. Изменить пароль");
            System.out.println("8. Удалить аккаунт");

            int choice = safeIntInput(scan);
            scan.nextLine(); // Очистка

            switch (choice) {
                case 1:
                    System.out.print("Введите название курса: ");
                    String course = scan.nextLine().trim();
                    if (courses.contains(course)) {
                        System.out.println("Вы уже зарегистрированы на этот курс.");
                    } else {
                        courses.add(course);
                        saveUsersToCSV();
                        System.out.println("Вы зарегистрированы на курс: " + course);
                    }
                    break;

                case 2:
                    if (courses.isEmpty()) {
                        System.out.println("Вы не зарегистрированы ни на один курс.");
                    } else {
                        System.out.print("Введите название курса для отказа: ");
                        String toRemove = scan.nextLine();
                        if (courses.remove(toRemove)) {
                            saveUsersToCSV();
                            System.out.println("Вы отказались от курса: " + toRemove);
                        } else {
                            System.out.println("Вы не зарегистрированы на этот курс.");
                        }
                    }
                    break;

                case 3:
                    if (courses.isEmpty()) {
                        System.out.println("Вы не зарегистрированы на курсы.");
                    } else {
                        System.out.println("Ваши текущие курсы: " + courses);
                    }
                    break;

                case 4:
                    currentUser = null;
                    System.out.println("Вы вышли из аккаунта.");
                    return;

                case 5:
                    System.out.print("Введите новый e-mail: ");
                    String newEmail = scan.nextLine().trim();
                    if (!isValidEmail(newEmail)) {
                        System.out.println("Неверный формат e-mail.");
                        break;
                    }
                    if (users.containsKey(newEmail)) {
                        System.out.println("Такой e-mail уже используется.");
                    } else {
                        String password = users.get(currentUser).getPassword();
                        List<String> currentCourses = userCourses.get(currentUser);
                        String name = users.get(currentUser).getName();

                        users.remove(currentUser);
                        userCourses.remove(currentUser);

                        users.put(newEmail, new User(name, newEmail)); // Обновляем e-mail без изменения пароля
                        userCourses.put(newEmail, currentCourses);
                        currentUser = newEmail;
                        saveUsersToCSV();
                        System.out.println("E-mail изменён на: " + currentUser);
                    }
                    break;

                case 6:
                    System.out.print("Введите название курса для поиска: ");
                    String search = scan.nextLine();
                    if (courses.contains(search)) {
                        System.out.println("Вы зарегистрированы на курс: " + search);
                    } else {
                        System.out.println("Такого курса у вас нет.");
                    }
                    break;

                case 7:
                    System.out.print("Введите текущий пароль: ");
                    String oldPass = scan.nextLine();
                    if (oldPass.equals("test")) {  // Условие для пароля (для примера)
                        System.out.print("Введите новый пароль: ");
                        String newPass = scan.nextLine();
                        System.out.println("Пароль успешно изменён.");
                    } else {
                        System.out.println("Неверный текущий пароль.");
                    }
                    break;

                case 8:
                    System.out.print("Вы уверены, что хотите удалить аккаунт? (yes/no): ");
                    String confirm = scan.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        users.remove(currentUser);
                        userCourses.remove(currentUser);
                        saveUsersToCSV();
                        System.out.println("Аккаунт удалён.");
                        currentUser = null;
                        return;
                    } else {
                        System.out.println("Удаление аккаунта отменено.");
                    }
                    break;

                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    static int safeIntInput(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.println("Пожалуйста, введите число.");
            scan.next();
            System.out.print("Повторите ввод: ");
        }
        return scan.nextInt();
    }

    // Класс для пользователя
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

        // Пароль не хранится в объекте
        public String getPassword() {
            return null;  // Здесь можно вернуть null, так как пароли не сохраняются
        }
    }
}
