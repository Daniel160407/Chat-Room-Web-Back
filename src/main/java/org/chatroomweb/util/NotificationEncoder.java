package org.chatroomweb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Encoder;
import org.chatroomweb.message.Message;

public class NotificationEncoder implements Encoder.Text<Message> {
    @Override
    public String encode(Message object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
