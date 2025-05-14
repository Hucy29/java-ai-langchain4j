package com.hcy.javaailangchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author 贫道重阳
 * @date 2025/5/14 23:02
 * @description
 */
//如果配置了多个大语音模型，则需要AiService注解指明要当前服务要用的大语音模型
//如果只有一个大语音模型，则无需配置(wiringMode = EXPLICIT, chatModel = "qwenChatModel")，只需要@AiService就行
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel")
public interface Assistant {
    String chat(String message);
}
