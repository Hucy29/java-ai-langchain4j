package com.hcy.javaailangchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author 贫道重阳
 * @date 2025/6/8 15:46
 * @description
 */
@SpringBootTest
public class EmbeddingTest {
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private EmbeddingStore embeddingStore;;

    @Test
    public void test() {
        Response<Embedding> response = embeddingModel.embed("你好");
        System.out.println("向量维度====>"+response.content().vector().length);
        System.out.println("向量输出====>"+response.toString());
    }

    @Test
    public void test2() {
        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        TextSegment segment2 = TextSegment.from("今天天气真好");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);
    }

    @Test
    public void test3() {
        //提问，并将问题转换为向量数据
        Embedding query = embeddingModel.embed("你最喜欢的运动是什么？").content();

        //创建搜索请求对象，搜索最匹配的向量数据
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(EmbeddingSearchRequest.builder()
                .queryEmbedding(query)
                .maxResults(1)
                //.minScore(0.8)//搜索结果分数必须大于0.8，如果搜索结果分数小于0.8，则不返回搜索结果,0.8及以上准确度较高
                .build());

        //获取搜索结果列表，并获取第一条搜索结果
        EmbeddingMatch<TextSegment> embeddingMatch = searchResult.matches().get(0);
        System.out.println(embeddingMatch.score());
        System.out.println("最匹配的句子是：" + embeddingMatch.embedded().text());

    }

    @Test
    public void test4() {
        Document document1 = FileSystemDocumentLoader.loadDocument("Document/xiehe.txt",new TextDocumentParser());
        Document document2 = FileSystemDocumentLoader.loadDocument("Document/协和简介.pdf",new ApachePdfBoxDocumentParser());
        List<Document> documents = Arrays.asList(document1, document2);

        //文本向量化并存入向量库，将每个片段进行向量化，得到一个嵌入向量
        EmbeddingStoreIngestor.builder().embeddingStore(embeddingStore).embeddingModel(embeddingModel).build().ingest(documents);
    }
}
