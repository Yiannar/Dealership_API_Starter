package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.LeaseContract;

public interface LeaseContractDao {
    LeaseContract getById(int contract_id);
    void update(int contract_id, LeaseContract leaseContract);
}
