package com.hcy.javaailangchain4j;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
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
}
