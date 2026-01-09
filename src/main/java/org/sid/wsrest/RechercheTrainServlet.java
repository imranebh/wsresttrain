package org.sid.wsrest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RechercheTrainServlet", urlPatterns = {"/recherche-train"})
public class RechercheTrainServlet extends HttpServlet {

    private TrainResource trainResource;

    @Override
    public void init() throws ServletException {
        trainResource = new TrainResource();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String villeDepart = req.getParameter("villeDepart");
        String villeArrivee = req.getParameter("villeArrivee");

        if (villeDepart != null && villeArrivee != null) {
            List<Train> results = trainResource.searchTrains(villeDepart, villeArrivee);
            req.setAttribute("trains", results);
            req.setAttribute("searched", true);
        }

        req.getRequestDispatcher("/recherche.jsp").forward(req, resp);
    }
}
