package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Item;
import com.example.fakebukproject.domain.models.service.ItemServiceModel;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.error.Constants;
import com.example.fakebukproject.error.ItemNotFoundException;
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
    public List<ItemServiceModel> findItemBySeller(String seller) {
        return this.itemRepository.findAll()
                .stream()
                .map(i -> this.modelMapper.map(i, ItemServiceModel.class))
                .filter(i -> i.getSeller().equals(seller))
                .collect(Collectors.toList());
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
        return this.itemRepository.findById(id)
                .map(i -> this.modelMapper.map(i, ItemServiceModel.class))
                .orElseThrow(() -> new ItemNotFoundException(Constants.ITEM_ID_NOT_FOUND));
    }

    @Override
    public ItemServiceModel editItem(String id, ItemServiceModel itemServiceModel) {
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Constants.ITEM_ID_NOT_FOUND));

        item.setSeller(itemServiceModel.getSeller());
        item.setItemName(itemServiceModel.getItemName());
        item.setPrice(itemServiceModel.getPrice());
        item.setDescription(itemServiceModel.getDescription());
        item.setItemPictureURL(itemServiceModel.getItemPictureURL());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(itemServiceModel.getSeller());
        logServiceModel.setDescription("Item edited");
        logServiceModel.setTime(LocalDateTime.now());

        return this.modelMapper.map(this.itemRepository.saveAndFlush(item), ItemServiceModel.class);
    }

    @Override
    public void deleteItem(String id) {
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Constants.ITEM_ID_NOT_FOUND));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(item.getSeller());
        logServiceModel.setDescription("Item deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        this.itemRepository.delete(item);
    }
}
