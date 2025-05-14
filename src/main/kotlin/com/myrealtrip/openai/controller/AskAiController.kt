package com.myrealtrip.openai.controller

import com.myrealtrip.openai.service.ChatService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * AskAiController - AI 질문 처리 REST 컨트롤러
 * 채팅 서비스를 통해 사용자 질문을 처리하고 응답을 반환합니다.
 */
@RestController
class AskAiController(private val chatService: ChatService) {
    
    private val logger = LoggerFactory.getLogger(AskAiController::class.java)
    
    /**
     * "/ask" 엔드포인트에 대한 처리
     * 기본 응답을 생성합니다.
     * @param message 사용자 입력 메시지
     * @return 처리된 응답 문자열
     */
    @GetMapping("/ask")
    fun getResponse(@RequestParam("message") message: String): String {
        logger.info("기본 응답 요청 수신: {}", message)
        return chatService.getResponse(message)
    }
    
    /**
     * "/ask-ai" 엔드포인트에 대한 처리
     * 옵션을 적용한 응답을 생성합니다.
     * @param message 사용자 입력 메시지
     * @return 처리된 응답 문자열
     */
    @GetMapping("/ask-ai")
    fun getResponseOptions(@RequestParam("message") message: String): String {
        logger.info("옵션 적용 응답 요청 수신: {}", message)
        return chatService.getResponseOptions(message)
    }
}