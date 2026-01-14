package localchat.demo.websocket;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    public String uuid;
    public String sender;
    public String content;
    public LocalDateTime createdAt;
}
