
package com.example.webhook.controller;

import com.example.webhook.entity.Event;
import com.example.webhook.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.webhook.entity.Delivery;
import com.example.webhook.entity.Subscriber;
import com.example.webhook.enums.DeliveryStatus;
import com.example.webhook.repository.DeliveryRepository;
import com.example.webhook.repository.SubscriberRepository;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;
    private final DeliveryRepository deliveryRepository;
    private final SubscriberRepository subscriberRepository;

    public EventController(
            EventRepository eventRepository,
            DeliveryRepository deliveryRepository,
            SubscriberRepository subscriberRepository) {

        this.eventRepository = eventRepository;
        this.deliveryRepository = deliveryRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {

        Event savedEvent = eventRepository.save(event);

        List<Subscriber> subscribers = subscriberRepository.findAll();

        for (Subscriber subscriber : subscribers) {
            Delivery delivery = new Delivery();

            delivery.setEvent(savedEvent);
            delivery.setSubscriber(subscriber);
            delivery.setStatus(DeliveryStatus.PENDING);

            deliveryRepository.save(delivery);
        }

        return savedEvent;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
