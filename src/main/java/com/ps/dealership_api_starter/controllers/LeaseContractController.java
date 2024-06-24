package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.LeaseContractDao;
import com.ps.dealership_api_starter.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lease")
public class LeaseContractController {

    private  LeaseContractDao leaseContractDao;

    @Autowired
    public LeaseContractController(LeaseContractDao leaseContractDao) {
        this.leaseContractDao = leaseContractDao;
    }

    @GetMapping("/{contract_id}")
    public LeaseContract getLeaseContractById(@PathVariable int contract_id) {
        return leaseContractDao.getById(contract_id);
    }

    @PutMapping("/{contract_id}")
    public void updateLeaseContract(@PathVariable int contract_id, @RequestBody LeaseContract leaseContract) {
        leaseContractDao.update(contract_id, leaseContract);
    }
}
