package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.ChatResponseService
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * ChatResponse를 활용한 AI 채팅 컨트롤러
 * 
 * ChatResponse 객체를 통해 AI 응답의 다양한 정보를 활용할 수 있습니다:
 * - content: AI의 실제 응답 내용
 * - role: 응답자의 역할 (assistant)
 * - metadata: 모델 정보, 생성 시간 등 메타데이터
 * - usage: 토큰 사용량 정보
 */
@RestController
class ChatResponseController(
    private val chatResponseService: ChatResponseService
) {
    /**
     * AI 채팅 응답을 받는 엔드포인트
     *
     * @param message 사용자 메시지
     * @param role AI의 역할 (기본값: "AI 비서")
     * @return CallResponseSpec 형태의 응답
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
}