package com.ef.extracters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by sergio.leottau on 1/10/17.
 */
@Service
public class IpExtractor implements Extractor<String, String> {

    /**
     * IP address regular expression
     */
    private static final String IP_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    /**
     * IP index position
     */
    private static int ipIndex = -1;

    @Override
    public String extract(String[] elements) {
        String ip = StringUtils.EMPTY;

        if(ipIndex != -1 && ipIndex < elements.length && elements[ipIndex].trim().matches(IP_REGEX)){
            ip = elements[ipIndex].trim();
        }else {
            int index = 0;
            for (String element : elements) {
                if (element.trim().matches(IP_REGEX)) {
                    ip = element.trim();
                    break;
                }
                index++;
            }
        }
        return ip;
    }
}
