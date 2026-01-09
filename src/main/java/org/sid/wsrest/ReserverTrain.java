package org.sid.wsrest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReserverTrain", urlPatterns = {"/reserve-train"})
public class ReserverTrain extends HttpServlet {

    private TrainResource trainResource;

    @Override
    public void init() throws ServletException {
        trainResource = new TrainResource();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Train> trains = trainResource.getAllTrains();
        req.setAttribute("trains", trains);
        req.getRequestDispatcher("/reserve.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trainId = req.getParameter("trainId");
        String passengerName = req.getParameter("passengerName"); // Optional, if you add it to the form

        // Simulate booking logic (since no database table exists for bookings)
        System.out.println("Reservation Request - Train ID: " + trainId + ", Passenger: " + passengerName);

        // In a real app, you would save this to a 'bookings' table here.
        
        resp.sendRedirect(req.getContextPath() + "/train-list");
    }
}