package com.kbe5.rento.domain.manager.dto.response;

import com.kbe5.rento.domain.manager.entity.Manager;

import java.util.List;

public record ManagerListResponse(
        List<Manager> mangeerList
) {
}
