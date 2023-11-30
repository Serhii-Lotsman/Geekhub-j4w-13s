package org.geekhub.hw7;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionImplement implements TransactionAnalyzer {
    private final List<Transaction> transactions;

    public TransactionImplement(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> getBiggestTransactionInCategory(String category) {
        return transactions.stream()
            .filter(transaction -> transaction.category().equals(category))
            .max(Comparator.comparingDouble(Transaction::amount));
    }

    @Override
    public double getTotalSpentForDate(LocalDate date) {
        return transactions.stream()
            .filter(transaction -> transaction.date().isEqual(date))
            .mapToDouble(Transaction::amount)
            .reduce(0, Double::sum);
    }

    @Override
    public List<Transaction> getTransactionsByCategoryAndDate(String category, LocalDate date) {
        return transactions.stream()
            .filter(transaction -> transaction.category().equals(category))
            .filter(transactionDate -> transactionDate.date().isEqual(date))
            .toList();
    }

    @Override
    public Map<String, Double> getSpentAmountByCategory() {
        return null;
    }

    @Override
    public Optional<LocalDate> getDateWithMostExpenses() {
        return Optional.empty();
    }

    @Override
    public Map<String, Double> getAverageSpendingPerCategory() {
        return null;
    }

    @Override
    public Optional<String> getMostPopularCategory() {
        return Optional.empty();
    }

    @Override
    public Map<String, Double> getCategoryWiseDistribution() {
        return null;
    }
}
