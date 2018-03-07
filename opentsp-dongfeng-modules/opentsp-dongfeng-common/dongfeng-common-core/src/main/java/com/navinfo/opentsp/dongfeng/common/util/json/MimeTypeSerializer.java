/*    */ package com.navinfo.opentsp.dongfeng.common.util.json;
/*    */ 
/*    */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.MimeType;

import java.io.IOException;

/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ class MimeTypeSerializer extends JsonSerializer<MimeType>
/*    */ {
/*    */   public void serialize(MimeType value, JsonGenerator gen, SerializerProvider serializers)
/*    */     throws IOException
/*    */   {
/* 17 */     gen.writeString(value.toString());
/*    */   }
/*    */ }

/* Location:           F:\.m2\repository\com\navinfo\opentsp-common-messaging\1.346-RC13\opentsp-common-messaging-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.json.MimeTypeSerializer
 * JD-Core Version:    0.6.2
 */