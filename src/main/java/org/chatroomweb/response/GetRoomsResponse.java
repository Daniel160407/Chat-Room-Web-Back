package org.chatroomweb.response;

public record GetRoomsResponse(Integer id, String name, Integer maxMembers, Integer currentMembers) {
}
