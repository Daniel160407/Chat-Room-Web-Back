package org.chatroomweb.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessage implements Message{
    private String sid;
    private String receiver;
    private String message;
}