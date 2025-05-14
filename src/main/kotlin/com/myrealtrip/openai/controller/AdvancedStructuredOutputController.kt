package com.myrealtrip.openai.controller

import com.myrealtrip.openai.dto.Movie
import com.myrealtrip.openai.dto.TravelRecommendation
import com.myrealtrip.openai.service.AdvancedStructuredOutputService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/advanced-structured")
class AdvancedStructuredOutputController(
    private val advancedStructuredOutputService: AdvancedStructuredOutputService
) {
    /**
     * 예시 요청:
     * GET /api/advanced-structured/list?message=한국의 주요 도시 5개를 알려줘
     * GET /api/advanced-structured/list?message=일본의 유명한 관광지 3곳을 알려줘
     */
    @GetMapping("/list")
    fun getListOfItems(@RequestParam message: String): List<String> {
        return advancedStructuredOutputService.getListOfItems(message)
    }

    /**
     * 예시 요청:
     * GET /api/advanced-structured/movies?message=액션 영화 3개 추천해줘
     * GET /api/advanced-structured/movies?message=로맨스 영화 3개 추천해줘
     */
    @GetMapping("/movies")
    fun getMovieRecommendations(@RequestParam message: String): List<Movie> {
        return advancedStructuredOutputService.getMovieRecommendations(message)
    }

    /**
     * 예시 요청:
     * GET /api/advanced-structured/travel?message=도쿄 여행 정보 알려줘
     * GET /api/advanced-structured/travel?message=파리 여행 정보 알려줘
     */
    @GetMapping("/travel")
    fun getTravelRecommendation(@RequestParam message: String): TravelRecommendation {
        return advancedStructuredOutputService.getTravelRecommendation(message)
    }

    /**
     * 예시 요청:
     * GET /api/advanced-structured/map?message=한국의 수도와 인구를 알려줘
     * GET /api/advanced-structured/map?message=일본의 주요 도시와 인구를 알려줘
     */
    @GetMapping("/map")
    fun getStructuredMap(@RequestParam message: String): Map<String, Any> {
        return advancedStructuredOutputService.getStructuredMap(message)
    }
} 