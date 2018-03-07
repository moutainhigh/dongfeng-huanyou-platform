package messaging;

public enum ResultCode {
    OK(200, "OK"),
    CLIENT_ERROR(400, "Client Error"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    SERVER_ERROR(500, "Server Error"),
    CONFLICT(409, "Conflict");

    private final int code;
    private final String result;

    private ResultCode(int code, String result) {
        this.code = code;
        this.result = result;
    }

    public int code() {
        return this.code;
    }

    public String result() {
        return this.result;
    }
}