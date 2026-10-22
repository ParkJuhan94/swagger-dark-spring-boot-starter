# swagger-dark-spring-boot-starter

> **⚠️ 이 프로젝트는 더 이상 유지하지 않습니다.**
> Swagger UI **5.31.0**부터 공식 다크모드(상단 토글 버튼)가 생겨 이 스타터가 필요 없어졌습니다.
> springdoc이 번들한 UI가 그보다 낮다면, 웹자르만 올려서 공식 다크모드를 쓰세요.
>
> ```gradle
> implementation 'org.webjars:swagger-ui:5.32.14'
> ```
>
> ```yaml
> springdoc:
>   swagger-ui:
>     version: 5.32.14   # 웹자르만 올리면 springdoc이 옛 경로를 찾아 404가 나므로 함께 지정
> ```
>
> springdoc 2.6.0(Spring Boot 3.3.6), 2.1.0(Spring Boot 3.2.0)에서 이 방식으로 동작을 확인했습니다.
> springdoc 2.9.x로 전체를 올리는 방법은 Spring 6.2(Spring Boot 3.4 이상)가 전제입니다.

Swagger UI(springdoc-openapi)에 다크 테마를 입히는 Spring Boot 스타터입니다. 의존성 한 줄이면 적용되고, 별도 설정이 필요 없습니다.

- 배경 `#161e2d` 네이비 계열, GET/POST/PUT/DELETE 등 메서드별 박스 색은 어두운 배경에서도 구분되도록 채도를 올렸습니다.
- 드롭다운 화살표, 펼침 화살표, `required` 라벨(박스), 표 구분선 등 기본 스타일에서 대비가 낮은 요소를 보정했습니다.
- CSS는 Swagger UI의 `index.html`에 인라인 `<style>`로 주입합니다. 별도 CSS 경로가 없어 Spring Security의 `permitAll` 설정이 필요 없습니다.

## 사용법

JitPack을 저장소로 추가한 뒤 의존성을 넣습니다.

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.x'
    implementation 'com.github.ParkJuhan94:swagger-dark-spring-boot-starter:v0.1.0'
}
```

## 설정

| 프로퍼티 | 기본값 | 설명 |
|---|---|---|
| `swagger-dark.enabled` | `true` | `false`로 두면 다크 테마를 끕니다 |
| `swagger-dark.index-path` | `/swagger-ui/index.html` | CSS를 주입할 Swagger UI 진입 페이지 경로(컨텍스트 경로 제외) |

## 호환 범위

| 항목 | 확인된 범위 |
|---|---|
| Spring Boot | 3.2.0, 3.3.6 |
| springdoc-openapi | 2.1.0, 2.6.0 (Swagger UI 5.x) |
| Java | 17 이상 |
| 웹 스택 | Servlet(Spring MVC)만 지원, WebFlux는 미지원 |

CSS는 Swagger UI 5.x의 클래스명에 의존하므로, 다른 메이저 버전에서는 일부 색이 달라 보일 수 있습니다.

## 동작 방식

springdoc에는 테마 설정이 없어서 서블릿 필터가 `/swagger-ui/index.html` 응답의 `</head>` 앞에 `<style>`을 끼워 넣습니다. 자동 구성(`SwaggerDarkAutoConfiguration`)이 필터를 등록하며, springdoc이 클래스패스에 없거나 서블릿 웹 애플리케이션이 아니면 아무 일도 하지 않습니다.

## 라이선스

MIT
