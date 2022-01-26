package com.example.ec.service;

import com.example.ec.domain.Event;
import com.example.ec.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventService {
    @Value("${ec.importfile}")
    private String importFile;

    @Autowired
    private EventRepository eventRepository;

    public Event createEventEntity(String title, String place, String speaker, String eventType) {
        return eventRepository.findByTitle(title).orElse(eventRepository.save(new Event(title, place, speaker, eventType)));
    }

    /**
     * Create tour entities from an external file
     */
    @PostConstruct
    public void createEvents() throws IOException {
        Event.read(importFile).forEach(importedEvent ->
                createEventEntity(
                        importedEvent.getTitle(),
                        importedEvent.getPlace(),
                        importedEvent.getEventType(),
                        importedEvent.getSpeaker())
        );
    }


    // API Implementation

    public void save(Event event) {
        eventRepository.save(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public Event verifyEvent(Integer id) throws NoSuchElementException {
        return eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Event with id=" + id + " not found."));
    }

    public List<Event> getAllEvents() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Event> getAllEventsByTitle(String title) throws NoSuchElementException {
        List<Event> events = eventRepository.findAllByTitle(title);
        if (!events.isEmpty()) {
            return events;
        } else {
            throw new NoSuchElementException();
        }
    }
}
