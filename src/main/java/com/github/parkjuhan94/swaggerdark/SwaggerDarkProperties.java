package com.github.parkjuhan94.swaggerdark;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger-dark")
public class SwaggerDarkProperties {

    /** 다크 테마 적용 여부. */
    private boolean enabled = true;

    /** 다크 CSS를 주입할 Swagger UI 진입 페이지 경로(컨텍스트 경로 제외). */
    private String indexPath = "/swagger-ui/index.html";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }
}
