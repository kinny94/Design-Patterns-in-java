package Prototype;

import java.util.Arrays;

class Address implements Cloneable {

    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return "Address{ " +
        "streetName='" + streetName + '\'' + 
        ", houseNumber=" + houseNumber + 
        '}';
    }

    // deep copy
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Address(streetName, houseNumber);
    }
}

class Person implements Cloneable {
    
    public String[] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person {" +
        "names=" + Arrays.toString(names) + 
        ", address=" + address + 
        '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // this following clone will not work since we will be copying the reference of these objects we will need to make the copy of names and a copy of address as well. Clone method b default does a shallow copy and we want deep copy in most of the cases
        return new Person(names.clone(), (Address) address.clone());
    }
}

public class Demo {
    public static void main(String[] args) throws Exception {
        Person john = new Person(
            new String[]{"John", "Smith"}, 
            new Address("London Road", 123)
        );
        
        Person jane = (Person)  john.clone();
        jane.names[0]= "Jane";
        jane.address.houseNumber = 123;

        System.out.println(john);
        System.out.println(jane);
    }
}
