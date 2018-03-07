/*    */ package com.navinfo.opentsp.dongfeng.common.util.json;
/*    */ 
/*    */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ public class MimeTypeDeserializer extends JsonDeserializer<MimeType>
/*    */ {
/*    */   public MimeType deserialize(JsonParser p, DeserializationContext ctxt)
/*    */     throws IOException
/*    */   {
/* 17 */     String mimeString = (String)p.readValueAs(String.class);
/* 18 */     return MimeTypeUtils.parseMimeType(mimeString);
/*    */   }
/*    */ }

/* Location:           F:\.m2\repository\com\navinfo\opentsp-common-messaging\1.346-RC13\opentsp-common-messaging-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.json.MimeTypeDeserializer
 * JD-Core Version:    0.6.2
 */