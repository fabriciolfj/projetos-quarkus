package com.github.fabriciolfj.controller.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {

    private String item;
    private BigDecimal price;
}
