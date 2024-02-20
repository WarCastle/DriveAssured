package com.castle.drive.config;

import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.config.properties.OpenApiProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author YuLong
 * @Date 2024/2/19 11:35
 * @Classname OpenApiConfig
 * @Description openApi配置
 */
@Configuration
public class OpenApiConfig {

    private static final String TOKEN_NAME = LoginConstant.TOKEN_NAME;

    private static final String AUTH_PACKAGE = "com.castle.drive.auth";
    private static final String SYSTEM_PACKAGE = "com.castle.drive.system";
    private static final String USER_PACKAGE = "com.castle.drive.user";
    private static final String COMMON_PACKAGE = "com.castle.drive.common";
    private static final String GENERATOR_PACKAGE = "com.castle.drive.generator";

    @Resource
    private OpenApiProperties openApiProperties;

    /**
     * token请求头参数
     */
    private final Parameter tokenParameter = new HeaderParameter().name(TOKEN_NAME).required(false).schema(new StringSchema()._default("").name(TOKEN_NAME));

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(openApiProperties.getTitle())
                        .description(openApiProperties.getDescription())
                        .termsOfService(openApiProperties.getTermsOfService())
                        .contact(new Contact().name(openApiProperties.getContactName()).url(openApiProperties.getContactUrl()).email(openApiProperties.getContactEmail()))
                        .version(openApiProperties.getVersion()))
                .externalDocs(new ExternalDocumentation().description(openApiProperties.getExternalDescription()));
    }

    @Bean
    public GroupedOpenApi authApi() {
        String[] packagedToMatch = {AUTH_PACKAGE};
        return api("2.登录授权接口文档", packagedToMatch);
    }

    @Bean
    public GroupedOpenApi adminApi() {
        String[] packagedToMatch = {SYSTEM_PACKAGE};
        return api("3.系统管理接口文档", packagedToMatch);
    }

    @Bean
    public GroupedOpenApi userApi() {
        String[] packagedToMatch = {USER_PACKAGE};
        return api("4.App用户模块接口文档", packagedToMatch);
    }

    @Bean
    public GroupedOpenApi commonApi() {
        String[] packagedToMatch = {COMMON_PACKAGE};
        return api("5.公共服务接口文档", packagedToMatch);
    }

    @Bean
    public GroupedOpenApi generatorApi() {
        String[] packagedToMatch = {GENERATOR_PACKAGE};
        return api("6.生成代码接口文档", packagedToMatch);
    }

    /**
     * 除了上面的接口之外，其它的接口都在项目接口文档中
     * 请根据实际情况进行自定义
     * @return
     */
    @Bean
    public GroupedOpenApi projectApi() {
        return GroupedOpenApi.builder()
                .group("1.项目接口文档")
                .addOperationCustomizer(getOperationCustomizer())
                .pathsToMatch("/**")
                .packagesToExclude(AUTH_PACKAGE, SYSTEM_PACKAGE, USER_PACKAGE, COMMON_PACKAGE, GENERATOR_PACKAGE)
                .build();
    }

    /**
     * 配置接口
     * @param group
     * @param packagedToMatch
     * @return
     */
    private GroupedOpenApi api(String group, String[] packagedToMatch) {
        return GroupedOpenApi.builder()
                .group(group)
                .addOperationCustomizer(getOperationCustomizer())
                .pathsToMatch("/**")
                .packagesToScan(packagedToMatch).build();
    }

    /**
     * 配置自定义请求头
     * @return
     */
    public OperationCustomizer getOperationCustomizer() {
        return (operation, handlerMethod) -> {
            operation.addParametersItem(tokenParameter);
            return operation;
        };
    }

}
