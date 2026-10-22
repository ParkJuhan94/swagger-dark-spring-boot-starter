package com.github.parkjuhan94.swaggerdark;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SwaggerDarkFilterTest {

    private static final String HTML = "<html><head><title>t</title></head><body></body></html>";

    private final SwaggerDarkFilter filter = new SwaggerDarkFilter("/swagger-ui/index.html", "body{color:red}");

    private String run(String uri, String contextPath) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", contextPath + uri);
        request.setContextPath(contextPath);
        MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, (req, res) -> res.getOutputStream().write(HTML.getBytes(StandardCharsets.UTF_8)));
        return response.getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void indexPage에는_head_앞에_스타일이_주입된다() throws Exception {
        String body = run("/swagger-ui/index.html", "");

        assertThat(body).contains("<style id=\"swagger-dark\">body{color:red}</style></head>");
    }

    @Test
    void 컨텍스트_경로가_있어도_주입된다() throws Exception {
        String body = run("/swagger-ui/index.html", "/app");

        assertThat(body).contains("swagger-dark");
    }

    @Test
    void 다른_경로는_건드리지_않는다() throws Exception {
        assertThat(run("/api/health", "")).isEqualTo(HTML);
    }
}
