package org.example;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

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
/**
 * Вывод суммы потраченных средств на категорию Х за последний месяц со счёта Y
 * */
    public BigDecimal getMonthlySpendingByCategory(BankAccount account, String category) {
        TransactionService transactionService = new TransactionService();

        BigDecimal totalSum = BigDecimal.ZERO;

        if (account == null || transactionService.isValidCategory(category)) {
            return totalSum;
        }

        // 25.11.2025
        // 25.10.2025
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1L);
        for (Transaction transaction : account.getTransactions()) {
            if (TransactionType.PAYMENT.equals(transaction.getType())
                    && transaction.getCategory().equals(category)
                    && transaction.getCreatedDate().isAfter(Instant.from(oneMonthAgo))) {
                    totalSum = totalSum.add(transaction.getAmount());
            }
        }
        return totalSum;
    }

    /**
     * Вывод суммы потраченных средств на n категорий за последний месяц со всех счетов пользователя
     * */
    public Map<String, BigDecimal> getMonthlySpendingByCategories(User user, Set <String> categories) {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        TransactionService transactionService = new TransactionService();
        Set<String> validCategories =  transactionService.validateCategories(categories);
        if(user == null || validCategories.isEmpty()) {
            return resultMap;
        }

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1L);

        for (BankAccount account : user.getAccounts()) {
            for (Transaction transaction : account.getTransactions()) {
                if (TransactionType.PAYMENT.equals(transaction.getType())
                        && validCategories.contains(transaction.getCategory())
                        && transaction.getCreatedDate().isAfter(Instant.from(oneMonthAgo))) {
                    resultMap.merge(transaction.getCategory(), transaction.getAmount(), BigDecimal::add);
                    // ИЛИ
                    // когда в MAP уже есть запись по данной категории
                    // когда в MAP ещё нет записи по данной категории
                    var transactionCategory = transaction.getCategory();
                    if (!resultMap.containsKey(transactionCategory)) {
                        resultMap.put(transactionCategory, transaction.getAmount());
                    } else {
                        var currentCategory = resultMap.get(transactionCategory);
                        currentCategory = currentCategory.add(transaction.getAmount());
                        resultMap.put(transactionCategory, currentCategory);
                    }
                }
            }
        }

        return resultMap;
    }

    /**
     * Вывод истории операций по всем счетам и по всем категориям от наибольшей к наименьшей
     */
    public LinkedHashMap<String, List<Transaction>> getTransactionHistoryByAmount(User user) {
        LinkedHashMap<String, List<Transaction>> resultMap = new LinkedHashMap<>();
        if (user == null) {
            return resultMap;
        }

        List<Transaction> transactions = new ArrayList<>();
        for (BankAccount account : user.getAccounts()) {
            for (Transaction transaction : account.getTransactions()) {
                if (TransactionType.PAYMENT.equals(transaction.getType())){
                    transactions.add(transaction);
                }
            }
        }
        transactions.sort(Comparator.comparing(Transaction::getAmount));
        for (Transaction transaction : transactions) {
            resultMap.computeIfAbsent(transaction.getCategory(), k -> new ArrayList<>()).add(transaction);
        }

        return resultMap;
    }

    /**
     * Вывод последних N транзакций пользователя
     */
    public List<Transaction> getLastNTransactions(User user, int n) {
        List<Transaction> lastTransactions = new ArrayList<>();
        if (user == null) {
            return lastTransactions;
        }

        List<Transaction> transactions = new ArrayList<>();
        for (BankAccount account : user.getAccounts()) {
            transactions.addAll(account.getTransactions());
        }

        transactions.sort((t1, t2) -> t2.getCreatedDate().compareTo(t1.getCreatedDate()));

        for (int i = 0; i < Math.min(n, transactions.size()); i++) {
            lastTransactions.add(transactions.get(i));
        }
        return lastTransactions;
    }

    /**
     * Вывод топ-N самых больших платёжных транзакций пользователя
     */
    public PriorityQueue<Transaction> getTopNLargestTransactions(User user, int n) {
        PriorityQueue<Transaction> transactionPriorityQueue =
                new PriorityQueue<>(Comparator.comparing(Transaction::getAmount));

        if (user == null) {
            return transactionPriorityQueue;
        }

        for (BankAccount account : user.getAccounts()) {
            for (Transaction transaction : account.getTransactions()) {
                if (TransactionType.PAYMENT.equals(transaction.getType())){
                    if (transactionPriorityQueue.size()<n) {
                        transactionPriorityQueue.offer(transaction);
                    } else if (transactionPriorityQueue.peek() != null
                            && transactionPriorityQueue.peek().getAmount().compareTo(transaction.getAmount()) < 0) {
                        transactionPriorityQueue.poll();
                        transactionPriorityQueue.offer(transaction);

                    }
                }
            }
        }
        return transactionPriorityQueue;
    }
}

