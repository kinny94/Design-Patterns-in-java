package Singleton.ThreadSafety;

class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("initializing a lazy singleton!");
    }

    // this creates race condition.. considering two threads checks for it at the same time
    public static LazySingleton getLazyInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    // double - checked locking -- not used anymore
    public static LazySingleton getSyncInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }   
        return instance;
    }
}

class InnerStaticSingleton {

    private InnerStaticSingleton() {}

    private static class Impl {
        private static final InnerStaticSingleton instance = new InnerStaticSingleton();
    }

    // this approach avoid the problem of synchronization - no need to take care of thraead safety
    public InnerStaticSingleton getInstance() {
        return Impl.instance;
    }
}

public class Demo {
    
}
