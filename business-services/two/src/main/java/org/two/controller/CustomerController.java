package org.two.controller;

import static com.netflix.servo.annotations.DataSourceType.COUNTER;
import static com.netflix.servo.annotations.DataSourceType.GAUGE;

import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.monitor.Timer;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Timed("customer")
public class CustomerController {

    public static final String CUSTOMERS_ALL_COUNT = "Customers.all.count";
    @Monitor(name="CurrentConnections", type=GAUGE)
    private AtomicInteger currentConnections = new AtomicInteger(0);

    @Monitor(name="TotalConnections", type=COUNTER)
    private AtomicInteger totalConnections = new AtomicInteger(0);

    private List<String> names = Arrays.asList("Sergio Vargas", "Donald Clown", "Hachiro Perez");
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Autowired
    private final MeterRegistry meterRegistry;

    @Autowired
    private final MonitorRegistry monitorRegistry;

    @Autowired
    private final Timer timer;

    public CustomerController(MeterRegistry meterRegistry, MonitorRegistry monitorRegistry, Timer timer) {
        this.meterRegistry = meterRegistry;
        this.monitorRegistry = monitorRegistry;
        this.timer = timer;
        monitorRegistry.register(this.timer);
        Monitors.registerObject("CustomerController", this);
    }

    @GetMapping("/customers")
    @Timed(value = "customers.all", longTask = true)
    public List<String> listCustomers() throws InterruptedException {
        long start = System.nanoTime();
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(500));
        meterRegistry.counter(CUSTOMERS_ALL_COUNT).increment();
        long end = System.nanoTime();
        timer.record(end -  start, TimeUnit.NANOSECONDS);
        currentConnections.incrementAndGet();
        return names;
    }

    @GetMapping("/customers/counter")
    @Timed(value = "customers.all", longTask = true)
    @Monitor(name = "ListCustomers", type = COUNTER)
    public Integer customerCounter() throws InterruptedException {
        long start = System.nanoTime();
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(500));
        meterRegistry.counter(CUSTOMERS_ALL_COUNT).increment();
        long end = System.nanoTime();
        timer.record(end -  start, TimeUnit.NANOSECONDS);
        return totalConnections.incrementAndGet();
    }

    @GetMapping("/customers/counter/gauge")
    @Timed(value = "customers.all", longTask = true)
    @Monitor(name = "ListCustomersWithGauge", type = GAUGE)
    public Integer customerCounterWithGauge() throws InterruptedException {
        long start = System.nanoTime();
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(500));
        meterRegistry.counter(CUSTOMERS_ALL_COUNT).increment();
        long end = System.nanoTime();
        timer.record(end -  start, TimeUnit.NANOSECONDS);
        return currentConnections.incrementAndGet();
    }
}
