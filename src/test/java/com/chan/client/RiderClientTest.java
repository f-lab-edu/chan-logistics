package com.chan.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import wiremock.com.google.common.net.MediaType;

class RiderClientTest {

    public static void matchDeliveryResponse(WireMockServer mock){
        mock.stubFor(WireMock.post(WireMock.urlMatching("/rider/delivery/match"))
                .willReturn(WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.JSON_UTF_8.type())
                    .withBodyFile("payload/match-delivery-response.json")
                )
        );
    }

}