/*    */ package com.navinfo.dongfeng.terminal.comm.common.handler;

import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import messaging.Command;

/*    */
/*    */
/*    */ public abstract class AbstractCommandHandler<C extends Command<?>, CR extends CommonResult>
/*    */   implements CommandHandler<C, CR>
/*    */ {
/*    */   private final Class<C> commandType;
/*    */   private final Class<CR> resultType;
/*    */ 
/*    */   protected AbstractCommandHandler(Class<C> commandType, Class<CR> resultType)
/*    */   {
/* 16 */     this.commandType = commandType;
/* 17 */     this.resultType = resultType;
/*    */   }
/*    */ 
/*    */   public Class<C> getCommandType()
/*    */   {
/* 25 */     return this.commandType;
/*    */   }
/*    */ 
/*    */   public Class<CR> getResultType()
/*    */   {
/* 33 */     return this.resultType;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\opentsp-common-core-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentspcore.common.handler.AbstractCommandHandler
 * JD-Core Version:    0.6.2
 */