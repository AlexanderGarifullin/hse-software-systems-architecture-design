package hse_paps_code.paps.controllers;

import hse_paps_code.paps.entities.Tag;
import hse_paps_code.paps.services.TagService;
import hse_paps_code.paps.transfers.TagPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Tag>> getTagsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(tagService.getTagsByTask(taskId));
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<Tag> createTag(@PathVariable Long taskId, @RequestBody TagPayload tag) {
        return ResponseEntity.ok(tagService.createTag(taskId, tag));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long tagId, @RequestBody TagPayload tag) {
        return ResponseEntity.ok(tagService.updateTag(tagId, tag));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }
}
