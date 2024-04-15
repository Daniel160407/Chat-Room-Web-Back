package org.chatroomweb.message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PublicMessage implements Message{
    private String roomName;
    private String sender;
    private String message;
}
