package resources;

public class RaceCondition {

    public static class IncrementThread extends Thread {
        
        private InventoryCounter inventoryCounter;
        
        public IncrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i=0; i<10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

     public static class DecrementThread extends Thread {
        
        private InventoryCounter inventoryCounter;
        
        public DecrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i=0; i<10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        private synchronized void increment() {
            items++;
        }

        private synchronized void decrement() {
            items--;
        }

        public synchronized int getItems() {
            return items;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementThread incrementThread = new IncrementThread(inventoryCounter);
        DecrementThread decrementThread = new DecrementThread(inventoryCounter);

        incrementThread.start();
        decrementThread.start();
        incrementThread.join();
        decrementThread.join();
        System.out.println("We have " + inventoryCounter.getItems() + " items");
    }
}
