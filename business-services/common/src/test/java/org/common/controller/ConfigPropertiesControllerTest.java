package org.common.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConfigPropertiesControllerTest {

    private static final String BASE_URL = "http://localhost:8085";

    private static final String DYNAMIC_PROPERTIES_URL = "/properties-from-dynamic";
    private static final Map<String, String> EXPECTED_PROPERTIES = createExpectedProperties();

    private static Map<String, String> createExpectedProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("org.common.properties.one", "one FROM:config.properties");
        map.put("org.common.properties.two", "not found!");
        map.put("org.common.properties.three", "three FROM:config.properties");
        map.put("org.common.properties.four", "not found!");
        return map;
    }

    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    private MockMvc mvc;


    @Test
    public void givenNonDefaultConfigurationFilesSetup_whenRequestProperties_thenEndpointRetrievesValuesInFiles() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(DYNAMIC_PROPERTIES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        String contentAsString = mvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        assertThat(contentAsString).contains(createExpectedProperties().values());
    }

}