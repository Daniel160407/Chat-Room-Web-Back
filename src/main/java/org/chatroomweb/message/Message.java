package org.chatroomweb.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.ToString;
import org.chatroomweb.notification.Notification;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrivateMessage.class, name = "PrivateMessage"),
        @JsonSubTypes.Type(value = PublicMessage.class, name = "PublicMessage"),
        @JsonSubTypes.Type(value = Notification.class, name = "Notification"),
        @JsonSubTypes.Type(value = ChangeUsernameMessage.class, name = "ChangeUsernameMessage")
})
public interface Message {
}