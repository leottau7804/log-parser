package com.ef.repositories;

import com.ef.model.AccessLog;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Access log repository. It is in charge with access log persistence and searching.
 * <p>
 * Created by sergio.leottau on 1/10/17.
 */
public interface AccessLogRepository extends CrudRepository<AccessLog, Integer> {


    /**
     * This method is in charge of search all access logs for a given IP address between the dates given. Equivalent SQL:
     * <p>
     * SELECT * FROM parser.access_log WHERE ip = #ip AND date >= #begin AND date <= #end
     *
     * @param ip    ip to do the search
     * @param begin start date to filter results
     * @param end   end date to filter results
     * @return list with access logs
     */
    List<AccessLog> findByIpAndDateGreaterThanEqualAndDateLessThanEqual(String ip, Date begin, Date end);
}
