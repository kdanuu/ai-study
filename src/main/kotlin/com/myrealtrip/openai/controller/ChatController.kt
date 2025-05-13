package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.ChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatService: ChatService
) {
    @GetMapping("/ai")
    fun chat(@RequestParam message: String): String {
        return chatService.getAIResponse(message)
    }
}