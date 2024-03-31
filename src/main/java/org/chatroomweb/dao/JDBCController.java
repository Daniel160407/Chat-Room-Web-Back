package org.chatroomweb.dao;

import org.chatroomweb.request.AddNewRoomRequest;
import org.chatroomweb.request.AddNewUserRequest;
import org.chatroomweb.request.GetUserBySidRequest;
import org.chatroomweb.request.RemoveUserBySidRequest;
import org.chatroomweb.response.GetRoomsResponse;

import java.util.List;

public interface JDBCController {
    List<GetRoomsResponse> getRooms();

    void addRoom(AddNewRoomRequest newRoom);

    String getUserName(GetUserBySidRequest getUserBySidRequest);

    void addUser(AddNewUserRequest newUser);

    void removeUser(RemoveUserBySidRequest removeUser);
}
