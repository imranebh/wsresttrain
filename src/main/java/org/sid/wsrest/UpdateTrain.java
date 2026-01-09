package org.sid.wsrest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@WebServlet(name="UpdateTrain", urlPatterns = {"/update-train"})
public class UpdateTrain extends HttpServlet {

    private TrainResource trainResource;

    @Override
    public void init() throws ServletException {
        trainResource = new TrainResource();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                Long id = Long.parseLong(idStr);
                Response response = trainResource.getTrain(id);
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    Train train = (Train) response.getEntity();
                    req.setAttribute("train", train);
                    req.getRequestDispatcher("/update.jsp").forward(req, resp);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/train-list");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String nom = req.getParameter("nom");
        String villeDepart = req.getParameter("villeDepart");
        String villeArrivee = req.getParameter("villeArrivee");
        String heureDepart = req.getParameter("heureDepart");

        if (idStr != null) {
            try {
                Long id = Long.parseLong(idStr);
                Train train = new Train();
                train.setNom(nom);
                train.setVilleDepart(villeDepart);
                train.setVilleArrivee(villeArrivee);
                train.setHeureDepart(heureDepart);
                
                trainResource.updateTrain(id, train);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/train-list");
    }
}
