package com.pgl.application;

import com.pgl.server.AppServer;
import com.pgl.server.DerbyServer;
import com.pgl.utils.ContextName;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.InputStream;
import java.util.Properties;

@ComponentScan(basePackages = {"com.pgl"})
@EntityScan(basePackages = {"com.pgl.models"})
@EnableJpaRepositories(basePackages = {"com.pgl.repositories"})
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {
    protected static Logger logger = LoggerFactory.getLogger(ServerApplication.class);
    protected static String APPLICATION_PROPERTIES_FILE = "application.properties";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
        application.setBannerMode(Banner.Mode.OFF);

        application.addListeners(new ApplicationListener<ApplicationStartingEvent>() {
            @Override
            public void onApplicationEvent(ApplicationStartingEvent event) {
                startDbServer();
            }
        });
        application.run(args);
    }

    public static void startDbServer() {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = ServerApplication.class.getResourceAsStream("/" + APPLICATION_PROPERTIES_FILE);
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }

        for (Object keyo : properties.keySet()) {
            String key = (String) keyo;
            if (key.startsWith("pgl.") && System.getProperties().containsKey(key)) {
                properties.setProperty(key, System.getProperty(key));
            }

            if (key.startsWith("logging.") && System.getProperties().containsKey(key)) {
                properties.setProperty(key, System.getProperty(key));
            }
        }

        String host = properties.getProperty("pgl.db.server.host");
        if (StringUtils.isBlank(host)) {
            host = "127.0.0.1";
        }

        String dbHome = properties.getProperty("pgl.db.server.home");
        if (StringUtils.isBlank(dbHome)) {
            dbHome = FilenameUtils.concat(System.getProperty("user.dir"), FilenameUtils.concat("database/pgl_db", ""));
        } else {
            if(dbHome.startsWith("~/")){
                dbHome = dbHome.replace("~", System.getProperty("user.dir"));
            }else
            if(dbHome.startsWith("user.dir/")){
                dbHome = dbHome.replace("user.dir", System.getProperty("user.dir"));
            }else
            if(dbHome.startsWith("${user.dir}/")){
                dbHome = dbHome.replace("${user.dir}", System.getProperty("user.dir"));
            }
        }

        String portString = properties.getProperty("pgl.db.server.port");
        int port = 60601;
        if (!StringUtils.isBlank(portString)) {
            try {
                port = Integer.valueOf(portString.trim());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

        try {
            System.setProperty("pgl.db.server.port", port+"");
            System.setProperty("pgl.db.server.host", host+"");
            System.setProperty("pgl.db.server.home", dbHome);

            AppServer server = new DerbyServer();
            server.launch();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
