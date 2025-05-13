package com.myrealtrip.openai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatClient: ChatClient  // 생성자 주입
) {

    fun getAIResponse(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }
}