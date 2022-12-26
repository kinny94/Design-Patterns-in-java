package Singleton.StaticBlockSingleton;

import java.io.File;
import java.io.IOException;

class StaticBlockSingleton {
    
    private StaticBlockSingleton() throws IOException {
        System.out.println("Singleton is initializing!!");
        File.createTempFile(".", ".");
    }

    private static StaticBlockSingleton instance;

    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            System.err.println("failed to create the singleton!");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}

public class Demo {
    
}
