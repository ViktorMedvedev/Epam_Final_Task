package main.java.by.epam.tattoo.util;

import java.math.BigDecimal;

public interface ConstantHeap {
    int MAX_DISCOUNT_VALUE = 50;
    int DEFAULT_DISCOUNT_CHARGE = 2;
    BigDecimal PERCENT_CONVERTER = new BigDecimal(0.01);
    String NOT_CHOSEN_EN = "All";
    String NOT_CHOSEN_RU = "Все";
    int RECORDS_PER_PAGE = 12;
}
