package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.models.LeaseContract;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lease")
public interface LeaseContractController {

    @GetMapping("/{contract_id}")
    LeaseContract getLeaseContractById(@PathVariable int contract_id);

    @PutMapping("/{contract_id}")
    void updateLeaseContract(@PathVariable int contract_id, @RequestBody LeaseContract leaseContract);

}
