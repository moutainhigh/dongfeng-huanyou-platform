package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 立即拍照/录像
 * Gps_2153_TakePhotography
 * @author maojin
 *
 */
@Component
public class Gps_2153_TakePhotography extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
//			SimPhotoCache.instance().add(packet.getUniqueMark(), packet.getSession());//添加拍照操作缓存
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "立即拍照/录像");
		} catch (Exception e) {
			log.error("立即拍照/录像异常", e);
		}
		return null;
	}


}
