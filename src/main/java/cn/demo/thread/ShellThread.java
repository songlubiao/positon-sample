package cn.demo.thread;

import cn.demo.exception.DemoException;
import cn.demo.model.Currency;
import cn.demo.util.ValidTool;
import cn.hutool.core.lang.Pair;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author 76732
 */
@RequiredArgsConstructor
public class ShellThread implements Runnable {

    private final Lock lock;
    private final List<Currency> currencies;

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
                String inputLine = is.readLine();

                if (inputLine.length() == 0) {
                    System.out.println("Error empty input !");
                }
                if (inputLine.equalsIgnoreCase("quit")) {
                    System.exit(0);
                }

                String[] items = inputLine.trim().split(" ");
                if (items.length != 2) {
                    System.out.println("Error input !");
                }

                String currencyCode = items[0];
                if (!ValidTool.validateCurrency(currencyCode)) {
                    System.out.println("Error input !");
                }
                int amount = Integer.parseInt(items[1]);
                Currency currency = Currency.builder()
                        .currencyCode(currencyCode)
                        .amount(amount)
                        .build();
                currencies.add(currency);

                String path = this.getClass().getResource("/").toString();
                Pair<Map<String, BigDecimal>, Map<String, BigDecimal>> pairMap = ValidTool.getPair(currencies);
                ValidTool.display(pairMap.getKey(), pairMap.getValue());

            } catch (IOException | DemoException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
