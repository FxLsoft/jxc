package com.jeeplus.common.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.jeeplus.common.websocket.service.onchat.LayIMSocketHandler;
import com.jeeplus.common.websocket.service.onchat.LayIMSocketHandshakeInterceptor;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandshakeInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	//注册layIM socket服务
    	//registry.addHandler(layImSocketHandler(),"/layIMSocketServer").addInterceptors(new LayIMSocketHandshakeInterceptor());
    	//registry.addHandler(layImSocketHandler(), "/sockjs/layIMSocketServer").addInterceptors(new LayIMSocketHandshakeInterceptor()).withSockJS();
        
        //注册 系统通知socket服务
        registry.addHandler(systemInfoSocketHandler(),"/systemInfoSocketServer").addInterceptors(new SystemInfoSocketHandshakeInterceptor());
        registry.addHandler(systemInfoSocketHandler(), "/sockjs/systemInfoSocketServer").addInterceptors(new SystemInfoSocketHandshakeInterceptor()).withSockJS();
    }

    @Bean
    public WebSocketHandler layImSocketHandler(){
        return new LayIMSocketHandler();
    }

    @Bean
    public WebSocketHandler systemInfoSocketHandler(){
        return new SystemInfoSocketHandler();
    }
}