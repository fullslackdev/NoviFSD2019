package dev.fullslack.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseProperties.class.getName());

    protected static Properties getConnectionData() {
        Properties props = new Properties();

        String fileName = "db.properties";

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            props.load(in);
        } catch (IOException ex) {
            LOGGER.error("Error Message Logged !!!",ex.getMessage(),ex);
        }

        return props;
    }
}
