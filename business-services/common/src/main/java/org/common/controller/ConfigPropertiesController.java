package org.common.controller;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropertiesController {

    @Value("${org.common.properties.one:not found!}")
    private String propertyOneWithValue;

    @Value("${org.common.properties.two:not found!}")
    private String propertyTwoWithValue;

    @Value("${org.common.properties.three:not found!}")
    private String propertyThreeWithValue;

    @Value("${org.common.properties.four:not found!}")
    private String propertyFourWithValue;

    private DynamicStringProperty propertyOneWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("org.common.properties.one", "not found!");

    private DynamicStringProperty propertyTwoWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("org.common.properties.two", "not found!");

    private DynamicStringProperty propertyThreeWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("org.common.properties.three", "not found!");

    private DynamicStringProperty propertyFourWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("org.common.properties.four", "not found!");

    private DynamicIntProperty intPropertyWithDynamic = DynamicPropertyFactory.getInstance()
            .getIntProperty("org.common.properties.int", 0);

    @GetMapping("/properties-from-value")
    public Map<String, String> getPropertiesFromValue() {
        Map<String, String> properties = new HashMap<>();
        properties.put("org.common.properties.one", propertyOneWithValue);
        properties.put("org.common.properties.two", propertyTwoWithValue);
        properties.put("org.common.properties.three", propertyThreeWithValue);
        properties.put("org.common.properties.four", propertyFourWithValue);
        return properties;
    }

    @GetMapping("/properties-from-dynamic")
    public Map<String, String> getPropertiesFromDynamic() {
        Map<String, String> properties = new HashMap<>();
        properties.put(propertyOneWithDynamic.getName(), propertyOneWithDynamic.get());
        properties.put(propertyTwoWithDynamic.getName(), propertyTwoWithDynamic.get());
        properties.put(propertyThreeWithDynamic.getName(), propertyThreeWithDynamic.get());
        properties.put(propertyFourWithDynamic.getName(), propertyFourWithDynamic.get());
        return properties;
    }

    @GetMapping("/int-property")
    public Map<String, Integer> getIntPropertyFromDynamic() {
        Map<String, Integer> properties = new HashMap<>();
        properties.put(intPropertyWithDynamic.getName(), intPropertyWithDynamic.get());
        return properties;
    }
}
