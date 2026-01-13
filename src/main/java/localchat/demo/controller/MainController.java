package localchat.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import localchat.demo.util.ExtractUuidFromCookies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class MainController {



    //private static final String COOKIE_NAME = "chat_uuid";
    private ExtractUuidFromCookies extractUuidFromCookies;

    public MainController(ExtractUuidFromCookies extractUuidFromCookies) {
        this.extractUuidFromCookies = extractUuidFromCookies;
    }

    @GetMapping("/")
    public String goToHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        String uuid = getUuid(request, response);//getUuid(request);
        model.addAttribute("title", "Home");
        model.addAttribute("uuid", uuid);
        return "home";
    }
    @GetMapping("/chat")
    public String goToChat(Model model, HttpServletRequest request, HttpServletResponse response) {
        String uuid = getUuid(request, response);
        model.addAttribute("title", "Chat");
        model.addAttribute("uuid", uuid);
        return "chat";
    }
    @GetMapping("/about")
    public String goToAbout(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }
    private String getUuid(HttpServletRequest request, HttpServletResponse response){
//        if(request.getCookies()!=null){
//            for(Cookie cookie: request.getCookies()){
//                if(COOKIE_NAME.equals(cookie.getName())){
//                    return cookie.getValue();
//                }
//            }
//        }
        if(extractUuidFromCookies.getUuid(request)!=null){
            return extractUuidFromCookies.getUuid(request);
        }
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(extractUuidFromCookies.COOKIE_NAME, uuid);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*365);
        response.addCookie(cookie);
        return uuid;
    }
//    private String getUuid(HttpServletRequest request){
//        String clientIp = request.getRemoteAddr();
//
//        if ("0:0:0:0:0:0:0:1".equals(clientIp) || "127.0.0.1".equals(clientIp)) {
//            return "127.0.0.1";
//        }
//
////        String forwarded = request.getHeader("X-Forwarded-For");
////        if (forwarded != null && !forwarded.isEmpty()) {
////            return forwarded.split(",")[0].trim();
////        }
//
//        return clientIp;
//    }
}
