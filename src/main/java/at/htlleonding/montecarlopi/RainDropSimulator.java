package at.htlleonding.montecarlopi;

import java.util.concurrent.ThreadLocalRandom;

public class RainDropSimulator implements Runnable {
    private final int raindrops;

    public RainDropSimulator (int raindrops) {
        this.raindrops = raindrops;
    }

    public int getRaindrops() {
        return raindrops;
    }

    @Override
    public void run() {
        int amountInCircle = 0;

        for (int i = 0; i < this.raindrops; i++) {
            double raindropPosX = ThreadLocalRandom.current().nextDouble();
            double raindropPosY = ThreadLocalRandom.current().nextDouble();

            double c = Math.pow(raindropPosX, 2) + Math.pow(raindropPosY, 2);

            if (c <= 1) {
                amountInCircle++;
            }
        }

        RainDropCounter.getInstance().addToCounter(amountInCircle);
    }
}
