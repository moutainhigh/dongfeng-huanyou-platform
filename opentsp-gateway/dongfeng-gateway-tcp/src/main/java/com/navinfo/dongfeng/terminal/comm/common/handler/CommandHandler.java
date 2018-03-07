package com.navinfo.dongfeng.terminal.comm.common.handler;


import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import messaging.Command;

public abstract interface CommandHandler<C extends Command<?>, CR extends CommonResult>
{
  public abstract CR handle(C paramC);

  public abstract Class<C> getCommandType();

  public abstract Class<CR> getResultType();
}

/* Location:           C:\Users\Administrator\Desktop\opentsp-common-core-1.346-RC13.jar
 * Qualified Name:     com.navinfo.opentspcore.common.handler.CommandHandler
 * JD-Core Version:    0.6.2
 */