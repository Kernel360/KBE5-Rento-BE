package com.kbe5.domain.firebase.service;

import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.drive.service.DriveReader;
import com.kbe5.domain.firebase.dto.FcmCommand;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.manager.service.ManagerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    private final ManagerReader managerReader;
    private final DriveReader driveReader;
    private final FirebaseSender sender;

    @Override
    public void getDrive(Long driveId) {
        Drive drive = driveReader.getDrive(driveId);

        String vehicleNumber = drive.getVehicle().getInformation().getVehicleNumber();
        List<Manager> managers = managerReader.findAllByCompany(drive.getVehicle().getCompany());
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if(drive.getDriveStatus() == DriveStatus.DRIVING || drive.getDriveStatus() == DriveStatus.COMPLETED) {
            FcmCommand.NotificationRequest request = buildNotificationRequest(
                    drive.getDriveStatus(),
                    vehicleNumber,
                    now
            );
            sender.send(request, managers);
        }
    }

    private FcmCommand.NotificationRequest buildNotificationRequest(DriveStatus driveStatus, String vehicleNumber, String now) {
        String title;
        String body;
        String url = "https://www.rento.world/realtime-event";

        if (driveStatus == DriveStatus.DRIVING) {
            title = "운행 시작";
            body = String.format("%s의 운행이 시작되었습니다.\n%s", vehicleNumber, now);
        } else {
            title = "운행 종료";
            body = String.format("%s의 운행이 종료되었습니다.\n%s", vehicleNumber, now);
        }

        return FcmCommand.NotificationRequest.builder()
                .title(title)
                .body(body)
                .url(url)
                .build();
    }
}
