package it.jiniux.gdlp.infrastructure.json;

import it.jiniux.gdlp.core.domain.Book;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonFile implements AutoCloseable {
    private final RandomAccessFile file;

    private final FileChannel channel;
    private final FileLock lock;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonFile(File file) throws IOException {
        this.file = new RandomAccessFile(file, "rw");
        this.channel = this.file.getChannel();
        this.lock = channel.lock();

        if (file.length() == 0) {
            objectMapper.writeValue(file, List.of());
        }
    }

    public void update(List<Book> books) {
        try {
            file.setLength(0); // clear the file
            file.seek(0);

            List<JsonBookData> jsonBookData = books.stream()
                    .map(b -> JsonBookDataMapper.getInstance().toJsonBookData(b))
                    .toList();

            objectMapper.writeValue(file, jsonBookData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> load() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            file.seek(0);
            List<JsonBookData> dataBooks = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, JsonBookData.class));
            List<Book> books = new ArrayList<>();

            for (JsonBookData jsonBookData : dataBooks) {
                books.add(JsonBookDataMapper.getInstance().toBook(jsonBookData));
            }

            return books;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load books from JSON file", e);
        }
    }

    @Override
    public void close() throws Exception {
        lock.release();
        channel.close();
        file.close();
    }
}
