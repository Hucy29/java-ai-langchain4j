package com.hcy.javaailangchain4j.tools;

import com.hcy.javaailangchain4j.assistant.entites.AppointmentDO;
import com.hcy.javaailangchain4j.assistant.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 贫道重阳
 * @date 2025/5/30 15:56
 * @description
 */
@Slf4j
@Component
public class AppointmentTools {
    @Autowired
    private AppointmentService appointmentService;

    @Tool(name = "预约挂号",value = "根据参数，先执行工具方法queryDepartment查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息,用户确认后再进行预约。")
    private String bookAppointment(AppointmentDO appointmentDO) {
        AppointmentDO db = appointmentService.getOne(appointmentDO);
        if(db==null){
            appointmentDO.setId(null);//放置大模型幻觉设置了id
            if(appointmentService.save(appointmentDO)){
                return "预约成功，并返回预约想起";
            }else {
                return "预约失败";
            }
        }
        return "预约已存在，请勿重复预约";
    }

    @Tool(name = "取消预约挂号",value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回true。否则返回false。")
    private String cancelDepartment(AppointmentDO appointmentDO) {
        AppointmentDO db = appointmentService.getOne(appointmentDO);
        if(db!=null){
            if(appointmentService.removeById(db)){
                return "取消成功";
            }else {
                return "取消失败";
            }
        }
        return "预约不存在";
    }

    @Tool(name = "查询是否有号源",value = "根据科室名称，日期，时间和医生查询是否有号源，并返回给用户")
    private boolean queryDepartment(@P(value = "科室名称",required = true) String name,
                                   @P(value = "日期",required = true) String date,
                                   @P(value = "时间,可选值：上午、下午",required = true) String time,
                                   @P(value = "医生名称",required = false) String doctor) {
        log.info("查询是否有号源");
        log.info("科室名称={0}，日期：{1}，时间：{2}，医生：{3}",name,date,time,doctor);

        //TODO:查询数据库
        //如果没有指定医生，则根据其他参数查询是否有可预约的医生，有返回true，否则返回false

        //如果指定了医生，则判断医生是否有排班，有返回true，否则返回false
        //如果有排班，则判断医生排班时间段是否有号源，有返回true，否则返回false

        return true;
    }
}
