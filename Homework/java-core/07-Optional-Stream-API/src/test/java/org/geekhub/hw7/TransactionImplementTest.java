package org.geekhub.hw7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionImplementTest {

    private List<Transaction> transactions;
    private TransactionImplement transactionImplement;

    @BeforeEach
    void setUp() {
        this.transactions = new ArrayList<>();
        transactionImplement = new TransactionImplement(transactions);
        //TODO need to refactors List contain and replaces new ArrayList with ListOf()
        transactions.add(new Transaction(
            12.23,
            "currency operation",
            LocalDate.of(2000, 10, 10)
        ));
        transactions.add(new Transaction(
            32.13,
            "sales operation",
            LocalDate.of(2001, 11, 10)
        ));
        transactions.add(new Transaction(
            50.13,
            "sales operation",
            LocalDate.of(2004, 1, 30)
        ));
        transactions.add(new Transaction(
            15.23,
            "currency operation",
            LocalDate.of(2000, 10, 10)
        ));
        transactions.add(new Transaction(
            12.23,
            "currency operation",
            LocalDate.of(2010, 11, 5)
        ));
    }

    @Test
    void getBiggestTransactionInCategory_shouldReturnMaxAmount() {
        var transactionResult = transactionImplement.getBiggestTransactionInCategory("sales operation");
        Optional<Transaction> testResult = Optional.ofNullable(transactions.get(2));

        assertEquals(testResult, transactionResult);
    }

    @Test
    void getBiggestTransactionInCategory_shouldAnEmptyOptional_whenDoesNotTransaction() {
        transactions.clear();
        var transactionResult = transactionImplement.getBiggestTransactionInCategory("sales operation");

        assertEquals(Optional.empty(), transactionResult);
    }

    @Test
    void getTotalSpentForDate() {
        var transactionResult = transactionImplement.getTotalSpentForDate(LocalDate.of(2000, 10, 10));
        double testResult = 27.46;

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
        Map<String, Double> testMap = new HashMap<>();
        testMap.put(transactions.get(1).category(), 82.26);
        testMap.put(transactions.get(0).category(), 39.69);
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
        void getDateWithMostExpenses_shouldReturnAnEmptyOptional_whenDoesNotTransaction() {
        transactions.clear();
        var transactionResult = transactionImplement.getDateWithMostExpenses();

        assertEquals(Optional.empty(), transactionResult);
    }

    @Test
    void getAverageSpendingPerCategory() {
        Map<String, Double> testMap = new HashMap<>();
        testMap.put(transactions.get(1).category(), 41.13);
        testMap.put(transactions.get(0).category(), 13.229999999999999);
        var transactionResult = transactionImplement.getAverageSpendingPerCategory();

        assertEquals(testMap, transactionResult);
    }

    @Test
    void getMostPopularCategory() {
    }

    @Test
    void getCategoryWiseDistribution() {
    }

    @AfterEach
    void tearDown() {
        transactions.clear();
    }
}
