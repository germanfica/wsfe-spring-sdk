package com.germanfica.wsfe.net;

import jakarta.xml.soap.SOAPMessage;

public class DefaultSoapRequestHandler implements SoapRequestHandler {

    private final BaseApiRequest baseApiRequest;

    public DefaultSoapRequestHandler(BaseApiRequest baseApiRequest) {
        this.baseApiRequest = baseApiRequest;
    }

    @Override
    public <T> T handleRequest(ApiRequest apiRequest, Class<T> responseType) throws Exception {
        SOAPMessage soapMessage = baseApiRequest.createSoapMessage(
                apiRequest.getSoapAction(),
                apiRequest.getPayload(),
                apiRequest.getNamespace(),
                apiRequest.getOperation(),
                apiRequest.getBodyElements()
        );

        SOAPMessage soapResponse = baseApiRequest.sendSoapRequest(soapMessage, apiRequest.getEndpoint());



        String xmlResponse = baseApiRequest.extractResponse(soapResponse);

        System.out.println(xmlResponse);

        return baseApiRequest.mapToDto(xmlResponse, responseType);
    }
}
