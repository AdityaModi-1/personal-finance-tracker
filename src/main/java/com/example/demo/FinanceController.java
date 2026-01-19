package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

class Transaction{
    private double amount;
    private String description;

    public Transaction(double amount, String description){
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {return amount; }
    public String getDescription() {return description; }
}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class FinanceController{
    private List<Transaction> transactions = new ArrayList<>();

    public FinanceController(){
        
    }

    @GetMapping("/transactions")
    public List<Transaction> getAll(){
        return transactions;
    }

    @PostMapping("/add")
    public Transaction add(@RequestBody Transaction t){
        transactions.add(t);
        return t;
    }

    @GetMapping("/predict")
    public double getPrediction() {

        // --- DEBUG PRINTS ---
        System.out.println("DEBUG: Calculation started.");
        System.out.println("DEBUG: List size is: " + transactions.size());
        // --------------------

        if (transactions.isEmpty() || transactions.size() < 2) {
            System.out.println("DEBUG: Not enough data. Returning 0.");
            return 0.0; 
        }


        if (transactions.isEmpty() || transactions.size() < 2) {
            return 0.0; 
        }

        int n = transactions.size();
        double[] months = new double[n];
        double[] amounts = new double[n];

        for (int i = 0; i < n; i++) {
            months[i] = i + 1; 
            amounts[i] = transactions.get(i).getAmount(); 
        }

        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;

        for (int i = 0; i < n; i++) {
            sumX += months[i];
            sumY += amounts[i];
            sumXY += (months[i] * amounts[i]);
            sumXX += (months[i] * months[i]);
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        double nextMonth = n + 1;
        double prediction = (slope * nextMonth) + intercept;

        return prediction > 0 ? prediction : 0;
    }
}