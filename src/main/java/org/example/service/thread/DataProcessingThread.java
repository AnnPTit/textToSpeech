package org.example.service.thread;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataProcessingThread extends Thread{

    @Scheduled(fixedRate = 10000)
    public void processDataEvery10Sec() {
        System.out.println("Xử lý dữ liệu lúc " + java.time.LocalTime.now());
    }
}
