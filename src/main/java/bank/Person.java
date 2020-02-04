package bank;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private int age;
    private Address address;
    private List<Account> accounts = new ArrayList<>();

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "Person{name=" + name + ", age=" + age + ", address=" + address + ", accounts=" + accounts + '}';
    }
}