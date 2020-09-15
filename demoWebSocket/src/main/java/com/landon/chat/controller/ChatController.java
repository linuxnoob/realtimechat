package com.landon.chat.controller;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.landon.chat.model.ChatInMessage;
import com.landon.chat.model.ChatOutMessage;

@Controller
public class ChatController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ChatOutMessage greeting(ChatInMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new ChatOutMessage("Hello, " + HtmlUtils.htmlEscape(message.getSenderName()) + "!");
    }
    
    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public ChatOutMessage userTyping(ChatInMessage message) throws Exception {
        return new ChatOutMessage("Someone is typing...");
    }

    @MessageMapping("/guestChat")
    @SendTo("/topic/guestchats")
    public ChatOutMessage handleMessaging(ChatInMessage message) throws Exception{
        Thread.sleep(1000);
        return new ChatOutMessage(message.getMessage());
    }

    @MessageExceptionHandler
    @SendTo("/topic/errors")
    public ChatOutMessage handleException(Throwable exception){

        ChatOutMessage myError = new ChatOutMessage("An error happend");
        return myError;
    }

}
