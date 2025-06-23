package com.kbe5.domain.event.repository;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.entity.EventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, EventId> {

}
