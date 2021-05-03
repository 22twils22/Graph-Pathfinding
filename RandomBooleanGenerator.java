import java.util.Random;

public class RandomBooleanGenerator {

    private double probTrue;
    private Random random;

    public RandomBooleanGenerator(double probTrue) {
        this.probTrue = probTrue;
        random = new Random();
    }

    public boolean getRandomBool() {
        return random.nextDouble() < probTrue;
    }

    public double getProbabilityOfTrue() {
        return probTrue;
    }

}
