package com.kbe5.adapter.dto.response;



import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.entity.GeofenceEvent;
import com.kbe5.domain.event.entity.OnOffEvent;
import com.kbe5.domain.event.enums.GpsCondition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventDataResponse(
        // Event 공통 필드
        Long mdn,
        String terminalId,
        Integer makerId,
        Integer packetVersion,
        Integer deviceId,
        String gpsCondition,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer angle,
        Integer speed,
        Long currentAccumulatedDistance,

        // CycleEvent 필드
        LocalDateTime cycleEventTime,
        Integer cycleCount,

        // GeofenceEvent 필드
        LocalDateTime geofenceEventTime,
        Integer geoGrpId,
        Integer geoPid,
        Integer evtVal,

        // OnOffEvent 필드
        LocalDateTime onTime,
        LocalDateTime offTime,
        Integer batteryVolt,

        // 이벤트 타입 구분
        String eventType
) {

    public static EventDataResponse fromEntity(Event event) {
        return new EventDataResponse(
                event.getMdn(),
                event.getTerminalId(),
                event.getMakerId(),
                event.getPacketVersion(),
                event.getDeviceId(),
                GpsCondition.NORMAL.toString(),
                event.getLatitude(),
                event.getLongitude(),
                event.getAngle(),
                event.getSpeed(),
                event.getCurrentAccumulatedDistance(),

                (event instanceof CycleEvent ce) ? ce.getOTime() : null,
                (event instanceof CycleEvent ce) ? ce.getCycleCount() : null,

                (event instanceof GeofenceEvent ge) ? ge.getOTime() : null,
                (event instanceof GeofenceEvent ge) ? ge.getGeoGrpId() : null,
                (event instanceof GeofenceEvent ge) ? ge.getGeoPid() : null,
                (event instanceof GeofenceEvent ge) ? ge.getEvtVal() : null,

                (event instanceof OnOffEvent oe) ? oe.getOnTime() : null,
                (event instanceof OnOffEvent oe) ? oe.getOffTime() : null,
                (event instanceof OnOffEvent oe) ? oe.getBatteryVolt() : null,

                event.getClass().getSimpleName().toUpperCase()
        );
    }
}
