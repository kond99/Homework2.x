package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String id;                    // уникальный идентификатор пользователя
    private String name;                  // имя пользователя
    private List<BankAccount> accounts;   // список счетов пользователя, это список объектов класса BankAccount

    public User(String id, String name, List<BankAccount> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = new ArrayList<>(accounts);
    }

    public List<BankAccount> getAccounts(){
        return Collections.unmodifiableList(accounts);   // Возвращает "замороженный" список
    }

    public void addAccount(BankAccount account) {  // добавление нового счёта пользователю
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}

