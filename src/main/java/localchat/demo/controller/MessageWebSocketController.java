package localchat.demo.controller;

import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.service.MessageService;
import localchat.demo.service.UserService;
import localchat.demo.util.ExtractUuidFromCookies;
import localchat.demo.dto.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {
    private MessageService messageService;
    private UserService userService;
    private ExtractUuidFromCookies extractUuidFromCookies;

    private static Logger log = LoggerFactory.getLogger(MessageWebSocketController.class);

    public MessageWebSocketController(MessageService messageService, UserService userService, ExtractUuidFromCookies extractUuidFromCookies) {
        this.messageService = messageService;
        this.userService = userService;
        this.extractUuidFromCookies = extractUuidFromCookies;
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    public ChatMessageDTO send(ChatMessageDTO incoming){
        log.debug("receiving ws message: {} ", incoming.content);
        User user = userService.getUserByUuid(incoming.uuid);
        if(user == null){
            log.debug("IllegalArgumentException: user not found");
            throw new IllegalArgumentException("user not found");
        }
        Message message = messageService.saveMessage(incoming.content, user);
        ChatMessageDTO out = new ChatMessageDTO();
        out.content = message.getContent();
        out.sender = message.getSenderNickname();
        out.createdAt = message.getCreatedAt();
        log.info("received ws message");
        return out;
    }
}
