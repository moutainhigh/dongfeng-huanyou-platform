package messaging;

import java.lang.annotation.*;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CommandType {
    public abstract String value();
}