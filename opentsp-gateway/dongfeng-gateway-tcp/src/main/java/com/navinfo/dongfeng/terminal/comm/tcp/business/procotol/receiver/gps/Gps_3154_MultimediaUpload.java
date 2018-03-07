package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.lc.core.protocol.terminal.monitor.LCMultimediaUpload;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.LogSwitch;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 多媒体数据上传
 * Gps_3154_MultimediaUpload
 * @author maojin
 *
 */
@Component
public class Gps_3154_MultimediaUpload extends Command{
	Logger logger = LoggerFactory.getLogger(Gps_3154_MultimediaUpload.class);

	@Override
	public Object processor(Packet packet) {
		try{
			String sim=packet.getUniqueMark().substring(1);
			if(LogSwitch.LogLevel.INFO_LEVEL)
				log.info("3154_MultimediaUpload>>"+sim+">>content:"+Convert.bytesToHexString(packet.getContentForBytes()));
		//接收到数据，解析protobuf对象
		LCMultimediaUpload.MultimediaUpload multUpload= LCMultimediaUpload.MultimediaUpload.parseFrom(packet.getContentForBytes());
/*******cz,20160714,undo		
		//拼PhotoBean对象
		PhotoBean photoBean=new PhotoBean();
		photoBean.setCph(packet.getUniqueMark().substring(1));//sim卡号
		
		HyCarTerminal carTerminal=CarTerminalCache.instance().get(CarTerminalCache.SIM_PRIFEX,packet.getUniqueMark().substring(1));
		if(carTerminal!=null){
			photoBean.setVehicleId(Integer.parseInt(carTerminal.getCarId()+"")); //车辆i
		}else{
			photoBean.setVehicleId(0); //缓存不存在默认置0
		}
		photoBean.setmId(multUpload.getMediaId());//媒体Id
		photoBean.setCamera(multUpload.getChannels());//拍照通道
		photoBean.setImgHeight(240);//根据图片数据自动生成，默认240
		photoBean.setImgWidth(320);//根据图片数据自动生成，默认320
		photoBean.setPacketTotal(1);//目前按不分包处理
//		photoBean.getPackets().add(0, Convert.bytesToHexString(multUpload.getMediaData().toByteArray()));//不分包只有一个包数据
		photoBean.setPackets(new String[]{Convert.bytesToHexString(multUpload.getMediaData().toByteArray())});//不分包只有一个包数据
		photoBean.seteMediaType(multUpload.getTypes().getNumber());//媒体类型，0x00 图片，0x01 音频，0x02 录像
		photoBean.setmEncode(multUpload.getEncode().getNumber());///多媒体格式编码,0x00 jpeg图片格式,0x01 tif图片格式,0x02 mp3音频格式,0x03 wav音频格式,0x04 wmv视频格式
		photoBean.setSign(multUpload.getEvents().getNumber());//多媒体事件项，平台下发消息，定时动作...
		
		HyDataAction dataAction=new HyDataAction();
		LocationData locationData= multUpload.getLocationData();
		dataAction.setVehicleId((long)photoBean.getVehicleId()); //车辆Id
		dataAction.setAlarmInfo(locationData.getAlarm());//报警状态
		dataAction.setStatisInfo(locationData.getStatus());//车辆状态
		dataAction.setLongitude(locationData.getLongitude());//经度
		dataAction.setLatitude(locationData.getLatitude());//纬度
		dataAction.setHeight(locationData.getHeight());//高度
		dataAction.setVelocity(locationData.getSpeed());//速度
		dataAction.setDirection(locationData.getDirection());//方向
		dataAction.setGpsTime(locationData.getGpsDate());//GPS时间
		dataAction.setOriginalLng(locationData.getOriginalLng());//原始经度
		dataAction.setOriginalLat(locationData.getOriginalLat());//原始纬度
		StringBuffer remark=new StringBuffer();
		remark.append("{");
		Map<FieldDescriptor, Object> allFields= locationData.getAllFields();
		for(FieldDescriptor key:allFields.keySet()){
			remark.append(key.getName()+":"+allFields.get(key).toString()+",");
		}
		if(remark.length()>1){//去掉最后,
			remark.deleteCharAt(remark.length()-1);
		}
		remark.append("}");
		dataAction.setRemark(remark.toString());
		
		photoBean.setGpsdata(dataAction);;
		//添加驾驶员姓名和从业资格号码
		//从刷卡信息缓存中根据车辆Id卡号获取刷卡信息
		 if(null!=DriverInfoCache.instance().get(sim)){
			//获取人员信息
			 HyEmployees employees=new HyEmployees();
			 employees.setEmpCyzghm(DriverInfoCache.instance().get(sim).getCertificateCode());
			 HyEmployeesAndDetail employeesAndDetail=new HyEmployeesAndDetail();
			 employeesAndDetail.setHyEmployees(employees);
			List<HyEmployeesAndDetail> ressAndDetails= new EmployeesManageServiceImpl().selectByPropertyFromCache(employeesAndDetail);
				if(ressAndDetails != null&&ressAndDetails.size()>0){
					if(ressAndDetails.get(0).getHyEmployees().getEmpName().equals(DriverInfoCache.instance().get(sim).getName())){
						photoBean.seteDriverName(DriverInfoCache.instance().get(sim).getName());
					}else{
						photoBean.seteDriverName("未知");
					}
				}
			photoBean.seteDriverCode(DriverInfoCache.instance().get(sim).getCertificateCode());
			photoBean.seteDriverTime(DriverInfoCache.instance().get(sim).getCardSwitchDate());
		 }
		
		//调用webServic 写入图片并存储数据 ，caozhen，20151123，undo
		 CarManageBusi myservice=new CarManageBusi();
		String[] result= myservice.VehPotoGaphOperate(photoBean);
	    //result:[0]执行结果，[1]车辆ID,[2]图片存放路径,[3]图片记录ID
		if(result[0]=="-1"){
			return null;
		}
		//根据返回存储结果修改protobuf对象里的媒体数据为图片地址
		MultimediaUpload.Builder builder=MultimediaUpload.newBuilder();
		builder.setChannels(multUpload.getChannels());
		builder.setEncode(multUpload.getEncode());
		builder.setEvents(multUpload.getEvents());
		builder.setLocationData(multUpload.getLocationData());
		builder.setMediaId(multUpload.getMediaId());
		builder.setTypes(multUpload.getTypes());
		//caozhen,20151211,done
		builder.setMediaData(ByteString.copyFromUtf8(result[2]));
		if(result[3]!=null&&!result[3].equals("null")){
		builder.setMediaId(Long.parseLong(result[3]));//方便前台业务操作，调整为存放图片记录Id
		}
		
		//生成包对象返回给客户端
		Packet packetClient=new Packet();
		packetClient.setMsgType(constant.CLIENT_MESSAGE_TYPE);
		packetClient.setUniqueMark(packet.getUniqueMark());
		packetClient.setSerialNumber(IDFactory.builderID(IDType.SerialNumber).toString());
		packetClient.setContentForBytes(builder.build().toByteArray());
		packetClient.setCommand("3154");
		if(LogSwitch.LogLevel.INFO_LEVEL)
		System.out.println("3154_MultimediaUpload>>>>content:"+Convert.bytesToHexString(packetClient.getContentForBytes()));
		//获取链路并发送
//		Set<IoSession> sessionClients=SimPhotoCache.instance().getByKey(packet.getUniqueMark());
//		if(sessionClients!=null){
//			for(IoSession session:sessionClients){
//				session.write(packetClient);
//			}
//		}
		sendMsgToClient(packetClient, "多媒体数据上传");
		//测试链路发送
//		Set <Long> keys=GpsLinkCache.instance().getServerIdentifiesSessionCache().keySet();
//		for(Long key:keys){
//			GpsLinkCache.instance().getServerIdentifiesSessionCache(key).write(packetClient);
//		}
		
		//根据809发送缓存记录，判断是否809发送，809发送回复给809
//		Packet oldPacket=Access809LinkCache.instance().getserialNumberToPacketCache(multUpload.getMediaId()+"");
//		if(null!=oldPacket){
//			packet.setCommand("5104");
//			GpsCommandFactory.processor(packet);
//		}
		
		//清空拍照操作缓存
		SimPhotoCache.instance().delete(packet.getUniqueMark());
//		sendMsgToClient(packetClient, "多媒体数据上传");
		//回复上行消息通用应答到云平台
		Packet packetLC=new Packet();
		packetLC.setMsgType(constant.GPS_MESSAGE_TYPE);
		packetLC.setSerialNumber(IDFactory.IDType.SerialNumber.toString());
		packetLC.setCommand("2001");
		packetLC.setUniqueMark(packet.getUniqueMark());
		
		LCUpCommonRes.UpCommonRes.Builder bulider1 = LCUpCommonRes.UpCommonRes.newBuilder();
		bulider1.setSerialNumber(Integer.parseInt(packet.getSerialNumber(),16));
		bulider1.setResponseId(3154);
		bulider1.setResult(ResponseResult.success);
		packetLC.setContentForBytes(bulider1.build().toByteArray());
		sendMsgToCloud(packetLC, "多媒体数据上传");
*******/		
		}catch(Exception e){
			logger.error("异常", e);
		}
		return null;
	}


}
