package org.geekhub.hw7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionImplementTest {

    private List<Transaction> transactions;
    private TransactionImplement transactionImplement;

    @BeforeEach
    void setUp() {
        this.transactions = List.of(
            new Transaction(
                12.5,
                "currency operation",
                LocalDate.of(2000, 10, 10)
            ),
            new Transaction(
                32.5,
                "sales operation",
                LocalDate.of(2001, 11, 10)
            ),
            new Transaction(
                50.5,
                "sales operation",
                LocalDate.of(2004, 1, 30)
            ),
            new Transaction(
                15.5,
                "currency operation",
                LocalDate.of(2000, 10, 10)
            ),
            new Transaction(
                17,
                "currency operation",
                LocalDate.of(2010, 11, 5)
            )
        );
        transactionImplement = new TransactionImplement(transactions);
    }

    @Test
    void getBiggestTransactionInCategory() {
        var transactionResult = transactionImplement.getBiggestTransactionInCategory("sales operation");
        Optional<Transaction> testResult = Optional.ofNullable(transactions.get(2));

        assertEquals(testResult, transactionResult);
    }

    @Test
    void getBiggestTransactionInCategory_shouldReturnEmptyOptional_whenDoesNotTransaction() {
        transactionImplement = new TransactionImplement(new ArrayList<>());
        var transactionResult = transactionImplement.getBiggestTransactionInCategory("sales operation");

        assertEquals(Optional.empty(), transactionResult);
    }

    @Test
    void getTotalSpentForDate() {
        var transactionResult = transactionImplement.getTotalSpentForDate(LocalDate.of(2000, 10, 10));
        double testResult = transactions.get(0).amount() + transactions.get(3).amount();

        assertEquals(testResult, transactionResult);
    }

    @Test
    void getTransactionsByCategoryAndDate() {
        List<Transaction> testList = new ArrayList<>();
        testList.add(transactions.get(0));
        testList.add(transactions.get(3));
        var transactionResult = transactionImplement.getTransactionsByCategoryAndDate(
            "currency operation",
            LocalDate.of(2000, 10, 10));

        assertEquals(testList, transactionResult);
    }

    @Test
    void getSpentAmountByCategory() {
        Map<String, Double> testMap = new LinkedHashMap<>();
        testMap.put(transactions.get(1).category(), 83.0);
        testMap.put(transactions.get(0).category(), 45.0);
        var transactionResult = transactionImplement.getSpentAmountByCategory();

        assertEquals(testMap, transactionResult);
    }

    @Test
    void getDateWithMostExpenses() {
        Optional<LocalDate> testDate = Optional.of(LocalDate.of(2004, 1, 30));
        var transactionResult = transactionImplement.getDateWithMostExpenses();

        assertEquals(testDate, transactionResult);
    }

    @Test
        void getDateWithMostExpenses_shouldReturnEmptyOptional_whenDoesNotTransaction() {
        transactionImplement = new TransactionImplement(new ArrayList<>());
        var transactionResult = transactionImplement.getDateWithMostExpenses();

        assertEquals(Optional.empty(), transactionResult);
    }

    @Test
    void getAverageSpendingPerCategory() {
        Map<String, Double> testMap = new LinkedHashMap<>();
        testMap.put(transactions.get(1).category(), 41.5);
        testMap.put(transactions.get(0).category(), 15.0);
        var transactionResult = transactionImplement.getAverageSpendingPerCategory();

        assertEquals(testMap, transactionResult);
    }

    @Test
    void getMostPopularCategory() {
        Optional<String> testResult = Optional.of(transactions.get(0).category());
        var transactionResult = transactionImplement.getMostPopularCategory();

        assertEquals(testResult, transactionResult);
    }

    @Test
    void getMostPopularCategory_shouldReturnEmptyOptional_whenDoesNotTransaction() {
        transactionImplement = new TransactionImplement(new ArrayList<>());
        var transactionResult = transactionImplement.getMostPopularCategory();

        assertEquals(Optional.empty(), transactionResult);
    }

    @Test
    void getCategoryWiseDistribution() {
        Map<String, Double> testMap = new LinkedHashMap<>();
        testMap.put(transactions.get(0).category(), 45.0 * 100 / 128);
        testMap.put(transactions.get(1).category(), 83.0 * 100 / 128);
        var transactionResult = transactionImplement.getCategoryWiseDistribution();

        assertEquals(testMap, transactionResult);
    }
}
