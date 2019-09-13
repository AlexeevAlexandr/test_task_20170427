package com.opinta.service;

import com.opinta.dto.ParcelDto;
import com.opinta.entity.Parcel;

import java.util.List;

public interface ParcelService {

    List<Parcel> getAll();

    Parcel getById(long id);

    ParcelDto save(ParcelDto parcelDto);

    void update(long id, Parcel parcel);

    void delete(long id);
}
