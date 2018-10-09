package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

    private static final String template = "TWD %s, %s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/exchangerate")
    public ExchangeRate exchangerate(@RequestParam(value="name", defaultValue="USD") String name, 
    @RequestParam(value="value", defaultValue="1000") String value) {
        return new ExchangeRate(counter.incrementAndGet(), String.format(template, value, accessValue(name, value)));
    }

    private String accessValue(String currency, String value) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("USD", "sp-america-img");
        map.put("HKD", "sp-hong-kong-img");
        map.put("GBP", "sp-england-img");
        map.put("AUD", "sp-australia-img");
        map.put("CAD", "sp-canada-img");
        if(!map.keySet().contains(currency)) currency = "USD";

        String rate = "";
        try {
            Float num = Float.parseFloat(value);
            Document html = Jsoup.connect("https://rate.bot.com.tw/xrt/all/day").get();
            Element root = html.selectFirst("tbody");
            Elements query = root.select("tr:has(."+ map.get(currency) +") > td:not(:has(*))");
            query.forEach(item->
                {
                    Float origin = num / Float.parseFloat(item.ownText());
                    item.text(String.valueOf(origin));
                }
            );

            // TWD cash selling -> index 1
            rate = currency + " " + query.get(1).text();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return rate;
    }
}