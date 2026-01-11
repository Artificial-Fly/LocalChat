package localchat.demo.controller;

import localchat.demo.entity.User;
import localchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody Map<String, String> requestBody){
        String ipAdress = requestBody.get("IpAdress");
        if (ipAdress==null || ipAdress.trim().isEmpty()){
            throw new IllegalArgumentException("IP adress cannot be null");
        }
        return userService.CreateUserByIp(ipAdress);
    }
    @GetMapping
    public User get(@RequestParam(value="ip", required = false) String ipAdress, @RequestParam(value = "nickname", required = false) String nickname){
        if(ipAdress!=null && !ipAdress.trim().isEmpty()){
            return userService.getUserByIp(ipAdress);
        }else if(nickname!=null && !nickname.trim().isEmpty()){
            return userService.getUserByNickname(nickname);
        }else {
            throw new IllegalArgumentException("ip or nickname is required");
        }

    }

}
