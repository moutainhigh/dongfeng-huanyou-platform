/*    */ package com.navinfo.opentsp.common.messaging.routing;
/*    */ 
/*    */


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
/*    */ 
/*    */ public class MessageGroupInfo
/*    */ {
/*    */ 
/*    */   @JsonProperty
/*    */   private String exchangeName;
/*    */ 
/*    */   @JsonProperty
/*    */   private String queueName;
/*    */   private Map<String, String> attributes;
/*    */ 
/*    */   public String getExchangeName()
/*    */   {
/* 32 */     return this.exchangeName;
/*    */   }
/*    */ 
/*    */   public void setExchangeName(String exchangeName) {
/* 36 */     this.exchangeName = exchangeName;
/*    */   }
/*    */ 
/*    */   public String getQueueName() {
/* 40 */     return this.queueName;
/*    */   }
/*    */ 
/*    */   public void setQueueName(String queueName) {
/* 44 */     this.queueName = queueName;
/*    */   }
/*    */ 
/*    */   public Map<String, String> getAttributes() {
/* 48 */     return this.attributes;
/*    */   }
/*    */ 
/*    */   public void setAttributes(Map<String, String> attributes) {
/* 52 */     this.attributes = attributes;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 57 */     return "MessageGroupInfo{exchangeName='" + this.exchangeName + '\'' + ", queueName='" + this.queueName + '\'' + ", attributes=" + this.attributes + '}';
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\opentsp-common-command-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentsp.common.messaging.routing.MessageGroupInfo
 * JD-Core Version:    0.6.2
 */