package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.PermissionTreeOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.RoleDetailOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.RoleOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.role.*;
import com.navinfo.opentsp.dongfeng.system.converter.PermissionConverter;
import com.navinfo.opentsp.dongfeng.system.converter.RoleConverter;
import com.navinfo.opentsp.dongfeng.system.entity.RoleEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.*;
import com.navinfo.opentsp.dongfeng.system.repository.RoleRepository;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaocy on 2017/03/13. 角色相关功能Service
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class RoleServiceImpl extends BaseService implements IRoleService {
	/**
	 * Brand Service LOG
	 */
	protected static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;
	
    @Resource
    private IOperateLogService operateLogService;
	/**
	 * 获取角色列表
	 */
	/**
	 * 获取角色列表
	 */
	@Override
	public HttpCommandResultWithData queryRoleList(RoleListCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("accountId", command.getUserInfor().getUserId());
		if (!StringUtil.isEmpty(command.getName())) {
			parameters.put("name", command.getName());
		}
		if (!StringUtil.isEmpty(command.getType())) {
			parameters.put("type", command.getType());
		}
		setUserAccountIds(command, parameters);
       
		PagingInfo page = new PagingInfo();
		PagingInfo<RoleListPojo> pageInfo = dao.sqlPagelFind("queryRoleList", parameters,
				NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()),
				RoleListPojo.class);
		page.setList(RoleConverter.listPojoToDto(pageInfo.getList()));
		page.setPage_total(pageInfo.getPage_total());
		page.setTotal(pageInfo.getTotal());
		result.setData(page);
		return result;
	}
	
	/**
	 * 获取角色
	 */
	@Override
	public HttpCommandResultWithData getRole(GetRoleCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		RoleEntity entity = roleRepository.findByrId(StringUtil.toBigInteger(command.getId()));
		List<PermissionTreePojo> permission = null;
		if (entity != null && entity.getrCode() != null) {
			Map<String, Integer> param = new HashMap<String, Integer>();
			param.put("rCode", entity.getrCode());
			permission = dao.sqlFind("getRoleAction", param, PermissionTreePojo.class);
		}
		if (!StringUtil.isEmpty(permission)) {
			RoleDetailOutdto dto = RoleConverter.detailToDto(entity, permission);
			result.setData(dto);
		}
		return result;
	}
	
	/**
	 * 新增角色信息
	 */
	@Transactional
	@Override
	public CommonResult addRole(AddRoleCommand command) {
		CommonResult result = new CommonResult();
		// 按照角色名查询,库中是否已经有该角色名,有(重新填写).
		List<RoleListPojo> list = dao.sqlFind("queryRoleListByName", command, RoleListPojo.class);
		if(list != null && !list.isEmpty()){
			result.fillResult(ReturnCode.ROLE_ADD_FAILED);
			return result;
		}
		RoleEntity entity = new RoleEntity();
		entity.setCreateTime(DateUtil.getNowDate(DateUtil.time_pattern));
		entity.setrName(command.getName());
		entity.setRoleType(StringUtil.toBigInteger(command.getRoleType()));
		RoleEntity role = (RoleEntity) dao.save(entity);
		role.setrCode(role.getrId().intValue());
		dao.update(role);
		if (StringUtil.isNotEmpty(command.getPermission())) {
			String[] permission = StringUtil.split(command.getPermission(), ",");
			for (int i = 0; i < permission.length; i++) {
				saveRoleActionMapping(role.getrCode(), Integer.parseInt(permission[i]));
			}
		}
		entity.setCreateAccount(StringUtil.toBigInteger(command.getUserInfor().getUserId()));
		
		operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.ROLE.getValue() + command.getName(), result);

		return result;
	}
	/**
	 * 修改角色信息
	 */
	@Transactional
	@Override
	public CommonResult editRole(EditRoleCommand command) {
		CommonResult result = new CommonResult();
		RoleEntity entity = roleRepository.findByrId(StringUtil.toBigInteger(command.getId()));
		Integer rCode = entity.getrCode();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rCode", rCode);
		List<BigInteger> bInts = dao.sqlFindField("queryAccountIDByRCode", map);
		if(CollectionUtils.isNotEmpty(bInts)) {
			if (StringUtil.isNotEmpty(command.getRoleType())) {
				 BigInteger bigInt = StringUtil.toBigInteger(command.getRoleType());
				 BigInteger bigIntRole = entity.getRoleType();
				 if(!bigInt.equals(bigIntRole)) {
					 result = result.fillResult(ReturnCode.ROLE_MODIFY);
		             return result;
				 }
			}
		}
		if (StringUtil.isNotEmpty(command.getName())) {
			entity.setrName(command.getName());
		}
		if (StringUtil.isNotEmpty(command.getRoleType())) {
			entity.setRoleType(StringUtil.toBigInteger(command.getRoleType()));
		}
		if (StringUtil.isNotEmpty(command.getPermission())) {
			delRoleMapping(entity.getrCode());
			String[] permission = StringUtil.split(command.getPermission(), ",");
			for (int i = 0; i < permission.length; i++) {
				saveRoleActionMapping(entity.getrCode(), Integer.parseInt(permission[i]));
			}
		}
		entity.setUpdateTime(DateUtil.getNowDate(DateUtil.time_pattern));
		dao.update(entity);
		//操作日志
		operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.ROLE.getValue() + command.getName(), result);

		return result;
	}
	
	/**
	 * 删除角色
	 */
	@Transactional
	@Override
	public CommonResult delRole(DelRoleCommand command) {
		CommonResult result = new CommonResult();
		RoleEntity entity = roleRepository.findByrId(StringUtil.toBigInteger(command.getId()));
		Integer rCode = entity.getrCode();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rCode", rCode);
		List<BigInteger> bInts = dao.sqlFindField("queryAccountInfoByRCode", map);
		if(CollectionUtils.isNotEmpty(bInts)) {
			if(bInts.size() == 1) {
				 result = result.fillResult(ReturnCode.ROLE_DELETE);
	             return result;
			}
		}
		delRoleMapping(entity.getrCode());
		dao.delete(entity);
		//操作日志
		operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.ROLE.getValue() + entity.getrName(), result);
		return result;
	}


	/**
	 * 通过用户类型获取用户角色
	 */
	@Override
	public HttpCommandResultWithData queryRoleByType(RoleByTypeCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("roleType", command.getType());
		parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("accountId", command.getUserInfor().getUserId());
        setUserAccountIds(command, parameters);
        
        PagingInfo page = new PagingInfo();
		PagingInfo<RoleOutdto> pageInfo = dao.sqlPagelFind("queryRoleByAccountId", parameters,
				NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()),
				RoleOutdto.class);
		page.setList(pageInfo.getList());
		page.setPage_total(pageInfo.getPage_total());
		page.setTotal(pageInfo.getTotal());
		result.setData(page);
		return result;
	}
	/**
	 * 获取角色权限
	 */
	@Override
	public HttpCommandResultWithData queryRolePermission(RolePermissionCommand command) {
		HttpCommandResultWithData result = new HttpCommandResultWithData();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("roleType", command.getRoleType());
		parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("accountId", command.getUserInfor().getUserId());
        if(command.getUserInfor().getUserId() != 1){
        	 PermissionIdsPojo idsPojo = (PermissionIdsPojo) dao.sqlFindObject("queryRolePermissionIds", parameters, PermissionIdsPojo.class);
             parameters.put("ids", idsPojo.getIds());
        }
		List<PermissionTreePojo> list = dao.sqlFind("queryRolePermission", parameters, PermissionTreePojo.class);
		List<PermissionTreeOutdto> dtoList = PermissionConverter.listPojoToDto(list);
		result.setData(dtoList);
		return result;
	}
	
	/**
	 * 保存
	 * @param rCode
	 * @param aCode
	 */
	private void saveRoleActionMapping(int rCode, int aCode) {
		RoleActionMappingPojo mapping = new RoleActionMappingPojo();
		mapping.setrCode(rCode);
		mapping.setaCode(aCode);
		dao.executeUpdate("insertRoleActionMapping", mapping);
	}
	
	/**
	 * 删除角色映射
	 * @param rCode
	 */
	private void delRoleMapping(int rCode) {
		RoleActionMappingPojo mapping = new RoleActionMappingPojo();
		mapping.setrCode(rCode);
		dao.executeUpdate("delRoleActionMapping", mapping);
	}
	
	/**
	 * 设置用户的二级以下用户ID参数
	 * @param command
	 * @param parameters
	 */
	private void setUserAccountIds(BaseCommand command, Map<String, Object> parameters) {
		List<UserListPojo> list = new ArrayList<UserListPojo>();
		int type = command.getUserInfor().getType();
		if (type != 1) {
			Map<String, Object> userParameter = new HashMap<>();
			userParameter.put("userType", type);
			userParameter.put("accountId", command.getUserInfor().getUserId());
			UserIDPojo subUserIDs = (UserIDPojo)dao.sqlFindObject("querySubUserIDs", parameters ,UserIDPojo.class);
	        logger.info("QuerySubUserIDs end. result is {}", subUserIDs.getChilds());
	        userParameter.put("subUserIDs", subUserIDs.getChilds());
			list = dao.sqlFind("queryUserList", userParameter, UserListPojo.class);
		}
		StringBuilder builder = new StringBuilder();
		if (null != list && !list.isEmpty()) {
			for (UserListPojo user : list) {
				builder.append(user.getAccountId() + ",");
			}
			builder.delete(builder.length() - 1, builder.length());
		}
		if (!StringUtil.isEmpty(builder.toString())) {
			parameters.put("accounts", builder.toString());
		}
	}
}
