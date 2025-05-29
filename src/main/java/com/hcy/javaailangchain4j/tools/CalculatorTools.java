package com.hcy.javaailangchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 贫道重阳
 * @date 2025/5/27 22:16
 * @description 大模型工具类，定义该种工具类时，不论是类名，还是方法名，都要非常清晰的能表示出类和方法的功能。以便大模型识别并调用。
 */
@Slf4j
@Component
public class CalculatorTools {
    //@Tool注解的方法
    //既可以是静态的，也可以是实例方法
    //可以是具有任何可见性（public，private，protected,default）。

    //@Tool注解有2个参数，name和value
    //name表示工具名称，value表示工具描述
    //如果name和value都为空，则name默认为方法名，value默认为方法描述
    //如果name和value都不为空，则name优先
    //如果name和value都为空，则name默认为方法名，value默认为方法描述
    //如果name能很好的表示出方法功能，则value可以省略，以防止大模型出现“幻觉”，无法理解方法的功能。

    //@P注解,表示参数
    //@P注解有2个参数，value和required
    //value表示参数名称，required表示参数是否必填

    //@ToolMemoryId注解
    //如果有多个用户，或者每个用户有多个聊天记忆，则需要使用@ToolMemoryId注解，指定会话id，以便tool方法区分
    @Tool(name = "加法运算",value = "将两个参数a和b相加并返回运算结果")
    double sum(
            @ToolMemoryId int memoryId,
            @P(value = "加数1",required = true) double a,
            @P(value = "加数2",required = true) double b) {
        log.info("memoryId===>"+memoryId);
        log.info("调用加法运算");
        return a + b;
    }
    @Tool("平方根运算")
    double squareRoot(@ToolMemoryId int memoryId,double a) {
        log.info("调用平方根运算");
        return Math.sqrt(a);
    }
}
