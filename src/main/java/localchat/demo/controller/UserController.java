package localchat.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import localchat.demo.entity.User;
import localchat.demo.service.UserService;
import localchat.demo.util.ExtractUuidFromCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    private ExtractUuidFromCookies extractUuidFromCookies;

    public UserController(UserService userService, ExtractUuidFromCookies extractUuidFromCookies) {
        this.userService = userService;
        this.extractUuidFromCookies = extractUuidFromCookies;
    }

    @PostMapping
    public User create(HttpServletRequest request){
        String uuid = extractUuidFromCookies.getUuid(request);
        if(uuid==null || uuid.isBlank()){
            throw new IllegalArgumentException("UUID is missing");
        }
        return userService.CreateUserByUuid(uuid);
    }
    @GetMapping
    public User get(@RequestParam(value="uuid", required = false) String uuid, @RequestParam(value = "nickname", required = false) String nickname){
        if(uuid !=null && !uuid.trim().isEmpty()){
            return userService.getUserByUuid(uuid);
        }else if(nickname!=null && !nickname.trim().isEmpty()){
            return userService.getUserByNickname(nickname);
        }else {
            throw new IllegalArgumentException("ip or nickname is required");
        }

    }

}
