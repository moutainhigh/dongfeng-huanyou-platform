package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.CancelStatueCommand;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyCarEntity;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.VoiceMonitoringPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.ICancelStatueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 取消防控中间状态
 *
 * @wenya
 * @create 2017-04-12 11:05
 **/
@Service
public class CancelStatueServiceImpl extends BaseService implements ICancelStatueService{
    @Override
    @Transactional
    public HttpCommandResultWithData cancelStatue(CancelStatueCommand command) {
        logger.info("=====  cancelStatue start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //密码错误，返回
          if(!command.getPassword().equals(command.getUserInfor().getPassword())){
              result.setMessage("密码错误");
              result.fillResult(ReturnCode.ERROR_PASSWORD);
              return result;
          }
        Map<String, Object> conMap = new HashMap<String, Object>();
        conMap.put("carId", command.getCarId());
        //sql定义在car-QueryCommonCar-dynamic.xml中
        VoiceMonitoringPojo pojo = (VoiceMonitoringPojo) dao.sqlFindObject("queryCommIdByCarId", conMap, VoiceMonitoringPojo.class);
        int lockStauts = updateCarInfo(command);
        //redis 清除
        if(pojo.getBdCommId()!=null && pojo.getBdCommId().longValue()!=0){
            clearRedis(String.valueOf(pojo.getBdCommId()));
        }
        if(pojo.getFkCommId()!=null && pojo.getFkCommId().longValue()!=0){
            clearRedis(String.valueOf(pojo.getFkCommId()));
        }
        result.setMessage("撤销成功");
        result.fillResult(ReturnCode.OK);
        result.setData(lockStauts);
        logger.info("===== cancelStatue end  =====");
        return result;
    }

    private void clearRedis(String sim) {
        Set<String> key =  redisService.getKeys("MSGPUSH_*_"+sim+"_"+2170);
        if(key !=null){
            redisService.delKeys("MSGPUSH_*_"+sim+"_"+2170);
        }

//        Iterator<String> it = key.iterator();
//        while (it.hasNext()){
//            redisService.del(it.next());
//        }
        Set<String> keys =  redisService.getKeys("MSGPUSH_*_"+sim+"_"+2271);
        if(keys !=null){
            redisService.delKeys("MSGPUSH_*_"+sim+"_"+2271);
        }
//        Iterator<String> its = keys.iterator();
//        while (its.hasNext()){
//            redisService.del(its.next());
//        }
    }

    //修改车辆状态
    private int updateCarInfo(CancelStatueCommand command) {
        HyCarEntity carEntity = new HyCarEntity();
        carEntity.setCarId(BigInteger.valueOf(command.getCarId()));
        HyCarEntity car = (HyCarEntity)dao.findOne(carEntity);
        car.setOperateUser(command.getUserInfor().getUsername());
        car.setOperateDate(new BigInteger(new Date().getTime()/1000+""));
        car.setOperateCommon(command.getContent());
        car.setOperateIp(command.getAccountIp());
        if(car.getLockStauts()==4){
            car.setLockStauts(0);
            car.setCarFkdate(null);//取消防控激活，需要将防控时限清空
        }else if(car.getLockStauts()==5){
            car.setLockStauts(2);
        }else if(car.getLockStauts()==6){
            car.setLockStauts(2);
        }else if(car.getLockStauts()==7){
            car.setLockStauts(3);
        }
        dao.update(car);
        return car.getLockStauts();
    }

}
