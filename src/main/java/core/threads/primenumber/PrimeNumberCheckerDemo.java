package core.threads.primenumber;

/**
 * PrimeNumberCheckerDemo performs the long lasting operation to check if a number is a prime-number within extra threads.
 */
public class PrimeNumberCheckerDemo extends Thread {
    private long n;

    public PrimeNumberCheckerDemo(long n) {
        this.n = n;
    }

    public static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // IGNORED
            }
        }

        return true;
    }

    @Override
    public void run() {
        if (isPrime(n))
            System.out.println(n + " is a prime number");
        else
            System.out.println(n + " is not a prime number");
    }

    public static void main(String[] args) throws InterruptedException {
        PrimeNumberCheckerDemo t1 = new PrimeNumberCheckerDemo(945374839411l);
        PrimeNumberCheckerDemo t2 = new PrimeNumberCheckerDemo(17l);
        PrimeNumberCheckerDemo t3 = new PrimeNumberCheckerDemo(7329873498l);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}
