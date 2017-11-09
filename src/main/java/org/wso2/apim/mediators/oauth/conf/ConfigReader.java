/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
