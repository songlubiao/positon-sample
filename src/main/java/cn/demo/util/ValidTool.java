package cn.demo.util;

import cn.demo.exception.DemoException;
import cn.demo.model.Currency;
import cn.demo.model.Rate;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Pair;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 76732
 */
public class ValidTool {

    /**
     * 判断字符串大写
     *
     * @param currencyCode 货币编码
     * @return
     */
    public static boolean isUpperCase(String currencyCode) {
        for (int i = 0; i < currencyCode.length(); i++) {
            char c = currencyCode.charAt(i);
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证金额位数
     *
     * @param currency
     * @return
     */
    public static boolean validateCurrency(String currency) throws DemoException {
        if (currency.length() != 3 || (!isUpperCase(currency))) {
            throw new DemoException("currency code error");
        }
        return true;
    }

    public static Map<String, BigDecimal> getRate(String rateFilePath) {
        List<String> lines = FileUtil.readUtf8Lines(rateFilePath);
        List<Rate> rates = lines.stream().map(str -> {
            String[] items = str.split(" ");
            String currencyCode = items[0];
            BigDecimal amount = new BigDecimal(items[1]);
            return Rate.builder()
                    .currencyCode(currencyCode)
                    .amount(amount)
                    .build();
        }).collect(Collectors.toList());
        return rates.stream()
                .collect(Collectors.toMap(Rate::getCurrencyCode, Rate::getAmount));
    }

    public static void display(Map<String, BigDecimal> currencyMap,
                               Map<String, BigDecimal> rates) {
        System.out.println();
        if (currencyMap.isEmpty()) {
            return;
        }

        currencyMap.forEach((k, v) -> {
            if (rates.containsKey(k)) {
                BigDecimal valueWithUsd = rates.get(k).multiply(v).setScale(2);
                System.out.println(String.format("%s %s (USD %s)", k, v, valueWithUsd));
            } else {
                System.out.println(String.format("%s %s", k, v));
            }
        });

        System.out.println();
    }

    public static Pair<Map<String, BigDecimal>, Map<String, BigDecimal>> getPair(List<Currency> currencies) {
        Map<String, List<Currency>> rateGroup = currencies.stream()
                .collect(Collectors.groupingBy(Currency::getCurrencyCode));

        Map<String, BigDecimal> currenciesMap = rateGroup.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, e -> {
                    Integer count = e.getValue().stream()
                            .map(Currency::getAmount)
                            .reduce(0, Integer::sum);
                    return new BigDecimal(count);
                })
        );

        Map<String, BigDecimal> rates = ValidTool.getRate("currencyRate.txt");
        return new Pair<>(currenciesMap, rates);
    }
}
