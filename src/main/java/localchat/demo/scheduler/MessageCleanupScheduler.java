package localchat.demo.scheduler;

import localchat.demo.service.MessageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageCleanupScheduler {
    private MessageService messageService;

    public MessageCleanupScheduler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupMessages(){
        int deleted = messageService.clearOldMessages();
        System.out.println("deleted messages: "+deleted);
    }
}
