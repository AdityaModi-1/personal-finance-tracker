import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Expense{
    String description;
    double amount;
    String category;

    public Expense(String description, double amount, String category){
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String toString(){
        return category + ": $" + amount + " (" + description + ")";
    }
}

public class Main{
    static ArrayList<Expense> expenseList = new ArrayList<>();

    public static void saveData(){
        try(PrintWriter writer = new PrintWriter(new FileWriter("expenses.txt"))){
            for(Expense e: expenseList){
                writer.println(e.description + "," + e.amount + "," + e.category);
            }
        } catch (IOException e){
            System.out.println("Error saving file.");
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Personal Finance Tracker ===");

        while (true){
            System.out.println("\n1. Add Expense");
            System.out.println("2. All Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1){
                System.out.print("Description (e.g. Coffee): ");
                String desc = scanner.nextLine();

                System.out.print("Amount (e.g., 5.50): ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Category. (Food/Rent/Fun): ");
                String cat = scanner.nextLine();

                expenseList.add(new Expense(desc, amount, cat));
                saveData();
                System.out.println("Expense Saved!");
            } else if (choice == 2){
                System.out.println("\n--- Your Expenses ---");
                double total = 0;
                for(Expense e: expenseList){
                    System.out.println(e.toString());
                    total += e.amount;
                }
                
                System.out.println("---------------------");
                System.out.println("TOTAL SPENT: $" + total);
            } else if(choice == 3){
                System.out.println("Goodbye!");
                break;
            }
        }
        scanner.close();
    }
}

