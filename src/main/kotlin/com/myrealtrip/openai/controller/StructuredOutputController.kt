package com.myrealtrip.openai.controller

import com.myrealtrip.openai.dto.Movie
import com.myrealtrip.openai.dto.TravelRecommendation
import com.myrealtrip.openai.service.StructuredOutputService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/structured")
class StructuredOutputController(
    private val structuredOutputService: StructuredOutputService
) {
    /**
     * 예시 요청:
     * GET /api/structured/list?query=한국의 주요 도시 5개를 알려줘
     * GET /api/structured/list?query=일본의 유명한 관광지 3곳을 알려줘
     * GET /api/structured/list?query=인기 있는 프로그래밍 언어 5개를 알려줘
     */
    @GetMapping("/list")
    fun getListOfItems(@RequestParam query: String): List<String> {
        return structuredOutputService.getListOfItems(query)
    }

    /**
     * 예시 요청:
     * GET /api/structured/movies?genre=액션
     * GET /api/structured/movies?genre=로맨스
     * GET /api/structured/movies?genre=공포
     */
    @GetMapping("/movies")
    fun getMovieRecommendations(@RequestParam genre: String): List<Movie> {
        return structuredOutputService.getMovieRecommendations(genre)
    }

    /**
     * 예시 요청:
     * GET /api/structured/travel?destination=도쿄
     * GET /api/structured/travel?destination=파리
     * GET /api/structured/travel?destination=뉴욕
     */
    @GetMapping("/travel")
    fun getTravelRecommendation(@RequestParam destination: String): TravelRecommendation {
        return structuredOutputService.getTravelRecommendation(destination)
    }

    /**
     * 예시 요청:
     * GET /api/structured/map?query=한국의 수도와 인구를 알려줘
     * GET /api/structured/map?query=일본의 주요 도시와 인구를 알려줘
     * GET /api/structured/map?query=세계 3대 요리의 특징을 알려줘
     */
    @GetMapping("/map")
    fun getStructuredMap(@RequestParam query: String): Map<String, Any> {
        return structuredOutputService.getStructuredMap(query)
    }
} 