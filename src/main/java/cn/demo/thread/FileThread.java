package cn.demo.thread;

import cn.demo.exception.DemoException;
import cn.demo.model.Currency;
import cn.demo.util.ValidTool;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Pair;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * @author 76732
 */
@RequiredArgsConstructor
public class FileThread implements Runnable {

    private final Lock lock;

    private final String fileName;

    @Override
    public void run() {
        lock.lock();
        try {
            List<String> lines = FileUtil.readUtf8Lines(fileName);
            List<Currency> currencies = lines.stream().map(str -> {
                String[] items = str.split(" ");
                String currencyCode = items[0];
                try {
                    if (!ValidTool.validateCurrency(currencyCode)) {
                        System.out.println("Error input !");
                    }
                } catch (DemoException e) {
                    System.out.println("Error input");
                }
                int amount = Integer.parseInt(items[1]);
                return Currency.builder()
                        .currencyCode(currencyCode)
                        .amount(amount)
                        .build();
            }).collect(Collectors.toList());

            Pair<Map<String, BigDecimal>, Map<String, BigDecimal>> pairMap = ValidTool.getPair(currencies);
            ValidTool.display(pairMap.getKey(), pairMap.getValue());

        } finally {
            lock.unlock();
        }

    }


}
