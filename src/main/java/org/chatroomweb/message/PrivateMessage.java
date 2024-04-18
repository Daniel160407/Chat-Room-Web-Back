package org.chatroomweb.message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PrivateMessage implements Message {
    private String roomName;
    private String sender;
    private String receiver;
    private String message;
}