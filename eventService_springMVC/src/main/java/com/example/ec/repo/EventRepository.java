package com.example.ec.repo;

import com.example.ec.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8082")
public interface EventRepository extends CrudRepository<Event, Integer> {
    /**
     * Find Event by title.
     *
     * @param title event title
     * @return Optional of Event
     */
    Optional<Event> findByTitle(@Param("title")String title);

    /**
     * Find all Events with provided title.
     *
     * @param title event title
     * @return Optional of List<Event>
     */
    Optional<List<Event>> findAllByTitle(@Param("title")String title);
}
