package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.ChatResponseService
import com.myrealtrip.openai.service.DetailedChatService
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatResponseController(
    private val chatResponseService: ChatResponseService,
    private val detailedChatService: DetailedChatService
) {
    /**
     * 간단한 content + role만 응답하는 기본 엔드포인트
     */
    @GetMapping("/ai/response", produces = ["application/json"])
    @ResponseBody
    fun getChatResponse(
        @RequestParam message: String,
        @RequestParam role: String = "AI 비서"
    ): Map<String, Any> {
        val response = chatResponseService.getResponse(message, role)
        return mapOf(
            "content" to (response.content() ?: "No response"),
            "role" to "assistant"
        )
    }

    /**
     * 전체 ChatResponse를 반환하는 디버그용 상세 응답 엔드포인트
     */
    @GetMapping("/ai/response/detailed", produces = ["application/json"])
    @ResponseBody
    fun getDetailedChatResponse(
        @RequestParam message: String,
        @RequestParam role: String = "AI 비서"
    ): ChatResponse? {
        return detailedChatService.getDetailedResponse(message, role)
    }
}
