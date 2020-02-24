package testNG.bank;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
//@BeforeSuite	annotated method will run before the execution of all the test methods in the suite.
//@BeforeTest	annotated method will be executed before the execution of all the test methods of available classes belonging to that folder.
//@BeforeClass	annotated method will be executed before the first method of the current class is invoked.
//@BeforeMethod	annotated method will be executed before each test method will run.
//@BeforeGroups	annotated method run only once for a group before the execution of all test cases belonging to that group.

//@AfterMethod	annotated method will run after the execution of each test method.
//@AfterGroups	annotated method run only once for a group after the execution of all test cases belonging to that group.
//@AfterClass	annotated method will be invoked after the execution of all the test methods of the current class.
//@AfterTest	annotated method will be executed after the execution of all the test methods of available classes belonging to that folder.
//@AfterSuite	annotated method will run after the execution of all the test methods in the suite.

public class BankTest {
    private List<Person> people = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();

    @BeforeTest
    public void setUp() {
        Account a1 = new Account("AABBCC111");
        Account a2 = new Account("FFGGHH222");
        Account a3 = new Account("LLMMNN333");
        Account a4 = new Account("QQRRSS444");
        Account a5 = new Account("WWXXZZ555");
        Account a6 = new Account("HJYTFD666");
        Collections.addAll(accounts, a1, a2, a3, a4, a5, a6);

        Person p1 = new Person("Alex", 45, new Address("Baker", 3));
        p1.addAccount(a1);
        p1.addAccount(a2);
        Person p2 = new Person("Maria", 14, new Address("Oxford", 14));
        p2.addAccount(a3);
        Person p3 = new Person("Stephan", 34, new Address("Carnaby", 1));
        p3.addAccount(a4);
        Person p4 = new Person("Max", 24, new Address("Downing", 5));
        p4.addAccount(a5);
        p4.addAccount(a6);
        Person p5 = new Person("Max", 34, new Address("Oxford", 12));
        Person p6 = new Person("Tom", 45, new Address("Bond", 44));
        Collections.addAll(people, p1, p2, p3, p4, p5, p6);
    }

    @Test
    public void testFullListGetAddressesPeopleOlder17() {
        List<Address> expected = Arrays.asList(
                new Address("Baker", 3),
                new Address("Carnaby", 1),
                new Address("Downing", 5),
                new Address("Oxford", 12),
                new Address("Bond", 44));
        List<Address> actual = Bank.getAddressesPeopleOlder17(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGetAddressesPeopleOlder17() {
        List<Address> expected = new ArrayList<>();
        List<Address> actual = Bank.getAddressesPeopleOlder17(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGetAddressesPeopleOlder17() {
        List<Address> expected = new ArrayList<>();
        List<Address> actual = Bank.getAddressesPeopleOlder17(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGetDistinctNames() {
        List<String> expected = Arrays.asList("Alex", "Maria", "Stephan", "Max", "Tom");
        List<String> actual = Bank.getDistinctNames(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGetDistinctNames() {
        List<String> expected = new ArrayList<>();
        List<String> actual = Bank.getDistinctNames(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGetDistinctNames() {
        List<String> expected = new ArrayList<>();
        List<String> actual = Bank.getDistinctNames(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGroupingPeopleByAge() {
        Map<Integer, List<Person>> expected = new HashMap<>();
        expected.put(14, Collections.singletonList(people.get(1)));
        expected.put(24, Collections.singletonList(people.get(3)));
        expected.put(34, Arrays.asList(people.get(2), people.get(4)));
        expected.put(45, Arrays.asList(people.get(0), people.get(5)));
        Map<Integer, List<Person>> actual = Bank.groupingPeopleByAge(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGroupingPeopleByAge() {
        Map<Integer, List<Person>> expected = new HashMap<>();
        Map<Integer, List<Person>> actual = Bank.groupingPeopleByAge(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGroupingPeopleByAge() {
        Map<Integer, List<Person>> expected = new HashMap<>();
        Map<Integer, List<Person>> actual = Bank.groupingPeopleByAge(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGroupingPeopleByName() {
        Map<String, List<Person>> expected = new HashMap<>();
        expected.put("Alex", Collections.singletonList(people.get(0)));
        expected.put("Maria", Collections.singletonList(people.get(1)));
        expected.put("Stephan", Collections.singletonList(people.get(2)));
        expected.put("Max", Arrays.asList(people.get(3), people.get(4)));
        expected.put("Tom", Collections.singletonList(people.get(5)));
        Map<String, List<Person>> actual = Bank.groupingPeopleByName(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGroupingPeopleByName() {
        Map<String, List<Person>> expected = new HashMap<>();
        Map<String, List<Person>> actual = Bank.groupingPeopleByName(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGroupingPeopleByName() {
        Map<String, List<Person>> expected = new HashMap<>();
        Map<String, List<Person>> actual = Bank.groupingPeopleByName(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGetIbans() {
        List<String> expected = Arrays.asList(
                "AAB******", "FFG******",
                "LLM******", "QQR******",
                "WWX******", "HJY******");
        List<String> actual = Bank.getIbans(accounts);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGetIbans() {
        List<String> expected = new ArrayList<>();
        List<String> actual = Bank.getIbans(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGetIbans() {
        List<String> expected = new ArrayList<>();
        List<String> actual = Bank.getIbans(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGetIbansFromPeople() {
        List<String> expected = Arrays.asList(
                "AAB******", "FFG******", "LLM******",
                "QQR******", "WWX******", "HJY******");
        List<String> actual = Bank.getIbansFromPeople(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGetIbansFromPeople() {
        List<String> expected = new ArrayList<>();
        List<String> actual = Bank.getIbansFromPeople(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGetIbansFromPeople() {
        List<String> expected = new ArrayList<>();
        List<String> actual = Bank.getIbansFromPeople(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGetSumAgePeopleOlder17() {
        Integer expected = 182;
        Integer actual = Bank.getSumAgePeopleOlder17(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGetSumAgePeopleOlder17() {
        Integer expected = 0;
        Integer actual = Bank.getSumAgePeopleOlder17(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGetSumAgePeopleOlder17() {
        Integer expected = 0;
        Integer actual = Bank.getSumAgePeopleOlder17(null);
        assertEquals(actual, expected);
    }

    @Test
    public void testFullListGetPeopleOlder17() {
        String expected = "In Germany Alex, Stephan, Max, Max, Tom are of legal age.";
        String actual = Bank.getPeopleOlder17(people);
        assertEquals(actual, expected);
    }

    @Test
    public void testEmptyListGetPeopleOlder17() {
        String expected = "";
        String actual = Bank.getPeopleOlder17(new ArrayList<>());
        assertEquals(actual, expected);
    }

    @Test
    public void testNullGetPeopleOlder17() {
        String expected = "";
        String actual = Bank.getPeopleOlder17(null);
        assertEquals(actual, expected);
    }
}