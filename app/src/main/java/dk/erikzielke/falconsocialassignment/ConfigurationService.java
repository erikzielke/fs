package dk.erikzielke.falconsocialassignment;

import android.content.Context;

import dk.erikzielke.falconsocialassignment.model.Configuration;

public class ConfigurationService {
    public static Configuration instance;

    public static void setConfiguration(Configuration configuration) {
        instance = configuration;
    }

    public static Configuration getConfiguration() {
        return instance;
    }
}
