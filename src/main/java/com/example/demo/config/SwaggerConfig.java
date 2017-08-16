package com.example.demo.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Alex on 16.08.2017.
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {

    private String clientId = "client-with-secret";
    private String clientSecret = "secret";

    public static final String securitySchemaOAuth2 = "oauth2";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc = "accessEverything";

    @Bean
    public Docket api() {

        List<ResponseMessage> list = new java.util.ArrayList<ResponseMessage>();
        list.add(new ResponseMessageBuilder()
                .code(500)
                .message("500 message")
                .responseModel(new ModelRef("JSONResult«string»"))
                .build());
        list.add(new ResponseMessageBuilder()
                .code(401)
                .message("Unauthorized")
                .responseModel(new ModelRef("JSONResult«string»"))
                .build());


        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/chatApi.*"))
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()));
    }


    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = newArrayList();
        authorizationScopeList.add(new AuthorizationScope("global", "access all"));

        List<GrantType> grantTypes = newArrayList();
        final TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("http://localhost:8080/oauth/token", clientId, clientSecret);
        final TokenEndpoint tokenEndpoint = new TokenEndpoint("http://localhost8080/oauth/token", "access_token");
        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);

        grantTypes.add(authorizationCodeGrant);

        OAuth oAuth = new OAuth("oauth", authorizationScopeList, grantTypes);

        return oAuth;
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**")).build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope authorizationScope =
                new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections
                .singletonList(new SecurityReference(securitySchemaOAuth2, authorizationScopes));
    }


/*    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Online Store",
                "1.0",
                "Terms of service",
                new Contact("John Thompson", "https://springframework.guru/about/", "john@springfrmework.guru"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }*/

//    @Bean
//    public Docket productApi() {
////        List<SecurityScheme> lista = new ArrayList<>();
////
////        lista.add(oauth());
////
////        List<SecurityContext> listaaa = new ArrayList<>();
////
////        listaaa.add(securityContext());
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(regex("/chatApi.*"))
//                .build()
//                .apiInfo(metaData())
//                .securitySchemes(newArrayList(apiKey()))
//                .securityContexts(newArrayList(securityContext()));
//    }
//
//    private ApiInfo metaData() {
//        ApiInfo apiInfo = new ApiInfo(
//                "Spring Boot REST API",
//                "Spring Boot REST API for Online Store",
//                "1.0",
//                "Terms of service",
//                new Contact("John Thompson", "https://springframework.guru/about/", "john@springfrmework.guru"),
//                "Apache License Version 2.0",
//                "https://www.apache.org/licenses/LICENSE-2.0");
//        return apiInfo;
//    }
//
//    @Autowired
//    private TypeResolver typeResolver;
//
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("/anyPath.*"))
//                .build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return newArrayList(
//                new SecurityReference("mykey", authorizationScopes));
//    }
//
//    @Bean
//    SecurityConfiguration security() {
//        return new SecurityConfiguration(
//                "client-with-secret",
//                "secret",
//                "oauth2-resource",
//                "test-app",
//                "apiKey",
//                ApiKeyVehicle.HEADER,
//                "api_key",
//                "," /*scope separator*/);
//    }
//
//    @Bean
//    UiConfiguration uiConfig() {
//        return new UiConfiguration(
//                "validatorUrl",// url
//                "none",       // docExpansion          => none | list
//                "alpha",      // apiSorter             => alpha
//                "schema",     // defaultModelRendering => schema
//                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
//                false,        // enableJsonEditor      => true | false
//                true,         // showRequestHeaders    => true | false
//                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
//    }

//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//
//    }
//
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("/*"))
//                .build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//
//        List<SecurityReference> list = new ArrayList<>();
//
//        list.add(new SecurityReference("id_token", authorizationScopes));
//        return list;
//    }
//
//
//    @Bean
//    SecurityConfiguration security() {
//        return new SecurityConfiguration(
//                "client-with-secret",
//                "secret",
//                "oauth2-resource",
//                "test-app",
//                "apiKey",
//                ApiKeyVehicle.HEADER,
//                "api_key",
//                "," /*scope separator*/);
//    }
//
//    @Bean
//    SecurityScheme oauth() {
//        return new OAuthBuilder()
//                .name("oauth2")
//                .grantTypes(grantTypes())
//                .build();
//    }
//
//    List<GrantType> grantTypes() {
//        GrantType grantType = new ImplicitGrantBuilder()
//                .loginEndpoint(new LoginEndpoint("https://localhost8080/oauth/authorize"))
//                .build();
//
//        List<GrantType> list = new ArrayList<>();
//        list.add(grantType);
//        return list;
//    }

}
