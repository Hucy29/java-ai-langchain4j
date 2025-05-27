package com.hcy.javaailangchain4j.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author 贫道重阳
 * @date 2025/5/19 21:40
 * @description: Assistant通过注解的方式实现记忆的案例
 */
@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel",chatMemory = "chatMemory")
public interface MemoryChatAssistant {

    //用户提示词模板，it为message.
    //该注解只支持单入参
    //每次向大模型发送的消息，都会被这个注解所拦截，并将message替换到{{it}}。
    //最终发给大模型的消息是：你是我的好朋友，请用陕西话回答问题，并添加一些表情符号。+ message
    //如果使用V注解，则可以指定替换信息
    @UserMessage("你是我的好朋友，请用陕西话回答问题，并添加一些表情符号。{{it}}")
    String chat(String message);

    //与chat方法等效
    @UserMessage("你是我的好朋友，请用陕西话回答问题，并添加一些表情符号。{{msg}}")
    String chat1(@V("msg") String message);
}
