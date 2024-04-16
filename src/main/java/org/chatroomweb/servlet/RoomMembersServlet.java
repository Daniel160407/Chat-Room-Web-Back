package org.chatroomweb.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.chatroomweb.dao.MySQLController;
import org.chatroomweb.request.ChangeRoomCurrentMembersRequest;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/roomMembers")
public class RoomMembersServlet extends HttpServlet {
    private final MySQLController mySQLController = new MySQLController();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        out.println(new ObjectMapper().writeValueAsString(mySQLController.getCurrentRoomMembersAmount()));
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");

        String roomName = request.getParameter("roomName");
        int currentMembers = Integer.parseInt(request.getParameter("currentMembers"));

        System.err.println(roomName);
        System.err.println(currentMembers);

        boolean updated = mySQLController.changeCurrentRoomMembers(new ChangeRoomCurrentMembersRequest(roomName, currentMembers));

        if (updated) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
