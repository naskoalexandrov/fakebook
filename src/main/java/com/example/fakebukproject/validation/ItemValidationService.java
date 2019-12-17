package com.example.fakebukproject.validation;

import com.example.fakebukproject.domain.models.service.ItemServiceModel;

public interface ItemValidationService {
    boolean isItemValid(ItemServiceModel itemServiceModel);
}
