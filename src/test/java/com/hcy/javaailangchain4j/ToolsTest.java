package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.SeparateChatAssistant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 贫道重阳
 * @date 2025/5/27 22:09
 * @description
 */
@Slf4j
@SpringBootTest
public class ToolsTest {
    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    /**
     * 测试大模型的数学运算能力
     */
    @Test
    public void testTools() {
        String result = separateChatAssistant.chat(1, "1+2等于几？475695037565的平方根是多少？");
        log.info("result:{}",result);
        //输出：result:1+2等于3啦～至于475695037565的平方根嘛，这个数字太大了，我直接算不出来，不过你可以用计算器来帮忙算一记！😊
        //证明大模型数学运算能力不行，如果需要类似的功能，需要给大模型提供工具，引入@Tool注解

        //添加tools = {"calculatorTools"}，再次执行
        //输出：
        //2025-05-27T22:26:14.701+08:00  INFO 36292 --- [           main] c.h.j.assistant.tools.CalculatorTools    : 调用加法运算
        //2025-05-27T22:26:16.827+08:00  INFO 36292 --- [           main] c.h.j.assistant.tools.CalculatorTools    : 调用平方根运算
        //2025-05-27T22:26:21.538+08:00  INFO 36292 --- [           main] com.hcy.javaailangchain4j.ToolsTest      : result:1+2等于3啦～至于475695037565的平方根嘛，算出来大概是689706.49！这种大数字直接算真的有点吃力，还好有计算器帮忙，哈哈！😄
        //证明tool注解生效了

        //聊天记录
        //[{"text":"你是我的好朋友，请用上海话回答问题，回答问题的时候适当添加符号。\r\n今天是2025-05-27","type":"SYSTEM"},
        // {"contents":[{"text":"1+2等于几？475695037565的平方根是多少？","type":"TEXT"}],"type":"USER"},
        // {"text":"1+2等于3啦～至于475695037565的平方根嘛，这个数字太大了，我直接算不出来，不过你可以用计算器来帮忙算一记！😊","type":"AI"},
        // {"contents":[{"text":"1+2等于几？475695037565的平方根是多少？","type":"TEXT"}],"type":"USER"},
        // {"toolExecutionRequests":[{"id":"call_9abfccba934f474da3f4a4","name":"sum","arguments":"{\"arg0\": 1, \"arg1\": 2}"}],"type":"AI"},
        // {"id":"call_9abfccba934f474da3f4a4","toolName":"sum","text":"3.0","type":"TOOL_EXECUTION_RESULT"},
        // {"toolExecutionRequests":[{"id":"call_3a4e2b82f98146e4b572f7","name":"squareRoot","arguments":"{\"arg0\": 475695037565.0}"}],"type":"AI"},
        // {"id":"call_3a4e2b82f98146e4b572f7","toolName":"squareRoot","text":"689706.4865324959","type":"TOOL_EXECUTION_RESULT"},
        // {"text":"1+2等于3啦～至于475695037565的平方根嘛，算出来大概是689706.49！这种大数字直接算真的有点吃力，还好有计算器帮忙，哈哈！😄","type":"AI"}]

        //从第二次聊天记录可以看出，大模型识别了工具，给每个tool方法分配了id。调用了工具,并记录了方法的信息：方法id,方法名称,入参信息，以及调用者：AI。并把结果返回给用户
        //其中 "type":"TOOL_EXECUTION_RESULT"的信息表示tool方法把结果返回给AI。
    }
}
