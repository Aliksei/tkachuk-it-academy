package by.itacademy.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtil {

    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            properties = new Properties();
            properties.load(Files.newBufferedReader(Paths.get("C:\\Users\\Aliksei\\IdeaProjects\\jd2-project\\database\\src\\main\\resources\\app.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
