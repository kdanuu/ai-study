package com.myrealtrip.openai.service

import org.slf4j.LoggerFactory
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service

/**
 * ChatService - AI 채팅 서비스 클래스
 * 채팅 요청을 처리하고 응답을 생성합니다.
 */
@Service
class ChatService(private val chatModel: ChatModel) {
    
    private val logger = LoggerFactory.getLogger(ChatService::class.java)
    
    /**
     * 기본 메시지를 처리하고 응답을 반환합니다.
     * @param message 사용자 입력 메시지
     * @return 처리된 응답 문자열
     */
    fun getResponse(message: String): String {
        logger.info("기본 응답 처리 중: {}", message)
        return chatModel.call(message)
    }
    
    /**
     * 옵션을 적용하여 메시지를 처리하고 응답을 반환합니다.
     * @param message 사용자 입력 메시지
     * @return 처리된 응답 문자열
     */
    fun getResponseOptions(message: String): String {
        logger.info("옵션 적용 응답 처리 중: {}", message)
        
        val response = chatModel.call(
            Prompt(
                message,
                OpenAiChatOptions.builder()
                    .model("gpt-4o")
                    .temperature(0.4)
                    .build()
            )
        )
        
        return response.getResult().output.text ?: ""
    }
}