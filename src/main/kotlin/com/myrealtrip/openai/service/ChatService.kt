package com.myrealtrip.openai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatClient: ChatClient
) {
    fun getAIResponse(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }

    fun getTravelGuideResponse(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .system("당신은 20년 경력의 여행 가이드입니다. 여행자들에게 친절하고 전문적인 조언을 제공해주세요.")
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }

    fun getCodeReviewerResponse(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .system("당신은 시니어 소프트웨어 엔지니어입니다. 코드 리뷰를 전문적으로 수행하며, 보안, 성능, 가독성 측면에서 자세한 피드백을 제공해주세요.")
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }

    fun getLanguageTeacherResponse(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .system("당신은 15년 경력의 언어 교사입니다. 학생들의 질문에 친절하게 답변하고, 문법과 발음에 대해 자세히 설명해주세요.")
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }

    fun getMultiTurnConversation(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .system("당신은 친절한 AI 비서입니다. 대화를 자연스럽게 이어가며, 이전 대화 맥락을 기억하고 있습니다.")
                .user("안녕하세요!")
                .user(message)
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }

    /**
     * 어시스턴트 메시지를 포함한 대화 예제
     * Spring AI의 Fluent API를 사용하여 대화 흐름을 구성합니다.
     * 
     * 대화 순서:
     * 1. 시스템 메시지: AI의 역할 정의
     * 2. messages()를 사용하여 이전 대화 기록 추가
     *    - UserMessage: 사용자의 첫 인사
     *    - AssistantMessage: AI의 응답
     * 3. 현재 사용자 메시지 추가
     * 4. AI 응답 생성 및 반환
     */
    fun getAssistantExample(message: String): String {
        return requireNotNull(
            chatClient.prompt()
                .system("당신은 친절한 AI 비서입니다.")  // AI의 역할 정의
                .messages(  // 이전 대화 기록 추가
                    UserMessage("안녕하세요!"),  // 사용자의 첫 메시지
                    AssistantMessage("안녕하세요! 무엇을 도와드릴까요?")  // AI의 응답
                )
                .user(message)  // 현재 사용자 메시지
                .call()  // AI 응답 생성
                .content()  // 응답 내용 반환
        ) { "AI 응답이 null입니다." }
    }

    /**
     * 마이리얼트립 프론트엔드 코드 리뷰 예시
     * 실제 코드를 포함하여 AI가 코드 리뷰를 수행할 수 있도록 합니다.
     */
    fun getCodeReviewExample(message: String): String {
        val codeExample = """
            // 마이리얼트립 호텔 검색 컴포넌트
            @Component
            class HotelSearchComponent {
                private val searchService: HotelSearchService
                private val state = mutableStateOf<SearchState>(SearchState.Initial)
                
                fun searchHotels(criteria: SearchCriteria) {
                    viewModelScope.launch {
                        try {
                            state.value = SearchState.Loading
                            val results = searchService.searchHotels(criteria)
                            state.value = SearchState.Success(results)
                        } catch (e: Exception) {
                            state.value = SearchState.Error(e.message ?: "검색 중 오류가 발생했습니다")
                        }
                    }
                }
            }
            
            // 검색 상태를 나타내는 sealed class
            sealed class SearchState {
                object Initial : SearchState()
                object Loading : SearchState()
                data class Success(val results: List<Hotel>) : SearchState()
                data class Error(val message: String) : SearchState()
            }
        """.trimIndent()

        return requireNotNull(
            chatClient.prompt()
                .system("당신은 마이리얼트립의 시니어 프론트엔드 개발자입니다. 코드 리뷰를 전문적으로 수행하며, 보안, 성능, 가독성 측면에서 자세한 피드백을 제공해주세요.")
                .user("다음 코드를 리뷰해주세요:\n$codeExample\n\n$message")
                .call()
                .content()
        ) { "AI 응답이 null입니다." }
    }
}