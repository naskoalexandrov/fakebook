package com.example.fakebukproject.validation.implementations;

import com.example.fakebukproject.domain.models.service.ItemServiceModel;
import com.example.fakebukproject.validation.ItemValidationService;

public class ItemValidationServiceImpl implements ItemValidationService {
    @Override
    public boolean isItemValid(ItemServiceModel itemServiceModel) {
        return itemServiceModel != null;
    }
}
