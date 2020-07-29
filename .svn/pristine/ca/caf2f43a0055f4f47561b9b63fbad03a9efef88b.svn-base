package com.nuite.common.validator;

import com.nuite.common.exception.SmartException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new SmartException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new SmartException(message);
        }
    }
}
