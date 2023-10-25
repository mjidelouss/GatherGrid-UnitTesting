package com.squad.squad.service;

import com.squad.squad.domain.Event;
import com.squad.squad.domain.User;
import com.squad.squad.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
        eventService = new EventService(eventRepository);
    }

    @Test
    void updateEvent() {
        // Arrange
        Event event = new Event();
        event.setId(1L);

        // Mock the behavior of the repository
        when(eventRepository.getEvent(1L)).thenReturn(event);
        when(eventRepository.updateEvent(event)).thenReturn(event);

        // Act
        Event updatedEvent = eventService.updateEvent(event);

        // Assert
        assertEquals(event, updatedEvent);
    }

    @Test
    void deleteEvent() {
        // Arrange
        Long eventId = 1L;

        // Mock the behavior of the repository
        when(eventRepository.getEvent(eventId)).thenReturn(new Event());

        // Act
        assertDoesNotThrow(() -> eventService.deleteEvent(eventId));

        // Assert
        // You can add assertions based on the behavior you expect.
    }

    @Test
    void getEventById() {
        // Arrange
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);

        // Mock the behavior of the repository
        when(eventRepository.getEvent(eventId)).thenReturn(event);

        // Act
        Event retrievedEvent = eventService.getEventById(eventId);

        // Assert
        assertEquals(event, retrievedEvent);
    }

    @Test
    void getEventsOfOrganiser() {
        // Arrange
        User organizer = new User();

        // Mock the behavior of the repository
        when(eventRepository.getEventsOfOrganiser(organizer)).thenReturn(List.of(new Event(), new Event()));

        // Act
        List<Event> events = eventService.getEventsOfOrganiser(organizer);

        // Assert
        assertEquals(2, events.size());
    }

    @Test
    void getAllEvents() {
        // Mock the behavior of the repository
        when(eventRepository.getAllEvents()).thenReturn(List.of(new Event(), new Event()));

        // Act
        List<Event> events = eventService.getAllEvents();

        // Assert
        assertEquals(2, events.size());
    }

    // You can write similar test cases for other methods like saveEvent, getEvent, and searchEvents.
}
