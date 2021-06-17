package com.example.loggingconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

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
