package org.example;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public class Transaction {
    private final String id;                                       // уникальный идентификатор транзакции
    private final BigDecimal amount;                               // сумма транзакции
    private final TransactionType type;                            // тип транзакции - Enum (DEPOSIT, WITHDRAWAL, TRANSFER)
    private final LocalDateTime date;                              // дата выполнения транзакции
    private final BankAccount sourceAccount;                       // источник транзакции (если применимо)
    private final BankAccount targetAccount;                       // получатель транзакции (если применимо)
    private final String category;

    public Transaction(String id,
                       BigDecimal amount,
                       TransactionType type,
                       LocalDateTime date,
                       BankAccount sourceAccount,
                       BankAccount targetAccount,
                       String category) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.category = category;
    }

    public String getId() {                                              // Геттеры
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    public String getCategory() {
        return category;
    }

    private static Transaction createTransaction(String id,
                                                 BigDecimal amount,
                                                 TransactionType type,
                                                 LocalDateTime date,
                                                 BankAccount sourceAccount,
                                                 BankAccount targetAccount,
                                                 String category) {
        return new Transaction(id, amount, type, date, sourceAccount, targetAccount, category);
    }

    // написать метод: конструктор для создания транзакции
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", sourceAccount=" + (sourceAccount != null ? sourceAccount : "null") +
                ", targetAccount=" + (targetAccount != null ? targetAccount : "null") +
                ", category=" + category +
                '}';
    }

    public Instant getCreatedDate() {
        return null;
    }
}
