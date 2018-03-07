package com.navinfo.opentsp.dongfeng.rpws.webservice.dao.impl;

import com.lc.core.protocol.dataaccess.common.LCRegularData.RegularData;
import com.lc.core.protocol.webservice.originaldata.LCQueryRegularDataRes;
import com.lc.core.protocol.webservice.originaldata.LCQueryRegularDataRes.QueryRegularDataRes;
import com.lc.rp.webservice.service.CommonParameter;
import com.lc.rp.webservice.service.DataAnalysisWebService;
import com.lc.rp.webservice.service.Point;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.WebServiceFactory;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.entity.WebServiceEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicDataQueryWebServiceDao implements BasicDataQueryWebService {

    private final static Log log = LogFactory.getLog(BasicDataQueryWebServiceDao.class);

    @Autowired
    private WebServiceFactory webServiceFactory;

    @Override
    public byte[] getTerminalTrack(long terminalId, long beginDate, long endDate, boolean isFilterBreakdown, long breakdownCode, CommonParameter commonParameter,int isAll) throws Exception {
        return getTerminalTrack(terminalId, beginDate, endDate, isFilterBreakdown, breakdownCode, false, 0, commonParameter,isAll);
    }

    @Override
    public byte[] getTerminalTrack(long terminalId, long beginDate, long endDate, boolean isFilterBreakdown,
                                   long breakdownCode, boolean isThin, int level, CommonParameter commonParameter,int isAll) throws Exception {
        try {

            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            List<Long> idList = new ArrayList<Long>();
            idList.add(terminalId);
            if (entity.getWebService() != null) {
                commonParameter.setAccessTocken(entity.getAccessTocken());
                byte[] data = entity.getWebService().getTerminalTrack(terminalId, beginDate,
                        endDate, isFilterBreakdown, breakdownCode, isThin, level,
                        isAll,commonParameter);
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0----------termianl"
                            + terminalId);
                    return null;
                }
            } else
                return null;
        } catch (Exception e) {
            log.error("调用位置云获取轨迹数据出错，调用参数：terminalIds(" + terminalId + ");异常:"
                    , e);
            return null;
        }
    }

    @Override
    public byte[] getCANBusData(List<Long> terminalIds, int type, long beginDate, long endDate,
                                CommonParameter commonParameter) {
        byte[] data = null;

        WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
        try {
            if (entity.getWebService() != null) {
                commonParameter.setAccessTocken(entity.getAccessTocken());
                data = entity.getWebService().getCANBusData(terminalIds, type, beginDate,
                        endDate, commonParameter);
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("调用位置云获取轨迹数据出错，调用参数：terminalIds(" + terminalIds + ");异常："
                    , e);
        }
        return data;
    }

    @Override
    public byte[] getLastestLocationData(List<Long> terminalIds, int type) {
        byte[] data = null;
        try {
            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (entity.getWebService() != null) {
                CommonParameter para = new CommonParameter();
                para.setMultipage(false);
                data = entity.getWebService().getLastestLocationData(terminalIds, "", type, para);
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("调用位置云最新位置数据出错，调用参数：terminalIds(" + terminalIds + ")");
        }
        return data;
    }

    @Override
    public byte[] getLastestLocationData(List<Long> terminalIds, String milesRange, int type, CommonParameter commonParameter) {
        byte[] data = null;
        try {
            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (entity.getWebService() != null) {
                data = entity.getWebService().getLastestLocationData(terminalIds, milesRange, type, commonParameter);
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("调用位置云最新位置数据出错，调用参数：terminalIds(" + terminalIds + ")");
        }
        return data;
    }

    @Override
    public byte[] getTerminalLocationData(Long terminalid, Long queryDate, int index) {
        byte[] data = null;
        try {
            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (entity.getWebService() != null) {
                data = entity.getWebService().getTerminalLocationData(terminalid, queryDate,
                        index, entity.getAccessTocken());
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("调用位置云位置数据检索出错，调用参数：terminalid：" + terminalid);
        }
        return data;
    }

    @Override
    public byte[] getLastestLocationDataByAlarmFilter(List<Long> terminalIds, boolean isFilter, Long alarmFilter,
                                                      int isCancel, int type, CommonParameter commonParameter) {
        try {

            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (entity.getWebService() != null) {
                commonParameter.setAccessTocken(entity.getAccessTocken());
                byte[] data = entity.getWebService().getLastestLocationDataByAlarmFilter(
                        terminalIds, isFilter, alarmFilter, isCancel, type,
                        commonParameter);

                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0");
                    return null;
                }
            } else
                return null;
        } catch (Exception e) {
            log.error("调用位置云区域查询数据出错，异常：", e);
            return null;
        }
    }

    @Override
    public byte[] getTerminalRegular(List<Long> terminalIds, List<Long> areaIds, long accessTocken1) throws Exception {
        boolean type = false;
        if (terminalIds == null) {
            terminalIds = new ArrayList<Long>();
        }
        if (areaIds == null) {
            areaIds = new ArrayList<Long>();
            type = true;
        }
        try {

            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (entity.getWebService() != null) {
                byte[] bytes = entity.getWebService().getTerminalRegular(terminalIds, areaIds,
                        type, entity.getAccessTocken());
                return bytes;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn("调用位置云获取轨区域规则出错，调用参数：terminalIds(" + terminalIds + ")");
            return null;
        }
    }

    @Override
    public List<Long[]> getTerminalMessageSwitch(List<Long> terminalIds) {
        webServiceFactory.getWebServiceEntity(DataAnalysisWebService.class);

        WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> basicDataQueryWebEntity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);

        List<Long[]> maps = new ArrayList<Long[]>();

        if (basicDataQueryWebEntity.getWebService() != null) {
            try {
                byte[] dataBytes = basicDataQueryWebEntity.getWebService().getTerminalRegular(terminalIds, null,
                        true, basicDataQueryWebEntity.getAccessTocken());
                if (dataBytes == null) {
                    for (Long ts : terminalIds) {
                        Long[] temp = new Long[2];
                        temp[0] = ts;
                        temp[1] = 0l;
                        maps.add(temp);
                    }
                    return maps;
                }
                QueryRegularDataRes res = LCQueryRegularDataRes.QueryRegularDataRes.parseFrom(dataBytes);
                List<RegularData> dataLists = res.getDatasList();
                for (Long ts : terminalIds) {
                    Long[] temp = new Long[2];
                    temp[0] = ts;
                    temp[1] = 0l;
                    for (RegularData data : dataLists) {
                        Long terId = data.getTerminalId();
                        if (ts.equals(terId)) {
                            Boolean msgSwitch = data.getTerminalMessage()
                                    .getMessageSwitch();
                            temp[1] = msgSwitch.booleanValue() == true ? 1L
                                    : 0L;
                        }
                    }
                    maps.add(temp);
                }

            } catch (Exception e) {
                log.warn("调用位置云接口出错，方法：getTerminalMessageSwitch");
                // e.printStackTrace();
            }
        }
        return maps;
    }

    @Override
    public byte[] getTerminalInDistrict(List<Long> terminalIds, int districtCode) {
        try {

            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (null != entity && entity.getWebService() != null) {
                byte[] data = entity.getWebService().getTerminalInDistrict(terminalIds,
                        districtCode, entity.getAccessTocken());

                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0");
                    return null;
                }
            } else
                return null;
        } catch (Exception e) {
            log.error("调用位置云区域查询数据出错，异常：", e);
            return null;
        }
    }

    @Override
    public byte[] getTerminalInArea(List<Long> terminalIds, int type, List<Point> points, int radius) {
        try {
            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (null != entity && entity.getWebService() != null) {
                // byte[] data = bdqws.getTerminalInArea(type, points, radius,
                // accessTocken);
                byte[] data = entity.getWebService().getTerminalInArea(terminalIds, type,
                        points, radius, entity.getAccessTocken());

                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云轨迹接口返回数量为0");
                    return null;
                }
            } else
                return null;
        } catch (Exception e) {
            log.error("调用位置云区域查询数据出错，异常：", e);
            return null;
        }
    }

    /**
     * 获取首次连线时间
     *
     * @param terminalIds
     * @return
     */
    public byte[] getTerminalFirstReceiveDate(List<Long> terminalIds) {
        try {
            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (null != entity && entity.getWebService() != null) {
                byte[] data = entity.getWebService().getTerminalFirstRecieveDate(terminalIds);
                if (data != null) {
                    return data;
                } else {
                    log.warn(terminalIds + "——getTerminalFirstReceiveDate 接口返回数据为空");
                    return null;
                }
            } else
                return null;
        } catch (Exception e) {
            log.error("调用位置云获取首次连接时间异常，异常：", e);
            return null;
        }
    }

    @Override
    public byte[] getLastestTerminalStatus(List<Long> terminalIds, long l) {
        try {
            WebServiceEntity<com.lc.rp.webservice.service.BasicDataQueryWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.BasicDataQueryWebService.class);
            if (null != entity && entity.getWebService() != null) {
                byte[] data = entity.getWebService().getLastestTerminalStatus(terminalIds, l);
                if (data != null) {
                    return data;
                } else {
                    log.warn(terminalIds + "——getLastestTerminalStatus 接口返回数据为空");
                    return null;
                }
            } else
                return null;
        } catch (Exception e) {
            log.error("调用位置云获取首次连接时间异常，异常：", e);
            return null;
        }
    }

//
//	@Override
//	public byte[] getTest(CommonParameter commonParameter) {
//		System.out.println("==============");
//		
//		return new byte[0];
//	}

}
