package com.opinta.service;

import com.opinta.dao.ParcelDao;
import com.opinta.dto.ParcelDto;
import com.opinta.entity.Parcel;
import com.opinta.entity.Shipment;
import com.opinta.mapper.ParcelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ParcelServiceImpl implements ParcelService{

    private final ParcelDao parcelDao;
    private final ParcelMapper parcelMapper;

    public ParcelServiceImpl(ParcelDao parcelDao, ParcelMapper parcelMapper) {
        this.parcelDao = parcelDao;
        this.parcelMapper = parcelMapper;
    }

    @Override
    @Transactional
    public List<Parcel> getAll() {
        log.info("Getting all parcels");
        return parcelDao.getAll();
    }

    @Override
    @Transactional
    public Parcel getById(long id) {
        Parcel parcel = parcelDao.getById(id);
        if (parcel == null){
            log.info("Parcel by id not found {}", id);
            return null;
        }
        log.info("Getting parcel by id {} ", id);
        return parcel;
    }

    @Override
    @Transactional
    public ParcelDto save(ParcelDto parcelDto) {
        log.info("Saving parcel {}", parcelDto);
        Parcel parcel = parcelMapper.toEntity(parcelDto);
        return parcelMapper.toDto(parcelDao.save(parcel));
    }

    @Override
    @Transactional
    public void update(long id, Parcel parcel) {
        Parcel parcel1 = parcelDao.getById(id);
        if (parcel1 == null){
            log.info("Parcel not found {}", id);
        }
        log.info("Updating parcel");
        parcel.setId(id);
        parcelDao.update(parcel);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Parcel parcel = parcelDao.getById(id);
        if (parcel == null){
            log.info("Parcel not found {}", id);
        }
        log.info("Deleting parcel");
        parcelDao.delete(parcel);
    }
}
