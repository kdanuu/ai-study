package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.BasicChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val basicChatService: BasicChatService
) {
    @GetMapping("/ai")
    fun chat(@RequestParam message: String): String {
        return basicChatService.getAIResponse(message)
    }

    @GetMapping("/ai/travel")
    fun travelGuide(@RequestParam message: String): String {
        return basicChatService.getTravelGuideResponse(message)
    }

    @GetMapping("/ai/code-review")
    fun codeReview(@RequestParam message: String): String {
        return basicChatService.getCodeReviewExample(message)
    }

    @GetMapping("/ai/language")
    fun languageTeacher(@RequestParam message: String): String {
        return basicChatService.getLanguageTeacherResponse(message)
    }

    @GetMapping("/ai/conversation")
    fun multiTurnConversation(@RequestParam message: String): String {
        return basicChatService.getMultiTurnConversation(message)
    }

    @GetMapping("/ai/assistant")
    fun assistantExample(@RequestParam message: String): String {
        return basicChatService.getAssistantExample(message)
    }
}