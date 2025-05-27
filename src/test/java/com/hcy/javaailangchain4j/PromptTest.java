package com.hcy.javaailangchain4j;

import com.hcy.javaailangchain4j.assistant.SeparateChatAssistant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 贫道重阳
 * @date 2025/5/23 15:48
 * @description
 */
@Slf4j
@SpringBootTest
public class PromptTest {
    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testPrompt() {
        //@SystemMessage("你是我的好朋友，请用河南话回答问题。")
        //第一次
        //log.info(separateChatAssistant.chat(3, "我是谁"));
        //输出内容：你这是考我哩吧，你就是你啊，俺的好朋友！有啥事儿你就直说吧。

        //第二次
        //log.info(separateChatAssistant.chat(3, "我是Hcy"));
        //输出：哦，原来你是Hcy啊，俺的好朋友！有啥事儿需要帮忙不？

        //第三次
        //log.info(separateChatAssistant.chat(3, "我是谁"));
        //输出：你是Hcy啊，俺的好朋友！有啥事儿需要俺帮忙不？

        //三次调用是分开调用，三次结果表现出提示词的作用，以及聊天记忆持久化的效果
        //添加提示词后，会多发送一条type=SYSTEM的信息
        //mongodb的聊天记忆：
        //[{"text":"你是我的好朋友，请用河南话回答问题。","type":"SYSTEM"},{"contents":[{"text":"我是谁","type":"TEXT"}],"type":"USER"},
        // {"text":"你这是考我哩吧，你就是你啊，俺的好朋友！有啥事儿你就直说吧。","type":"AI"},{"contents":[{"text":"我是Hcy","type":"TEXT"}],"type":"USER"},
        // {"text":"哦，原来你是Hcy啊，俺的好朋友！有啥事儿需要帮忙不？","type":"AI"},{"contents":[{"text":"我是谁","type":"TEXT"}],"type":"USER"},
        // {"text":"你是Hcy啊，俺的好朋友！有啥事儿需要俺帮忙不？","type":"AI"}]

        //@SystemMessage("你是我的好朋友，请用东北话回答问题。")
        //第四次
        //log.info(separateChatAssistant.chat(3, "我是谁"));
        //输出：哎呀，你这不是逗我呢嘛！你是谁我咋能不知道呢，咱俩可是老铁啊！但你要是非让我猜，那我还真猜不出来。你倒是说说，你到底是谁啊？哈哈。
        //mongodb的聊天记忆：
        //[{"contents":[{"text":"我是谁","type":"TEXT"}],"type":"USER"},{"text":"你这是考我哩吧，你就是你啊，俺的好朋友！有啥事儿你就直说吧。","type":"AI"},
        // {"contents":[{"text":"我是Hcy","type":"TEXT"}],"type":"USER"},{"text":"哦，原来你是Hcy啊，俺的好朋友！有啥事儿需要帮忙不？","type":"AI"},
        // {"contents":[{"text":"我是谁","type":"TEXT"}],"type":"USER"},{"text":"你是Hcy啊，俺的好朋友！有啥事儿需要俺帮忙不？","type":"AI"},
        // {"text":"你是我的好朋友，请用东北话回答问题。","type":"SYSTEM"},{"contents":[{"text":"我是谁","type":"TEXT"}],"type":"USER"},
        // {"text":"哎呀，你这不是逗我呢嘛！你是谁我咋能不知道呢，咱俩可是老铁啊！但你要是非让我猜，那我还真猜不出来。你倒是说说，你到底是谁啊？哈哈。","type":"AI"}]

        //对比两次聊天记忆发现，修改提示词后，大语音模型重新发送了一个type=SYSTEM的信息，且将原来的提示词信息删除了
        //同时聊天记忆重置，不在读取上一个提示词的聊天信息。

        //第五次：通过模板获取提示词
        log.info(separateChatAssistant.chat(4, "今天是几号？我是谁？"));
        //输出：今朝是2025年5月26号哦！侬是啥人嘛... 哈哈，侬侪呒没讲过自家名字，阿拉哪能晓得啦~ 😄
    }

    /**
     * 测试 chat1 V注解
     */
    @Test
    public void testPrompt1() {
        log.info(separateChatAssistant.chat1(5, "今天是几号？我是谁？"));
        //{
        //  "_id": {
        //    "$oid": "6835874f3ba343fdc1a2c0f4"
        //  },
        //  "memoryId": 5,
        //  "content": "[{\"contents\":[{\"text\":\"你是我的好朋友，请用粤语回答问题。今天是几号？我是谁？\",\"type\":\"TEXT\"}],\"type\":\"USER\"},{\"text\":\"你好呀，好朋友！今日几号我就唔知啦，因为我冇办法睇到实际嘅日期。至于你系边个，我谂你应该系一个好有心思嘅人，会谂住用粤语同我倾计。不过如果你问我你嘅真实身份嘅话，咁就真係对唔住喇，我真係答唔到你。希望呢个回答对你有少少帮助啦！如果仲有其他问题想问，记得继续揾我倾计吖！\",\"type\":\"AI\"}]"
        //}
    }

    /**
     * 测试 chat2 V注解
     */
    @Test
    public void testPrompt2() {
        //从数据库获取用户信息
        String userName = "小丽";
        int age = 18;

        log.info(separateChatAssistant.chat2(6, "我是谁？我多大了", userName, age));
        //输出：哎呀，小丽你这还问我呢？你不就是我滴好朋友小丽嘛，今年18岁啦！😊
    }
}
