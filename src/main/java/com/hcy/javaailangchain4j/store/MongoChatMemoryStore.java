package com.hcy.javaailangchain4j.store;

import com.hcy.javaailangchain4j.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        // 创建查询条件，匹配memoryId与给定值相同的文档
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        ChatMessages message = mongoTemplate.findOne(new Query(criteria), ChatMessages.class);
        if(message!=null && StringUtils.isNotBlank(message.getContent())){
            return ChatMessageDeserializer.messagesFromJson(message.getContent());
        }
        return new LinkedList<>();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        // 检查memoryId是否为null，如果为null则直接返回，不进行任何操作
        if(memoryId==null){
            return;
        }

        // 创建查询条件，匹配memoryId与给定值相同的文档
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        // 使用上述条件构建查询对象
        Query query = new Query(criteria);
        // 创建更新对象，用于定义如何更新匹配的文档
        Update update = new Update();
        // 将聊天消息列表序列化为JSON，并设置为更新内容
        update.set("content", ChatMessageSerializer.messagesToJson(list));

        // 执行更新操作，如果文档不存在，则插入新文档
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        if(memoryId==null){
            return;
        }
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);

    }
}
