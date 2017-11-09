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

package org.wso2.apim.mediators.oauth.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.apim.mediators.oauth.client.domain.TokenResponse;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * OAuth token generator scheduled executor.
 */
public class TokenGeneratorScheduledExecutor {

    private static final Log log = LogFactory.getLog(TokenGeneratorScheduledExecutor.class);

    private ScheduledExecutorService executorService;
    private String tokenApiUrl;
    private String apiKey;
    private String apiSecret;
    private String username;
    private String password;
    private int tokenRefreshInterval;

    public TokenGeneratorScheduledExecutor() {
        this.executorService = new ScheduledThreadPoolExecutor(1);
        this.tokenApiUrl = System.getenv("OAUTH_MEDIATOR_TOKEN_API_URL");
        this.apiKey = System.getenv("OAUTH_MEDIATOR_API_KEY");
        this.apiSecret = System.getenv("OAUTH_MEDIATOR_API_SECRET");
        this.username = System.getenv("OAUTH_MEDIATOR_USERNAME");
        this.password = System.getenv("OAUTH_MEDIATOR_PASSWORD");
        this.tokenRefreshInterval = Integer.parseInt(System.getenv("OAUTH_MEDIATOR_TOKEN_REFRESH_INTERVAL"));
    }

    /**
     * Initialize oauth client scheduled executor
     */
    public void init() {
        try {
            log.info("Initializing oauth token generator...");

            executorService.scheduleAtFixedRate(()->{
                try {
                    log.info("Generating access token: [token-endpoint] " + tokenApiUrl);
                    TokenResponse tokenResponse = OAuthClient.generateToken(tokenApiUrl, apiKey, apiSecret,
                            username, password);
                    log.info("Access token: " + tokenResponse.getAccessToken());
                    TokenCache.getInstance().getTokenMap().put("AccessToken", tokenResponse.getAccessToken());
                } catch (Exception e) {
                    log.error(e);
                }
            }, 0, tokenRefreshInterval, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
