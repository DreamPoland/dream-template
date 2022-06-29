package cc.dreamcode.template.features.randomizer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public class IntRandomizer {

    private final int firstInteger;
    private final int secondInteger;

    public int randomize() {
        if (this.getFirstInteger() >= this.getSecondInteger()) {
            return this.getSecondInteger();
        }

        return new Random().nextInt() * (this.getSecondInteger() - this.getFirstInteger()) + this.getFirstInteger();
    }

}
