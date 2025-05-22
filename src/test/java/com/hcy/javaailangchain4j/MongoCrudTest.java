package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author 贫道重阳
 * @date 2025/5/22 23:06
 * @description
 */
@SpringBootTest
public class MongoCrudTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     */
    @Test
    public void insert() {
        mongoTemplate.insert(new ChatMessages(2l, "聊天记录2"));
    }
}
