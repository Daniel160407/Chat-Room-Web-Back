package org.chatroomweb.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import org.chatroomweb.model.Room;
import org.chatroomweb.model.User;

@Getter
public class JDBCConnector {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("chat_room");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction entityTransaction = entityManager.getTransaction();

    private final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    private CriteriaQuery<Room> roomCriteriaQuery = criteriaBuilder.createQuery(Room.class);
    private CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

    private Root<Room> roomRoot = roomCriteriaQuery.from(Room.class);
    private Root<User> userRoot = userCriteriaQuery.from(User.class);


    public static JDBCConnector instance;

    public static JDBCConnector getInstance() {
        if (instance == null) {
            instance = new JDBCConnector();
        }
        return instance;
    }

    public void initializeRoomCriteria() {
        roomCriteriaQuery = criteriaBuilder.createQuery(Room.class);
        roomRoot = roomCriteriaQuery.from(Room.class);
    }

    public void initializeUserCriteria() {
        userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        userRoot = userCriteriaQuery.from(User.class);
    }
}