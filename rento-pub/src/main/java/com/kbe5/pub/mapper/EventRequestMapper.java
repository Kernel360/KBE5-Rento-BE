package com.kbe5.pub.mapper;

import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.pub.dto.request.cycleinfo.CycleEventRequest;
import com.kbe5.pub.dto.request.cycleinfo.CycleInfoRequest;
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
}
