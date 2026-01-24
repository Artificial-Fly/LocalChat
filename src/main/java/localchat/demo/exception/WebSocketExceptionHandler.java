package localchat.demo.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebSocketExceptionHandler {
    @MessageExceptionHandler(UserNotFoundException.class)
    @SendTo("/topic/errors")
    public String handleUserError(UserNotFoundException e){
        return e.getMessage();
    }
    @MessageExceptionHandler(EmptyMessageException.class)
    @SendTo("/topic/errors")
    public String handleMessageError(EmptyMessageException e){
        return e.getMessage();
    }
}
