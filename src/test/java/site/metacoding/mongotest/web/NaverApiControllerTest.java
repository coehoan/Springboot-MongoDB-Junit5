package site.metacoding.mongotest.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import site.metacoding.mongotest.domain.Naver;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // 통합테스트
public class NaverApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void save_테스트() throws JsonProcessingException {
        // given (데이터만들기)
        Naver naver = new Naver();
        naver.setTitle("스프링1강");
        naver.setCompany("재밌어요");

        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(naver);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);

        // when (실행)
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.POST, httpEntity, String.class);

        // then (검증)
        // System.out.println("================================");
        // System.out.println(response.getBody());
        // System.out.println(response.getHeaders());
        // System.out.println(response.getStatusCodeValue());
        // System.out.println("================================");
        // assertEquals(201, response.getStatusCodeValue());
        DocumentContext dc = JsonPath.parse(response.getBody());
        // System.out.println(dc.jsonString());
        String title = dc.read("$.title");
        // System.out.println(title);
        assertEquals("스프링1강", title);
    }

    @Test
    public void findAll_테스트() {
        // given

        // when
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.GET, null, String.class);

        // then
        assertEquals(200, response.getStatusCodeValue());
    }
}
