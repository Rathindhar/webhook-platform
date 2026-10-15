package com.example.webhook.service;

import com.example.webhook.entity.Delivery;
import com.example.webhook.entity.Event;
import com.example.webhook.entity.Subscriber;
import com.example.webhook.enums.DeliveryStatus;
import com.example.webhook.repository.DeliveryRepository;
import com.example.webhook.repository.EventRepository;
import com.example.webhook.repository.SubscriberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DeliveryRepository deliveryRepository;
    private final SubscriberRepository subscriberRepository;

    public EventService(
            EventRepository eventRepository,
            DeliveryRepository deliveryRepository,
            SubscriberRepository subscriberRepository) {

        this.eventRepository = eventRepository;
        this.deliveryRepository = deliveryRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @Transactional
    public Event createEvent(Event event) {

        Event savedEvent = eventRepository.save(event);

        List<Subscriber> subscribers =
                subscriberRepository.findAll();

        for (Subscriber subscriber : subscribers) {
            Delivery delivery = new Delivery();

            delivery.setEvent(savedEvent);
            delivery.setSubscriber(subscriber);
            delivery.setStatus(DeliveryStatus.PENDING);

            deliveryRepository.save(delivery);
        }

        return savedEvent;
    }
}
