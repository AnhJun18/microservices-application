package com.pa.inventoryservice.controller;

import com.pa.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @GetMapping("{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){

        return inventoryService.isInStock(skuCode);
    }
}
