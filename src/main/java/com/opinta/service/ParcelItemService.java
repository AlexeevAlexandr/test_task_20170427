package com.opinta.service;

import com.opinta.dto.ParcelItemDto;
import com.opinta.entity.ParcelItem;

import java.util.List;

public interface ParcelItemService {

    List<ParcelItem> getAll();

    ParcelItem getById(long id);

    ParcelItemDto save(ParcelItemDto parcelItemDto);

    void update(long id, ParcelItem parcelItem);

    void delete(long id);
}
