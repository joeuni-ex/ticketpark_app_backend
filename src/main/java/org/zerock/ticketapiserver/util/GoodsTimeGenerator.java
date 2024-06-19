package org.zerock.ticketapiserver.util;

import org.zerock.ticketapiserver.controller.advice.formatter.LocalDateFormatter;
import org.zerock.ticketapiserver.domain.GoodsTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoodsTimeGenerator {

    private static final LocalDateFormatter localDateFormatter = new LocalDateFormatter();

    public static List<GoodsTime> generateGoodsTimes(String startDate, String endDate, List<String> timeList) {
        List<GoodsTime> goodsTimes = new ArrayList<>();

        try {
            LocalDate start = localDateFormatter.parse(startDate, Locale.getDefault());
            LocalDate end = localDateFormatter.parse(endDate, Locale.getDefault());

            while (!start.isAfter(end)) {
                for (String time : timeList) {
                    GoodsTime goodsTime = GoodsTime.builder()
                            .date(localDateFormatter.print(start, Locale.getDefault()))
                            .time(time)
                            .build();
                    goodsTimes.add(goodsTime);
                }
                start = start.plusDays(1);
            }
        } catch (Exception e) {
            e.printStackTrace(); // handle the exception appropriately
        }

        return goodsTimes;
    }
}
