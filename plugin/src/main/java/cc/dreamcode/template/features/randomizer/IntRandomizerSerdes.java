package cc.dreamcode.template.features.randomizer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class IntRandomizerSerdes implements ObjectSerializer<IntRandomizer> {
    @Override
    public boolean supports(@NonNull Class<? super IntRandomizer> type) {
        return IntRandomizer.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull IntRandomizer object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("from", object.getFirstInteger());
        data.add("to", object.getSecondInteger());
    }

    @Override
    public IntRandomizer deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new IntRandomizer(
                data.get("from", Integer.class),
                data.get("to", Integer.class)
        );
    }
}
