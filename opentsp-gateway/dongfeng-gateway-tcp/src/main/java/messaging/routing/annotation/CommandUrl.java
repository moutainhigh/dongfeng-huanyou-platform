package messaging.routing.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Component
@Target({java.lang.annotation.ElementType.TYPE})
public @interface CommandUrl
{
  public abstract String value();
}

/* Location:           C:\Users\Administrator\Desktop\opentsp-common-command-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.routing.annotation.CommandUrl
 * JD-Core Version:    0.6.2
 */