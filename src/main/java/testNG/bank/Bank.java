package testNG.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Bank {
    public static List<Address> getAddressesPeopleOlder17(List<Person> people) {
        if (people != null)
            return people
                    .stream()
                    .filter(person -> person.getAge() > 17)
                    .map(Person::getAddress)
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    public static List<String> getDistinctNames(List<Person> people) {
        if (people != null)
            return people
                    .stream()
                    .map(Person::getName)
                    .distinct()
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    public static Map<Integer, List<Person>> groupingPeopleByAge(List<Person> people) {
        if (people != null)
            return people
                    .stream()
                    .collect(Collectors.groupingBy(Person::getAge));
        return new HashMap<>();
    }

    public static Map<String, List<Person>> groupingPeopleByName(List<Person> people) {
        if (people != null)
            return people
                    .stream()
                    .collect(Collectors.groupingBy(Person::getName, TreeMap::new, Collectors.toList()));
        //.collect(Collectors.groupingBy(Person::getName));
        return new HashMap<>();
    }

    public static List<String> getIbans(List<Account> accounts) {
        if (accounts != null)
            return accounts
                    .stream()
                    .map(Account::getIban)
                    .map(Bank::getHiddenIban)
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    public static List<String> getIbansFromPeople(List<Person> people) {
        if (people != null)
            return people
                    .stream()
                    .flatMap(person -> person.getAccounts().stream())
                    .map(Account::getIban)
                    .map(Bank::getHiddenIban)
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }

    public static Integer getSumAgePeopleOlder17(List<Person> people) {
        if (people != null)
            return people
                    .stream()
                    .map(Person::getAge)
                    .filter(p -> p > 17)
                    .reduce(0, Integer::sum);
        return 0;
    }

    public static String getPeopleOlder17(List<Person> people) {
        if (people != null && people.size() != 0)
            return people
                    .stream()
                    .filter(person -> person.getAge() > 17)
                    .map(Person::getName)
                    .collect(Collectors.joining(", ", "In Germany ", " are of legal age."));
        return "";
    }

    private static String getHiddenIban(String iban) {
        return iban.substring(0, 3) + iban.substring(3).replaceAll("\\w", "*");
    }
}