package localchat.demo.service;

import jakarta.transaction.Transactional;
import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.exception.EmptyMessageException;
import localchat.demo.exception.UserNotFoundException;
import localchat.demo.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private int recentMessageDays = 7;
    private int oldMessageDays = 30;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //save message
    public Message saveMessage(String content, User user){
        if (content==null||content.trim().isEmpty()){
            throw new EmptyMessageException("error: message is empty");
        }
        if(user==null){
            throw new UserNotFoundException("error: user cannot be empty");
        }
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
    @Transactional
    public int clearOldMessages(){
        LocalDateTime timeAgo = LocalDateTime.now().minusDays(oldMessageDays);
        return messageRepository.deleteOlderThan(timeAgo);
    }

}
