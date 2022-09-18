package com.test.project.utilites;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

        private static Properties properties = new Properties();

        public PropertyManager() {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(
                        System.getProperty("user.dir") + "//src/test/resources/configs/config.properties");
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            try {
                properties.load(fileInputStream);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        public static String getProperty(String property) {
            return properties.getProperty(property);
        }

}

