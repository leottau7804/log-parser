package com.ef.repositories;

import com.ef.model.AccessLog;
import com.ef.model.Blocked;
import org.springframework.data.repository.CrudRepository;

/**
 * IP address blocked repository.
 *
 * Created by sergio.leottau on 1/10/17.
 */
public interface BlockedRepository extends CrudRepository<Blocked, Integer> {
}
