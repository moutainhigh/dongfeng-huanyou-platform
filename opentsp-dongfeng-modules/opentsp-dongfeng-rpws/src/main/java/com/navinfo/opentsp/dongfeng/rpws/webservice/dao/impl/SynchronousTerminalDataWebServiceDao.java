package com.navinfo.opentsp.dongfeng.rpws.webservice.dao.impl;

import com.lc.rp.webservice.service.CommonParameter;
import com.navinfo.opentsp.dongfeng.common.rmi.SynchronousTerminalDataWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.HyPoint;
import com.navinfo.opentsp.dongfeng.rpws.common.utils.douglas.Douglas;
import com.navinfo.opentsp.dongfeng.rpws.common.utils.douglas.Point;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.WebServiceFactory;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.entity.WebServiceEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//import com.navinfo.huanyou.rpwsclient.webservice.module.HyPoint;

@Component
public class SynchronousTerminalDataWebServiceDao implements SynchronousTerminalDataWebService {
    @Value("${areaCode}")
    private int areaCode;

    private final static Log log = LogFactory.getLog(SynchronousTerminalDataWebServiceDao.class);

    @Autowired
    private WebServiceFactory webServiceFactory;

    @Override
    public int terminalAddOrUpdate(Long terminalId, int tTypeId, String tCode, Boolean isAdd, int businessId) {
        int result = -1;
        try {

            WebServiceEntity<com.lc.rp.webservice.service.impl.center.SynchronousTerminalDataWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.impl.center.SynchronousTerminalDataWebService.class);
//			String dynamicPassword = CloudSynchronizeAuth();
            if (entity.getWebService() != null) {
                result = entity.getWebService().terminalAddOrUpdate(terminalId, tTypeId,
                        areaCode, true, tCode, businessId, isAdd,
                        entity.getAcckey());
            } else {
                log.warn("调用位置云服务services:" + entity.getWebService());
            }
        } catch (Exception e) {
            log.error("调用位置云出错,isAdd:" + isAdd + ",终端类型：" + businessId , e);
        }
        return result;
    }

    @Override
    public int terminalDelete(Long terminalId) {
        int result = -1;
        try {
//			String dynamicPassword = CloudSynchronizeAuth();

            WebServiceEntity<com.lc.rp.webservice.service.impl.center.SynchronousTerminalDataWebService> entity = webServiceFactory.getWebServiceEntity(com.lc.rp.webservice.service.impl.center.SynchronousTerminalDataWebService.class);
            List<Long> terminalIdList = new ArrayList<Long>();
            terminalIdList.add(terminalId);
            if (entity.getWebService() != null) {
                result = entity.getWebService().terminalDelete(terminalIdList,
                        entity.getAcckey());
            } else {
                log.warn("调用位置云服务services:" + entity.getAcckey());
            }
        } catch (Exception e) {
            log.error("调用位置云出错,terminalId:" + terminalId , e);
        }
        return result;
    }

    @Override
    public List<HyPoint> getDouglasPoint(List<HyPoint> points) {
        List<HyPoint> resultPoints = new ArrayList<HyPoint>();
        String pointString = "";
        for (HyPoint p : points) {
            pointString += p.getLatitude() + " " + p.getLongitude() + ",";
        }
        pointString = pointString.substring(0, pointString.length() - 1);// 去掉最后的逗号
        Douglas d = new Douglas();
        d.readPoint(pointString);
        d.compress(d.points.get(0), d.points.get(d.points.size() - 1), 100);// 精度后续从配置文件读取,小数点后4位
        for (int i = 0; i < d.points.size(); i++) {
            Point p = d.points.get(i);
            if (p.getIndex() > -1) {
                // System.out.print(p.getX() + "——" + p.getY() + ",");
                HyPoint hPoint = new HyPoint();
                hPoint.setLatitude((int) p.getX());
                hPoint.setLongitude((int) p.getY());
                resultPoints.add(hPoint);
            }
        }
        return resultPoints;
    }

    @Override
    public byte[] getTest(CommonParameter commonParameter) {
        System.out.println("==============");

        return new byte[0];
    }

}
