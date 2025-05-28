package com.hcy.javaailangchain4j.assistant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hcy.javaailangchain4j.assistant.entites.AppointmentDO;

/**
 * @author 贫道重阳
 * @date 2025/5/28 22:41
 * @description
 */
public interface AppointmentService extends IService<AppointmentDO>{
    AppointmentDO getOne(AppointmentDO appointmentDO);
}
