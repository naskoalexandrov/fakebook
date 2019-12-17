package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.ItemServiceModel;

import java.util.List;

public interface ItemService {

    ItemServiceModel addItem(ItemServiceModel itemServiceModel);

    List<ItemServiceModel> findItemBySeller(String seller);

    List<ItemServiceModel> findAllItems();

    ItemServiceModel findById(String id);

    ItemServiceModel editItem(String id, ItemServiceModel itemServiceModel);

    void deleteItem(String id);
}
