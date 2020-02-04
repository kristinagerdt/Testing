package bank;

import java.util.Objects;

public class Address {
    private String street;
    private int houseNumber;

    public Address(String street, int houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getHouseNumber() == address.getHouseNumber() &&
                getStreet().equals(address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getHouseNumber());
    }

    @Override
    public String toString() {
        return "Address{" + houseNumber + " " + street + " Street}";
    }
}