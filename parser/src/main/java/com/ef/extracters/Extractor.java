package com.ef.extracters;

/**
 * Interface to extract information of access log.
 * Created by sergio.leottau on 1/10/17.
 */
public interface Extractor<T, E> {

    /**
     * Extract information of access log.
     *
     * @param elements elements array
     * @return the value found
     */
    T extract(E[] elements);
}
