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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.apim.mediators.oauth.client.OAuthClient;
import org.wso2.apim.mediators.oauth.client.domain.TokenResponse;
import org.wso2.apim.mediators.oauth.conf.OAuthEndpoint;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * OAuth token generator scheduled executor.
 */
public class TokenGeneratorScheduledExecutor {

    private static final Log log = LogFactory.getLog(TokenGeneratorScheduledExecutor.class);

    private ScheduledExecutorService executorService;

    public TokenGeneratorScheduledExecutor() {
        this.executorService = new ScheduledThreadPoolExecutor(5);
    }

    /**
     * Initialize oauth client scheduled executor
     */
    public void schedule(OAuthEndpoint oAuthEndpoint) {
        try {
            log.info("Scheduling token generator for token endpoint " + getEndpointId(oAuthEndpoint));

            executorService.scheduleAtFixedRate(()->{
                try {
                    log.info("Generating access token: " + getEndpointId(oAuthEndpoint));

                    TokenResponse tokenResponse = OAuthClient.generateToken(oAuthEndpoint.getTokenApiUrl(),
                            oAuthEndpoint.getApiKey(), oAuthEndpoint.getApiSecret(), oAuthEndpoint.getUsername(),
                            oAuthEndpoint.getPassword(), oAuthEndpoint.getGrantType());
                    log.info("Access token generated: " + getEndpointId(oAuthEndpoint)
                            + " [access-token] " + tokenResponse.getAccessToken());
                    TokenCache.getInstance().getTokenMap().put(oAuthEndpoint.getId(), tokenResponse.getAccessToken());
                } catch (Exception e) {
                    log.error("Could not generated access token " + getEndpointId(oAuthEndpoint), e);
                }
            }, 0, oAuthEndpoint.getTokenRefreshInterval(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private String getEndpointId(OAuthEndpoint oAuthEndpoint) {
        return "[id] " + oAuthEndpoint.getId() + " [url] " + oAuthEndpoint.getTokenApiUrl();
    }
}
