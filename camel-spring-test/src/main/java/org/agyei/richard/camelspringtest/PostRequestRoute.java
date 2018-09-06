package org.agyei.richard.camelspringtest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostRequestRoute extends RouteBuilder {
    public static final String ROUTE_ID = "PostRequestRoute";
    private String routeEndpoint = "jsonplaceholder.typicode.com";

    private String  camelHttpParams;

    public PostRequestRoute(@Value("${camel.http.params}") String camelHttpParams) {
        this.camelHttpParams = camelHttpParams;
    }

    @Override
    public void configure() throws Exception {
        from("timer:p-requests?period={{timer.period}}")
                .routeId(ROUTE_ID)
                .setProperty("ROUTE_ENDPOINT_URI", constant(routeEndpoint))
                .setHeader(Exchange.HTTP_PATH, constant("1"))

                //  .setHeader(Exchange.HTTP_QUERY, simple("key=${exchangeProperty.KEY}"))
//                .toD("https4:${exchangeProperty.ROUTE_ENDPOINT_URI}/posts/1")
                .to(String.format("https4:%s/posts?%s", routeEndpoint, camelHttpParams))
                .convertBodyTo(String.class)
                .to("log:org.agyei.richard.camelspringtest.PostRequestRoute?level=TRACE&showBody=true&showHeaders=true")
                .removeHeader(Exchange.HTTP_PATH)
                .log("${body}");
    }
}
