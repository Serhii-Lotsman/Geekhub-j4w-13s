package org.geekhub.hw7;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            .filter(transaction -> transaction.date().isEqual(date))
            .toList();
    }

    @Override
    public Map<String, Double> getSpentAmountByCategory() {
        return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::category, Collectors.summingDouble(Transaction::amount)));
    }

    @Override
    public Optional<LocalDate> getDateWithMostExpenses() {
        return transactions.stream()
            .max(Comparator.comparingDouble(Transaction::amount)).map(Transaction::date);
    }

    @Override
    public Map<String, Double> getAverageSpendingPerCategory() {
        return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::category, Collectors.averagingDouble(Transaction::amount)));
    }

    @Override
    public Optional<String> getMostPopularCategory() {
        Map<String, Long> categoryCounts = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::category, Collectors.counting()));

        return categoryCounts.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey);
    }

    @Override
    public Map<String, Double> getCategoryWiseDistribution() {
        return null;
    }
}
