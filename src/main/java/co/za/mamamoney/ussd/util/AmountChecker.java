package co.za.mamamoney.ussd.util;

import java.math.BigDecimal;

public class AmountChecker {

    private static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(0);
    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(3000);

    public static Boolean isInvalidAmount(BigDecimal amount) {
        return (amount.compareTo(MIN_AMOUNT) < 0 || amount.compareTo(MAX_AMOUNT) > 0);
    }
}
