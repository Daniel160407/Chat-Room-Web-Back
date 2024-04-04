package org.chatroomweb.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.chatroomweb.notification.Notification;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrivateMessage.class, name = "PrivateMessage"),
        @JsonSubTypes.Type(value = PublicMessage.class, name = "PublicMessage"),
        @JsonSubTypes.Type(value = Notification.class, name = "Notification")
})
public interface Message {
}