package org.agyei.richard.camelspringtest;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = org.agyei.richard.camelspringtest.CamelSpringTestApplication.class)
@EnableRouteCoverage
@DisableJmx
public class PostRequestRouteTest {
    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "mock:log:foo")
    private MockEndpoint mock;

    @EndpointInject(uri = "mock:mocked-endpoint")
    private MockEndpoint mockRequestEndpoint;

    @Value("${json.placeholder.base.url}")
    private String jsonPlaceHolderString;

    @Before
    public void mockEndpoints() throws Exception {
        AdviceWithRouteBuilder mockSolr = new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                weaveAddLast().to("mock:log:foo");
                interceptSendToEndpoint(String.format("https4:%s/posts*", jsonPlaceHolderString))
                        .when(header(Exchange.HTTP_PATH).isEqualTo("1"))
                        .process(exchange -> System.out.println("Intercepted Route endpoint"))
                        .skipSendToOriginalEndpoint()
                        .to("mock:mocked-endpoint");
            }
        };

        mockRequestEndpoint.whenAnyExchangeReceived(exchange -> {
            exchange.getIn().setBody("Hello, World.");
        });

        camelContext.getRouteDefinition(PostRequestRoute.ROUTE_ID).adviceWith(camelContext, mockSolr);
    }

    @Test
    public void shouldSayFoo() throws Exception {
        mock.expectedBodiesReceived("Hello, World.");

        // we expect that one or more messages is automatic done by the Camel
        // route as it uses a timer to trigger
        NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(1).create();

        assertTrue(notify.matches(10, TimeUnit.SECONDS));
//
        mock.assertIsSatisfied();
    }

}