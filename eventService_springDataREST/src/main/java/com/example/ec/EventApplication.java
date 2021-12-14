package com.example.ec;

import com.example.ec.service.EventService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class EventApplication implements CommandLineRunner {

    @Value("${ec.importfile}")
    private String importFile;

    @Autowired
    private EventService eventService;

    public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        createEvents(importFile);
    }

    /**
     * Create tour entities from an external file
     */
    private void createEvents(String fileToImport) throws IOException {
        EventFromFile.read(fileToImport).forEach(importedEvent ->
            eventService.createEvent(
                    importedEvent.getTitle(),
                    importedEvent.getPlace(),
                    importedEvent.getEventType(),
                    importedEvent.getSpeaker())
        );
    }

    /**
     * Helper class to import data.json
     */
    private static class EventFromFile {
        //fields
        private String place, title, eventType, speaker;
        //reader
        static List<EventFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<EventFromFile>>() {});
        }
        protected EventFromFile(){}

        String getPlace() { return place; }

        String getTitle() { return title; }

        String getEventType() { return eventType; }

        String getSpeaker() { return speaker; }
    }
}
