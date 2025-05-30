package com.kbe5.rento.domain.event.repository;

import com.kbe5.rento.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
