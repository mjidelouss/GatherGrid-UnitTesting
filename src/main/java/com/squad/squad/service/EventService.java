package com.squad.squad.service;
import com.squad.squad.domain.User;
import com.squad.squad.repository.EventRepository;

import com.squad.squad.domain.Event;
import com.squad.squad.repository.EventRepository;
import com.squad.squad.repository.UserRepository;

import java.util.Date;
import java.util.List;

public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService = new UserService();

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        validateEventData(event);
        userService.validateUserData(event.getOrganiser());
        return eventRepository.saveEvent(event);
    }


    public List<Event> searchEvents(String name, Date date, String hour, String place) {
        String jpql = "SELECT e FROM Event e WHERE 1=1";
        if (name != null && !name.isEmpty()) {
            jpql += " AND e.name LIKE :name";
        }
        if (date != null) {
            jpql += " AND e.date = :date";
        }
        if (hour != null && !hour.isEmpty()) {
            jpql += " AND e.hour = :hour";
        }
        if (place != null && !place.isEmpty()) {
            jpql += " AND e.place LIKE :place";
        }
        return eventRepository.searchEvents(name, date, hour, place, jpql);
    }
    public Event updateEvent(Event event) {
        Event existingEvent = eventRepository.getEvent(event.getId());
        if (existingEvent == null) {
            throw new IllegalArgumentException("Event with ID " + event.getId() + " does not exist");
        }
        validateEventData(event);
        userService.validateUserData(event.getOrganiser());
        return eventRepository.updateEvent(event);
    }

    public void deleteEvent(Long id) {
        Event existingEvent = eventRepository.getEvent(id);
        if (existingEvent == null) {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist");
        }
        eventRepository.deleteEvent(id);
    }

    public List<Event> getEventsOfOrganiser(User org) {
        if (org == null) {
            throw new IllegalArgumentException("Organizer cannot be null");
        }
        return eventRepository.getEventsOfOrganiser(org);
    }

    public List<Event> getAllEvents() {return eventRepository.getAllEvents();}

    public Event getEventById(Long id) {
        Event event = eventRepository.getEvent(id);
        if (event == null) {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist");
        }
        return event;
    }

    public Event getEvent(Long id) {
        Event event = eventRepository.getEvent(id);
        if (event == null) {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist");
        }
        return event;
    }

    public void validateEventData(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (event.getName() == null || event.getName().isEmpty() || event.getName().isBlank()) {
            throw new IllegalArgumentException("Event name is required");
        }
        if (event.getDate() == null) {
            throw new IllegalArgumentException("Event date is required");
        }
        if (event.getDate().before(new Date())) {
            throw new IllegalArgumentException("Event date is not Valid");
        }
        if (event.getHour() == null) {
            throw new IllegalArgumentException("Event hour is required");
        }
        if (event.getPlace() == null || event.getPlace().isEmpty() || event.getName().isBlank()) {
            throw new IllegalArgumentException("Event place is required");
        }
        if (event.getOrganiser() == null) {
            throw new IllegalArgumentException("Event organizer is required");
        }
        if (event.getCategory() == null || event.getCategory().getName().isEmpty()) {
            throw new IllegalArgumentException("Event Category is required");
        }
    }

}

