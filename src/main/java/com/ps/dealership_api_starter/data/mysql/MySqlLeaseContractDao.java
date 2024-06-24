package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.LeaseContractDao;
import com.ps.dealership_api_starter.models.LeaseContract;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlLeaseContractDao extends MySqlDaoBase implements LeaseContractDao {

    public MySqlLeaseContractDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public LeaseContract getById(int contract_id) {
        String sql = "SELECT * FROM lease_contracts WHERE contract_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, contract_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving lease contract with ID: " + contract_id, e);
        }
        return null;
    }

    @Override
    public void update(int contract_id, LeaseContract leaseContract) {
        String sql = "UPDATE lease_contracts " +
                "SET contract_date = ?, customer_name = ?, customer_email = ?, vin = ?, lease_start_date = ?, lease_end_date = ?, monthly_payment = ? " +
                "WHERE contract_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, leaseContract.getContractDate());
            preparedStatement.setString(2, leaseContract.getCustomerName());
            preparedStatement.setString(3, leaseContract.getCustomerEmail());
            preparedStatement.setInt(4, leaseContract.getVin());
            preparedStatement.setString(5, leaseContract.getContractDate());
            preparedStatement.setString(6, leaseContract.getContractDate());
            preparedStatement.setBigDecimal(7, BigDecimal.valueOf(leaseContract.getMonthlyPayment()));
            preparedStatement.setInt(8, contract_id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Lease contract with ID " + contract_id + " was updated successfully!");
            } else {
                System.out.println("No lease contract found with ID " + contract_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating lease contract with ID: " + contract_id, e);
        }
    }

    protected static LeaseContract mapRow(ResultSet resultSet) throws SQLException {
        int contract_id = resultSet.getInt("contract_id");
        String contract_date = resultSet.getString("contract_date");
        String customer_name = resultSet.getString("customer_name");
        String customer_email = resultSet.getString("customer_email");
        int vin = resultSet.getInt("vin");
        String lease_start_date = resultSet.getString("lease_start_date");
        String lease_end_date = resultSet.getString("lease_end_date");
        double monthly_payment = resultSet.getDouble("monthly_payment");

        return new LeaseContract(contract_id, contract_date, customer_name, customer_email, vin, lease_start_date, lease_end_date, monthly_payment);
    }
}
