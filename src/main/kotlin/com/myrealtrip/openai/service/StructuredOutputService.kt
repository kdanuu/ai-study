package com.myrealtrip.openai.service

import com.myrealtrip.openai.dto.Movie
import com.myrealtrip.openai.dto.TravelRecommendation
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service

@Service
class StructuredOutputService(
    private val chatClient: ChatClient
) {
    fun getListOfItems(query: String): List<String> {
        val messages = listOf(
            SystemMessage("""
                당신은 리스트 생성 전문가입니다.
                사용자의 요청에 따라 리스트를 생성해주세요.
                각 항목은 새로운 줄에 작성해주세요.
            """.trimIndent()),
            UserMessage(query)
        )

        return chatClient.prompt(Prompt(messages))
            .call()
            .content()
            ?.split("\n")
            ?.filter { it.isNotBlank() }
            ?: emptyList()
    }

    fun getMovieRecommendations(genre: String): List<Movie> {
        val messages = listOf(
            SystemMessage("""
                당신은 영화 추천 전문가입니다.
                주어진 장르에 맞는 영화 3개를 추천해주세요.
                각 영화는 다음 형식으로 작성해주세요:
                제목: [제목]
                감독: [감독]
                개봉년도: [년도]
                장르: [장르]
                평점: [평점]
                
                각 영화는 빈 줄로 구분해주세요.
            """.trimIndent()),
            UserMessage("장르: $genre")
        )

        return chatClient.prompt(Prompt(messages))
            .call()
            .content()
            ?.split("\n\n")
            ?.filter { it.isNotBlank() }
            ?.map { movieText ->
                val lines = movieText.split("\n")
                Movie(
                    title = lines[0].substringAfter("제목: ").trim(),
                    director = lines[1].substringAfter("감독: ").trim(),
                    year = lines[2].substringAfter("개봉년도: ").trim().toInt(),
                    genre = lines[3].substringAfter("장르: ").trim(),
                    rating = lines[4].substringAfter("평점: ").trim().toDouble()
                )
            }
            ?: emptyList()
    }

    fun getTravelRecommendation(destination: String): TravelRecommendation {
        val messages = listOf(
            SystemMessage("""
                당신은 여행 전문가입니다.
                주어진 여행지에 대한 상세한 추천 정보를 제공해주세요.
                다음 형식으로 작성해주세요:
                여행지: [여행지]
                최적 방문 시기: [시기]
                주요 명소: [명소1, 명소2, 명소3]
                현지 음식: [음식1, 음식2, 음식3]
                예상 예산: [금액]원
            """.trimIndent()),
            UserMessage("여행지: $destination")
        )

        val response = chatClient.prompt(Prompt(messages))
            .call()
            .content()
            ?: throw RuntimeException("AI 응답이 null입니다.")

        val lines = response.split("\n")
        return TravelRecommendation(
            destination = lines[0].substringAfter("여행지: ").trim(),
            bestTimeToVisit = lines[1].substringAfter("최적 방문 시기: ").trim(),
            attractions = lines[2].substringAfter("주요 명소: ").trim().split(", "),
            localCuisine = lines[3].substringAfter("현지 음식: ").trim().split(", "),
            estimatedBudget = lines[4].substringAfter("예상 예산: ").trim().replace("원", "").toInt()
        )
    }

    fun getStructuredMap(query: String): Map<String, Any> {
        val messages = listOf(
            SystemMessage("""
                당신은 정보 구조화 전문가입니다.
                사용자의 요청에 따라 정보를 키-값 쌍으로 구조화해주세요.
                각 항목은 다음 형식으로 작성해주세요:
                키: 값
            """.trimIndent()),
            UserMessage(query)
        )

        return chatClient.prompt(Prompt(messages))
            .call()
            .content()
            ?.split("\n")
            ?.filter { it.isNotBlank() }
            ?.associate { line ->
                val (key, value) = line.split(":", limit = 2)
                key.trim() to value.trim()
            }
            ?: emptyMap()
    }
} 