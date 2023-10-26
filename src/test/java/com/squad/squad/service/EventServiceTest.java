package com.squad.squad.service;

import com.squad.squad.domain.Category;
import com.squad.squad.domain.Event;
import com.squad.squad.domain.User;
import com.squad.squad.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {
    private EventRepository eventRepository;
    private  EventService eventService;
    @BeforeEach
    void setup(){
        eventRepository = Mockito.mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }
    @Test
    public void saveEvent1() {
        Category category = new Category("WEB");
        Event event = new Event("Event Test 1", new Date(), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", category, null);

        assertThrows(IllegalArgumentException.class, () -> eventService.saveEvent(event), "Organiser is null");
    }

    @Test
    public void saveEvent2() {
        User organiser = new User(1L, "LittleJumper", "John", "Doe", "mjid.elouss@gmail.com", "password");
        Category category = new Category("WEB");
        Event event = new Event("Event Test 1", new Date(), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", category, null);

        Mockito.when(eventRepository.saveEvent(event)).thenReturn(event);
        Event output = eventService.saveEvent(event);

        assertEquals(event, output);
    }

    @Test
    public void saveEvent3() {
        User organiser = new User(1L, "LittleJumper", "John", "Doe", "mjid.elouss@gmail.com", "password");
        Event event = new Event("Event Test 1", new Date(), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", null, organiser);

        assertThrows(IllegalArgumentException.class, () -> eventService.saveEvent(event), "Category is null");
    }

    @Test
    public void saveEvent4() {
        User organiser = new User(1L, "LittleJumper", "John", "Doe", "mjid.elouss@gmail.com", "password");
        Event event = new Event("Event Test 1", new Date(), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", new Category(), organiser);

        assertThrows(IllegalArgumentException.class, () -> eventService.saveEvent(event), "Category is empty");
    }

    @Test
    public void saveEvent5() {
        Category category = new Category("WEB");
        Event event = new Event("Event Test 1", new Date(), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", category, new User());

        assertThrows(IllegalArgumentException.class, () -> eventService.saveEvent(event), "Organiser is empty");
    }

    @Test
    public void saveEvent6() {
        Category category = new Category("WEB");
        User organiser = new User(1L, "LittleJumper", "John", "Doe", "mjid.elouss@gmail.com", "password");
        Event event = new Event("Event Test 1", new Date(122, 10, 25), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", category, organiser);

        assertThrows(IllegalArgumentException.class, () -> eventService.saveEvent(event), "Date is already passed");
    }

    @Test
    void searchEvents() {

    }

    @Test
    void updateEvent() {
        User organiser = new User(1L, "LittleJumper", "John", "Doe", "mjid.elouss@gmail.com", "password");
        Category category = new Category("WEB");
        Event event = new Event(1L,"Event 1", new Date(), Time.valueOf("12:21:21"), "YouCode", "Event 1 Test Description", category, organiser);
        Category updatedCategory = new Category("MOBILE");
        Event updatedEvent = new Event(1L,"Updated Event 1", new Date(), Time.valueOf("13:21:21"), "YouCode", "Event 1 Test Description", updatedCategory, organiser);

        Mockito.when(eventRepository.updateEvent(updatedEvent)).thenReturn(updatedEvent);
        Mockito.when(eventRepository.getEvent(updatedEvent.getId())).thenReturn(updatedEvent);
        Event output = eventService.updateEvent(updatedEvent);

        assertNotEquals(event, output);
    }

    @Test
    void deleteEvent() {
        Event event = new Event();
        event.setId(1L);
        doNothing().when(eventRepository).deleteEvent(event.getId());
        Mockito.when(eventRepository.getEvent(event.getId())).thenReturn(event);
        eventService.deleteEvent(event.getId());
        verify(eventRepository, times(1)).deleteEvent(event.getId());
    }

    @Test
    void getEventsOfOrganiser() {
    }

    @Test
    void getAllEvents() {

    }

    @Test
    void getEventById() {
    }

    @Test
    void getEvent() {
    }
}