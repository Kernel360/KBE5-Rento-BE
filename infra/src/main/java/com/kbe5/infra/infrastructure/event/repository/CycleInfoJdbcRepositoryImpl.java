package com.kbe5.infra.infrastructure.event.repository;

import com.kbe5.domain.event.entity.CycleData;
import io.hypersistence.tsid.TSID;
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
        "(tsid, cycle_info_time, mdn, drive_id, sec, gps_condition, latitude, longitude, angle, speed, sum, battery) " +
        "VALUES (:tsid, :cycleInfoTime, :mdn, :driveId,:sec, :gpsCondition, :latitude, :longitude, :angle, :speed, "
        + ":sum, :battery)";


    @Override
    public void bulkInsert(List<CycleData> cycleDataList) {

        SqlParameterSource[] sqlParameterSources = cycleDataList.stream()
            .map(this::makeCycleInfoParameterSource)
            .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(
            CYCLE_INFO_BULK_INSERT_SQL,
            sqlParameterSources
            );
    }

    private SqlParameterSource makeCycleInfoParameterSource(CycleData cycleData) {
        return new MapSqlParameterSource()
            .addValue("tsid", TSID.Factory.getTsid().toLong())
            .addValue("cycleInfoTime", cycleData.getCycleInfoTime())
            .addValue("mdn", cycleData.getMdn())
            .addValue("driveId", cycleData.getDriveId())
            .addValue("sec", cycleData.getSec())
            .addValue("gpsCondition", cycleData.getGpsCondition().name())
            .addValue("latitude", cycleData.getLatitude())
            .addValue("longitude", cycleData.getLongitude())
            .addValue("angle", cycleData.getAngle())
            .addValue("speed", cycleData.getSpeed())
            .addValue("sum", cycleData.getSum())
            .addValue("battery", cycleData.getBattery());
    }
}
