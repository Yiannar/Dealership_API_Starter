package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlVehicleDao extends MySqlDaoBase implements VehicleDao {

    public MySqlVehicleDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Vehicle> getAllVehicles(String make, String model, String vehicleType, String color,
                                        int minYear, int maxYear, int minMiles, int maxMiles,
                                        double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE " +
                "(make LIKE ? OR ? IS NULL) AND " +
                "(model LIKE ? OR ? IS NULL) AND " +
                "(vehicle_type LIKE ? OR ? IS NULL) AND " +
                "(color LIKE ? OR ? IS NULL) AND " +
                "(year >= ? OR ? = 0) AND " +
                "(year <= ? OR ? = 0) AND " +
                "(odometer >= ? OR ? = 0) AND " +
                "(odometer <= ? OR ? = 0) AND " +
                "(price >= ? OR ? = 0) AND " +
                "(price <= ? OR ? = 0)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + make + "%");
            preparedStatement.setString(2, make);
            preparedStatement.setString(3, "%" + model + "%");
            preparedStatement.setString(4, model);
            preparedStatement.setString(5, "%" + vehicleType + "%");
            preparedStatement.setString(6, vehicleType);
            preparedStatement.setString(7, "%" + color + "%");
            preparedStatement.setString(8, color);
            preparedStatement.setInt(9, minYear);
            preparedStatement.setInt(10, minYear);
            preparedStatement.setInt(11, maxYear);
            preparedStatement.setInt(12, maxYear);
            preparedStatement.setInt(13, minMiles);
            preparedStatement.setInt(14, minMiles);
            preparedStatement.setInt(15, maxMiles);
            preparedStatement.setInt(16, maxMiles);
            preparedStatement.setDouble(17, minPrice);
            preparedStatement.setDouble(18, minPrice);
            preparedStatement.setDouble(19, maxPrice);
            preparedStatement.setDouble(20, maxPrice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vehicle vehicle = mapRow(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return vehicles;
    }


    @Override
    public Vehicle getByVin(int vin) {
        String sql = "SELECT * FROM vehicles WHERE vin = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (year, make, model, vehicle_type, color, odometer, price, sold) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, vehicle.getYear());
            pstmt.setString(2, vehicle.getMake());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getVehicleType());
            pstmt.setString(5, vehicle.getColor());
            pstmt.setInt(6, vehicle.getOdometer());
            pstmt.setBigDecimal(7, BigDecimal.valueOf(vehicle.getPrice()));
            pstmt.setBoolean(8, vehicle.isSold());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedVin = generatedKeys.getInt(1);
                        vehicle.setVin(generatedVin); // Assuming you have a setter for vin in Vehicle class
                        System.out.println("A new vehicle with VIN " + generatedVin + " was inserted successfully!");
                    } else {
                        throw new SQLException("Creating vehicle failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating vehicle: " + e.getMessage());
        }
        return vehicle;
    }




    @Override
    public void update(int vin, Vehicle vehicle) {
        String sql = "UPDATE vehicles " +
                "SET make = ?, model = ?, vehicle_type = ?, color = ?, year = ?, odometer = ?, price = ?, sold = ? " +
                "WHERE vin = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getVehicleType());
            preparedStatement.setString(4, vehicle.getColor());
            preparedStatement.setInt(5, vehicle.getYear());
            preparedStatement.setInt(6, vehicle.getOdometer());
            preparedStatement.setDouble(7, vehicle.getPrice()); // Use setBigDecimal for precision
            preparedStatement.setBoolean(8, vehicle.isSold());
            preparedStatement.setInt(9, vin); // Set vin in the WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Vehicle with VIN " + vin + " was updated successfully!");
            } else {
                System.out.println("No vehicle found with VIN " + vin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



    @Override
    public void delete(int vin) {
        String sql = "DELETE FROM vehicles WHERE vin = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, vin);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected static Vehicle mapRow(ResultSet resultSet) throws SQLException {
        int vin = resultSet.getInt("vin");
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String vehicleType = resultSet.getString("vehicle_type");
        String color = resultSet.getString("color");
        int year = resultSet.getInt("year");
        int odometer = resultSet.getInt("odometer");
        double price = resultSet.getDouble("price");
        boolean sold = resultSet.getBoolean("sold");

        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
    }
}
