
package com.example.webhook.controller;

import com.example.webhook.entity.Subscriber;
import com.example.webhook.repository.SubscriberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final SubscriberRepository subscriberRepository;

    public SubscriberController(
            SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscriber createSubscriber(
            @RequestBody Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @GetMapping
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subscriber getSubscriberById(@PathVariable Long id) {
        return subscriberRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Subscriber not found with id: " + id
                        )
                );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscriber(@PathVariable Long id) {
        if (!subscriberRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Subscriber not found with id: " + id
            );
        }

        subscriberRepository.deleteById(id);
    }
}
