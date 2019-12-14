package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.ItemServiceModel;

import java.util.List;

public interface ItemService {

    ItemServiceModel addItem(ItemServiceModel itemServiceModel);

    List<ItemServiceModel> findAllItems();

    ItemServiceModel findById(String id);
}
