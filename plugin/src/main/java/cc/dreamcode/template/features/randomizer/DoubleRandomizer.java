package cc.dreamcode.template.features.randomizer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public class DoubleRandomizer {

    private final double firstDouble;
    private final double secondDouble;

    public static DoubleRandomizer of(double firstDouble, double secondDouble) {
        return new DoubleRandomizer(firstDouble, secondDouble);
    }

    public double randomize() {
        if (this.getFirstDouble() >= this.getSecondDouble()) {
            return this.getSecondDouble();
        }

        return new Random().doubles(this.getFirstDouble(), this.getSecondDouble())
                .findAny()
                .orElse(this.getSecondDouble());
    }

}
