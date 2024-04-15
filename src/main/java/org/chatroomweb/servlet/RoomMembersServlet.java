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
        String currentMembersParam = request.getParameter("currentMembers");
        int currentMembers = 0;

        try {
            currentMembers = Integer.parseInt(currentMembersParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.println("{\"error\": \"Invalid currentMembers parameter\"}");
            out.close();
            return;
        }

        System.err.println(roomName);
        System.err.println(currentMembers);
        mySQLController.changeCurrentRoomMembers(new ChangeRoomCurrentMembersRequest(roomName, currentMembers));

        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println("{\"message\": \"Current members updated successfully\"}");
        out.close();
    }

    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
