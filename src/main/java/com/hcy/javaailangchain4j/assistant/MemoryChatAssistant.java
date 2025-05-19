package com.hcy.javaailangchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author 贫道重阳
 * @date 2025/5/19 21:40
 * @description: Assistant通过注解的方式实现记忆的案例
 */
@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel",chatMemory = "chatMemory")
public interface MemoryChatAssistant {
    String chat(String message);
}
