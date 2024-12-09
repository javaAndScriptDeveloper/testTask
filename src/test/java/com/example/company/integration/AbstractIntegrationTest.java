package com.example.company.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.company.AbstractTest;
import com.example.company.Application;
import com.example.company.client.CurrencyRatesApiClient;
import com.example.company.repository.CurrencyRepo;
import com.example.company.utils.JsonUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Tag("integration")
@DirtiesContext
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest extends AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected CurrencyRatesApiClient currencyRatesApiClient;

    @Autowired
    protected CurrencyRepo currencyRepo;

    @Value("${api.path}")
    protected String baseApiPath;

    @BeforeEach
    public void setup() {
        currencyRepo.deleteAll();
    }

    @SneakyThrows
    protected ResultActions executeGetApiCall(String url) {
        return mockMvc.perform(get(url)).andDo(print());
    }

    @SneakyThrows
    protected <T> ResultActions executePostApiCall(String url, T dto) {
        return mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(dto)))
                .andDo(print());
    }
}
