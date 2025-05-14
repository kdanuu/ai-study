package com.myrealtrip.openai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.stereotype.Service

@Service
class DetailedChatService(
    private val chatClient: ChatClient
) {
    fun getDetailedResponse(message: String, role: String): ChatResponse? {
        return chatClient.prompt()
            .system("당신은 ${role}입니다. 사용자의 질문에 친절하고 상세하게 답변해주세요.")
            .user(message)
            .call()
            .chatResponse()
    }

}
