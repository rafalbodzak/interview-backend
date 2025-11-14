package com.interview.infrastructure.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationService {

    public void sendAlbumCreatedNotificationEmails(String artistEmail, String albumTitle) {
        log.info("Sending notification to {} about new album: {}", artistEmail, albumTitle);
        
        // Symulacja wysyłki emaila/SMS - długa operacja I/O
        try {
            Thread.sleep(100);  // Symulacja opóźnienia sieci
        } catch (InterruptedException e) {
            throw new RuntimeException("Error sending notification email. ", e);
        }
        
        log.info("Notification sent successfully");
    }
}
