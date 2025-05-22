package com.hcy.javaailangchain4j.assistant.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 贫道重阳
 * @date 2025/5/22 23:01
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class ChatMessages {
    /**
     * 唯一标识，映射到MongoDB文档的_id字段
     */
    @Id
    private ObjectId messageId;

    /**
     * 储存当前聊天记录列表的json字段
     * 包含 userMessage 和 AiMessage
     */
    private String content;
}
