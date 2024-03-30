package org.chatroomweb.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import org.chatroomweb.model.Room;
import org.chatroomweb.request.AddNewRoomRequest;
import org.chatroomweb.response.GetRoomsResponse;

import java.util.ArrayList;
import java.util.List;

public class MySQLController implements JDBCController {
    private CriteriaQuery<Room> select;
    private TypedQuery<Room> typedQuery;
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();

    @Override
    public List<GetRoomsResponse> getRooms() {
        jdbcConnector.initializeCriteria();

        select = jdbcConnector.getCriteriaQuery().select(
                jdbcConnector.getRoot()
        );

        typedQuery = jdbcConnector.getEntityManager().createQuery(select);

        List<Room> rooms = typedQuery.getResultList();
        List<GetRoomsResponse> roomsResponses = new ArrayList<>();
        rooms.forEach(room -> roomsResponses.add(new GetRoomsResponse(room.getId(), room.getName(), room.getMaxMembers())));

        return roomsResponses;
    }

    @Override
    public void addRoom(AddNewRoomRequest newRoom) {
        jdbcConnector.initializeCriteria();

        try {
            jdbcConnector.getEntityTransaction().begin();

            Room room = new Room(newRoom.name(), newRoom.maxMembers());
            jdbcConnector.getEntityManager().merge(room);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }
}
