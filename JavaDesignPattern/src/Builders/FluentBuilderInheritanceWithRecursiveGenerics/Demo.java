package Builders.FluentBuilderInheritanceWithRecursiveGenerics;

class Person {
    public String name;
    public String position;

    @Override
    public String toString() {
        return "Person {" + "name'" + name + '\'' + ", position='" + position + '\'' + '}'; 
    }
}

class PersonBuilder<T extends PersonBuilder<T>> {
    
    protected Person person = new Person();

    public T withName(String name) {
        person.name = name;
        return self();
    }

    public Person build() {
        return person;
    }

    protected T self() {
        return (T) this;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
    
    public EmployeeBuilder worksAt(String position) {
        person.position = position;
        return self();
    }

    @Override
    protected EmployeeBuilder self() {
        return this;
    }
}

public class Demo {
    public static void main(String[] args) {
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        Person dmitri = employeeBuilder.withName("Dmitri")
                                        .worksAt("Developer")
                                        .build();
        System.out.println(dmitri);
    }
}
