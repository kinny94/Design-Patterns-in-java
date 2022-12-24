package SingleResponsibilityPrinciple;// SRP - Single Responsibility principle -> It states that a class should have only one reason to change. A single class should have only one responsibility. If a class take on a lot of responsibility, you end up with a God object which is an antipattern.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// SingleResponsibilityPrinciple.Journal class manages one concern of adding and removing journal enteries
class  Journal {
    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    public void addEntry(String text) {
      entries.add("" + (++count) + ": " + text);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    // Now we are not handling adding and removal of entries but also doing other stuff...
    public void save(String fileName) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(fileName)) {
            out.println(toString());
        }
    }
}

// SingleResponsibilityPrinciple.Persistence class manages saving and loading of Journals from some sort of storage
class Persistence {
    public void saveToFile(Journal journal, String fileName, boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(fileName).exists()) {
            try (PrintStream out = new PrintStream(fileName)) {
                out.println(journal.toString());
            }
        }
    }
}

class Demo {
    public static void main(String[] args) throws Exception {
        Journal j = new Journal();
        j.addEntry("I cried today!");
        j.addEntry("I wrote a program");
        System.out.println(j);

        Persistence p = new Persistence();
        String fileName = System.getProperty("user.dir") + "/SingleResponsibilityPrinciple.Journal.txt";
        p.saveToFile(j, fileName, true);

        Runtime.getRuntime().exec(new String[] { "open", fileName.toString()});
    }
}