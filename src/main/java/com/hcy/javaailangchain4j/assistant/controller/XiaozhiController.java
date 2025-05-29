package com.hcy.javaailangchain4j.assistant.controller;

import com.hcy.javaailangchain4j.assistant.XiaozhiAgent;
import com.hcy.javaailangchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 贫道重阳
 * @date 2025/5/27 18:17
 * @description
 */

@Tag(name = "硅谷小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {
    @Autowired
    private XiaozhiAgent xiaozhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm) {
        return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
