package com.opinta.mapper;

import com.opinta.dto.AddressDto;
import com.opinta.entity.Address;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<AddressDto, Address> {
}
