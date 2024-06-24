package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class SalesContractController {
    private final SalesContractDao salesContractDao;

    @Autowired
    public SalesContractController(SalesContractDao salesContractDao){
        this.salesContractDao = salesContractDao;
    }

    @GetMapping("/{contract_id}")
    public SalesContract getContractById(@PathVariable int contract_id){
        SalesContract contract = salesContractDao.getById(contract_id);
        if (contract == null) {
            throw new RuntimeException("No contract found with ID: " + contract_id);
        }
        return contract;
    }

    @PutMapping("/{contract_id}")
    public void updateContract(@PathVariable int contract_id, @RequestBody SalesContract salesContract){
        salesContractDao.update(contract_id, salesContract);
    }
}



