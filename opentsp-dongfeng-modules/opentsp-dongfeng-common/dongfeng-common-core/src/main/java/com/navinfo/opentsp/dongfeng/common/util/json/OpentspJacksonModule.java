/*    */ package com.navinfo.opentsp.dongfeng.common.util.json;
/*    */ 
/*    */

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import org.springframework.util.MimeType;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class OpentspJacksonModule extends Module
/*    */ {
/*    */   public String getModuleName()
/*    */   {
/* 22 */     return getClass().getName();
/*    */   }
/*    */ 
/*    */   public Version version()
/*    */   {
/* 31 */     return new Version(1, 0, 0, null, null, null);
/*    */   }
/*    */ 
/*    */   public void setupModule(SetupContext setupContext)
/*    */   {
/* 41 */     SimpleSerializers serializers = new SimpleSerializers();
/* 42 */     addSerializers(serializers);
/* 43 */     setupContext.addSerializers(serializers);
/*    */ 
/* 45 */     SimpleDeserializers deserializers = new SimpleDeserializers();
/* 46 */     addDeserializers(deserializers);
/* 47 */     setupContext.addDeserializers(deserializers);
/*    */   }
/*    */ 
/*    */   private void addDeserializers(SimpleDeserializers deserializers) {
/* 51 */     deserializers.addDeserializer(MimeType.class, new MimeTypeDeserializer());
/*    */   }
/*    */
/*    */   private void addSerializers(SimpleSerializers serializers) {
/* 55 */     serializers.addSerializer(MimeType.class, new MimeTypeSerializer());
/*    */   }
/*    */ }

/* Location:           F:\.m2\repository\com\navinfo\opentsp-common-messaging\1.346-RC13\opentsp-common-messaging-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.json.OpentspJacksonModule
 * JD-Core Version:    0.6.2
 */