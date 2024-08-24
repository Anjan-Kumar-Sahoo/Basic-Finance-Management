import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FinanceManager {
    private static User user;
    private static List<Expense> expenses;
    private static final String FILE_NAME = "ak.txt";

    public static void main(String[] args) {
        expenses = new ArrayList<>();

        loadUserData();
        loadExpenses();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n1. Add Expense\n2. View Report\n3. Edit Salary\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    generateReport();
                    break;
                case 3:
                    editSalary(scanner);
                    break;
                case 4:
                    saveExpenses();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter category: ");
        String category = scanner.next();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        Expense expense = new Expense(category, amount, LocalDate.now());
        expenses.add(expense);

        System.out.println("Expense added.");
    }

    private static void editSalary(Scanner scanner) {
        System.out.print("Enter new salary: ");
        double newSalary = scanner.nextDouble();
        user.setSalary(newSalary);
        System.out.println("Salary updated to: " + newSalary);
    }

    private static void loadUserData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String salaryLine = br.readLine(); // Read the first line as salary
            if (salaryLine != null) {
                double salary = Double.parseDouble(salaryLine);
                user = new User(salary);
                System.out.println("User data loaded. Salary: " + user.getSalary());
            }
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            br.readLine(); // Skip the first line (salary)

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String category = data[0];
                double amount = Double.parseDouble(data[1]);
                LocalDate date = LocalDate.parse(data[2]);
                expenses.add(new Expense(category, amount, date));
            }
            System.out.println("Expenses loaded. Total: " + expenses.size());
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }

    private static void saveExpenses() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(user.getSalary() + "\n"); // Save salary as the first line
            for (Expense expense : expenses) {
                writer.write(expense.getCategory() + "," + expense.getAmount() + "," + expense.getDate() + "\n");
            }
            System.out.println("Data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void generateReport() {
        double totalExpenses = calculateTotalExpenses();
        double remainingBalance = user.getSalary() - totalExpenses;

        System.out.println("\n--- Expense Report ---");
        for (Expense expense : expenses) {
            double percentage = (expense.getAmount() / totalExpenses) * 100;
            System.out.printf("Category: %s | Amount: %.2f | Date: %s | Percentage: %.2f%%\n",
                    expense.getCategory(), expense.getAmount(), expense.getDate(), roundToTwoDecimalPlaces(percentage));
        }

        System.out.println("\nTotal Expenses: " + totalExpenses);
        System.out.println("Remaining Balance: " + remainingBalance);
    }

    private static double calculateTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    private static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}