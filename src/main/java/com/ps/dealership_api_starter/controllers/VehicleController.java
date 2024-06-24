package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @GetMapping
    public List<Vehicle> getAllVehicles(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String color,
            @RequestParam(required = false, defaultValue = "0") int minYear,
            @RequestParam(required = false, defaultValue = "0") int maxYear,
            @RequestParam(required = false, defaultValue = "0") int minMiles,
            @RequestParam(required = false, defaultValue = "0") int maxMiles,
            @RequestParam(required = false, defaultValue = "0") double minPrice,
            @RequestParam(required = false, defaultValue = "0") double maxPrice
    ) {
        return vehicleDao.getAllVehicles(make, model, vehicleType, color, minYear, maxYear, minMiles, maxMiles, minPrice, maxPrice);
    }

    @GetMapping("/{vin}")
    public Vehicle getVehicleByVin(@PathVariable int vin) {
        return vehicleDao.getByVin(vin);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleDao.create(vehicle);
    }

    @PutMapping("/{vin}")
    public void updateVehicle(@PathVariable int vin, @RequestBody Vehicle vehicle) {
        vehicleDao.update(vin, vehicle);
    }

    @DeleteMapping("/{vin}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int vin) {
        vehicleDao.delete(vin);
    }
}
