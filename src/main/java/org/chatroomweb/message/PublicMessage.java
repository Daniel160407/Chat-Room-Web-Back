package org.chatroomweb.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicMessage implements Message{
    private String sid;
    private String message;
}
