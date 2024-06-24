package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.SalesContract;

public interface SalesContractDao {

    SalesContract getById(int contractId);

    void update(int contractId, SalesContract salesContract);
}
