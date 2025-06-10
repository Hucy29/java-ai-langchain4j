package com.hcy.javaailangchain4j.config;

import com.hcy.javaailangchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 贫道重阳
 * @date 2025/5/27 18:12
 * @description
 */
@Configuration
public class XiaozhiAgentConfig {
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;
    @Autowired
    private EmbeddingStore embeddingStore;
    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    public ChatMemoryProvider chatMemoryProviderXiaozhi(){
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(30)
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }


    @Bean
    ContentRetriever contentRetrieverXiaozhiPincone(){
        return EmbeddingStoreContentRetriever.builder()
                //设置用于生成嵌入向量的嵌入模型
                .embeddingStore(embeddingStore)
                //设置要使用的嵌入存储
                .embeddingModel(embeddingModel)
                //设置最大检索结果数
                .maxResults(1)
                //设置最小得分阈值，只有得分大于等于0.8才能返回
                .minScore(0.8)
                .build();
    }
}
