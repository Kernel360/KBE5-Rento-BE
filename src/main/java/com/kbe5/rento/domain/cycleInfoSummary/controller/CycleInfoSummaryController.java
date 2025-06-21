package com.kbe5.rento.domain.cycleInfoSummary.controller;

import com.kbe5.rento.domain.cycleInfoSummary.service.CycleInfoSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cycleInfoSummary")
public class CycleInfoSummaryController {

    private final CycleInfoSummaryService cycleInfoSummaryService;

    @PostMapping()
    public void create(@RequestParam Long driveId){
        cycleInfoSummaryService.create(driveId);
    }
}
