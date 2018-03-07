package com.navinfo.opentsp.dongfeng.rpws.webservice.dao.impl;

import com.lc.rp.webservice.service.CommonParameter;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.WebServiceFactory;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.entity.WebServiceEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataAnalysisWebServiceDao implements DataAnalysisWebService {

    private final static Log log = LogFactory.getLog(DataAnalysisWebServiceDao.class);

    @Autowired
    private WebServiceFactory webServiceFactory;

    @Override
    public byte[] getGridCrossCounts(List<Long> terminalIds, List<Long> tileIds, long startDate, long endDate) {
        byte[] data = null;
        try {
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				service = WebServiceUtils.getDataAnalysisWs();
//			}
//			if (!checkWebServiceEntity()) {
//				
//				entity = WebServiceUtils.getDataAnalysisWs();
//			}
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);

            data = entity.getWebService().getGridCrossCounts(terminalIds, tileIds, startDate,
                    endDate, entity.getAccessTocken());
            log.warn("调用位置云接口getGridCrossCounts,返回结果：" + data);
        } catch (Exception e) {
            log.error("调用位置云获取轨迹数据出错，调用参数：terminalIds(" + terminalIds + "),异常：", e);
        }
        return data;
    }

    @Override
    public byte[] getVehiclePassTimesBytileId(List<Long> titlids, long startDate, long endDate) {
        byte[] data = null;
        log.warn("调用位置云接口getVehiclePassTimesBytileId，参数：" + "startDate:"
                + startDate + "endDate:" + endDate);
        try {
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				getDataAnalysisWs();
//			}

//			if (!checkWebServiceEntity()) {
//				entity = WebServiceUtils.getDataAnalysisWs();
//			}
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);

            data = entity.getWebService().getVehiclePassTimesBytileId(titlids, startDate,
                    endDate, entity.getAccessTocken());
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云瓦片ID查询车次接口返回数量为0----------data" + data);
                return null;
            }
        } catch (Exception e) {
            log.warn("调用位置云接口出错，方法：GetVehiclePassTimesBytileId");
        }
        return data;
    }

    @Override
    public byte[] getVehiclePassInArea(List<Integer> districtCodes, int type, long startDate, long endDate) {
        byte[] data = null;
        try {
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				getDataAnalysisWs();
//			}
//			
//			if (!checkWebServiceEntity()) {
//				entity = WebServiceUtils.getDataAnalysisWs();
//			}

            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);

            data = entity.getWebService().getVehiclePassInArea(districtCodes, type, startDate,
                    endDate, entity.getAccessTocken());
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                return null;
            }

        } catch (Exception e) {
            log.warn("调用位置云接口出错，方法：getVehiclePassInArea");
        }
        return data;
    }

    @Override
    public byte[] getVehiclePassTimesRecords(int districtCode, long startDate, long endDate) {
        byte[] data = null;
        try {
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				getDataAnalysisWs();
//			}
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            data = entity.getWebService().getVehiclePassTimesRecords(districtCode, startDate,
                    endDate, entity.getAccessTocken());
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云网格车次检索接口返回数量为0----------data" + data);
                return null;
            }
        } catch (Exception e) {
            log.warn("调用位置云接口出错，方法：getVehiclePassTimesRecords");
        }
        return data;
    }

    @Override
    public byte[] getLastestVehiclePassInArea(List<Integer> districtCodes) {
        byte[] data = null;
        try {
//			if (service != null) {
//			} else {
//				getDataAnalysisWs();
//			}
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            data = entity.getWebService().getLastestVehiclePassInArea(districtCodes,
                    entity.getAccessTocken());
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                return null;
            }
        } catch (Exception e) {
            log.warn("调用位置云获取轨迹数据出错，调用参数：districtCodes(" + districtCodes + ")");
        }
        return data;
    }

    @Override
    public byte[] getMileageAndOilData(List<Long> terminalIds) {
        byte[] data = null;
        try {
//			if (service != null) {
//			} else {
//				getDataAnalysisWs();
//			}

            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            data = entity.getWebService().getMileageAndOilData(terminalIds);
            if (data != null) {
                return data;
            } else {
                log.warn("---最新里程能耗数据检索数量为0----------data" + data);
                return null;
            }
        } catch (Exception e) {
            log.warn("调用最新里程能耗数据检索出错，调用参数：terminalIds(" + terminalIds + ")");
        }
        return data;
    }

    @Override
    public byte[] getMileageConsumptionRecords(List<Long> terminalIds, long startDate, long endDate,
                                               CommonParameter commonParameter) {
        byte[] data = null;
        try {

            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            if (entity.getWebService() != null) {
                commonParameter.setAccessTocken(entity.getAccessTocken());
                commonParameter.setMultipage(false);
                data = entity.getWebService().getMileageConsumptionRecords(terminalIds,
                        startDate, endDate, commonParameter);
                if (data != null) {
                    return data;
                } else {
                    log.warn("--- 里程能耗数据数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("调用 里程能耗数据出错，调用参数：terminalIds(" + terminalIds + ")");
        }
        return data;
    }

    @Override
    public byte[] getOvertimeParkRecords(List<Long> terminalID, List<Long> areaId, long startDate, long endDate,
                                         CommonParameter commonParameter) {
        byte[] data = null;
        try {
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            if (entity.getWebService() != null) {// 服务不存在，重新获取
                commonParameter.setAccessTocken(entity.getAccessTocken());
                data = entity.getWebService().getOvertimeParkRecords(terminalID, areaId,
                        startDate, endDate, commonParameter);
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云滞留超时检索接口返回数量为0----------data" + data);
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn("调用位置云获取滞留超时检索列表出错,方法：getOvertimeParkRecords");
        }
        return data;
    }

    @Override
    public byte[] delOvertimeParkRecords(Long accessTocken, String id, Long recordDate) {
        byte[] data = null;
        try {

            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            if (entity.getWebService() != null) {// 服务不存在，重新获取
                data = entity.getWebService().delOvertimeParkRecords(accessTocken, id,
                        recordDate);
                if (data != null) {
                    return data;
                } else {
                    log.warn("---调用位置云滞留超时检索接口返回数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("调用位置云获取滞留超时检索列表出错,方法：getOvertimeParkRecords");
        }
        return data;
    }

    @Override
    public byte[] getFaultCodeRecords(List<Long> terminalIds, int spn, int fmi, long beginDate, long endDate,
                                      CommonParameter commonParameter) {
        byte[] data = null;
        try {

            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				getDataAnalysisWs();
//			}
            commonParameter.setAccessTocken(entity.getAccessTocken());
            commonParameter.setSort(true);
            commonParameter.setSortTerminal(true);
            data = entity.getWebService().getFaultCodeRecords(terminalIds, spn, fmi,
                    beginDate, endDate, commonParameter);
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云轨迹接口返回数量为0----------data" + data);
                return null;
            }
        } catch (Exception e) {
            log.warn("调用位置云获取轨迹数据出错，调用参数：terminalIds(" + terminalIds + ")");
            log.error(e.getMessage(), e);
        }
        return data;
    }

    @Override
    public byte[] getStagnationTimeoutRecords(List<Long> terminalIds, Long startDate, Long endDate,
                                              CommonParameter commonParameter) {
        try {
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				getDataAnalysisWs();
//			}
            commonParameter.setAccessTocken(entity.getAccessTocken());
            commonParameter.setSort(true);
            byte[] data = entity.getWebService().getStagnationTimeoutRecords(terminalIds,
                    startDate, endDate, commonParameter);
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云轨迹接口返回数量为0");
                return null;
            }

        } catch (Exception e) {
            log.error("调用位置云区域查询数据出错，异常：", e);
            return null;
        }
    }

    @Override
    public byte[] StagnationTimeoutCancelOrNot(String id_, boolean isCancel, Long recordDate) {
        try {
//			if (service != null) {// 服务不存在，重新获取
//			} else {
//				getDataAnalysisWs();
//			}
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            byte[] data = entity.getWebService().stagnationTimeoutCancelOrNot(id_, isCancel, recordDate, entity.getAccessTocken());
            if (data != null) {
                return data;
            } else {
                log.warn("---调用位置云轨迹接口返回数量为0");
                return null;
            }

        } catch (Exception e) {
            log.error("调用位置云区域查询数据出错，异常：", e);
            return null;
        }
    }

    @Override
    public byte[] calculateMileageConsumption(Long terminalId, Long startDate, Long endDate) {
        byte[] data = null;
        try {
            WebServiceEntity<com.lc.rp.webservice.service.DataAnalysisWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.DataAnalysisWebService.class);
            if (entity.getWebService() != null) {
                Long accessToken = entity.getAccessTocken();
                data = entity.getWebService().calculateMileageConsumption(terminalId,
                        startDate, endDate, accessToken);
                if (data != null) {
                    return data;
                } else {
                    log.warn("--- 获取计算的里程能耗数据数量为0----------data" + data);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("调用 计算的里程能耗数据出错，调用参数：terminalIds(" + terminalId + ")");
        }
        return data;
    }

    @Override
    public byte[] getTest(CommonParameter commonParameter) {
        System.out.println("==============");

        return new byte[0];
    }


}
