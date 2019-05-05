package com.leo.labs.start.jar.demo.uid.api;

import com.leo.labs.start.jar.demo.uid.exception.UidGenerateException;

public interface UidGenerator {


    /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    String getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);


}
