package it.jiniux.gdlp.infrastructure.json;

import it.jiniux.gdlp.core.application.DataAccessProvider;
import it.jiniux.gdlp.core.application.TransactionManager;
import it.jiniux.gdlp.core.domain.BookRepository;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryBookRepository;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryTransactionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonDataAccessProvider implements DataAccessProvider {
    private final JsonRepositoryBookRepository bookRepository;
    private final JsonTransactionManager transactionManager;

    public JsonDataAccessProvider() {
        this.transactionManager = new JsonTransactionManager(new InMemoryTransactionManager());
        this.bookRepository = new JsonRepositoryBookRepository(
                new InMemoryBookRepository(transactionManager.getInMemoryTransactionManager()), initializeFile());
    }

    public static Path getAppDataPath(String appName) {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (os.contains("win")) {
            String appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = Paths.get(userHome, "AppData", "Roaming").toString();
            }
            return Paths.get(appData, appName);
        } else if (os.contains("mac")) {
            return Paths.get(userHome, "Library", "Application Support", appName);
        } else {
            return Paths.get(userHome, ".local", "share", appName);
        }
    }

    private JsonFile initializeFile() {
        try {
            Path appDataPath = getAppDataPath("gdlp");
            Files.createDirectories(appDataPath);

            Path filePath = appDataPath.resolve("books.json");
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            return new JsonFile(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize the data file in the appdata folder", e);
        }
    }

    @Override
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    public void gracefullyClose() {
        InMemoryTransactionManager inMemoryTransactionManager = this.transactionManager.getInMemoryTransactionManager();
        try {
            inMemoryTransactionManager.acquireReentrantLock(false);
            if (inMemoryTransactionManager.getCurrentTransactionState().isPresent()) {
                throw new IllegalStateException("Cannot close data access provider in the middle of a transaction.");
            }

            bookRepository.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            inMemoryTransactionManager.releaseReentrantLock(false);
        }
    }
}
