package com.example.fakebukproject.integration;

import com.example.fakebukproject.repository.ItemRepository;
import com.example.fakebukproject.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemServiceTests {

    @Autowired
    private ItemService service;

    @MockBean
    private ItemRepository mockItemRepository;

    @Test(expected = IllegalArgumentException.class)
    public void addWithInvalidValues_ThrowError() {
        service.addItem(null);
        verify(mockItemRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findItemBySellerWithInvalidValues_ThrowError() {
        service.findItemBySeller(null);
        verify(mockItemRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findItemByIdWithInvalidValues_ThrowError() {
        service.findById(null);
        verify(mockItemRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllItemsWithInvalidValues_ThrowError() {
        service.findAllItems();
        verify(mockItemRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteItemWithInvalidValues_ThrowError() {
        service.deleteItem(null);
        verify(mockItemRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void editItemWithInvalidValues_ThrowError() {
        service.editItem(null, null);
        verify(mockItemRepository)
                .save(any());
    }
}
