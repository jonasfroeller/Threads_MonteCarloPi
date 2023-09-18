package at.htlleonding.montecarlopi;

public class RainDropCounter {
    private static final RainDropCounter instance = new RainDropCounter();
    private int amountInCircle = 0;

    public static RainDropCounter getInstance() {
        return instance;
    }

    public int getAmountInCircle() {
        return this.amountInCircle;
    }

    public synchronized void addToCounter(int amountInCircle) {
        this.amountInCircle += amountInCircle;
    }

    public void resetCounter() {
        amountInCircle = 0;
    }
}
