package com.navinfo.opentsp.dongfeng.system.converter;


import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.*;

public class CarConverter {
	
	public static CarOutdto convertVechicleOutdto2CarOutdto(VechicleOutdto vechicleOutdto) {
		
		CarOutdto carOutdto = new CarOutdto();
		
		//设置VIN号
//		String structureNum = !StringUtils.isEmpty(vechicleOutdto.getVinOld()) ? vechicleOutdto.getSturctureNum() + "(" + vechicleOutdto.getVinOld() + ")" : vechicleOutdto.getSturctureNum();
//		vechicleOutdto.setSturctureNum(structureNum);
		
		//设置底盘号
//		String chassis = !StringUtils.isEmpty(vechicleOutdto.getChassisOld()) ? vechicleOutdto.getChassisNum() + "(" + vechicleOutdto.getChassisOld() + ")" : vechicleOutdto.getChassisNum();
//		vechicleOutdto.setChassisNum(chassis);
		
		CarDetailOutdto detail = new CarDetailOutdto();
		detail.setEngineType(vechicleOutdto.getEngineType());
		detail.setEngineTypeValue(vechicleOutdto.getEngine_TYPE_VALUE());
		detail.setEngineNumber(vechicleOutdto.getEngineNumber());
		detail.setImg_path(null == vechicleOutdto.getImg_path() || "".equals(vechicleOutdto.getImg_path().trim()) ? "" : vechicleOutdto.getImg_path().trim());
//		detail.setAakSaleTime(vechicleOutdto.getMbSalesDate());
		detail.setMbSalesStatus(vechicleOutdto.getMbSalesStatus());
		detail.setMbSaleStatusValue(vechicleOutdto.getAakStatusValue());
		detail.setMbSaleTime(vechicleOutdto.getMbSalesDate());
		
//		String engineTypeValue = StringUtils.isEmpty(vechicleOutdto.getENGINE_TYPE_VALUE()) ? "" : vechicleOutdto.getENGINE_TYPE_VALUE();
//		String engineTypeRear = StringUtils.isEmpty(vechicleOutdto.getEngineTypeRear()) ? "" : vechicleOutdto.getEngineTypeRear();
//		String newEngineTypeRear = null != vechicleOutdto.getInstalType() && 2 == vechicleOutdto.getInstalType().intValue() ? engineTypeRear : engineTypeRear;

		detail.setEngineTypeRear(vechicleOutdto.getEngineTypeRear());
		detail.setEdgl(vechicleOutdto.getEdgl());
		detail.setZdgl(vechicleOutdto.getZdgl());
		detail.setZbzl(vechicleOutdto.getZbzl());
		detail.setZzl(vechicleOutdto.getZzl());
		
		//设置燃料类型
		detail.setFuel(vechicleOutdto.getFuel());
		detail.setFuelValue(vechicleOutdto.getFuelValue());
		
		TerminalOutdto terminal = new TerminalOutdto();
		terminal.setId(vechicleOutdto.gettId());
		terminal.setTerminalId(null == vechicleOutdto.getTerminalCode() || "".equals(vechicleOutdto.getTerminalCode()) ? "" : vechicleOutdto.getTerminalCode().trim());
		terminal.setSimNo(vechicleOutdto.getTerminalSim());
		
		TerminalOutdto controller = new TerminalOutdto();
		
		controller.setId(vechicleOutdto.getControllerId());
		controller.setTerminalId(null == vechicleOutdto.getTerminalCode() || "".equals(vechicleOutdto.getFkCode().trim()) ? "" : vechicleOutdto.getFkCode().trim());
		controller.setSimNo(vechicleOutdto.getFkSim());
		
		DealerOutdto dealerOutdto = new DealerOutdto();
		dealerOutdto.setId(vechicleOutdto.getDealerId());
		dealerOutdto.setTname(vechicleOutdto.getCompanyName());
		dealerOutdto.setDealerCode(vechicleOutdto.getDealerCode());
		
		BusinessOutdto businessOutdto = new BusinessOutdto();
		businessOutdto.setCid(vechicleOutdto.getCid());
		businessOutdto.setBusinessName(vechicleOutdto.getBusinessName());
		businessOutdto.setBusinessCode(vechicleOutdto.getBusinessCode());
		
		SaleOutdto saleOutdto = new SaleOutdto();
		saleOutdto.setSaleStatus(null == vechicleOutdto.getSaleSatusId() ? 0 : vechicleOutdto.getSaleSatusId());
		saleOutdto.setSaleStatusDescription(vechicleOutdto.getSalesStatusValue());
		saleOutdto.setSalesDateStr(vechicleOutdto.getSalesDateStr());
		saleOutdto.setInvoiceNumber(vechicleOutdto.getInvoiceNumber());
		saleOutdto.setCarAmount(vechicleOutdto.getCarAmount());
		saleOutdto.setLoanAmount(vechicleOutdto.getLoanAmount());
		saleOutdto.setSurplus(vechicleOutdto.getSurplus());
		
		carOutdto.setCar(vechicleOutdto);
		carOutdto.setDetail(detail);
		carOutdto.setTerminal(terminal);
		carOutdto.setController(controller);
		carOutdto.setDealer(dealerOutdto);
		carOutdto.setBusiness(businessOutdto);
		carOutdto.setSale(saleOutdto);
		
		return carOutdto;
		
	}
}
