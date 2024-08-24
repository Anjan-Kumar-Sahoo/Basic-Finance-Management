import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {
    public static void generateReport(List<Expense> expenses) {
        double totalSpent = 0;
        HashMap<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            totalSpent += expense.getAmount();
            categoryTotals.put(expense.getCategory(), 
                categoryTotals.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        System.out.println("Total Spent: " + totalSpent);
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue() / totalSpent) * 100 + "%");
        }
    }
}
