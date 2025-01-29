package hse_paps_code.paps.services;

import hse_paps_code.paps.entities.Tag;
import hse_paps_code.paps.mappers.payloads.TagMapper;
import hse_paps_code.paps.repositories.TagRepository;
import hse_paps_code.paps.repositories.TaskRepository;
import hse_paps_code.paps.transfers.TagPayload;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final TaskRepository taskRepository;

    private final TagMapper tagMapper;

    public List<Tag> getTagsByTask(Long taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        return tagRepository.findByTaskId(taskId);
    }

    public Tag createTag(Long taskId, TagPayload payload) {
        Tag tag = tagMapper.payloadToEntity(payload);

        tag.setTask(taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found")));
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tagId, TagPayload updatedTag) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tag.setName(updatedTag.name());
        return tagRepository.save(tag);
    }

    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tagRepository.delete(tag);
    }
}
