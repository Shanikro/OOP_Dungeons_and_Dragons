package model.utils.generators;

import java.util.Random;

public class RandomGenerator implements Generator {
    private Random random;

    public RandomGenerator(){
        random = new Random();
    }

    public RandomGenerator(long seed){
        random = new Random(seed);
    } // determinist

    @Override
    public int generate(int value) {
        return random.nextInt(value);
    }
}
