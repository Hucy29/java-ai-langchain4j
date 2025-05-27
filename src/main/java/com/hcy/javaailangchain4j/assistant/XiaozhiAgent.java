package com.hcy.javaailangchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author 贫道重阳
 * @date 2025/5/27 17:42
 * @description
 */
@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel",chatMemoryProvider = "chatMemoryProviderXiaozhi")
public interface XiaozhiAgent {
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String message);
}
