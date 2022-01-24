package com.example.ec.web;

import com.example.ec.domain.Event;
import com.example.ec.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/events")
public class EventController {
    private EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    protected EventController(){}

    /**
     * Create new Event.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody Event event) {
        eventRepository.save(event);
    }

    /**
     * Lookup for all events.
     *
     * @return List of all Event's.
     */
    @GetMapping
    public List<Event> getAllEvents() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * Update already existing event or throw NoSuchElementException.
     */
    @PutMapping
    public void updateEvent(@RequestBody Event event) {
        verifyEvent(event.getId());
        eventRepository.save(event);
    }

    /**
     * Delete Event by ID.
     *
     * @param eventId Event identifier.
     */
    @DeleteMapping(path = "/{eventId}")
    public void delete(@PathVariable(value = "eventId") int eventId) {
        Event event = verifyEvent(eventId);
        eventRepository.delete(event);
    }

    @GetMapping(path = "/{title}")
    public List<Event> getAllEventsByTitle(@PathVariable(value = "title") String title) throws NoSuchElementException {
        Optional<List<Event>> optionalEvents = eventRepository.findAllByTitle(title);
        if (optionalEvents.isPresent()) {
            return optionalEvents.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller.
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

    private Event verifyEvent(Integer id) throws NoSuchElementException {
        return eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Event with id=" + id + " not found."));
    }
}
