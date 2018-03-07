package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.common.LCResponseResult;
import com.lc.core.protocol.terminal.LCDriverInfoReport;
import com.lc.core.protocol.terminal.LCUpCommonRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory;
import com.navinfo.dongfeng.terminal.comm.common.IDFactory.IDType;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import org.springframework.stereotype.Component;

/**
 * 驾驶员身份信息采集上报
 * Gps_3006_DriverInfoReport
 * @author maojin
 *
 */
@Component
public class Gps_3006_DriverInfoReport extends Command{

	@Override
	public Object processor(Packet packet) {
		try {
			packet.setSession(null);
			packet.setMsgType(Constant.CLIENT_MESSAGE_TYPE);
			sendMsgToClient(packet, "驾驶员身份信息采集上报");
			//解析驾驶员上报信息并添加到缓存
			LCDriverInfoReport.DriverInfoReport driverInfoReport= LCDriverInfoReport.DriverInfoReport.parseFrom(packet.getContentForBytes());
			String carSim=packet.getUniqueMark().substring(1);//sim
			Boolean cardStatus=null;//true：插入；false：拔出
			String certificateCode=null;//从业资格证编码
			String name=null; //驾驶员姓名
			Long cardSwitchDate=null;//插卡/拔卡时间
			Integer results=null;//IC卡读取结果
			String organizationName =null;//从业资格发证机构名称
			Long licenseValidDate=null;//证件有效期，精确到天
			if(driverInfoReport.hasCardStatus())
				cardStatus=driverInfoReport.getCardStatus();
			else 
				cardStatus=true;
			if(driverInfoReport.hasCertificateCode())
				certificateCode=driverInfoReport.getCertificateCode();
			if(driverInfoReport.hasName())
				name=driverInfoReport.getName();
			if(driverInfoReport.hasCardSwitchDate())
				cardSwitchDate=driverInfoReport.getCardSwitchDate();
			if(driverInfoReport.hasResults())
				results=driverInfoReport.getResults().getNumber();
			if(driverInfoReport.hasOrganizationName())
				organizationName=driverInfoReport.getOrganizationName();
			if(driverInfoReport.hasLicenseValidDate())
				licenseValidDate=driverInfoReport.getLicenseValidDate();
/******cz,20160714,undo			
			if(cardStatus!=null&&!cardStatus){//拔卡删除缓存
				DriverInfoCache.instance().remove(carSim);
			}else{//非拔卡添加缓存
				DriverInfoCache.instance().add(carSim, cardStatus, certificateCode, name, cardSwitchDate, results, organizationName, licenseValidDate);
				//封装5117转发给809
//				GovUpExgMsgReportDriverInfoAck.Builder builder1=GovUpExgMsgReportDriverInfoAck.newBuilder();
//				HyCarTerminal carTerminal=CarTerminalCache.instance().getBySim(carSim);
//				if(carTerminal!=null){
//					builder1.setVehicleNo(carTerminal.getCarCph());
//					builder1.setVehicleColor(carTerminal.getCarColor());
//				}else{
//					builder1.setVehicleNo("");
//					builder1.setVehicleColor(0);
//				}
//				builder1.setCarSim(Long.parseLong(carSim));
//				if(name!=null)
//				builder1.setDriverName(name);
//				if(certificateCode!=null)
//				builder1.setDriverNo(certificateCode);//驾驶员编号用从业资格证号填充
//				if(certificateCode!=null)
//				builder1.setLicence(certificateCode);
//				if(organizationName!=null)
//				builder1.setOgName(organizationName);
//				if(licenseValidDate!=null)
//				builder1.setValidDate(licenseValidDate);
//				
//				Packet pk=new Packet();
//				pk.setUniqueMark("0"+carSim);
//				pk.setSerialNumber(packet.getSerialNumber());
//				pk.setMsgType(constant.ACCESS809_MESSAGE_TYPE);
//				pk.setCommand("5117");
//				pk.setSession(packet.getSession());
//				pk.setContentForBytes(builder1.build().toByteArray());
//				GpsCommandFactory.processor(pk);
			}
********/			
			
			// 回复云平台
			LCUpCommonRes.UpCommonRes.Builder bulider =  LCUpCommonRes.UpCommonRes.newBuilder();
			bulider.setSerialNumber(Integer.parseInt(packet.getSerialNumber()));
			bulider.setResponseId(3006);
			bulider.setResult(LCResponseResult.ResponseResult.success);
			packet.setCommand("2001");
			packet.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
			packet.setContentForBytes(bulider.build().toByteArray());
			packet.setMsgType(Constant.GPS_MESSAGE_TYPE);
			sendMsgToCloud(packet, "驾驶员身份信息采集上报");
		} catch (Exception e) {
			log.error("驾驶员身份信息采集上报异常", e);
		}
		return null;
	}


}
