# WSO2 OAuth Mediator

WSO2 OAuth mediator can be used for generating OAuth2 tokens for talking to service endpoints 
secured with OAuth2 protocol in WSO2 ESB/Integrator and API Manager. Currently, it only supports 
password grant type and it can be extended to incorporate other grant types as required.

## Getting Started

1. Clone this project:

   ````
   git clone https://github.com/imesh/wso2-oauth-meditor
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
   
4. Use the following sequence to engage the OAuth mediator in the in message flow:

   ````xml
   <sequence xmlns="http://ws.apache.org/ns/synapse" name="oauth-sequence2">
      <class name = "org.wso2.apim.mediators.oauth.OAuthMediator"></class>
   </sequence>
   ````
   
# License

This source code is licensed under Apache 2.0.