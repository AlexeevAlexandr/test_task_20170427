package com.opinta.mapper;

import com.opinta.dto.PostcodePoolDto;
import com.opinta.entity.PostcodePool;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface PostcodePoolMapper extends BaseMapper<PostcodePoolDto, PostcodePool> {
}
