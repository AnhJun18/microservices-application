package com.pa.inventoryservice.service;

import com.pa.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;


    @Transactional
    public  boolean isInStock(String code){
       return inventoryRepository.findBySkuCode(code).isPresent();
    }
}
