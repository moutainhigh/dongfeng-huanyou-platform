package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.webservice.statisticsdata.LCVehiclePassTimesRecords;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCGrandSonAreaTimes;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCSonAreaTimes;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCVehiclePassTimes;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryThermodynamicCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.BoundingBoxPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryThermodynamicPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryThermodynamicService;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @wenya
 * @create 2017-04-12 11:51
 **/@Service
public class QueryThermodynamicServiceImpl extends BaseService implements IQueryThermodynamicService{
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Override
    public HttpCommandResultWithData queryThermodynamic(QueryThermodynamicCommand command) {
        logger.info("=====  queryThermodynamic start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //时间参数转换
        long beginTime = DateUtil.getMinMonthDate(command.getTime());
        long endTime = DateUtil.getMaxMonthDate(command.getTime());
        //瓦片id生成
        List<Long> tileids = getTileids(command.getHeatMaplatitude(),command.getHeatMaplongitude(),command.getHeatMapradius());
        //调用位置云接口获取数据
        QueryThermodynamicPojo pojo = getVehiclePassTimesBytileId(tileids, beginTime,endTime);
        result.setData(pojo);
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryThermodynamic end  =====");
        return result;
    }

    private QueryThermodynamicPojo getVehiclePassTimesBytileId(List<Long> tileids, long beginTime, long endTime) {
        List<BoundingBoxPojo> maplist = new ArrayList<BoundingBoxPojo>();
        QueryThermodynamicPojo thermodynamic= new QueryThermodynamicPojo();
        try{
          logger.info("Call cloud interface getVehiclePassTimesBytileId start");
          logger.info("parameters is startDate:{}, endDate:{}, tileids:{}", beginTime, endTime, tileids);
          byte[] data = cloudDataRmiClientConfiguration.getDataAnalysisWebService().getVehiclePassTimesBytileId(tileids,beginTime,endTime);
          logger.info("call clould interface result size is {}", CollectionUtils.size(data));
          if(data!=null){
              LCVehiclePassTimesRecords.VehiclePassTimesRecords builder = LCVehiclePassTimesRecords.VehiclePassTimesRecords.parseFrom(data);
              List<LCVehiclePassTimes.VehiclePassTimes> vehiclePassTimes = builder.getDataListList();
              for(LCVehiclePassTimes.VehiclePassTimes entry1:vehiclePassTimes){
                  long tile1 = entry1.getId();
                  int[] zxy1 = tileIdTozxy(tile1);
                  BoundingBoxPojo box1 = tile2boundingBox(zxy1[1],zxy1[2], zxy1[0]);
                  box1.setCount(entry1.getTimes());
                  for(LCSonAreaTimes.SonAreaTimes entry2:entry1.getSonAreaTimesList()){
                      long tile2 = entry2.getId();
                      int[] zxy2 = tileIdTozxy(tile2);
                      BoundingBoxPojo box2 = tile2boundingBox(zxy2[1],zxy2[2], zxy2[0]);
                      box2.setCount(entry2.getTimes());
                      for(LCGrandSonAreaTimes.GrandSonAreaTimes entry3:entry2.getGrandsonAreaTimesList()){
                          long tile3 = entry3.getId();
                          int[] zxy3 = tileIdTozxy(tile3);
                          BoundingBoxPojo box3 = tile2boundingBox(zxy3[1],zxy3[2], zxy3[0]);
                          box3.setCount(entry3.getTimes());
                          if(box3.getCount()!=0){
                              maplist.add(box3);
                          }
                      }
                  }
              }
          }
          logger.info("getVehiclePassTimesBytileId BoundingBoxs is {}", CollectionUtils.size(maplist));
          if(maplist!=null&&!maplist.isEmpty()){
            ComparatorChain chain = new ComparatorChain();
            chain.addComparator(new BeanComparator("count"),true);//true,fase正序反序
            Collections.sort(maplist,chain);
            thermodynamic.setList(maplist);
            thermodynamic.setCountMax(maplist.get(0).getCount());
        }
      }catch (Exception e) {
          logger.error("调用位置云getVehiclePassTimesBytileId接口异常",e);
      }
        return thermodynamic;
    }

    //获取瓦片id集合
    private List<Long> getTileids(String heatMaplatitude, String heatMaplongitude, String heatMapradius) {
        int n=0;
        List<Long> list = new ArrayList<>();
        BigDecimal x = new BigDecimal(heatMaplatitude);
        BigDecimal lat = x.multiply(new BigDecimal("1000000"));
        BigDecimal y = new BigDecimal(heatMaplongitude);
        BigDecimal lon = y.multiply(new BigDecimal("1000000"));
        int radius = (int)Math.ceil(Double.parseDouble(heatMapradius));
        int[] tilezxy = getTile(lat.longValue(),lon.longValue(),13);
        if(radius%4800!=0){
            n=radius/4800+1+1;
        }else{
            n=radius/4800+1;
        }
        for(int i=tilezxy[1]-(n-1);i<=tilezxy[1]+(n-1);i++){
            for(int j=tilezxy[2]-(n-1);j<=tilezxy[2]+(n-1);j++){
                long e = xyzToTileId(tilezxy[0],i,j);
                list.add(e);
            }
        }
        return list;
    }
    //根据经纬度获得瓦片zxy
    public int[] getTile(long latitude, long longitude, int zoom) {
        //int[] gps=ToolUtil.changgeXY((int)longitude,(int)latitude);
        //double lat = ((double)gps[1])/Math.pow(10, 6);
        //double lon = ((double)gps[0])/Math.pow(10, 6);
        double lat = ((double)latitude)/Math.pow(10, 6);
        double lon = ((double)longitude)/Math.pow(10, 6);
        int[] zxy = new int[3];
        zxy[0] = zoom;
        zxy[1] = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
        zxy[2] = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat))
                + 1 / Math.cos(Math.toRadians(lat)))
                / Math.PI)
                / 2 * (1 << zoom));
        if (zxy[1] < 0)
            zxy[1] = 0;
        if (zxy[1] >= (1 << zoom))
            zxy[1] = ((1 << zoom) - 1);
        if (zxy[2] < 0)
            zxy[2] = 0;
        if (zxy[2] >= (1 << zoom))
            zxy[2] = ((1 << zoom) - 1);
        return zxy;
    }
    //根据zxy获取瓦片id
    public long xyzToTileId(int z, int x, int y){
        long tileId = 0;
        tileId = (long)(z*Math.pow(10, 10))+(long)(x*Math.pow(10, 5))+y;
        return tileId;
    }
    //根据瓦片id获取zxy
    public int[] tileIdTozxy(long tileId){
        int[] zxy = new int[3];
        zxy[0] = (int)(tileId / Math.pow(10, 10));
        zxy[1] = (int)(tileId / Math.pow(10, 5))%((int)Math.pow(10, 5));
        zxy[2] = (int)(tileId % Math.pow(10, 5));
        return zxy;
    }
    // 根据瓦片id获得经纬度
    private BoundingBoxPojo tile2boundingBox(final int x, final int y,
                                             final int zoom) {
        BoundingBoxPojo bb = new BoundingBoxPojo();
        bb.setNorth(tile2lat(y, zoom));
        bb.setSouth(tile2lat(y + 1, zoom));
        bb.setWest(tile2lon(x, zoom));
        bb.setEast(tile2lon(x + 1, zoom));
        return bb;
    }
    private double tile2lon(int x, int z) {
        return x / Math.pow(2.0, z) * 360.0 - 180;
    }

    private double tile2lat(int y, int z) {
        double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
        return Math.toDegrees(Math.atan(Math.sinh(n)));
    }
}
