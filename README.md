# WSO2 OAuth Mediator

WSO2 OAuth mediator can be used for generating OAuth2 tokens for talking to service endpoints 
secured with OAuth2 protocol in WSO2 ESB/Integrator and API Manager. Currently, it only supports 
password grant type and client-credentials grant type and it can be extended to incorporate other grant types as required.

## Getting Started

1. Clone this project:

   ````
   git clone https://github.com/imesh/wso2-oauth-mediator
   cd wso2-oauth-mediator/
   ````

2. Build OAuth Mediator using Maven:

   ````
   mvn clean install
   ````

3. Copy OAuth mediator JAR file to the following folder in the WSO2 server:

   ````
   [WSO2-SERVER-HOME]/repository/components/lib/
   ````
   
4. Create a new configuration file inside the ```[WSO2-SERVER-HOME]/repository/conf/``` folder with the name 
```wso2-oauth-mediator.json``` with the list of OAuth2 token endpoints required:

   ````json
   [
     {
       "id": "EP1",
       "tokenApiUrl": "http://localhost:8280/token",
       "apiKey": "1234567890",
       "apiSecret": "0987654321",
       "username": "admin",
       "password": "admin",
       "grantType": "password",
       "tokenRefreshInterval": 20
     }
   ]
   ````
   Note: If you want to use client-credentials grant type, change grantType to "client-credentials".
   Note that the token refresh interval is defined in seconds.

5. Create a sequence with the following OAuthMediator and the endpoint id provided in the configuration:

   ````xml
   <sequence xmlns="http://ws.apache.org/ns/synapse" name="oauth-sequence">
       <class name = "org.wso2.apim.mediators.oauth.OAuthMediator">
           <property name="endpointId" value="EP1"></property>
       </class>
   </sequence>
   ````
   
6. Now start the WSO2 server and send a request to the API/proxy endpoint and check the behaviour.
    
# License

This source code is licensed under Apache 2.0.