package org.two.config;

import com.netflix.servo.BasicMonitorRegistry;
import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.monitor.Timer;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServoConfiguration {

    @Bean
    public void registerMetrics() {
        Monitors.registerObject(this);
    }

    @Bean
    public MonitorRegistryMetricPoller monitorRegistryMetricPoller() {
        return new MonitorRegistryMetricPoller();
    }

    @Bean
    MonitorRegistry createMonitorRegistry() {
        return new BasicMonitorRegistry();
    }

    @Bean
    public Timer createTimer() {
        return new BasicTimer(MonitorConfig.builder("People")
                .withTag("endpoint", "people").build());
    }

    @Bean
    JvmThreadMetrics threadMetrics() {
        return new JvmThreadMetrics();
    }

    @Bean
    MeterRegistry meterRegistry() {
        return new CompositeMeterRegistry();
    }

}
