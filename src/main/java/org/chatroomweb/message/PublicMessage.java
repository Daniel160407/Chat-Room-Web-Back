package org.chatroomweb.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PublicMessage implements Message{
    private String sender;
    private String message;

    @Override
    public String toString() {
        return "PublicMessage{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
