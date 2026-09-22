package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties properties = new Properties();

    private ConfigReader(){

    }

    static {
        String fileName = "config.properties";

        try(InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)
        ){
            if(inputStream == null){
                throw new IllegalArgumentException("Could not find "+ fileName + " in the class path resourcess");
            }
            properties.load(inputStream);
        }catch(IOException e){
            throw new RuntimeException("unable to load properties configuration file : " + fileName
            , e);
        }


    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);


        // Safely validate without throwing NullPointerExceptions
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Property '" + key + "' was not found or is empty in config.properties");
        }


        return value.trim();
    }



}
