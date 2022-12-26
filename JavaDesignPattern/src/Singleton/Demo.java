package Singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class BasicSingleton implements Serializable {
    
    private static final BasicSingleton instance = new BasicSingleton();
    private int value = 0;

    private BasicSingleton () {

    }

    public static BasicSingleton getInstance() {
        return instance;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected Object readResolve() {
        return instance;
    }
}

public class Demo {

    static void saveToFile(BasicSingleton singleton, String fileName) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        try (
            fileOutputStream;
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            out.writeObject(singleton);
        }
    }

    static BasicSingleton readFromFile(String fileName) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        try (
            fileInputStream;
            ObjectInputStream in = new ObjectInputStream(fileInputStream)
        ) {
            return (BasicSingleton) in.readObject();
        }
    }
    
    public static void main(String[] args) throws Exception {
        BasicSingleton basicSingleton = BasicSingleton.getInstance();
        basicSingleton.setValue(123);
        System.out.println(basicSingleton.getValue());

        // Problem 1: Reflection
        // Problem 2: Serialization - when you deserialize, the JVM doesn't care that your constructor is private, it will still create a constructor anyway

        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(111);
        String fileName = "singleton.bin";
        saveToFile(singleton, fileName);

        singleton.setValue(222);

        BasicSingleton singleton2 = readFromFile(fileName);
        System.out.println(singleton == singleton2);
        System.out.println(singleton.getValue());
        System.out.println(singleton2.getValue());
    }
}
