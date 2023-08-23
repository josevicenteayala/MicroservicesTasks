package org.two.controller;

import static com.netflix.servo.annotations.DataSourceType.COUNTER;
import static com.netflix.servo.annotations.DataSourceType.GAUGE;
import static com.netflix.servo.annotations.DataSourceType.INFORMATIONAL;

import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.monitor.Timer;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Timed("people")
public class PeopleController {

    @Monitor(name="Status", type=INFORMATIONAL)
    private AtomicReference<String> status = new AtomicReference<String>("UP");

    @Monitor(name="CurrentConnections", type=GAUGE)
    private AtomicInteger currentConnections = new AtomicInteger(0);

    @Monitor(name="TotalConnections", type=COUNTER)
    private AtomicInteger totalConnections = new AtomicInteger(0);

    @Monitor(name="BytesIn", type=COUNTER)
    private AtomicLong bytesIn = new AtomicLong(0L);

    @Monitor(name="BytesOut", type=COUNTER)
    private AtomicLong bytesOut = new AtomicLong(0L);

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Autowired
    private final MeterRegistry meterRegistry;

    @Autowired
    private final MonitorRegistry monitorRegistry;

    @Autowired
    private final Timer timer;


    private List<String> names = Arrays.asList("Sergio Vargas", "Donald Clown", "Hachiro Perez");

    public PeopleController(MeterRegistry meterRegistry, MonitorRegistry monitorRegistry, Timer timer) {
        this.meterRegistry = meterRegistry;
        this.monitorRegistry = monitorRegistry;
        this.timer = timer;
        monitorRegistry.register(this.timer);
        Monitors.registerObject("PeopleController", this);
    }

    @GetMapping("/people")
    @Timed(value = "people.all", longTask = true)
    public List<String> listPeople() throws InterruptedException {
        long start = System.nanoTime();
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(500));
        meterRegistry.counter("People.people.all.count").increment();
        long end = System.nanoTime();
        timer.record(end -  start, TimeUnit.NANOSECONDS);
        currentConnections.incrementAndGet();
        return names;
    }

    @GetMapping("/people/counter")
    @Timed(value = "people.all", longTask = true)
    @Monitor(name = "ListPeople", type = COUNTER)
    public Integer peopleCounter() throws InterruptedException {
        long start = System.nanoTime();
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(500));
        meterRegistry.counter("People.people.all.count").increment();
        long end = System.nanoTime();
        timer.record(end -  start, TimeUnit.NANOSECONDS);
        currentConnections.incrementAndGet();
        return totalConnections.incrementAndGet();
    }

    @PostMapping("/people")
    @Timed(value = "people.update", longTask = true)
    public List<String> putPeople() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(1000));
        meterRegistry.counter("People.people.update.count").increment();
        return names;
    }

    @GetMapping("/asset")
    @Timed(value = "people.asset", longTask = true)
    public void test() throws Exception {
        meterRegistry.counter("People.people.asset.count").increment();
        throw new Exception("error!");
    }

    @GetMapping("/property")
    @Timed(value = "people.property", longTask = true)
    public void property(HttpServletResponse response) throws IOException {
        meterRegistry.counter("People.people.property.count").increment();
        response.sendRedirect("/asset");
    }

}
