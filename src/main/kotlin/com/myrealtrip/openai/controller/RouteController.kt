package com.myrealtrip.openai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * RouteController - 기본 라우팅 컨트롤러
 * 기본 페이지 요청을 처리합니다.
 */
@Controller
class RouteController {
    
    /**
     * "/askview" 엔드포인트에 대한 처리
     * @return 반환할 뷰 이름
     */
    @GetMapping("/askview")
    fun askview(): String {
        return "ask"
    }
}