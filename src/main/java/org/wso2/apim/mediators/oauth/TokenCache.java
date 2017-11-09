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

package org.wso2.apim.mediators.oauth;

import java.util.HashMap;
import java.util.Map;

/**
 * Token cache implementation.
 */
public class TokenCache {

    private static final TokenCache instance = new TokenCache();

    private final Map<String, String> tokenMap = new HashMap<>();

    /**
     * Private constructor.
     */
    private TokenCache() {

    }

    /**
     * Get token cache instance.
     * @return
     */
    public static TokenCache getInstance() {
        return instance;
    }

    /**
     * Get token map.
     * @return
     */
    public Map<String, String> getTokenMap() {
        return tokenMap;
    }
}
