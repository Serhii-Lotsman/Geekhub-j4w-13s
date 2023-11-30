package org.geekhub.hw7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionImplementTest {

    private List<Transaction> transactions;
    private TransactionImplement transactionImplement;

    @BeforeEach
    void setUp() {
        this.transactions = new ArrayList<>();
        transactionImplement = new TransactionImplement(transactions);

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
    void getBiggestTransactionInCategory_shouldReturnMaxAmountOptionalOrNull() {
        var transactionResult = transactionImplement.getBiggestTransactionInCategory("sales operation");
        Optional<Transaction> testResult = Optional.ofNullable(transactions.get(2));

        assertAll(
            () -> assertEquals(testResult, transactionResult),
            () -> assertEquals(testResult.orElse(null), transactionResult.orElse(null)
        ));
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
    }

    @Test
    void getDateWithMostExpenses() {
    }

    @Test
    void getAverageSpendingPerCategory() {
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
