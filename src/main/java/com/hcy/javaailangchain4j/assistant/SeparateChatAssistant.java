package com.hcy.javaailangchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author 贫道重阳
 * @date 2025/5/19 21:57
 * @description 创建记忆隔离对话智能体，以满足多用户的场景
 */
@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        //chatMemory = "chatMemory",此处不再配置会话记忆轮次，因为在创建chatMemoryProvider的bean时，已经配置。
        chatMemoryProvider = "chatMemoryProvider"
)
public interface SeparateChatAssistant {
    /**
     * 会话隔离的chat方法
     * @param memoryId 会话ID，可以是用户的token,也可以是uuid。保证唯一性即可。@MemoryId注解是必须得。
     * @param userMessage 会话内容
     * @return
     */
    //@SystemMessage：提示词，作用：设定角色，塑造AI助手的专业身份，明确助手的能力范围
    //@SystemMessage("你是我的好朋友，请用河南话回答问题。")
    @SystemMessage("你是我的好朋友，请用东北话回答问题。")
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
