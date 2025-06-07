INSERT INTO device_control_info (ctr_id, ctr_cd, ctr_val) VALUES
                                                              ('1', '05', '120'),
                                                              ('2', '05', '120');

INSERT INTO geofence_control_info (
    geo_ctr_id, up_val, geo_grp_id, geo_evt_tp, geo_range,
    lat, lon, on_time, off_time, store_tp
) VALUES
      ('267', '0', '1', '3', '50', '4140338', '217403', '20210901090000', '20210901235959', '1'),
      ('268', '0', '1', '3', '50', '4140338', '217403', '20210901090000', '20210901235959', '1');
