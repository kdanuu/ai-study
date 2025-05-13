package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.ParameterizedChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ParameterizedChatController(
    private val parameterizedChatService: ParameterizedChatService
) {
    @GetMapping("/ai/parameterized")
    fun parameterizedChat(
        @RequestParam message: String,
        @RequestParam role: String = "AI 비서",
        @RequestParam experience: String = "10년",
        @RequestParam specialty: String = "여행"
    ): String {
        return parameterizedChatService.getResponse(message, role, experience, specialty)
    }
} 