package org.chatroomweb.dao;

import org.chatroomweb.request.*;
import org.chatroomweb.response.GetCurrentRoomMembersResponse;
import org.chatroomweb.response.GetRoomsResponse;

import java.util.List;

public interface JDBCController {
    List<GetRoomsResponse> getRooms();

    void addRoom(AddNewRoomRequest newRoom);

    void changeCurrentRoomMembers(ChangeRoomCurrentMembersRequest changeRoomCurrentMembersRequest);

    void decreaseCurrentRoomMembers();

    List<GetCurrentRoomMembersResponse> getCurrentRoomMembersAmount();

    String getUserName(GetUserBySidRequest getUserBySidRequest);

    void addUser(AddNewUserRequest newUser);

    void removeUser(RemoveUserBySidRequest removeUser);
}
