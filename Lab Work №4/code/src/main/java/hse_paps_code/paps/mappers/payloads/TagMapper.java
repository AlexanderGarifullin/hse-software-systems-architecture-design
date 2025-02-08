package hse_paps_code.paps.mappers.payloads;

import hse_paps_code.paps.entities.Tag;
import hse_paps_code.paps.transfers.TagPayload;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag payloadToEntity(TagPayload payload) {
        return Tag.builder()
                .name(payload.name())
                .build();
    }
}
