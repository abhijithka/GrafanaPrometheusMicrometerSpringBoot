package com.example.labelapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/echo")
    public String echo() {
        streamBridge.send("process-out-0", "static stream");
        streamBridge.send("app1", "dynamic stream");
        return "Works";
    }
}
