package com.ps.dealership_api_starter.controllers;


import com.ps.dealership_api_starter.data.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class VehicleController {

    private VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

//    @GetMapping("/vehicle")
//    public List<Vehicle>getAllVehicles(@RequestParam);
}
