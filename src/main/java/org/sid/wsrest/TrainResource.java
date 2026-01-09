package org.sid.wsrest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/trains")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrainResource {

    public TrainResource() {
        // Simple attempt to create table if not exists
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS trains (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(255), " +
                    "villeDepart VARCHAR(255), " +
                    "villeArrivee VARCHAR(255), " +
                    "heureDepart VARCHAR(255))";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GET
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains";
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trains.add(mapResultSetToTrain(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Database error", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return trains;
    }

    @GET
    @Path("/search")
    public List<Train> searchTrains(@QueryParam("depart") String villeDepart, @QueryParam("arrivee") String villeArrivee) {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains WHERE villeDepart = ? AND villeArrivee = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, villeDepart);
            pstmt.setString(2, villeArrivee);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trains.add(mapResultSetToTrain(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Database error", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return trains;
    }

    @GET
    @Path("/{id}")
    public Response getTrain(@PathParam("id") Long id) {
        String sql = "SELECT * FROM trains WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Response.ok(mapResultSetToTrain(rs)).build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Train not found").build();
    }

    @POST
    public Response createTrain(Train train) {
        String sql = "INSERT INTO trains (nom, villeDepart, villeArrivee, heureDepart) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, train.getNom());
            pstmt.setString(2, train.getVilleDepart());
            pstmt.setString(3, train.getVilleArrivee());
            pstmt.setString(4, train.getHeureDepart());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return Response.serverError().entity("Creating train failed, no rows affected.").build();
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    train.setId(generatedKeys.getLong(1));
                }
            }
            return Response.status(Response.Status.CREATED).entity(train).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateTrain(@PathParam("id") Long id, Train updatedTrain) {
        String sql = "UPDATE trains SET nom = ?, villeDepart = ?, villeArrivee = ?, heureDepart = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedTrain.getNom());
            pstmt.setString(2, updatedTrain.getVilleDepart());
            pstmt.setString(3, updatedTrain.getVilleArrivee());
            pstmt.setString(4, updatedTrain.getHeureDepart());
            pstmt.setLong(5, id);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updatedTrain.setId(id);
                return Response.ok(updatedTrain).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Train not found").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTrain(@PathParam("id") Long id) {
        String sql = "DELETE FROM trains WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return Response.noContent().build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Train not found").build();
    }

    private Train mapResultSetToTrain(ResultSet rs) throws SQLException {
        return new Train(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("villeDepart"),
                rs.getString("villeArrivee"),
                rs.getString("heureDepart")
        );
    }
}
