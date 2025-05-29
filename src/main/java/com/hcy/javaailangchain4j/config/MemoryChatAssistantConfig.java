package com.hcy.javaailangchain4j.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 贫道重阳
 * @date 2025/5/19 21:45
 * @description: MemoryChatAssistant关于@AiService.chatMemory的配置
 */
@Configuration
public class MemoryChatAssistantConfig {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }
}
