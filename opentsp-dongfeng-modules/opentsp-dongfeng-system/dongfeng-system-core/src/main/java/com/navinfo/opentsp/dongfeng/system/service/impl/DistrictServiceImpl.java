package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.district.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.DealerIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.DistrictListOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TeamOutdto;
import com.navinfo.opentsp.dongfeng.system.constant.OperatorType;
import com.navinfo.opentsp.dongfeng.system.converter.DistrictConverter;
import com.navinfo.opentsp.dongfeng.system.converter.TeamConverter;
import com.navinfo.opentsp.dongfeng.system.pojo.DistrictPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.TeamIdPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.TeamPojo;
import com.navinfo.opentsp.dongfeng.system.repository.TeamRepository;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import com.navinfo.opentsp.dongfeng.system.service.IDistrictService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

/**
 * 销售区域ServiceImpl
 */
@Service
@Transactional(rollbackFor={Exception.class})
public class DistrictServiceImpl extends BaseService implements IDistrictService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	DealerService dealerService;
	@Resource
	private IOperateLogService operateLogService;

	/**
	 * 销售区域搜索 by Sunyu
	 *
	 * @param command
	 * @return
	 */
	@Override
	public HttpCommandResultWithData sqlPageDistrict(QueryDistrictCommand command) {
		logger.info(" ========== sqlPageDistrict start ==========");
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		PagingInfo page = new PagingInfo();
		Map<String, Object> param = new HashMap<>();
		param.put("accountType",command.getUserInfor().getType());
		param.put("accountId",command.getUserInfor().getUserId());
		param.put("tname",command.getTname());
		param.put("tlinkman",command.getTlinkman());
		param.put("parentId",command.getParentId());
		PagingInfo<DistrictPojo> pageinfo = dao.sqlPagelFind("queryDistrictPage", param, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), DistrictPojo.class);
		List<DistrictPojo> list = pageinfo.getList();
		page.setList(DistrictConverter.listPojoToDto(list));
		page.setPage_total(pageinfo.getPage_total());
		page.setTotal(pageinfo.getTotal());
		result.setData(page);
		result.fillResult(ReturnCode.OK);
		logger.info(" ========== sqlPageDistrict end ==========");
		return result;
	}

	@Override
	public CommonResult addDistrict(AddDistrictCommand command)throws Exception{
		// 用户信息TODO
//		UserInfoDto user = command.getUserInfor();

		CommonResult result = new CommonResult();
		//判断信息是否已存在
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tName", command.gettName());
		parameter.put("parentId", command.getParentId().toString());
		parameter.put("userType", command.getUserInfor().getType());
		parameter.put("userId", command.getUserInfor().getUserId());
		// 测试用
//		parameter.put("userType", 1);
//		parameter.put("userId", "1");

		if(isDistrictUnique(parameter)){
			DealerIndto dealerIndto = new DealerIndto();
			dealerIndto.setTname(command.gettName());
			dealerIndto.setParentId(command.getParentId());
			dealerIndto.setPname(command.getpName());
			dealerIndto.settLinkMan(command.getTlinkMan());
			dealerIndto.settLinkTle(command.getTlinkTel());
			dealerIndto.settType(0);
			result = dealerService.saveOrUpdate(dealerIndto);

			final String operator = OperatorType.ADD.getValue();
			operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.DISTRICT.getValue() + command.gettName(), result);

		}else{
			result = result.fillResult(ReturnCode.TEAM_HAS_EXIST);
			return result;
		}

		return  result;
	}

	private boolean isDistrictUnique(Map<String, Object> parameter){
		List<BigInteger> list = null;
		boolean flag = true;
		// 系统管理员
		if(StringUtil.isEq(UserTypeConstant.SYSTEM_ADMIN.getCode(), parameter.get("userType").toString())) {
			list = dao.sqlFindField("QueryRepeatBySys", parameter);
		}else{
			list = dao.sqlFindField("QueryRepeatByNotSys", parameter);
		}

		if (list != null && list.get(0) != null && list.get(0).signum() > 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	public HttpCommandResultWithData getTeam(GetTeamCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("name", command.getName());
		parameter.put("type", command.getType());
		parameter.put("pid", command.getPid());
		parameter.put("accountId", command.getUserInfor().getUserId());
		parameter.put("accountType", command.getUserInfor().getType());
		List<TeamPojo> list = dao.sqlFind("getTeamByType",parameter ,TeamPojo.class);
		if (StringUtil.isEmpty(command.getName())) {
			List<TeamOutdto> dtoList = TeamConverter.listPojoToDto(list);
			result.setData(dtoList);
			result.fillResult(ReturnCode.OK);
			return result;
		}

		parameter.clear();
		parameter.put("accountId", command.getUserInfor().getUserId());
		parameter.put("accountType", command.getUserInfor().getType());
		Set teamIds = new HashSet();
		if (CollectionUtils.isNotEmpty(list)) {
			for (TeamPojo team: list) {
				Map<String, Object> paras = new HashMap<>();
				paras.put("teamId", team.getId().longValue());
				TeamIdPojo teamIdPojo = (TeamIdPojo)dao.sqlFindObject("queryParentTeamsById", paras, TeamIdPojo.class);
				teamIds.addAll(Arrays.asList(StringUtil.split(teamIdPojo.getParents(), ",")));
			}
		}

		StringBuffer bufferIds = new StringBuffer();
		for (Object team : teamIds) {
			bufferIds.append(team.toString()).append(",");
		}

		parameter.put("teamIds", bufferIds.append("#").toString());
		List<TeamPojo> parents = dao.sqlFind("getTeamByType",parameter ,TeamPojo.class);
		List<TeamOutdto> dtoList = TeamConverter.listPojoToDto(parents);
		result.setData(dtoList);
		result.fillResult(ReturnCode.OK);
		return result;

	}

	@Override
	public CommonResult updateDistrict(UpdateDistrictCommand command) throws Exception{
		CommonResult result = new CommonResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", new BigInteger(String.valueOf(command.getId())));
		DistrictPojo pojo = (DistrictPojo) dao.sqlFindObject("getDistrict", map, DistrictPojo.class);
		if(!pojo.getTname().equals(command.gettName())||pojo.getParentId().longValue()!=command.getParentId()){
			//判断信息是否已存在
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("tName", command.gettName());
			parameter.put("parentId", command.getParentId().toString());
			parameter.put("userType", command.getUserInfor().getType());
			parameter.put("userId", command.getUserInfor().getUserId());
			if(!isDistrictUnique(parameter)){
				result = result.fillResult(ReturnCode.TEAM_HAS_EXIST);
				return result;
			}
		}
		DealerIndto dealerIndto = new DealerIndto();
		dealerIndto.setId(command.getId());
		dealerIndto.setTname(command.gettName());
		dealerIndto.setParentId(command.getParentId());
		dealerIndto.setPname(command.getpName());
		dealerIndto.settLinkMan(command.getTlinkMan());
		dealerIndto.settLinkTle(command.getTlinkTel());
		dealerIndto.settType(0);
		result = dealerService.saveOrUpdate(dealerIndto);

		final String operator = OperatorType.UPDATE.getValue() ;
		operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.DISTRICT.getValue() + command.gettName(), result);

		return  result;
	}

	@Override
	public HttpCommandResultWithData getDistrict(GetDistrictCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", new BigInteger(command.getId()));
		DistrictPojo pojo = (DistrictPojo) dao.sqlFindObject("getDistrict", map, DistrictPojo.class);
		DistrictListOutdto dto = DistrictConverter.pojoToDto(pojo);
		result.setData(dto);
		result.fillResult(ReturnCode.OK);
		return result;
	}
}