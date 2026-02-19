package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	// to read the Properties from propertyFile we have to use the Propeties.class
	// static block ill be loaded at the time of class loading and before the entry
	// point of the main method static class will be called
	private static Properties properties = new Properties();

	static {
		// reflex --read the properties form the class file
		InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties");
		if (input != null) {
			try {
				properties.load(input);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	// to get the value from the properties file
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	//this is used set the key or value at the time running for example if want generate some token or api key at the run time 
	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}

}
