package com.nam4o.myweb.server.cotroller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Getter
@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerController {

    @GetMapping("/test")
    public String test() {
        return "hi!";
    }
}
