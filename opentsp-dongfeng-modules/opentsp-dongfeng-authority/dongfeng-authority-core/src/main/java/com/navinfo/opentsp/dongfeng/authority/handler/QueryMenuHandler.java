package com.navinfo.opentsp.dongfeng.authority.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.QueryMenuCommand;
import com.navinfo.opentsp.dongfeng.authority.pojo.MenuPojo;
import com.navinfo.opentsp.dongfeng.authority.service.IMenuService;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryMenuHandler extends ValidateTokenAndReSetAbstracHandler<QueryMenuCommand, HttpCommandResultWithData> {

	protected static final Logger logger = LoggerFactory.getLogger(QueryMenuHandler.class);

	public QueryMenuHandler() {
		super(QueryMenuCommand.class, HttpCommandResultWithData.class);
	}

	protected QueryMenuHandler(Class<QueryMenuCommand> commandType, Class<HttpCommandResultWithData> resultType) {
		super(commandType, resultType);
	}

	/**
	 * 车辆扫码报表
	 */
	private static final String CAR_SCAN_ACTION_CODE = "2,5019";
	private static final int TERMINAL_CODE = 4004;
	private static final int TERMINAL_MODEL_CODE = 4010;
	private static final int TERMINAL0 = 1021;
	private static final int TERMINAL1 = 1031;
	private static final int TERMINAL2 = 1032;
	private static final int TERMINAL3 = 1033;
	private static final int TERMINAL4 = 1034;
	private static final int TERMINAL5 = 1035;

	@Autowired
	private IMenuService menuService;

	@Override
	protected HttpCommandResultWithData businessHandle(QueryMenuCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		List<MenuPojo> pojo = new ArrayList<>();
		try {
/*			// 车厂用户 交付室用户
			if (command.getUserInfor().getType() == UserTypeConstant.CAR_FACTORY.getCode()
					&& command.getUserInfor().getJobType() == JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode()) {
				pojo = menuService.queryMenuByCodes(CAR_SCAN_ACTION_CODE);
			} else {
				pojo = menuService.queryMenuByUserId(command.getUserInfor().getUserId(),
						command.getUserInfor().getType());
			}*/
			pojo = menuService.queryMenuByUserId(command.getUserInfor().getUserId(),
					command.getUserInfor().getType());
			// 如果该用户没有终端管理和终端型号管理权限，则从列表中移除 0,无; 1,有
			if (command.getUserInfor().getTerAuthority() == 0) {
				for (int i = 0; i < pojo.size(); i++) {
					if (pojo.get(i).getId() == TERMINAL_CODE || pojo.get(i).getId() == TERMINAL_MODEL_CODE) {
						pojo.remove(i);
					}
				}
			}
			if (command.getUserInfor().getType() == UserTypeConstant.CAR_FACTORY.getCode()
					&& command.getUserInfor().getTerAuthority() == 1) {
				addTerminalCode(command, pojo);
			}
			result.setData(pojo);
		} catch (Exception e) {
			logger.error(" query menu failed ", e);
			result.fillResult(ReturnCode.QUERY_MENU_FAILED);
		}
		return result;
	}

	private void addTerminalCode(QueryMenuCommand command, List<MenuPojo> pojo) {
		int flag0 = 0;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag4 = 0;
		int flag5 = 0;
		int flag6 = 0;
		int flag7 = 0;
		for (int i = 0; i < pojo.size(); i++) {
			if (pojo.get(i).getId() == TERMINAL_CODE) {
				flag0++;
			}
			if (pojo.get(i).getId() == TERMINAL_MODEL_CODE) {
				flag1++;
			}
			if (pojo.get(i).getId() == TERMINAL0) {
				flag2++;
			}
			if (pojo.get(i).getId() == TERMINAL1) {
				flag3++;
			}
			if (pojo.get(i).getId() == TERMINAL2) {
				flag4++;
			}
			if (pojo.get(i).getId() == TERMINAL3) {
				flag5++;
			}
			if (pojo.get(i).getId() == TERMINAL4) {
				flag6++;
			}
			if (pojo.get(i).getId() == TERMINAL5) {
				flag7++;
			}
		}

		if (flag0 != 1) {
			pojo.add(getMenuPojo0());
		}
		if (flag1 != 1) {
			pojo.add(getMenuPojo1());
		}
		if (flag2 != 1) {
			pojo.add(getMenuPojo2());
		}
		if (flag3 != 1) {
			pojo.add(getMenuPojo3());
		}
		if (flag4 != 1) {
			pojo.add(getMenuPojo4());
		}
		if (flag5 != 1) {
			pojo.add(getMenuPojo5());
		}
		if (flag6 != 1) {
			pojo.add(getMenuPojo6());
		}
		if (flag7 != 1) {
			pojo.add(getMenuPojo7());
		}
	}

	private MenuPojo getMenuPojo0() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL_CODE);
		pojo.setPid(3);
		pojo.setName("终端管理");
		return pojo;
	}

	private MenuPojo getMenuPojo1() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL_MODEL_CODE);
		pojo.setPid(3);
		pojo.setName("终端型号管理");
		return pojo;
	}

	private MenuPojo getMenuPojo2() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL0);
		pojo.setPid(4004);
		pojo.setName("终端参数");
		return pojo;
	}

	private MenuPojo getMenuPojo3() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL1);
		pojo.setPid(1021);
		pojo.setName("位置汇报设置");
		return pojo;
	}

	private MenuPojo getMenuPojo4() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL2);
		pojo.setPid(1021);
		pojo.setName("CAN汇报设置");
		return pojo;
	}

	private MenuPojo getMenuPojo5() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL3);
		pojo.setPid(1021);
		pojo.setName("驾驶行为设置");
		return pojo;
	}

	private MenuPojo getMenuPojo6() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL4);
		pojo.setPid(1021);
		pojo.setName("终端初始化参数设置");
		return pojo;
	}

	private MenuPojo getMenuPojo7() {
		MenuPojo pojo = new MenuPojo();
		pojo.setId(TERMINAL5);
		pojo.setPid(1021);
		pojo.setName("终端升级设置");
		return pojo;
	}

}