package com.xuyao.integration.listener;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WebServerListener implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        //获取项目端口
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
    }

    public int getServerPort() {
        return serverPort;
    }
}
