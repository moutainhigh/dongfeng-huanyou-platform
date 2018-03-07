package messaging;

import java.util.Objects;


public class ErrorItem {
    private Object code;
    private String message;

    public ErrorItem() {
    }

    public ErrorItem(Object code) {
        this.code = code;
    }

    public ErrorItem(Object code, String message) {
        this.code = code;
        this.message = message;
    }

    public Object getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        ErrorItem that = (ErrorItem) o;

        return (Objects.equals(this.code, that.code)) &&
                (Objects.equals(this.message, that.message));
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.code, this.message});
    }
}