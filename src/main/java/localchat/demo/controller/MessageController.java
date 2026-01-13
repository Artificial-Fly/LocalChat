package localchat.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.service.MessageService;
import localchat.demo.service.UserService;
import localchat.demo.util.ExtractUuidFromCookies;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    private ExtractUuidFromCookies extractUuidFromCookies;

    public MessageController(MessageService messageService, UserService userService, ExtractUuidFromCookies extractUuidFromCookies) {
        this.messageService = messageService;
        this.userService = userService;
        this.extractUuidFromCookies = extractUuidFromCookies;
    }

    //send message
    @PostMapping
    public Message create(@RequestBody Map<String, String> body, HttpServletRequest request){
        // get message's content and ip adress
        String content = body.get("content");
        //String uuid = body.get("uuid");
        if(content==null || content.trim().isEmpty()){
            content = "[empty]";
        }
        String uuid = extractUuidFromCookies.getUuid(request);
        if(uuid==null){
            throw new IllegalStateException("uuid cockie not found");
        }
        User user = userService.getUserByUuid(uuid);
        if(user==null){
            //throw new IllegalArgumentException("User cannot be null");
        }
        return messageService.saveMessage(content.trim(), user);

    }




    //get recent messages
    @GetMapping
    public List<Message> getMessages(@RequestParam(value = "limit", defaultValue = "10") int limit){
        //insert logic here..
        if(limit>0 && limit < 100){
            return messageService.getLastMessages(limit);
        }
        return messageService.getRecentMessages();
    }
    //delete old messages
    @DeleteMapping
    public void deleteOldMessages(){
        //insert logic here..
        messageService.clearOldMessages();
    }

}
