package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 贫道重阳
 * @date 2025/5/14 23:03
 * @description
 */
@SpringBootTest
public class AssistantTest {
    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testChat() {
        Assistant assistant = AiServices.create(Assistant.class,qwenChatModel);
        System.out.println(assistant.chat("你是谁？"));
    }
}
