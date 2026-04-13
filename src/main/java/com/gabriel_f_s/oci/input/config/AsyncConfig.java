package com.gabriel_f_s.oci.input.config;

import com.gabriel_f_s.oci.input.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);
    private final LoggingService loggingService;

    public AsyncConfig(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (exception, method, params) -> {
           String error = String.format("An error occurred in %s. Message: %s; Cause: %s;", method.getName(), exception.getMessage(), exception.getCause());
           loggingService.updateLogInCaseOfError(error);
           logger.error("{}... Stopping application.", error);
       };
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ETL-Processor-");
        executor.initialize();
        return executor;
    }
}
