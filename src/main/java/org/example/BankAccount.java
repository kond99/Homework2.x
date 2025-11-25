package org.example;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BankAccount {

    private String accountNumber;             // уникальный номер счёта
    private BigDecimal balance;               // текущий баланс счёта
    private User owner;                       // владелец счёта
    private List<Transaction> transactions;    // история транзакций

    public List<Transaction> getTransactions(){
        return Collections.unmodifiableList(transactions);   // Возвращает "замороженный" список
    }

    public BankAccount(String accountNumber,
                       BigDecimal balance,
                       User owner,
                       List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.balance = (balance != null) ? balance : BigDecimal.ZERO; //  если balance null, ставим 0
        this.owner = owner;
        this.transactions = transactions;
    }

    public void deposit(BigDecimal amount) {                         // пополнение счёта
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной и не нулевой!");
        }
        this.balance = this.balance.add(amount);                  // безопасное сложение
    }
    public boolean withdraw (BigDecimal amount) {                      // снятие средств со счёта (с проверкой на достаточность средств)
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной и не нулевой!");
        }
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        }
        return false; // недостаточно средств или неверная сумма
    }

    public BigDecimal getBalance () {                                     // возвращает текущий баланс
        return balance;
    }

    public void addTransaction (Transaction transaction) {                 // добавляет транзакцию в историю
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "BankAccount{accountNumber='" + accountNumber + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BankAccount that = (BankAccount) obj;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}