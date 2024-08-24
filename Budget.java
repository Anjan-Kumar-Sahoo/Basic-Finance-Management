import java.util.HashMap;

public class Budget {
    private HashMap<String, Double> categoryBudgets;

    public Budget() {
        categoryBudgets = new HashMap<>();
    }

    public void setBudget(String category, double amount) {
        categoryBudgets.put(category, amount);
    }

    public double getBudget(String category) {
        return categoryBudgets.getOrDefault(category, 0.0);
    }

    public boolean isOverBudget(String category, double expenseAmount) {
        return expenseAmount > getBudget(category);
    }
}
