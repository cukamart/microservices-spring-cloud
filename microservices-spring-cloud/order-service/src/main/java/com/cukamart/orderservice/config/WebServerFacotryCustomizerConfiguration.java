package com.cukamart.orderservice.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

// https://github.com/spring-cloud/spring-cloud-netflix/issues/843
// If I run multiple instances of the same service with random port eureka will discover only 1 instance
// because eureka will register as instanceId:port - port will be 0
// solution is either update port like this (can cause issues in production / testing with multiple threads) and race condition...
// or change instanceID name to always be unique :-)
@Configuration
public class WebServerFacotryCustomizerConfiguration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        try (ServerSocket socket = new ServerSocket(0);) {
            socket.getLocalPort();
            factory.setPort(socket.getLocalPort());
            System.getProperties().put("server.port", socket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
