package org.chatroomweb.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrivateMessage.class, name = "PrivateMessage"),
        @JsonSubTypes.Type(value = PublicMessage.class, name = "PublicMessage")
})
public interface Message {
}