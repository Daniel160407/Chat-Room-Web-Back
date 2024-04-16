package org.chatroomweb.message;

import lombok.Getter;

@Getter
public class ChangeUsernameMessage implements Message {
    private String username;
}
