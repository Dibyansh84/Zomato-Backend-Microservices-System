package com.api.gateway.filter;
import com.api.gateway.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements GlobalFilter
{
    //All incoming HTTP requests pass through GlobalFilter before reaching microservices.

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        // Extract request path from incoming HTTP request
        String path = exchange.getRequest()
                .getURI()
                .getPath();

        /*Allow public APIs without JWT authentication
        * Login and Register APIs are accessible publicly */
        if (path.contains("/api/auth/login")
                || path.contains("/api/auth/register"))
        {
            //Continue request processing without validation
            return chain.filter(exchange);
        }

        //Extract authorization header from the request
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        /* Validate authorization header
        * Header must not be null
        * Header must start with "Bearer" */

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            //Return 401 unauthorized response
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        //Extract JWT token by removing "Bearer " prefix
        String token = authHeader.substring(7);

        /* Validate JWT token using JwtUtil
        * If token is invalid or expired return unauthorized */

        if(!jwtUtil.validateToken(token))
        {
            //Return 401 Unauthorized response
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        //Continue request flow if token is valid
        return chain.filter(exchange);

    }
}
