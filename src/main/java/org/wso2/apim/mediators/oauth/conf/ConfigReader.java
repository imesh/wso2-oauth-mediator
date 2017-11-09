package org.wso2.apim.mediators.oauth.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class ConfigReader {

    private static final Log log = LogFactory.getLog(ConfigReader.class);
    private static final Gson gson = new GsonBuilder().create();

    public static List<OAuthEndpoint> readConfiguration(String confFilePath) throws FileNotFoundException {
        log.debug("Reading oauth mediator configuration...");
        JsonReader reader = new JsonReader(new FileReader(confFilePath));
        OAuthEndpoint[] array = gson.fromJson(reader, OAuthEndpoint[].class);
        return Arrays.asList(array);
    }
}
