package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.TemplateChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateChatController(
    private val templateChatService: TemplateChatService
) {
    @GetMapping("/ai/template-chat", produces = ["application/json"])
    @ResponseBody
    fun getChatResponse(
        @RequestParam message: String,
        @RequestParam role: String = "AI 비서"
    ): Map<String, Any> {
        val response = templateChatService.getResponse(message, role)
        return mapOf(
            "content" to (response.content() ?: "No response"),
            "role" to "assistant",
            "template" to "chat-template"
        )
    }
} 