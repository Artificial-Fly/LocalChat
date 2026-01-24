package localchat.demo.service;

import jakarta.transaction.Transactional;
import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.exception.EmptyMessageException;
import localchat.demo.exception.UserNotFoundException;
import localchat.demo.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private int recentMessageDays = 7;
    private int oldMessageDays = 30;

    private static Logger log = LoggerFactory.getLogger(MessageService.class);

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //save message
    public Message saveMessage(String content, User user){
        log.debug("Saving message from user {}", user.getNickname());
        if (content==null||content.trim().isEmpty()){
            log.debug("EmptyMessageException happened");
            throw new EmptyMessageException("Message is empty");
        }
        if(user==null){
            log.debug("UserNotFoundException happened");
            throw new UserNotFoundException("User cannot be empty");
        }
        Message message = new Message();
        message.setContent(content);
        message.setSenderNickname(user.getNickname());
        message.setCreatedAt(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        log.info("Message saved (id={}, nickname={})", savedMessage.getId(), savedMessage.getSenderNickname());
        return savedMessage;
    }
    //get recent (7d)
    public List<Message> getRecentMessages(){
        log.debug("Getting recent ({}) messages", recentMessageDays);
        LocalDateTime timeAgo = LocalDateTime.now().minusDays(recentMessageDays);
        List<Message> recentMessages = messageRepository.findMessageSince(timeAgo);
        log.info("Got {} recent messages", recentMessages.size());
        return recentMessages;
    }
    //get last N messages
    public List<Message> getLastMessages(int count){
        log.debug("Getting last ({}) messages", count);
        List<Message> lastMessages = messageRepository.findLastMessages(count);
        log.info("Got {} last messages", lastMessages.size());
        return lastMessages;
    }
    //clear old messages (30d+)
    @Transactional
    public int clearOldMessages(){
        log.debug("Clearing old ({}) messages", oldMessageDays);
        LocalDateTime timeAgo = LocalDateTime.now().minusDays(oldMessageDays);
        int clearedMessages = messageRepository.deleteOlderThan(timeAgo);
        log.info("Cleared {} messages", clearedMessages);
        return clearedMessages;
    }

}
