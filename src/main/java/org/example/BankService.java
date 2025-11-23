package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankService {

    private BigDecimal totalBalance;

    void createAccount (User user, String accountNumber) {              // создает новый счет для пользователя
        if (user == null) {
            throw new IllegalArgumentException("User не может быть null!");
        }
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("accountNumber не может быть пустым!");
        }
        BankAccount newAccount = new BankAccount(accountNumber,
                BigDecimal.ZERO, user,
                new ArrayList<Transaction>());
        user.addAccount(newAccount);
    }

    void transfer(BankAccount source, BankAccount target, BigDecimal amount) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Оба счета должны быть указаны!");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной!");
        }
        if (source.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счете отправителя!");
        }
        // все проверки пройдены - теперь меняем балансы
        source.withdraw(amount); // вычитание в методе withdraw()
        target.deposit(amount);  // прибавление в методе deposit()
    }

    public List<Transaction> getTransactionHistory(BankAccount account){
        return account.getTransactions();                                                        // возвращает историю транзакций для указанного счета
    }

    public BigDecimal getTotalBalance(User user) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BankAccount account : user.getAccounts()) {
            sum = sum.add(account.getBalance());
        }
        return sum;
    }
}

