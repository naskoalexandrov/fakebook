package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Item;
import com.example.fakebukproject.domain.models.service.ItemServiceModel;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.repository.ItemRepository;
import com.example.fakebukproject.service.ItemService;
import com.example.fakebukproject.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final LogService logService;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(LogService logService, ItemRepository itemRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemServiceModel addItem(ItemServiceModel itemServiceModel) {
        Item item = this.modelMapper.map(itemServiceModel, Item.class);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(itemServiceModel.getItemName().toLowerCase());
        logServiceModel.setDescription("Item added");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        return this.modelMapper.map(this.itemRepository.saveAndFlush(item), ItemServiceModel.class);
    }

    @Override
    public List<ItemServiceModel> findAllItems() {
        return  this.itemRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ItemServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemServiceModel findById(String id) {
        return null;
    }
}
