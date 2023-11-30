package org.geekhub.hw7;

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
    }

    @Test
    void getBiggestTransactionInCategory() {
        transactions.add(0, new Transaction(
            12.23,
            "currency operation",
            LocalDate.of(2000, 10, 10)
        ));
        transactions.add(1, new Transaction(
            32.13,
            "sales operation",
            LocalDate.of(2001, 11, 10)
        ));
        transactions.add(2, new Transaction(
            50.13,
            "sales operation",
            LocalDate.of(2004, 1, 30)
        ));

        var result = transactionImplement.getBiggestTransactionInCategory("sales operation");
        Optional<Transaction> transactionResult = Optional.ofNullable(transactions.get(2));

        assertEquals(transactionResult, result);
    }

    @Test
    void getTotalSpentForDate() {
    }

    @Test
    void getTransactionsByCategoryAndDate() {
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
}
