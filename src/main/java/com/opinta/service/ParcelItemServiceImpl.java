package com.opinta.service;

import com.opinta.dao.ParcelItemDao;
import com.opinta.dto.ParcelItemDto;
import com.opinta.entity.ParcelItem;
import com.opinta.mapper.ParcelItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ParcelItemServiceImpl implements ParcelItemService{

    private final ParcelItemDao parcelItemDao;
    private final ParcelItemMapper parcelItemMapper;

    public ParcelItemServiceImpl(ParcelItemDao parcelItemDao, ParcelItemMapper parcelItemMapper) {
        this.parcelItemDao = parcelItemDao;
        this.parcelItemMapper = parcelItemMapper;
    }

    @Override
    @Transactional
    public List<ParcelItem> getAll() {
        log.info("Getting all parcelItems");
        return parcelItemDao.getAll();
    }

    @Override
    @Transactional
    public ParcelItem getById(long id) {
        ParcelItem parcelItem = parcelItemDao.getById(id);
        if (parcelItem == null){
            log.info("ParcelItem by id not found {} ", id);
            return null;
        }
        log.info("Getting parcelItem by id {} ", id);
        return parcelItem;
    }

    @Override
    @Transactional
    public ParcelItemDto save(ParcelItemDto parcelItemDto) {
        log.info("Saving parcelItem {}", parcelItemDto);
        ParcelItem parcelItem = parcelItemMapper.toEntity(parcelItemDto);
        return parcelItemMapper.toDto(parcelItemDao.save(parcelItem));
    }

    @Override
    @Transactional
    public void update(long id, ParcelItem parcelItem) {
        ParcelItem parcelItem1 = parcelItemDao.getById(id);
        if (parcelItem1 == null){
            log.info("ParcelItem not found {}", id);
        }
        log.info("Updating parcelItem {}", parcelItem);
        parcelItem.setId(id);
        parcelItemDao.update(parcelItem);
    }

    @Override
    @Transactional
    public void delete(long id) {
        ParcelItem parcelItem = parcelItemDao.getById(id);
        if (parcelItem == null){
            log.info("ParcelItem not found {}", id);
        }
        log.info("Deleting parcelItem");
        parcelItemDao.delete(parcelItem);
    }
}
