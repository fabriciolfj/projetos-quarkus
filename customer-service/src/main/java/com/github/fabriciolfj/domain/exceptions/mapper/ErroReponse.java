package com.github.fabriciolfj.domain.exceptions.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErroReponse {

    private String message;
}
