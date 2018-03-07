package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.SecdTeamIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TeamOutdto;
import com.navinfo.opentsp.dongfeng.system.entity.HySecdTeamEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.TeamPojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocy on 2017/03/13.
 */
public class TeamConverter {

    public static TeamOutdto pojoToDto (TeamPojo pojo) {
        TeamOutdto dto = new TeamOutdto();
        try {
            CopyPropUtil.copyProp(pojo, dto);
        } catch (Exception e) {

        }

        return dto;
    }

    public static List<TeamOutdto> listPojoToDto (List<TeamPojo> list) {
        List<TeamOutdto> listDto = new ArrayList<TeamOutdto>();
        if (list != null) {
            for (TeamPojo pojo: list) {
                TeamOutdto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }

    public static List<HySecdTeamEntity> inDtoToSecdTeamList(BigInteger pid, String pCode, List<SecdTeamIndto> list){
        if (StringUtil.isEmpty(list)) {
            return null;
        }
        List<HySecdTeamEntity> result = new ArrayList<>(list.size());
        for (SecdTeamIndto entry : list) {
            result.add(inDtoToSecdTeam(pid, pCode, entry));
        }
        return result;
    }

    private static HySecdTeamEntity inDtoToSecdTeam(BigInteger pid, String pCode, SecdTeamIndto entry) {
        HySecdTeamEntity entity = new HySecdTeamEntity();
        if (!StringUtil.isEmpty(entry.getId())) {
            entity.setId(entry.getId());
        }
        entity.setName(entry.getName());
        entity.setCode(entry.getCode());
        if (!StringUtil.isEmpty(entry.getLatitude()) && !StringUtil.isEmpty(entry.getLongitude())) {
            entity.setLongitude(entry.getLongitude());
            entity.setLatitude(entry.getLatitude());
        }
        entity.setAddress(entry.getAddress());
        entity.setWorkRadius(entry.getWorkRadius());
        entity.setDealerCode(pCode);
        entity.setDealerId(pid);
        return entity;
    }
}
