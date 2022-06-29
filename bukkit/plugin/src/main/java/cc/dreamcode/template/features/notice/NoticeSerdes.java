package cc.dreamcode.template.features.notice;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class NoticeSerdes implements ObjectSerializer<Notice> {
    @Override
    public boolean supports(@NonNull Class<? super Notice> type) {
        return Notice.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Notice object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("type", object.getType());
        data.add("text", object.getText());

        if (object.getDuration() != 70) {
            data.add("duration", object.getDuration());
        }
    }

    @Override
    public Notice deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        if (data.containsKey("duration")) {
            return new Notice(
                    data.get("type", Notice.Type.class),
                    data.get("text", String.class),
                    data.get("duration", Integer.class)
            );
        }

        return new Notice(
                data.get("type", Notice.Type.class),
                data.get("text", String.class)
        );
    }
}
