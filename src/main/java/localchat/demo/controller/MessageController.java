package localchat.demo.controller;

import localchat.demo.entity.Message;
import localchat.demo.entity.User;
import localchat.demo.service.MessageService;
import localchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    //send message
    @PostMapping
    public Message create(@RequestBody Map<String, String> requestBody){
        // get message's content and ip adress
        String content = requestBody.get("content");
        String ipAdress = requestBody.get("ipAdress");
        if(content==null || content.trim().isEmpty()){
            content = "[empty]";
        }
        User user = userService.getUserByIp(ipAdress);
        if(user==null){
            throw new IllegalArgumentException("User cannot be null");
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
