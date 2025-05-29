package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.entites.AppointmentDO;
import com.hcy.javaailangchain4j.assistant.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 贫道重阳
 * @date 2025/5/29 10:46
 * @description
 */
@Slf4j
@SpringBootTest
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testSave() {
        AppointmentDO appointmentDO = new AppointmentDO();
        appointmentDO.setUsername("Alice");
        appointmentDO.setIdCard("12345678901");
        appointmentDO.setDepartment("内科");
        appointmentDO.setDate("2025-05-29");
        appointmentDO.setTime("上午");
        appointmentDO.setDoctor("Dr. Johnson");

        appointmentService.save(appointmentDO);
    }

    @Test
    public void testGetOne() {
        AppointmentDO appointmentDO = new AppointmentDO();
        appointmentDO.setUsername("Alice");
        appointmentDO.setIdCard("12345678901");
        appointmentDO.setDepartment("内科");
        appointmentDO.setDate("2025-05-29");
        appointmentDO.setTime("上午");

        AppointmentDO one = appointmentService.getOne(appointmentDO);

        log.info("one: {}", one);
    }
}
