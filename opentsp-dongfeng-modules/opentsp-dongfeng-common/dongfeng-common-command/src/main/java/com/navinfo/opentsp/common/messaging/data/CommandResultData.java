package com.navinfo.opentsp.common.messaging.data;


import com.navinfo.opentsp.common.messaging.AbstractCommand;
import com.navinfo.opentsp.common.messaging.ResultCode;

import java.io.Serializable;
import java.util.HashMap;


public class CommandResultData extends HashMap<String, Object>
        implements AbstractCommand.ExtendedResult, Serializable {
    public int getResultCode() {
        Object resultCode = get("resultCode");
        if (resultCode != null) {
            return Integer.valueOf(resultCode.toString()).intValue();
        }
        return ResultCode.OK.code();
    }

    public String getMessage() {
        Object message = get("message");
        if (message != null) {
            return message.toString();
        }
        return ResultCode.OK.result();
    }
}