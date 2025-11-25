package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {


        User user = new User ("user1", "John Doe", new ArrayList<BankAccount>());        // Создаём пользователя.

        BankService bankService = new BankService();                  // Создаём сервис.

        bankService.createAccount(user,"ACC123");        // Создаём счета.
        bankService.createAccount(user,"ACC456");

        List<BankAccount> accounts = user.getAccounts();   // Получаем счета пользователя
        BankAccount acc1 = accounts.get(0);
        BankAccount acc2 = accounts.get(1);

        acc1.deposit(new BigDecimal("1000"));          // Пополняем счёт №1

        bankService.transfer(acc1, acc2, new BigDecimal("150"));    // Переводим средства между счетами

        System.out.println("Баланс счёта ACC123: " + acc1.getBalance());  // Выводим балансы
        System.out.println("Баланс счёта ACC456: " + acc2.getBalance());

        System.out.println("История транзакций для счёта АСС123: ");         // Выводим историю транзакций
        bankService.getTransactionHistory(acc1).forEach(System.out::println);

        System.out.println("Вывод суммы потраченных средств на категорию Х за последний месяц со счёта Y: ");
        bankService.getMonthlySpendingByCategory(acc1, "Food");

        System.out.println("Вывод информации о том, сколько было потрачено средств на N категорий за последний месяц со всех счетов: ");
        bankService.getMonthlySpendingByCategories(user, Set.of("Food", "Alcohol"));

        System.out.println("Вывод истории операций по всем счетам и по всем категориям от наибольшей к наименьшей: ");
        bankService.getTransactionHistoryByAmount(user);

        System.out.println("Вывод последних N транзакций пользователя: ");
        bankService.getLastNTransactions(user, 5);

        System.out.println("Вывод топ-N самых больших платёжных транзакций пользователя: ");
        bankService.getTopNLargestTransactions(user, 5);

    }
}
