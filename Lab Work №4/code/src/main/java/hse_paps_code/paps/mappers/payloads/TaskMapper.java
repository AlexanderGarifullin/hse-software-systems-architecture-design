package hse_paps_code.paps.mappers.payloads;

import hse_paps_code.paps.entities.Tag;
import hse_paps_code.paps.entities.Task;
import hse_paps_code.paps.transfers.TagPayload;
import hse_paps_code.paps.transfers.TaskPayload;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task payloadToEntity(TaskPayload payload) {
        return Task.builder()
                .name(payload.name())
                .solve(payload.solve())
                .memoryLimit(payload.memoryLimit())
                .timeLimit(payload.timeLimit())
                .statement(payload.statement())
                .build();
    }
}
