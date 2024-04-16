package org.chatroomweb.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.chatroomweb.dao.MySQLController;
import org.chatroomweb.request.AddNewRoomRequest;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {
    private final MySQLController mySQLController = new MySQLController();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter printWriter = response.getWriter();
        System.out.println(new ObjectMapper().writeValueAsString(mySQLController.getRooms()));
        printWriter.println(new ObjectMapper().writeValueAsString(mySQLController.getRooms()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String name = request.getParameter("name");
        Integer maxMembers = Integer.valueOf(request.getParameter("maxMembers"));

        mySQLController.addRoom(new AddNewRoomRequest(name, maxMembers, 0));

        PrintWriter printWriter = response.getWriter();
        printWriter.println(new ObjectMapper().writeValueAsString(mySQLController.getRooms()));
    }
}