package at.htlleonding.montecarlopi;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the number of rain drops to simulate: ");
        int raindropsToGenerate = scanner.nextInt();

        System.out.println("Please enter number of threads to use: ");
        int threadsToUse = scanner.nextInt();

        System.out.println("====================");
        System.out.printf("Performing sequential calculation with %d rain drops.\n", raindropsToGenerate);
        Thread singleThread = new Thread(new RainDropSimulator(raindropsToGenerate));
        long startTime = System.nanoTime();
        singleThread.start();

        try {
            singleThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.nanoTime();
        double approximatelyPie = (double) (4 * RainDropCounter.getInstance().getAmountInCircle()) / raindropsToGenerate;

        System.out.printf("Result: %f\n", approximatelyPie);
        System.out.printf("Elapsed Time: %d ns\n", endTime - startTime);
        System.out.println("====================");

        // RESET STATISTICS
        RainDropCounter.getInstance().resetCounter();
        System.out.println();

        System.out.println("====================");
        System.out.printf("Performing threaded calculation with %d rain drops and %d threads.\n", raindropsToGenerate, threadsToUse);

        // START THREADS
        long[] threadStartTime = new long[threadsToUse];
        Thread[] totalThreadsToUse = new Thread[threadsToUse];
         for(int i = 0; i < totalThreadsToUse.length; i++){
             RainDropSimulator rainDropCounter = new RainDropSimulator(raindropsToGenerate);
             totalThreadsToUse[i] = new Thread(rainDropCounter);

             long st = System.nanoTime();
             threadStartTime[i] = st;
             totalThreadsToUse[i].start();
         }

         // AWAIT THREADS
        long[] threadEndTime = new long[threadsToUse];
        for (int i = 0; i < totalThreadsToUse.length; i++) {
            try {
                totalThreadsToUse[i].join();
                long et = System.nanoTime();
                threadEndTime[i] = et;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // RESULTS
        double approxPie = (double) (4 * RainDropCounter.getInstance().getAmountInCircle()) / raindropsToGenerate / threadsToUse;

        long totalElapsedTime = 0;
        for (int i = 0; i < threadStartTime.length; i++) {
            totalElapsedTime += threadEndTime[i] - threadStartTime[i];
        }

        System.out.printf("Result: %f\n", approxPie);
        System.out.printf("Elapsed Time: %d ns\n", totalElapsedTime);

        System.out.println("====================");
    }
}