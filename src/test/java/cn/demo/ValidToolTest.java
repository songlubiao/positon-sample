package cn.demo;

import cn.demo.util.ValidTool;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

public class ValidToolTest {

    @Test
    public void getRateTest() {
        String path = this.getClass().getResource("/").toString();
        Map<String, BigDecimal> rates = ValidTool.getRate(path + File.separator + "currencyRate.txt");
        rates.forEach((k, v) ->
                System.out.println(String.format("%s-%s", k, v))
        );
    }
}
