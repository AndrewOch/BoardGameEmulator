package ru.kpfu.itis.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.kpfu.itis.models.dtos.MessageDto;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.services.interfaces.UsersService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagesWebSocketHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UsersService usersService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        HttpHeaders headers = session.getHandshakeHeaders();
        String messageAsString = (String) message.getPayload();
        MessageDto messageDto = objectMapper.readValue(messageAsString, MessageDto.class);

        User user;
        switch (messageDto.getMessageType()) {
            case "enterChat":
                sessions.put(messageDto.getUsername(), session);
                messageDto.setMessageType("chat");
                messageDto.setBody("вошёл в чат...");
                for (WebSocketSession currentSession : sessions.values()) {
                    currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
                }
                break;
            case "chat":
                for (WebSocketSession currentSession : sessions.values()) {
                    currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
                }
                break;
        }
    }
}