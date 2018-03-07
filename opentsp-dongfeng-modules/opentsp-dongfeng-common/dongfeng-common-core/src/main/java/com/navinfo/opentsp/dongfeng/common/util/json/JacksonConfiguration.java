/*    */ package com.navinfo.opentsp.dongfeng.common.util.json;
/*    */ 
/*    */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*    */
/*    */
/*    */ 
/*    */ @Configuration
/*    */ public class JacksonConfiguration
/*    */ {
/*    */   @Bean
/*    */   public ObjectMapper objectMapper()
/*    */   {
/* 12 */     return JacksonUtils.objectMapperBuilder();
/*    */   }
/*    */ }

/* Location:           F:\.m2\repository\com\navinfo\opentsp-common-messaging\1.346-RC13\opentsp-common-messaging-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.json.JacksonConfiguration
 * JD-Core Version:    0.6.2
 */