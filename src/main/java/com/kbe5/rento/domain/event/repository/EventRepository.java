package com.kbe5.rento.domain.event.repository;

import com.kbe5.rento.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    /*@Query("""
      select e.currentAccumulatedDistance
      from OnOffEvent e
      where e.mdn       = :mdn
        and e.offTime  between :start and :end
      order by e.offTime desc
    """)
    Optional<Long> findLastOffDistance(
            @Param("mdn")   Long mdn,
            @Param("start") LocalDateTime start,
            @Param("end")   LocalDateTime end
    );*/

}
