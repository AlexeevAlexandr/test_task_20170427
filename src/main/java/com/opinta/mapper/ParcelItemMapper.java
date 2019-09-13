package com.opinta.mapper;

import com.opinta.dto.ParcelItemDto;
import com.opinta.entity.ParcelItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ParcelItemMapper extends BaseMapper<ParcelItemDto, ParcelItem>{

    @Override
    ParcelItemDto toDto(ParcelItem parcelItem);

    @Override
    ParcelItem toEntity(ParcelItemDto parcelItemDto);
}
