package com.songlinzheng.subscription.service;

import com.songlinzheng.subscription.entity.Subscription;
import com.songlinzheng.subscription.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;

@Service
@RequestMapping("/subscription")
@Slf4j
public class SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @PostMapping("/save")
    public ResponseEntity<?>  saveSubscription(@RequestBody Subscription subscription) {
        try {
            subscriptionRepository.save(subscription);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to save subscription.");
        }
        return ResponseEntity.ok("Successfully saved the subscription.");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteSubscription(@RequestBody Subscription subscription) {
        if (subscription.getFromUID() == subscription.getToUID()) {
            return ResponseEntity.badRequest().body("Wrong subscription format.");
        }
        try {
            subscriptionRepository.deleteByFromUIDEqualsAndToUIDEquals(subscription.getFromUID(), subscription.getToUID());
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to delete subscription.");
        }
        return ResponseEntity.ok("Successfully delete the subscription");
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<?> deleteAllSubscription(@RequestParam long fromUID) {
        try {
            subscriptionRepository.deleteByFromUIDEquals(fromUID);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to delete subscription.");
        }
        return ResponseEntity.ok("Successfully delete the subscription");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSubscription(@RequestParam long toUID) {
        return ResponseEntity.ok(subscriptionRepository.findAllByToUID(toUID));
    }
}
