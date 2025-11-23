package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        User user = new User ("user1", "John Doe", new ArrayList<BankAccount>() );        // Создаём пользователя.

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
    }
}
