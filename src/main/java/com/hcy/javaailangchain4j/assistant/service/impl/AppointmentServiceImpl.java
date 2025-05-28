package com.hcy.javaailangchain4j.assistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.javaailangchain4j.assistant.entites.AppointmentDO;
import com.hcy.javaailangchain4j.assistant.mapper.AppointmentMapper;
import com.hcy.javaailangchain4j.assistant.service.AppointmentService;

/**
 * @author 贫道重阳
 * @date 2025/5/28 22:42
 * @description
 */
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, AppointmentDO> implements AppointmentService {
    @Override
    public AppointmentDO getOne(AppointmentDO request) {
        LambdaQueryWrapper<AppointmentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentDO::getUsername, request.getUsername());
        queryWrapper.eq(AppointmentDO::getIdCard, request.getIdCard());
        queryWrapper.eq(AppointmentDO::getDepartment, request.getDepartment());
        queryWrapper.eq(AppointmentDO::getDate, request.getDate());
        queryWrapper.eq(AppointmentDO::getTime, request.getTime());

        AppointmentDO appointment = baseMapper.selectOne(queryWrapper);
        return appointment;
    }
}
