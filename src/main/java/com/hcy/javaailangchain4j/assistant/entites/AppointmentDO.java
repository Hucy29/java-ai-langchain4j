package com.hcy.javaailangchain4j.assistant.entites;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 贫道重阳
 * @date 2025/5/28 22:31
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appointment")
@ToString
public class AppointmentDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String idCard;
    private String department;
    private String date;
    private String time;
    private String doctor;
}
