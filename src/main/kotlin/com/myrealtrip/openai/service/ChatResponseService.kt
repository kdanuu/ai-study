package com.myrealtrip.openai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec
import org.springframework.stereotype.Service

/**
 * ChatResponse를 반환하는 AI 채팅 서비스
 *
 * Spring AI의 ChatResponse를 활용하여 AI 응답의 다양한 정보를 제공합니다.
 * - 시스템 메시지를 통한 AI 역할 정의
 * - 사용자 메시지 처리
 * - ChatResponse를 통한 상세 응답 정보 제공
 */
@Service
class ChatResponseService(
    private val chatClient: ChatClient
) {
    /**
     * AI 채팅 응답을 생성하는 메서드
     *
     * @param message 사용자 메시지
     * @param role AI의 역할 (기본값: "AI 비서")
     * @return ChatResponse 객체
     *         - result.output.content: AI의 실제 응답 내용
     *         - result.output.role: 응답자 역할
     *         - metadata: 모델 정보, 생성 시간 등
     *         - usage: 토큰 사용량 정보
     */
    fun getResponse(message: String, role: String): CallResponseSpec {
        // 시스템 메시지 구성: AI의 역할과 응답 스타일 정의
        val systemMessage = """
            당신은 {role}입니다.
            사용자의 질문에 친절하고 상세하게 답변해주세요.
        """.trimIndent()
            .replace("{role}", role)

        // ChatClient를 통해 AI 응답 생성
        return chatClient.prompt()
            .system(systemMessage)  // AI의 역할 정의
            .user(message)          // 사용자 메시지 전달
            .call()                 // AI 응답 생성

    }

} 