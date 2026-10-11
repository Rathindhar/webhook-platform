package com.example.webhook.repository;

import com.example.webhook.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
