package foxgem;

public class MockSystem {

    public boolean succeed() {
        return true;
    }

    public void fail() {
        throw new UnknownError();
    }

    public boolean maySucceed() {
        double random = Math.random();

        System.out.format("Random in maySucceed: %f\n", random);

        if (random < 0.5) {
            return true;
        } else if (random > 0.7 && random < 0.8) {
            try {
                Thread.currentThread().sleep(1200);
            } catch (InterruptedException e) {
                ;
            }

            System.out.println("just slept 1200ms");
            return true;
        } else {
            throw new UnknownError();
        }
    }

}
