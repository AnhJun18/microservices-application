package com.pa.inventoryservice.service;

import com.pa.inventoryservice.payload.response.InventoryResponse;
import com.pa.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;


    @Transactional
    public List<InventoryResponse> isInStock(List<String> code){
        return inventoryRepository.findBySkuCodeIn(code)
                .stream()
                .map(item->
                    InventoryResponse.builder()
                            .skuCode(item.getSkuCode())
                            .isInStock(item.getQuantity()>0)
                            .build()
                ).collect(Collectors.toList());

    }
}
