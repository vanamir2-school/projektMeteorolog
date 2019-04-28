package ppj.vana.projekt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilService {

    public static final int TRANSACTION_TIMEOUT = 20000;
    private static final Logger logger = LoggerFactory.getLogger(UtilService.class);
    private static final long ONE_DAY_MILISSECONDS = 86400000;
    private static SimpleDateFormat simpleDateFormat = null;

    private UtilService(){}

    /**
     * Converts UNIX time to String with pattern "dd-MM-yyyy HH:mm:ss".
     */
    public static String timestampToStringSeconds(Long seconds) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(seconds * 1000L);
    }

    public static String timestampToStringMilliSeconds(Long miliseconds) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(miliseconds);
    }

    public static Long getTimestampXDaysBackInSeconds(int daysAgo){
        Date currentTime = new Date();
        Long timestampXDaysBack = currentTime.getTime() - ONE_DAY_MILISSECONDS * daysAgo;
        Long timestampXDaysBackSeconds = timestampXDaysBack / 1000;
        logger.info("Current time: " + UtilService.timestampToStringMilliSeconds(currentTime.getTime()));
        logger.info("Timestamp {} days ago is: " + UtilService.timestampToStringSeconds(timestampXDaysBackSeconds), daysAgo);
        return timestampXDaysBackSeconds;
    }


}
