package cn.demo.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Currency {

    private String currencyCode;

    private int amount;
}
