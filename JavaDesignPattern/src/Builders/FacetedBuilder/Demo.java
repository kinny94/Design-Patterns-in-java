package Builders.FacetedBuilder;

class Person {
    
    public String streetAddress, postcode, city;
    public String companyName, position;
    public int annualIncome;

    @Override
    public String toString() {
        return "Person: {" + 
        "streetAddress='" + streetAddress + '\'' +
        ", postcode='" + postcode + '\'' + 
        ", city='" + city + '\'' +
        ", companyName='" + companyName + '\'' +
        ", position='" + position + '\'' +
        ", annualIncome='" + annualIncome + '\'' + 
        '}';
    }
}

// Builder facade - In this pattern the sub builders haver to inherit from the parent builder. The reason is as soon as they inherit from the base class, the expose the works and the lives method, which means you can switch from one sub builder to another sub builder inside a single fluent api call.
class PersonBuilder {
    
    protected Person person = new Person();

    public PersonAddressBuilder lives() {
        return new PersonAddressBuilder(person);
    }

    public PersonJobBuilder works() {
        return new PersonJobBuilder(person);
    }

    public Person build() {
        return person;
    }
}

class PersonAddressBuilder extends PersonBuilder {
    
    public PersonAddressBuilder(Person person) {
        this.person = person;
    }

    public PersonAddressBuilder at(String streetAddress) {
        person.streetAddress = streetAddress;
        return this;
    }

    public PersonAddressBuilder withPostCode(String postcode) {
        person.postcode = postcode;
        return this;
    }

    public PersonAddressBuilder in(String city) {
        person.city = city;
        return this;
    }
}

class PersonJobBuilder extends PersonBuilder {

    public PersonJobBuilder(Person person) {
        this.person = person;
    }

    public PersonJobBuilder at(String companyName) {
        this.person.companyName = companyName;
        return this;
    }

    public PersonJobBuilder asA(String position) {
        this.person.position = position;
        return this;
    }

    public PersonJobBuilder earning(int annualIncome) {
        this.person.annualIncome = annualIncome;
        return this;
    }
}
public class Demo {

    public static void main(String[] args) {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder
                        .lives()
                            .at("123 London Road")
                            .in("London")
                            .withPostCode("W1245")
                        .works()
                            .at("Google")
                            .asA("Janitor")
                            .earning(5000)
                        .build();
        System.out.println(person);
    }
}
