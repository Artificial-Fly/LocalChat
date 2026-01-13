package localchat.demo.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public final class ExtractUuidFromCookies {
    public static final String COOKIE_NAME = "chat_uuid";
    public String getUuid(HttpServletRequest request) {
        if(request.getCookies()!=null){
            for(Cookie cookie: request.getCookies()){
                if(COOKIE_NAME.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
        //throw new IllegalStateException("UUID cokie has not been found");
    }
}
