package com.myrealtrip.openai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class ParameterizedChatService(
    private val chatClient: ChatClient
) {
    /**
     * 파라미터화된 시스템 메시지를 사용하여 AI 응답을 생성합니다.
     * 
     * @param message 사용자 메시지
     * @param role AI의 역할 (기본값: "AI 비서")
     * @param experience 경력 (기본값: "10년")
     * @param specialty 전문 분야 (기본값: "여행")
     */
    fun getResponse(
        message: String,
        role: String = "AI 비서",
        experience: String = "10년",
        specialty: String = "여행"
    ): String {
        val systemMessage = """
            당신은 {experience} 경력의 {role}입니다.
            {specialty} 분야에서 전문적인 조언을 제공해주세요.
            친절하고 상세한 답변을 해주세요.
        """.trimIndent()
            .replace("{role}", role)
            .replace("{experience}", experience)
            .replace("{specialty}", specialty)

        return requireNotNull(
            chatClient.prompt()
                .system(systemMessage)
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }
} 