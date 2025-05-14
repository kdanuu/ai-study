package com.myrealtrip.openai.service

import com.myrealtrip.openai.dto.Movie
import com.myrealtrip.openai.dto.TravelRecommendation
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.converter.ListOutputConverter
import org.springframework.ai.converter.MapOutputConverter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.stereotype.Service

@Service
class AdvancedStructuredOutputService(
    private val chatClient: ChatClient
) {
    // Spring AI 변환기를 위한 변환 서비스 인스턴스 (기본 타입 변환 지원)
    private val conversionService = DefaultConversionService()

    /**
     * 사용자의 메시지를 기반으로 줄바꿈으로 구분된 문자열 리스트를 생성한다.
     * ListOutputConverter는 텍스트 기반의 리스트 응답을 파싱해 List<String>으로 변환한다.
     * Spring AI 0.8 이상에서는 ListOutputConverter는 단일 인자 (ConversionService)만 받는다.
     */
    fun getListOfItems(message: String): List<String> {
        return chatClient.prompt()
            .system("""
                당신은 리스트 생성 전문가입니다.
                사용자의 요청에 따라 리스트를 생성해주세요.
                각 항목은 줄 바꿈으로 구분해주세요.
            """.trimIndent())
            .user(message)
            .call()
            .entity(ListOutputConverter(conversionService))
            ?: emptyList()  // null 대응
    }

    /**
     * AI가 추천한 영화 리스트를 Movie DTO로 파싱해 List<Movie> 형태로 반환한다.
     * ListOutputConverter는 제네릭 타입 추론이 어려우므로 ParameterizedTypeReference로 타입을 명시한다.
     * 프롬프트 형식과 Movie 클래스의 필드명이 정확히 일치해야 파싱이 성공한다.
     */
    fun getMovieRecommendations(message: String): List<Movie> {
        val movieListType = object : ParameterizedTypeReference<List<Movie>>() {}

        return chatClient.prompt()
            .system("""
                당신은 영화 추천 전문가입니다.
                주어진 장르에 맞는 영화 3개를 추천해주세요.
                각 영화는 다음 형식으로 작성해주세요:
                제목: [제목]
                감독: [감독]
                개봉년도: [년도]
                장르: [장르]
                평점: [평점]

                각 영화는 빈 줄로 구분해주세요.
            """.trimIndent())
            .user(message)
            .call()
            .entity(movieListType)
            ?: emptyList()
    }

    /**
     * AI가 특정 여행지에 대한 상세 정보를 TravelRecommendation 객체로 반환한다.
     * 반환 값은 nullable이므로 null일 경우 예외를 발생시킨다.
     * 프롬프트와 DTO 필드명이 일치해야 매핑이 가능하다.
     */
    fun getTravelRecommendation(message: String): TravelRecommendation {
        return chatClient.prompt()
            .system("""
                당신은 여행 전문가입니다.
                주어진 여행지에 대한 상세한 추천 정보를 제공해주세요.
                다음 형식으로 작성해주세요:
                여행지: [여행지]
                최적 방문 시기: [시기]
                주요 명소: [명소1, 명소2, 명소3]
                현지 음식: [음식1, 음식2, 음식3]
                예상 예산: [금액]원
            """.trimIndent())
            .user(message)
            .call()
            .entity(TravelRecommendation::class.java)
            ?: throw IllegalStateException("AI 응답이 null입니다.")
    }

    /**
     * 키-값 형식의 정보를 AI로부터 받아와 Map<String, Any> 형태로 반환한다.
     * 반환 값은 nullable이고 mutable일 수 있으므로 toMap()으로 immutable하게 변환한 후 null-safe 처리한다.
     */
    fun getStructuredMap(message: String): Map<String, Any> {
        return chatClient.prompt()
            .system("""
                당신은 정보 구조화 전문가입니다.
                사용자의 요청에 따라 정보를 키-값 쌍으로 구조화해주세요.
                각 항목은 '키: 값' 형식으로 작성해주세요.
            """.trimIndent())
            .user(message)
            .call()
            .entity(MapOutputConverter())
            ?.toMap()
            ?: emptyMap()
    }
}
