package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.Assistant;
import com.hcy.javaailangchain4j.assistant.MemoryChatAssistant;
import com.hcy.javaailangchain4j.assistant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author 贫道重阳
 * @date 2025/5/19 21:17
 * @description :聊天记忆的简单实现
 */
@SpringBootTest
public class ChatMemoryTest {
    @Autowired
    private Assistant assistant;
    @Autowired
    private QwenChatModel qwenChatModel;

    /**
     * 此种情况就是和语音模型进行2次的交流，大语音模型不会产生记忆。
     */
    @Test
    public void testChatMemory() {
        System.out.println(assistant.chat("我是hcy"));

        System.out.println(assistant.chat("我是谁？"));
    }


    /**
     * 记忆的简答实现：
     * 将前n-1轮的 userMessage 和 aiMessage，随着第n轮的 userMessage 一起传给大语音模型
     */
    @Test
    public void testChatMemory2() {
        //第一轮
        UserMessage userMessage = UserMessage.userMessage("我是hcy");
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        //输出
        System.out.println(aiMessage.text());


        //第二轮
        UserMessage userMessage1 = UserMessage.userMessage("我是谁？");
        //将第一轮的 userMessage 和 aiMessage,以及第二轮的 userMessage 一起传给大模型
        ChatResponse chatResponse1 = qwenChatModel.chat(Arrays.asList(userMessage,aiMessage,userMessage1));
        AiMessage aiMessage1 = chatResponse1.aiMessage();

        //输出
        System.out.println(aiMessage1.text());
    }


    /**
     * 通过MessageWindowChatMemory实现大语音模型的记忆功能
     */
    @Test
    public void testChatMemory3() {
        //设置最大记忆会话次数
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        Assistant assistant = AiServices.builder(Assistant.class)
                                        .chatLanguageModel(qwenChatModel)
                                        .chatMemory(chatMemory)
                                        .build();

        System.out.println(assistant.chat("我是hcy"));
        System.out.println(assistant.chat("我是谁？"));
    }




    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    /**
     * 通过注解，以及配置bean来实现大语音模型的记忆
     *
     * 到此，可以简答的看做智能体了
     */
    @Test
    public void testChatMemory4() {
        System.out.println(memoryChatAssistant.chat("我是hcy"));
        System.out.println(memoryChatAssistant.chat("我是谁？"));
    }


    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory5() {
        System.out.println(separateChatAssistant.chat(1,"我是hcy"));
        System.out.println(separateChatAssistant.chat(1,"我是谁？"));
        System.out.println(separateChatAssistant.chat(2,"我是谁？"));
    }
}
