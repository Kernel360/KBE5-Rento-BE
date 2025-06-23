package com.kbe5.api.domain.stream.controller;


import com.kbe5.api.domain.stream.service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamService streamService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(){
        return streamService.subscribe();
    }


}
