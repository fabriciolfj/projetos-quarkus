package com.github.fabriciolfj.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ContextListener implements ServletContextListener {

    private ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        context = servletContextEvent.getServletContext();
        log.info("Web application started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        context = servletContextEvent.getServletContext();
        log.info("Web application stopped!");
    }
}
