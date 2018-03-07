/*    */ package com.navinfo.opentsp.dongfeng.common.util.json;
/*    */ 
/*    */ import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ public class JacksonUtils
/*    */ {
/*    */   public static ObjectMapper objectMapperBuilder()
/*    */   {
/* 23 */     ObjectMapper objectMapper = new ObjectMapper()
/* 20 */       .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
/* 21 */       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
/* 23 */       .registerModules(new Module[] { new JodaModule(), new OpentspJacksonModule() });
/*    */ 
/* 25 */     return objectMapper;
/*    */   }
/*    */ }

/* Location:           F:\.m2\repository\com\navinfo\opentsp-common-messaging\1.346-RC13\opentsp-common-messaging-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.json.JacksonUtils
 * JD-Core Version:    0.6.2
 */