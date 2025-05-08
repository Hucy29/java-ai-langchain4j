package com.hcy.javaailangchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaAiLangchain4jApplicationTests {
    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGPTDemo() {
        //api-key=demo  base-url默认为http://langchain4j.dev/demo/openai/v1 ,但是beat3版本不行
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        String answer = model.chat("who are you?");
        System.out.println(answer);
    }

    @Test
    public void testOpenAIDemo() {
        System.out.println(openAiChatModel.chat("你是谁?"));
    }
}
