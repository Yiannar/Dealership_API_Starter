package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlSalesContractDao extends MySqlDaoBase implements SalesContractDao {

    public MySqlSalesContractDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public SalesContract getById(int contract_id) {
        String sql = "SELECT * FROM sales_contracts WHERE contract_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, contract_id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            } else {
                System.out.println("No data found for contract_id: " + contract_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching contract by ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void update(int contract_id, SalesContract salesContract) {
        String sql = "UPDATE sales_contracts SET contract_date = ?, customer_name = ?, customer_email = ?, " +
                "vin = ?, sales_tax = ?, recording_fee = ?, processing_fee = ?, total_price = ?, finance_option = ?, monthly_payment = ? " +
                "WHERE contract_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, salesContract.getContractDate());
            pstmt.setString(2, salesContract.getCustomerName());
            pstmt.setString(3, salesContract.getCustomerEmail());
            pstmt.setInt(4, salesContract.getVin());
            pstmt.setBigDecimal(5, BigDecimal.valueOf(salesContract.getSalesTax()));
            pstmt.setBigDecimal(6, BigDecimal.valueOf(salesContract.getRecordingFee()));
            pstmt.setBigDecimal(7, BigDecimal.valueOf(salesContract.getProcessingFee()));
            pstmt.setBigDecimal(8, BigDecimal.valueOf(salesContract.getTotalPrice()));
            pstmt.setString(9, salesContract.getFinanceOption());
            pstmt.setBigDecimal(10, BigDecimal.valueOf(salesContract.getMonthlyPayment()));
            pstmt.setInt(11, contract_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating contract: " + e.getMessage(), e);
        }
    }

    protected static SalesContract mapRow(ResultSet resultSet) throws SQLException {
        int contractId = resultSet.getInt("contract_id");
        String contractDate = resultSet.getString("contract_date");
        String customerName = resultSet.getString("customer_name");
        String customerEmail = resultSet.getString("customer_email");
        int vin = resultSet.getInt("vin");
        BigDecimal salesTax = resultSet.getBigDecimal("sales_tax");
        BigDecimal recordingFee = resultSet.getBigDecimal("recording_fee");
        BigDecimal processingFee = resultSet.getBigDecimal("processing_fee");
        BigDecimal totalPrice = resultSet.getBigDecimal("total_price");
        String financeOption = resultSet.getString("finance_option");
        BigDecimal monthlyPayment = resultSet.getBigDecimal("monthly_payment");

        return new SalesContract(contractId, contractDate, customerName, customerEmail, vin, salesTax, recordingFee, processingFee, totalPrice, financeOption, monthlyPayment);
    }
}
