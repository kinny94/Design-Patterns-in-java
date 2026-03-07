package locking;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Locks {

    private Map<String, JLabel> cryptoLabels;
    private PricesContainer pricesContainer;
    private JPanel panel;
    private float animationStep = 0f;
    private boolean forward = true;

    private static final Color COLOR_START = new Color(144, 238, 144); // light green
    private static final Color COLOR_END = new Color(173, 216, 230);   // light blue

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Locks().start());
    }

    public void start() {
        JFrame frame = new JFrame("Cryptocurrency Prices");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setOpaque(true);

        cryptoLabels = createCryptoPriceLabels();
        addLabelsToPanel(cryptoLabels, panel);

        frame.add(panel);
        frame.setVisible(true);

        pricesContainer = new PricesContainer();
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);

        // Equivalent of AnimationTimer — tries to read prices and update labels
        Timer uiTimer = new Timer(16, e -> {
            if (pricesContainer.getLockObject().tryLock()) {
                try {
                    cryptoLabels.get("BTC").setText(String.valueOf(pricesContainer.getBitcoinPrice()));
                    cryptoLabels.get("ETH").setText(String.valueOf(pricesContainer.getEtherPrice()));
                    cryptoLabels.get("LTC").setText(String.valueOf(pricesContainer.getLitecoinPrice()));
                    cryptoLabels.get("BCH").setText(String.valueOf(pricesContainer.getBitcoinCashPrice()));
                    cryptoLabels.get("XRP").setText(String.valueOf(pricesContainer.getRipplePrice()));
                } finally {
                    pricesContainer.getLockObject().unlock();
                }
            }
        });

        // Equivalent of FillTransition — interpolates background color green <-> blue over 1s
        Timer backgroundTimer = new Timer(16, e -> {
            animationStep += forward ? 0.016f : -0.016f;
            if (animationStep >= 1f) { animationStep = 1f; forward = false; }
            if (animationStep <= 0f) { animationStep = 0f; forward = true; }
            panel.setBackground(interpolate(COLOR_START, COLOR_END, animationStep));
        });

        uiTimer.start();
        backgroundTimer.start();
        priceUpdater.start();
    }

    private Color interpolate(Color from, Color to, float t) {
        int r = (int) (from.getRed()   + t * (to.getRed()   - from.getRed()));
        int g = (int) (from.getGreen() + t * (to.getGreen() - from.getGreen()));
        int b = (int) (from.getBlue()  + t * (to.getBlue()  - from.getBlue()));
        return new Color(r, g, b);
    }

    private Map<String, JLabel> createCryptoPriceLabels() {
        Map<String, JLabel> map = new HashMap<>();
        for (String name : new String[]{"BTC", "ETH", "LTC", "BCH", "XRP"}) {
            map.put(name, new JLabel("0"));
        }
        return map;
    }

    private void addLabelsToPanel(Map<String, JLabel> labels, JPanel target) {
        for (Map.Entry<String, JLabel> entry : labels.entrySet()) {
            JLabel nameLabel = new JLabel(entry.getKey());
            nameLabel.setForeground(Color.BLUE);
            nameLabel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e)  { nameLabel.setForeground(Color.RED); }
                public void mouseReleased(MouseEvent e) { nameLabel.setForeground(Color.BLUE); }
            });
            target.add(nameLabel);
            target.add(entry.getValue());
        }
    }

    public static class PricesContainer {

        private Lock lockObject = new ReentrantLock();
        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public Lock getLockObject() { return lockObject; }

        public double getBitcoinPrice() { return bitcoinPrice; }
        public void setBitcoinPrice(double bitcoinPrice) { this.bitcoinPrice = bitcoinPrice; }

        public double getEtherPrice() { return etherPrice; }
        public void setEtherPrice(double etherPrice) { this.etherPrice = etherPrice; }

        public double getLitecoinPrice() { return litecoinPrice; }
        public void setLitecoinPrice(double litecoinPrice) { this.litecoinPrice = litecoinPrice; }

        public double getBitcoinCashPrice() { return bitcoinCashPrice; }
        public void setBitcoinCashPrice(double bitcoinCashPrice) { this.bitcoinCashPrice = bitcoinCashPrice; }

        public double getRipplePrice() { return ripplePrice; }
        public void setRipplePrice(double ripplePrice) { this.ripplePrice = ripplePrice; }
    }

    public static class PriceUpdater extends Thread {
        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while (true) {
                pricesContainer.getLockObject().lock();
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pricesContainer.setBitcoinPrice(random.nextInt(20000));
                    pricesContainer.setEtherPrice(random.nextInt(2000));
                    pricesContainer.setLitecoinPrice(random.nextInt(500));
                    pricesContainer.setBitcoinCashPrice(random.nextInt(5000));
                    pricesContainer.setRipplePrice(random.nextDouble());
                } finally {
                    pricesContainer.getLockObject().unlock();
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
