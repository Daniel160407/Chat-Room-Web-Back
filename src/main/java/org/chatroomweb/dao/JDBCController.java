package org.chatroomweb.dao;

import org.chatroomweb.request.AddNewRoomRequest;
import org.chatroomweb.response.GetRoomsResponse;

import java.util.List;

public interface JDBCController {
    List<GetRoomsResponse> getRooms();
    void addRoom(AddNewRoomRequest newRoom);
}
