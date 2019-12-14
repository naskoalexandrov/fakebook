package com.example.fakebukproject.repository;

import com.example.fakebukproject.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {

    Optional<Item> findItemByItemName(String itemName);
}
