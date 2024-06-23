package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.Vehicle;

import java.util.List;

public interface VehicleDao {

 List<Vehicle> getAllVehicles( String make, String model, String vehicleType, String color,
                              int minYear, int maxYear, int minMiles, int maxMiles, double minPrice,
                              double maxPrice);
 Vehicle getByVin(int vin);
 Vehicle create(Vehicle vehicle);
 void update(int vin, Vehicle vehicle);
 void delete(int vin);


}
