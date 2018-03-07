package com.navinfo.dongfeng.terminal.comm.tcp.business.TCPJoggle;

import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.terminal.LCDownCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.model.system.instructlayin.HyInstructLayin;
import com.navinfo.dongfeng.terminal.comm.services.IHyInstructLayinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TCPReceiver {

	@Resource
	private IHyInstructLayinService nstructLayinService;
	protected final static Logger log = LoggerFactory.getLogger(TCPReceiver.class);
	public void Receiver_3001(Packet packet)
	{
		try
		{
			LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(packet.getContentForBytes());
//			String responseId = Convert.decimalToHexadecimal(res.getResponseId(), 4);
		
			HyInstructLayin instructlayin=new HyInstructLayin();
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			instructlayin.setInstructDate(format.format(date));
			if(res.getResult()== LCResponseResult.ResponseResult.success)
			{
				instructlayin.setInstructState("下发设置成功");
			}
			else
			{
				instructlayin.setInstructState(res.getResult().toString());
			}
			instructlayin.setInstructSerialnumber(packet.getSerialNumber());
			nstructLayinService.updateByPrimaryKeySelective(instructlayin);
		}
		 catch (Exception e) {
				// TODO: handle exception
			 log.trace("消息异常;"+e.getMessage());
			}
	}
}
