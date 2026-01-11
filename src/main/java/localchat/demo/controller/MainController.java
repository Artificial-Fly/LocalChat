package localchat.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String goToHome(Model model, HttpServletRequest request) {
        String ipAdress = getIpAdress(request);
        model.addAttribute("title", "Home");
        model.addAttribute("ipAdress", ipAdress);
        return "home";
    }
    @GetMapping("/chat")
    public String goToChat(Model model, HttpServletRequest request) {
        String ipAdress = getIpAdress(request);
        model.addAttribute("title", "Chat");
        model.addAttribute("ipAdress", ipAdress);
        return "chat";
    }
    @GetMapping("/about")
    public String goToAbout(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }
    private String getIpAdress(HttpServletRequest request){
        String clientIp = request.getRemoteAddr();

        if ("0:0:0:0:0:0:0:1".equals(clientIp) || "127.0.0.1".equals(clientIp)) {
            return "127.0.0.1";
        }

        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0].trim();
        }

        return clientIp;
    }
}
