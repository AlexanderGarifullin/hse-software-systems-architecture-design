package com.fin.service;

import com.fin.entity.Test;
import com.fin.repository.TestRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExportServiceTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private ExportService exportService;

    @org.junit.jupiter.api.Test
    public void exportTests_withTests_returnsZipArchive() throws IOException {
        Test test1 = new Test();
        test1.setId(1);
        test1.setInput("Input1");

        Test test2 = new Test();
        test2.setId(2);
        test2.setInput("Input2");

        List<Test> tests = Arrays.asList(test1, test2);
        when(testRepository.findByTaskId(1L)).thenReturn(tests);

        byte[] zipBytes = exportService.exportTests(1L);

        assertNotNull(zipBytes);
        assertTrue(zipBytes.length > 0);

        Map<String, String> files = extractZipEntries(zipBytes);
        assertEquals(2, files.size(), "Ожидается 2 файла в архиве");
        assertEquals("Input1", files.get("test_1.txt"));
        assertEquals("Input2", files.get("test_2.txt"));
    }

    @org.junit.jupiter.api.Test
    public void exportTests_withNoTests_returnsEmptyZipArchive() throws IOException {
        when(testRepository.findByTaskId(1L)).thenReturn(Collections.emptyList());

        byte[] zipBytes = exportService.exportTests(1L);

        Map<String, String> files = extractZipEntries(zipBytes);
        assertTrue(files.isEmpty(), "Ожидается пустой ZIP архив");
    }

    @org.junit.jupiter.api.Test
    public void exportTestsAsync_withTests_returnsZipArchive() throws Exception {
        Test test1 = new Test();
        test1.setId(1);
        test1.setInput("AsyncInput1");
        when(testRepository.findByTaskId(2L)).thenReturn(Collections.singletonList(test1));


        CompletableFuture<byte[]> future = exportService.exportTestsAsync(2L);
        byte[] zipBytes = future.get();


        Map<String, String> files = extractZipEntries(zipBytes);
        assertEquals(1, files.size(), "Ожидается 1 файл в архиве");
        assertEquals("AsyncInput1", files.get("test_1.txt"));
    }

    private Map<String, String> extractZipEntries(byte[] zipBytes) throws IOException {
        Map<String, String> files = new HashMap<>();
        ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                byte[] data = zis.readAllBytes();
                files.put(entry.getName(), new String(data, StandardCharsets.UTF_8));
                zis.closeEntry();
            }
        }
        return files;
    }
}