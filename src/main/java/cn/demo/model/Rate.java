package cn.demo.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Rate {

    private String currencyCode;

    private BigDecimal amount;
}
