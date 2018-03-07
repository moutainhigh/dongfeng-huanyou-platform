package com.navinfo.opentsp.common.messaging;

import java.lang.annotation.*;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface KafkaConsumerHandler
{
  public abstract String topic();

  public abstract String commandId();
}