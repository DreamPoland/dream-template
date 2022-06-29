package cc.dreamcode.template.features.randomizer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public class DoubleRandomizer {

    private final double firstDouble;
    private final double secondDouble;

    public double randomize() {
        if (this.getFirstDouble() >= this.getSecondDouble()) {
            return this.getSecondDouble();
        }

        return new Random().nextDouble() * (this.getSecondDouble() - this.getFirstDouble()) + this.getFirstDouble();
    }

}
