package com.hcy.javaailangchain4j.assistant.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 贫道重阳
 * @date 2025/5/19 22:04
 * @description 隔离会话的相关配置
 */
@Configuration
public class SeparateChatAssistantConfig {
    /**
     * bean的名字一定要和SeparateChatAssistant类中的@AiService.chatMemoryProvider的值保持一致，这样才能在@AiService读取到bean的配置
     * @return
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        //此处maxMessages就是最大记忆轮次，因此在eparateChatAssistant类中的@AiService将不需要再配置chatMemory属性
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                //聊天记忆的存储默认使用的是 dev.langchain4j.memory.chat.SingleSlotChatMemoryStore
                //SingleSlotChatMemoryStore和InMemoryChatMemoryStore的实现方式不一样
                //SingleSlotChatMemoryStore：memoryId+messages
                //而InMemoryChatMemoryStore是用的ConcurrentHashMap，memoryId为key,messages为value
                //InMemoryChatMemoryStore更适合多线程
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .build();
    }
}
