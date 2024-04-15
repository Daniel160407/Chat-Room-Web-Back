package org.chatroomweb.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import org.chatroomweb.model.Room;
import org.chatroomweb.model.User;
import org.chatroomweb.request.*;
import org.chatroomweb.response.GetCurrentRoomMembersResponse;
import org.chatroomweb.response.GetRoomsResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class MySQLController implements JDBCController {
    private final JDBCConnector jdbcConnector = JDBCConnector.getInstance();

    @Override
    public List<GetRoomsResponse> getRooms() {
        jdbcConnector.initializeRoomCriteria();

        CriteriaQuery<Room> select = jdbcConnector.getRoomCriteriaQuery().select(
                jdbcConnector.getRoomRoot()
        );

        TypedQuery<Room> typedQuery = jdbcConnector.getEntityManager().createQuery(select);

        List<Room> rooms = typedQuery.getResultList();
        List<GetRoomsResponse> roomsResponses = new ArrayList<>();
        rooms.forEach(room -> roomsResponses.add(new GetRoomsResponse(room.getId(), room.getName(), room.getMaxMembers(), room.getCurrentMembers())));

        return roomsResponses;
    }

    @Override
    public void addRoom(AddNewRoomRequest newRoom) {
        jdbcConnector.initializeRoomCriteria();

        try {
            jdbcConnector.getEntityTransaction().begin();

            Room room = new Room(newRoom.name(), newRoom.maxMembers(), newRoom.currentMembers());
            jdbcConnector.getEntityManager().merge(room);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }

    @Override
    public void changeCurrentRoomMembers(ChangeRoomCurrentMembersRequest changeRoomCurrentMembersRequest) {
        jdbcConnector.initializeRoomCriteria();

        Room roomToBeUpdated = null;
        try {
            CriteriaQuery<Room> select = jdbcConnector.getRoomCriteriaQuery().select(
                    jdbcConnector.getRoomRoot()
            ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getRoomRoot().get("name"), changeRoomCurrentMembersRequest.name()));

            TypedQuery<Room> typedQuery = jdbcConnector.getEntityManager().createQuery(select);

            roomToBeUpdated = typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (roomToBeUpdated != null) {
            try {
                jdbcConnector.getEntityTransaction().begin();

                roomToBeUpdated.setCurrentMembers(changeRoomCurrentMembersRequest.amount());

                jdbcConnector.getEntityTransaction().commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (jdbcConnector.getEntityTransaction().isActive()) {
                    jdbcConnector.getEntityTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void decreaseCurrentRoomMembers(){
        jdbcConnector.initializeRoomCriteria();


    }

    @Override
    public List<GetCurrentRoomMembersResponse> getCurrentRoomMembersAmount() {
        jdbcConnector.initializeRoomCriteria();

        try {
            CriteriaQuery<Room> select = jdbcConnector.getRoomCriteriaQuery().select(
                    jdbcConnector.getRoomRoot()
            );

            TypedQuery<Room> typedQuery = jdbcConnector.getEntityManager().createQuery(select);
            List<Room> rooms = typedQuery.getResultList();
            List<GetCurrentRoomMembersResponse> membersResponses = new ArrayList<>();
            rooms.forEach(room -> membersResponses.add(new GetCurrentRoomMembersResponse(room.getName(), room.getCurrentMembers())));
            return membersResponses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String getUserName(GetUserBySidRequest getUserBySidRequest) {
        jdbcConnector.initializeUserCriteria();

        System.out.println(getUserBySidRequest.sid());
        try {
            CriteriaQuery<User> select = jdbcConnector.getUserCriteriaQuery().select(
                    jdbcConnector.getUserRoot()
            ).where(jdbcConnector.getCriteriaBuilder().equal(jdbcConnector.getUserRoot().get("sid"), getUserBySidRequest.sid()));

            TypedQuery<User> typedQuery = jdbcConnector.getEntityManager().createQuery(select);

            User user = typedQuery.getSingleResult();
            return user.getUserName();
        } catch (Exception e) {
            return "User";
        }
    }

    @Override
    public void addUser(AddNewUserRequest newUser) {
        jdbcConnector.initializeUserCriteria();

        try {
            jdbcConnector.getEntityTransaction().begin();

            User user = new User(newUser.sid(), newUser.userName());
            jdbcConnector.getEntityManager().merge(user);

            jdbcConnector.getEntityTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (jdbcConnector.getEntityTransaction().isActive()) {
                jdbcConnector.getEntityTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUser(RemoveUserBySidRequest removeUser) {
        jdbcConnector.initializeUserCriteria();

        User userToBeRemoved = jdbcConnector.getEntityManager().find(User.class, removeUser.Sid());

        if (userToBeRemoved != null) {
            jdbcConnector.getEntityManager().remove(userToBeRemoved);
        }
    }
}
