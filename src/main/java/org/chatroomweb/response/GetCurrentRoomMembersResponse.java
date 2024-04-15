package org.chatroomweb.response;

public record GetCurrentRoomMembersResponse(String roomName, Integer currentMembers) {
}
