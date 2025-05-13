package com.myrealtrip.openai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import java.nio.charset.StandardCharsets

@Service
class TemplateChatService(
    private val chatClient: ChatClient
) {
    private val template: String by lazy {
        val resource = ClassPathResource("templates/chat-template.txt")
        FileCopyUtils.copyToString(resource.inputStream.reader(StandardCharsets.UTF_8))
    }

    fun getResponse(message: String, role: String): CallResponseSpec {
        val systemPrompt = template
            .split("사용자 질문:")[0]
            .replace("{role}", role)
            .trim()

        return chatClient.prompt()
            .system(systemPrompt)
            .user(message)
            .call()
    }
} 