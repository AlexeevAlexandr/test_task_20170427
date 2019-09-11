package com.opinta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

import static com.opinta.constraint.RegexPattern.POSTCODE_REGEX;

@Getter
@Setter
public class PostcodePoolDto {
    private long id;
    @Pattern(regexp = POSTCODE_REGEX)
    private String postcode;
    private boolean closed;
}
