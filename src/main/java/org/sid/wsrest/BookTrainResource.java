package org.sid.wsrest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookTrainResource {

    public BookTrainResource() {
        // Create reservations table if not exists
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS reservations (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "bookNumber VARCHAR(255) UNIQUE, " +
                    "trainId BIGINT, " +
                    "numberPlaces INT, " +
                    "FOREIGN KEY (trainId) REFERENCES trains(id) ON DELETE CASCADE)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GET
    public List<BookTrain> getAllReservations() {
        List<BookTrain> reservations = new ArrayList<>();
        String sql = "SELECT r.id, r.bookNumber, r.trainId, r.numberPlaces, " +
                "t.id as t_id, t.nom, t.villeDepart, t.villeArrivee, t.heureDepart " +
                "FROM reservations r " +
                "JOIN trains t ON r.trainId = t.id";
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(mapResultSetToBookTrain(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Database error", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return reservations;
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") Long id) {
        String sql = "SELECT r.id, r.bookNumber, r.trainId, r.numberPlaces, " +
                "t.id as t_id, t.nom, t.villeDepart, t.villeArrivee, t.heureDepart " +
                "FROM reservations r " +
                "JOIN trains t ON r.trainId = t.id " +
                "WHERE r.id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Response.ok(mapResultSetToBookTrain(rs)).build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reservation not found").build();
    }

    @GET
    @Path("/bookNumber/{bookNumber}")
    public Response getReservationByBookNumber(@PathParam("bookNumber") String bookNumber) {
        String sql = "SELECT r.id, r.bookNumber, r.trainId, r.numberPlaces, " +
                "t.id as t_id, t.nom, t.villeDepart, t.villeArrivee, t.heureDepart " +
                "FROM reservations r " +
                "JOIN trains t ON r.trainId = t.id " +
                "WHERE r.bookNumber = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Response.ok(mapResultSetToBookTrain(rs)).build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reservation not found").build();
    }

    @GET
    @Path("/train/{trainId}")
    public List<BookTrain> getReservationsByTrain(@PathParam("trainId") Long trainId) {
        List<BookTrain> reservations = new ArrayList<>();
        String sql = "SELECT r.id, r.bookNumber, r.trainId, r.numberPlaces, " +
                "t.id as t_id, t.nom, t.villeDepart, t.villeArrivee, t.heureDepart " +
                "FROM reservations r " +
                "JOIN trains t ON r.trainId = t.id " +
                "WHERE r.trainId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, trainId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToBookTrain(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebApplicationException("Database error", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return reservations;
    }

    @POST
    public Response createReservation(BookTrain bookTrain) {
        // Validate that the train exists
        if (bookTrain.getCurrentTrain() == null || bookTrain.getCurrentTrain().getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Train information is required").build();
        }

        // Generate a unique book number if not provided
        String bookNumber = bookTrain.getBookNumber();
        if (bookNumber == null || bookNumber.isEmpty()) {
            bookNumber = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        // Check if train exists
        String checkTrainSql = "SELECT id FROM trains WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkTrainSql)) {
            checkStmt.setLong(1, bookTrain.getCurrentTrain().getId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Train with id " + bookTrain.getCurrentTrain().getId() + " does not exist").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Database error").build();
        }

        String sql = "INSERT INTO reservations (bookNumber, trainId, numberPlaces) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bookNumber);
            pstmt.setLong(2, bookTrain.getCurrentTrain().getId());
            pstmt.setInt(3, bookTrain.getNumberPlaces());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return Response.serverError().entity("Creating reservation failed, no rows affected.").build();
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bookTrain.setId(generatedKeys.getLong(1));
                    bookTrain.setBookNumber(bookNumber);
                }
            }

            // Fetch the complete reservation with train details
            return getReservation(bookTrain.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("A reservation with this book number already exists").build();
            }
            return Response.serverError().entity("Database error").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateReservation(@PathParam("id") Long id, BookTrain updatedBookTrain) {
        // Check if train exists if provided
        if (updatedBookTrain.getCurrentTrain() != null && updatedBookTrain.getCurrentTrain().getId() != null) {
            String checkTrainSql = "SELECT id FROM trains WHERE id = ?";
            try (Connection conn = DatabaseUtils.getConnection();
                 PreparedStatement checkStmt = conn.prepareStatement(checkTrainSql)) {
                checkStmt.setLong(1, updatedBookTrain.getCurrentTrain().getId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity("Train with id " + updatedBookTrain.getCurrentTrain().getId() + " does not exist").build();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.serverError().entity("Database error").build();
            }
        }

        String sql = "UPDATE reservations SET bookNumber = ?, trainId = ?, numberPlaces = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedBookTrain.getBookNumber());
            pstmt.setLong(2, updatedBookTrain.getCurrentTrain().getId());
            pstmt.setInt(3, updatedBookTrain.getNumberPlaces());
            pstmt.setLong(4, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return getReservation(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("A reservation with this book number already exists").build();
            }
            return Response.serverError().entity("Database error").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reservation not found").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReservation(@PathParam("id") Long id) {
        String sql = "DELETE FROM reservations WHERE id = ?";
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
        return Response.status(Response.Status.NOT_FOUND).entity("Reservation not found").build();
    }

    private BookTrain mapResultSetToBookTrain(ResultSet rs) throws SQLException {
        Train train = new Train(
                rs.getLong("t_id"),
                rs.getString("nom"),
                rs.getString("villeDepart"),
                rs.getString("villeArrivee"),
                rs.getString("heureDepart")
        );
        return new BookTrain(
                rs.getLong("id"),
                rs.getString("bookNumber"),
                train,
                rs.getInt("numberPlaces")
        );
    }
}

