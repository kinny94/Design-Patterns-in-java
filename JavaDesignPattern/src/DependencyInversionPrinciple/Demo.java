package DependencyInversionPrinciple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.javatuples.Triplet;

enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

// (Before implementing the interface) -> Major flaw with this design is that we are exposing an internal storage implementation of relations using the getter
class Relationships implements RelationshipBrowser { // low level modules - doesn't have any business logic, just maintains a list and manipulates it.
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<Person,Relationship,Person>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<Person,Relationship,Person>(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations
        .stream()
        .filter(x -> Objects.equals(x.getValue0().name, name) && x.getValue1() == Relationship.PARENT)
        .map(Triplet::getValue2)
        .collect(Collectors.toList());
    }
}

class Research { // High level modules - It is allowing us to do some operations on those low levels constructs. and depends on low level constructs
    // the following one violates the dependency inversion principle
    public Research(Relationships relationships) {
        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
        relations
            .stream()
            .filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
            .forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
    }

    // the following one DOES NOT violates the dependency inversion principl
    public Research(RelationshipBrowser browser) {
        List<Person> children = browser.findAllChildrenOf("John");
        for (Person child: children) {
            System.out.println("John has a child called: " + child.name);
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child2);
        relationships.addParentAndChild(parent, child1);

        new Research(relationships);
    }
}
