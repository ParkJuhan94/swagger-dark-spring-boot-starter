package com.github.parkjuhan94.swaggerdark;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Swagger UI 진입 페이지(index.html)의 {@code </head>} 앞에 다크 테마 CSS를 인라인 {@code <style>}로 끼워 넣는다.
 * 별도 CSS 경로를 만들지 않으므로 Spring Security의 permitAll 같은 사용처 설정이 필요 없다.
 */
class SwaggerDarkFilter extends OncePerRequestFilter {

    private static final String HEAD_END = "</head>";

    private final String indexPath;
    private final String styleTag;

    SwaggerDarkFilter(String indexPath, String css) {
        this.indexPath = indexPath;
        this.styleTag = "<style id=\"swagger-dark\">" + css + "</style>";
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return !indexPath.equals(path);
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws ServletException, IOException {
        ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
        chain.doFilter(request, wrapper);

        String html = new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        if (!html.contains(HEAD_END)) {
            wrapper.copyBodyToResponse();
            return;
        }
        byte[] body = html.replace(HEAD_END, styleTag + HEAD_END).getBytes(StandardCharsets.UTF_8);
        wrapper.resetBuffer();
        response.setContentLength(body.length);
        response.getOutputStream().write(body);
    }
}
