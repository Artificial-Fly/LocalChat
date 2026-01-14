package localchat.demo.controller;

import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.service.MessageService;
import localchat.demo.service.UserService;
import localchat.demo.util.ExtractUuidFromCookies;
import localchat.demo.websocket.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {
    private MessageService messageService;
    private UserService userService;
    private ExtractUuidFromCookies extractUuidFromCookies;

    public MessageWebSocketController(MessageService messageService, UserService userService, ExtractUuidFromCookies extractUuidFromCookies) {
        this.messageService = messageService;
        this.userService = userService;
        this.extractUuidFromCookies = extractUuidFromCookies;
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    public ChatMessageDTO send(ChatMessageDTO incoming){
        System.out.println("WS Message received: "+incoming.content);
        User user = userService.getUserByUuid(incoming.uuid);
        if(user == null){
            throw new IllegalArgumentException("user is not found");
        }
        Message message = messageService.saveMessage(incoming.content, user);
        ChatMessageDTO out = new ChatMessageDTO();
        out.content = message.getContent();
        out.sender = message.getSenderNickname();
        out.createdAt = message.getCreatedAt();
        return out;
    }
}
