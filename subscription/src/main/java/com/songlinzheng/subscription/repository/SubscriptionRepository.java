package com.songlinzheng.subscription.repository;

import com.songlinzheng.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select s from Subscription s where s.toUID = ?1")
    List<Subscription> findAllByToUID(Long fromUID);

    @Modifying
    @Query("delete from Subscription s where s.fromUID = ?1 and s.toUID = ?2")
    int deleteByFromUIDEqualsAndToUIDEquals(@NonNull long fromUID, @NonNull long toUID);

    @Modifying
    @Query("delete from Subscription s where s.fromUID = ?1")
    int deleteByFromUIDEquals(@NonNull long fromUID);



}
