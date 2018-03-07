//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface KafkaConsumerHandler {
    String topic();

    String commandId();
}
