package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.bean.ChatMessages;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author 贫道重阳
 * @date 2025/5/22 23:06
 * @description
 */
@SpringBootTest
@Slf4j
public class MongoCrudTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     */
//    @Test
//    public void insert() {
//        mongoTemplate.insert(new ChatMessages(2l, "聊天记录2"));
//    }

    @Test
    public void insert2() {
        ChatMessages message = new ChatMessages();
        message.setContent("聊天记录列表");
        mongoTemplate.insert(message);
    }

    @Test
    public void getById() {
        //id为objectId的字符串
        ChatMessages message = mongoTemplate.findById("682f437cee323564b0b7288e", ChatMessages.class);
        log.info(message.toString());
    }

    /**
     * 如果数据库中没有_id=682f437cee323564b0b7288e的数据，则会新增_id=682f437cee323564b0b7288e的数据
     * 所以，这个方法为新增或更新方法
     */
    @Test
    public void update() {
        Criteria criteria = Criteria.where("_id").is("682f437cee323564b0b7288e");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content","新聊天记录列表");
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    /**
     * ObjectId有长度校验，所以，如果idStr长度不符合要求，则会报错
     */
    @Test
    public void delete() {
        String idStr = "aaaaaaaaaaaaaaaaaaaaaaaa";
        Criteria criteria = Criteria.where("_id").is(idStr);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "新聊天记录列表---100");
        mongoTemplate.upsert(query, update, ChatMessages.class);

        // 使用安全方式构造 ObjectId 并检查有效性
        if (!ObjectId.isValid(idStr)) {
            log.warn("无效的 ObjectId 格式: {}", idStr);
            return;
        }

        ObjectId objectId = new ObjectId(idStr);

        ChatMessages message = mongoTemplate.findById(objectId, ChatMessages.class);
        if (message != null) {
            log.info("更新后数据: {}", message.toString());
        } else {
            log.info("更新后未找到对应数据");
        }

        mongoTemplate.remove(query, ChatMessages.class);

        ChatMessages message1 = mongoTemplate.findById(objectId, ChatMessages.class);
        if (message1 != null) {
            log.info("删除后仍存在数据: {}", message1.toString());
        } else {
            log.info("删除后数据已成功移除");
        }
    }
}
