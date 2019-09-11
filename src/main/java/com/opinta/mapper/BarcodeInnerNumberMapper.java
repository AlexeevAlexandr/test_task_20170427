package com.opinta.mapper;

import com.opinta.dto.BarcodeInnerNumberDto;
import com.opinta.entity.BarcodeInnerNumber;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface BarcodeInnerNumberMapper extends BaseMapper<BarcodeInnerNumberDto, BarcodeInnerNumber> {
}
