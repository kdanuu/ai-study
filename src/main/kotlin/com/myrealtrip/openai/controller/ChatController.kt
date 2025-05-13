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

    @GetMapping("/ai/travel")
    fun travelGuide(@RequestParam message: String): String {
        return chatService.getTravelGuideResponse(message)
    }

    @GetMapping("/ai/code-review")
    fun codeReview(@RequestParam message: String): String {
        return chatService.getCodeReviewExample(message)
    }

    @GetMapping("/ai/language")
    fun languageTeacher(@RequestParam message: String): String {
        return chatService.getLanguageTeacherResponse(message)
    }

    @GetMapping("/ai/conversation")
    fun multiTurnConversation(@RequestParam message: String): String {
        return chatService.getMultiTurnConversation(message)
    }

    @GetMapping("/ai/assistant")
    fun assistantExample(@RequestParam message: String): String {
        return chatService.getAssistantExample(message)
    }
}