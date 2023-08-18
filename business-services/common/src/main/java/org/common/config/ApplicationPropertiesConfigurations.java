package org.common.config;

import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationPropertiesConfigurations {

    @Bean
    public AbstractConfiguration addApplicationPropertiesSource() throws IOException {
        URL configPropertyURL = (new ClassPathResource("other.properties")).getURL();
        PolledConfigurationSource source = new URLConfigurationSource(configPropertyURL);
        return new DynamicConfiguration(source, new FixedDelayPollingScheduler());
    }

}
