package com.myrealtrip.openai.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * WebConfig - 웹 구성 클래스
 * CORS 설정 및 기타 웹 관련 구성을 처리합니다.
 */
@Configuration
class WebConfig : WebMvcConfigurer {
    
    /**
     * CORS 설정을 추가합니다.
     * 로컬 개발 환경에서의 테스트를 위한 CORS 설정입니다.
     * @param registry CORS 레지스트리
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}