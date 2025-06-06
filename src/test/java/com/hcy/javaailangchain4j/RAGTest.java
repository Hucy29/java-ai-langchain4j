package com.hcy.javaailangchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

/**
 * @author 贫道重阳
 * @date 2025/6/2 23:07
 * @description
 */
@Slf4j
@SpringBootTest
public class RAGTest {
    //文档加载器
    //文档加载器（Document Loader）的主要作用是从各种数据源中加载文档。它支持从简单的文本文件、网页内容、视频字幕，甚至是CSV文件等多种数据源中读取数据，
    // 并将其转化为Document对象。文档加载器是处理文档的第一步，确保数据可以被Langchain4j正确识别和处理‌

    //文档解析器
    //文档解析器（Document Parser）的作用是对加载的文档进行进一步的处理，提取出有用的信息。它不仅仅处理文本内容，还可能涉及到对文档结构的分析、元数据的提取等。
    // 通过解析，文档解析器能够将复杂的文档内容转化为更易于处理和管理的格式，为后续的操作提供便利‌

    //文档分割器
    //文档分割器（Document Splitter）则负责将大文档分割成更小的部分，通常是按段落或句子进行分割。这样做的好处包括提高处理效率、便于管理和检索等。
    // 分割后的文本片段可以作为基本单元进行嵌入和召回操作，进一步提升系统的语义匹配和相似度搜索能力‌
    @Test
    public void  test() {
        //使用FileSystemDocumentLoader加载文件，获取知识库
        //并使用默认的文本解析器TextDocumentParser对文档进行解析文件
//        Document document = FileSystemDocumentLoader.loadDocument("Document/xiehe.txt",new TextDocumentParser());
//        System.out.println(document.text());

        //从一个目录加载所有文件
//        List<Document> documents = FileSystemDocumentLoader.loadDocuments("Document",new TextDocumentParser());

        //从一个目录加载所有匹配的txt文件
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documents1 = FileSystemDocumentLoader.loadDocuments("Document", pathMatcher,new TextDocumentParser());

        //从一个目录及其子目录中加载所有文件
//        List<Document> documents2 = FileSystemDocumentLoader.loadDocumentsRecursively("Document",new TextDocumentParser());

        for (Document document : documents1){
            log.info("=================================================");
            log.info(document.metadata().toString());
            log.info(document.text());
        }
    }


    @Test
    public void test2() {
        //PDF解析器
        Document document = FileSystemDocumentLoader.loadDocument("Document/协和简介.pdf",new ApachePdfBoxDocumentParser());
        System.out.println(document.metadata());
        System.out.println(document.text());
    }

    /**
     * 加载文档并存入向量数据库
     */
    @Test
    public void test3() {
        //LangChain4j有一个“文档分割器”（DocumentSplitter）接口，并且提供了几种开箱即用的实现方式：
        //1、按段落文档分割器：DocumentByParagraphSplitter
        //2、按行文文档分割器：DocumentByLineSplitter
        //3、按句子文档分割器：DocumentBySentenceSplitter
        //4、按单词文档分割器：DocumentByWordSplitter
        //5、按字符文档分割器：DocumentByCharacterSplitter
        //6、按正则表达式文档分割器：DocumentByRegexSplitter
        //递归分割：DocumentSplitters.recursive(...),默认递归到：按单词文档分割器：DocumentByWordSplitter


        Document document = FileSystemDocumentLoader.loadDocument("Document/协和简介.pdf",new ApachePdfBoxDocumentParser());

        //创建向量数据库,为了简单起见，先暂时使用基于内存的向量存储。
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        //ingest
        //1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过300个token（默认300个，可以通过配置修改），并且有30个token的重叠部分保证连贯性。
        //DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        //2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
        //3、将原始文本和向量存储到向量数据库中（InMemoryEmbeddingStore）
        EmbeddingStoreIngestor.ingest(document,embeddingStore);

        //查看向量数据库内容
        System.out.println(embeddingStore);
    }
}
