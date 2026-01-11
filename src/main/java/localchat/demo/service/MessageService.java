package localchat.demo.service;

import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private int recentMessageDays = 7;
    private int oldMessageDays = 30;
    //save message
    public Message saveMessage(String content, User user){
        Message message = new Message();
        message.setContent(content);
        message.setSenderNickname(user.getNickname());
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }
    //get recent (7d)
    public List<Message> getRecentMessages(){
        LocalDateTime timeAgo = LocalDateTime.now().minusDays(recentMessageDays);
        return messageRepository.findMessageSince(timeAgo);
    }
    //get last N messages
    public List<Message> getLastMessages(int count){
        return messageRepository.findLastMessages(count);
    }
    //clear old messages (30d+)
    public void clearOldMessages(){
        LocalDateTime timeAgo = LocalDateTime.now().minusDays(oldMessageDays);
        messageRepository.deleteOlderThan(timeAgo);
    }
}
