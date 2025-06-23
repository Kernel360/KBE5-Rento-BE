package com.kbe5.domain.event.repository;

import com.kbe5.domain.event.entity.CycleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CycleInfoJdbcRepositoryImpl implements CycleInfoJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CYCLE_INFO_BULK_INSERT_SQL = "INSERT INTO cycle_info " +
        "(cycle_info_time, mdn, drive_id, sec, gps_condition, latitude, longitude, angle, speed, sum, battery) " +
        "VALUES (:cycleInfoTime, :mdn, :driveId,:sec, :gpsCondition, :latitude, :longitude, :angle, :speed, :sum, "
        + ":battery)";


    @Override
    public void bulkInsert(List<CycleInfo> cycleInfoList) {

        SqlParameterSource[] sqlParameterSources = cycleInfoList.stream()
            .map(this::makeCycleInfoParameterSource)
            .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(
            CYCLE_INFO_BULK_INSERT_SQL,
            sqlParameterSources
            );
    }

    private SqlParameterSource makeCycleInfoParameterSource(CycleInfo cycleInfo) {
        return new MapSqlParameterSource()
            .addValue("cycleInfoTime", cycleInfo.getCycleInfoTime())
            .addValue("mdn", cycleInfo.getMdn())
            .addValue("driveId", cycleInfo.getDriveId())
            .addValue("sec", cycleInfo.getSec())
            .addValue("gpsCondition", cycleInfo.getGpsCondition().name())
            .addValue("latitude", cycleInfo.getLatitude())
            .addValue("longitude", cycleInfo.getLongitude())
            .addValue("angle", cycleInfo.getAngle())
            .addValue("speed", cycleInfo.getSpeed())
            .addValue("sum", cycleInfo.getSum())
            .addValue("battery", cycleInfo.getBattery());
    }
}
