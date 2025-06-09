package com.kbe5.rento.domain.event.repository;

import com.kbe5.rento.domain.event.entity.CycleInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CycleInfoJdbcRepositoryImpl implements CycleInfoJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CYCLE_INFO_BULK_INSERT_SQL = "INSERT INTO cycle_info " +
        "(mdn, sec, gps_condition, latitude, longitude, angle, speed, sum, battery) " +
        "VALUES (:mdn, :sec, :gpsCondition, :latitude, :longitude, :angle, :speed, :sum, :battery)";


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
            .addValue("mdn", cycleInfo.getMdn())
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
