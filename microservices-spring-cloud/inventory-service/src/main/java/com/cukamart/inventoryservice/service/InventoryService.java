package com.cukamart.inventoryservice.service;

import com.cukamart.inventoryservice.dto.InventoryResponse;
import com.cukamart.inventoryservice.model.Inventory;
import com.cukamart.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(this::mapToInventoryResponse)
                .toList();
    }

    // todo - mapping reponse by mal byt v controlleri (mapper)
    private InventoryResponse mapToInventoryResponse(Inventory inv) {
        return new InventoryResponse(inv.getSkuCode(), inv.getQuantity() > 0);
    }
}
