package com.example.ec.service;

import com.example.ec.domain.Event;
import com.example.ec.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String title, String place, String speaker, String eventType) {
        return eventRepository.findByTitle(title).orElse(eventRepository.save(new Event(title, place, speaker, eventType)));
    }
}
