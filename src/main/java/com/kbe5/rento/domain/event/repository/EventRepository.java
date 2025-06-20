package com.kbe5.rento.domain.event.repository;

import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.entity.EventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, EventId> {

}
