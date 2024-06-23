package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlVehicleDao extends MySqlDaoBase implements VehicleDao {
private DataSource dataSource;

    @Autowired
    public MySqlVehicleDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Vehicle> getAllVehicles(String make, String model, String vehicleType, String color, int minYear, int maxYear,
                                        int minMiles, int maxMiles, double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles" +
                "WHERE make LIKE ? AND" +
                "model LIKE ? AND" +
                "vehicleType LIKE ? AND" +
                "color LIKE ? AND" +
                "minYear LIKE ? AND" +
                "maxYear LIKE ? AND" +
                "minMiles LIKE ? AND" +
                "maxMiles LIKE ? AND" +
                "minPrice LIKE ? AND" +
                "maxPrice LIKE ? AND";

        String makeToSearch = make == null ? "%" : make;
        String modelToSearch = model == null ? "%" : model;
        String vehicleTypeToSearch = vehicleType == null ? "%" : vehicleType;
        String colorToSearch = color == null ? "%" : color;
        int minYearToSearch = minYear == 0 ? 0 : minYear;
        int maxYearTosearch = maxYear == 0 ? 0 : maxYear;
        int minMilesToSearch = minMiles == 0 ? 0 : minMiles;
        int maxMilesTosearch = maxMiles == 0 ? 0 : maxMiles;
        double minPriceToSearch = minPrice == 0 ? 0 : minPrice;
        double maxPriceTosearch = maxPrice == 0 ? 0 : maxPrice;

        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + makeToSearch + "%");
            preparedStatement.setString(2, "%" + modelToSearch + "%");
            preparedStatement.setString(3, "%" + vehicleTypeToSearch + "%");
            preparedStatement.setString(4, "%" + colorToSearch + "%");
            preparedStatement.setInt(5, minYearToSearch);
            preparedStatement.setInt(6, maxYearTosearch);
            preparedStatement.setInt(7, minMilesToSearch);
            preparedStatement.setInt(8, maxMilesTosearch);
            preparedStatement.setDouble(9, minPriceToSearch);
            preparedStatement.setDouble(10, maxPriceTosearch);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Vehicle vehicle = mapRow(resultSet);
                vehicles.add(vehicle);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
        return vehicles;

    }

    @Override
    public Vehicle getByVin(int vin) {
        Vehicle vehicle = null;
        String sql = "SELECT * FROM vehicles WHERE vin = ?";
        try( Connection connection = getConnection()
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
){
            preparedStatement.setInt(1, vin);

            try(
                    ResultSet resultSet = preparedStatement.executeQuery();

            ){
                while ( resultSet.next()){
                    vehicle = mapRow(resultSet);
                    break;
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles(make, model, vehicle_type, color)" + "VALUES (?,?,?)";
        try(Connection connection = getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, );
        }
    }


    @Override
    public void update(int vin, Vehicle vehicle) {

    }

    @Override
    public void delete(int vin) {

        String sql = "DELETE FROM vehicle" + "WHERE vin = ?";
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, vin);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    protected static Vehicle mapRow(ResultSet resultSet) throws SQLException{
        String vin = resultSet.getString("vin");
        String make = resultSet.getString("make");
        String modelToSearch = resultSet.getString("model");
        String vehicleTypeToSearch = resultSet.getString("vehicle_Type");
        String colorToSearch = resultSet.getString("color");

        return new Vehicle(vin, make, modelToSearch, vehicleTypeToSearch, colorToSearch);
    }


}
