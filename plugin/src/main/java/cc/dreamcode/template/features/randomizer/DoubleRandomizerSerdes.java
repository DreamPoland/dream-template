package cc.dreamcode.template.features.randomizer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class DoubleRandomizerSerdes implements ObjectSerializer<DoubleRandomizer> {
    @Override
    public boolean supports(@NonNull Class<? super DoubleRandomizer> type) {
        return DoubleRandomizer.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull DoubleRandomizer object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("from", object.getFirstDouble());
        data.add("to", object.getSecondDouble());
    }

    @Override
    public DoubleRandomizer deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new DoubleRandomizer(
                data.get("from", Double.class),
                data.get("to", Double.class)
        );
    }
}
