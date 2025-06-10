package com.hcy.javaailangchain4j.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 贫道重阳
 * @date 2025/6/8 16:23
 * @description
 */
@Configuration
public class EmbeddingStoreConfig {
    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
                .apiKey(System.getenv("PINECONE_KEY"))
                .index("langchain4j-index")//如果指定的索引不存在，则自动创建索引
                .nameSpace("langchain4j-namespace")//如果指定的空间不存在，则自动创建空间
                .createIndex(PineconeServerlessIndexConfig.builder()
                        .cloud("AWS")//指定索引部署在AWS云服务上
                        .region("us-east-1")//指定索引部署在哪个区域
                        .dimension(embeddingModel.dimension())//指定索引的维度，该维度与embeddingModel的维度一致
                        .build())
                .build();
        return embeddingStore;
    }

}
