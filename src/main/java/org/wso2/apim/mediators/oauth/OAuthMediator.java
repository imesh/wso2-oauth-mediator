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
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.wso2.apim.mediators.oauth.client.TokenCache;
import org.wso2.apim.mediators.oauth.client.TokenGeneratorScheduledExecutor;

import java.util.Map;

/**
 * OAuth mediator for generating OAuth tokens for invoking service endpoints secured with OAuth2.
 */
public class OAuthMediator extends AbstractMediator {

    private static final Log log = LogFactory.getLog(OAuthMediator.class);

    static {
        // Initialize oauth client token generator
        new TokenGeneratorScheduledExecutor().init();
    }

    @Override
    public boolean mediate(MessageContext messageContext) {
        log.debug("---> OAuth mediator invoked");
        String accessToken = TokenCache.getInstance().getTokenMap().get("AccessToken");
        Map<String,Object> transportHeaders = (Map<String, Object>) ((Axis2MessageContext)messageContext)
                .getAxis2MessageContext().getProperty("TRANSPORT_HEADERS");
        transportHeaders.put("Authorization", "Bearer " + accessToken);
        log.debug("---> Access token set: " + accessToken);
        return true;
    }
}
