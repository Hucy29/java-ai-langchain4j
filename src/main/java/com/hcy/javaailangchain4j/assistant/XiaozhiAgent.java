package com.hcy.javaailangchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author 贫道重阳
 * @date 2025/5/27 17:42
 * @description
 */
@AiService(wiringMode = EXPLICIT,
        //chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",//流式输出
        chatMemoryProvider = "chatMemoryProviderXiaozhi",
        contentRetriever = "contentRetrieverXiaozhiPincone",
        tools = {"appointmentTools"})
public interface XiaozhiAgent {
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String message);
}
