package com.navinfo.opentsp.common.messaging;

@Deprecated
public class HttpResult {
    public static final ResultCode OK = ResultCode.OK;
    public static final ResultCode CLIENT_ERROR = ResultCode.CLIENT_ERROR;
    public static final ResultCode UNAUTHORIZED = ResultCode.UNAUTHORIZED;
    public static final ResultCode FORBIDDEN = ResultCode.FORBIDDEN;
    public static final ResultCode SERVER_ERROR = ResultCode.SERVER_ERROR;
    public static final ResultCode CONFLICT = ResultCode.CONFLICT;
}