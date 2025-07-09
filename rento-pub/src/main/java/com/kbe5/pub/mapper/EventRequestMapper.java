package com.kbe5.pub.mapper;

import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.OffEventCommand;
import com.kbe5.domain.event.dto.EventCommand.OnEventCommand;
import com.kbe5.pub.dto.request.cycleinfo.CycleEventRequest;
import com.kbe5.pub.dto.request.cycleinfo.CycleInfoRequest;
import com.kbe5.pub.dto.request.onoff.OffEventRequest;
import com.kbe5.pub.dto.request.onoff.OnEventRequest;
import java.util.List;

public class EventRequestMapper {

    /**
     * public static DeviceCommand.Register register(DeviceRegisterRequest request) { return
     * DeviceCommand.Register.builder() .mdn(request.mdn()) .terminalId(request.terminalId())
     * .makerId(request.makerId()) .packetVersion(request.packetVersion()) .deviceId(request.deviceId())
     * .companyCode(request.companyCode()) .deviceFirmWareVersion("LTE 1.2") .build(); },
     */
    public static EventCommand.CycleEventCommand cycleEventCommand(CycleEventRequest request) {
        List<EventCommand.CycleInfoCommand> cycleInfoCommands = request.cycleInfoRequests().stream()
            .map(EventRequestMapper::cycleInfoCommand)
            .toList();

        return EventCommand.CycleEventCommand.builder()
            .mdn(request.mdn())
            .terminalId(request.terminalId())
            .makerId(request.makerId())
            .packetVersion(request.packetVersion())
            .deviceId(request.deviceId())
            .oTime(request.oTime())
            .cycleCount(request.cycleCount())
            .cycleInfoCommands(cycleInfoCommands)
            .build();
    }

    public static EventCommand.CycleInfoCommand cycleInfoCommand(CycleInfoRequest request) {
        return EventCommand.CycleInfoCommand.builder()
            .sec(request.sec())
            .gpsCondition(request.gpsCondition())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .angle(request.angle())
            .speed(request.speed())
            .sum(request.sum())
            .battery(request.battery())
            .build();
    }

    public static EventCommand.OnEventCommand onEventCommand(OnEventRequest request) {
        return OnEventCommand.builder()
            .mdn(request.mdn())
            .terminalId(request.terminalId())
            .makerId(request.makerId())
            .packetVersion(request.packetVersion())
            .deviceId(request.deviceId())
            .onTime(request.onTime())
            .offTime(request.offTime())
            .gpsCondition(request.gpsCondition())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .angle(request.angle())
            .speed(request.speed())
            .currentAccumulatedDistance(request.sum())
            .build();
    }

    public static EventCommand.OffEventCommand offEventCommand(OffEventRequest request) {
        return OffEventCommand.builder()
            .mdn(request.mdn())
            .terminalId(request.terminalId())
            .makerId(request.makerId())
            .packetVersion(request.packetVersion())
            .deviceId(request.deviceId())
            .onTime(request.onTime())
            .offTime(request.offTime())
            .gpsCondition(request.gpsCondition())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .angle(request.angle())
            .speed(request.speed())
            .currentAccumulatedDistance(request.sum())
            .build();
    }
}
