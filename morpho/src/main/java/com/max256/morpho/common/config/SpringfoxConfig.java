package com.max256.morpho.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springfox基于swagger2,兼容老版本 与springMVC整合的显示http接口的功能 配置类
 * 
 * @author fbf
 * @since 2016年7月27日 下午4:08:17
 * 
 */
@Configuration
@EnableWebMvc
// 非springboot框架需要引入注解@EnableWebMvc
@EnableSwagger2
// 引入了一个注解@EnableSwagger2来启动swagger注解。（启动该注解使得用在controller中的swagger注解生效，覆盖的范围由@ComponentScan的配置来指定，这里默认指定为根路径"com.max256.morpho"下的所有controller）
@ComponentScan(basePackages = { "com.max256" })
public class SpringfoxConfig {

	/*
	 * controller控制器注解使用说明
	 * 
	 * @Api：用在类上，说明该类的作用
	 * 
	 * @ApiOperation：用在方法上，说明方法的作用
	 * 
	 * @ApiImplicitParams：用在方法上包含一组参数说明
	 * 
	 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
	 * paramType：参数放在哪个地方 header-->请求参数的获取：@RequestHeader
	 * query-->请求参数的获取：@RequestParam path（用于restful接口）-->请求参数的获取：@PathVariable
	 * body（不常用） form（不常用） name：参数名 dataType：参数类型 required：参数是否必须传 value：参数的意思
	 * defaultValue：参数的默认值
	 * 
	 * @ApiResponses：用于表示一组响应
	 * 
	 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 code：数字，例如400
	 * message：信息，例如"请求参数没填好" response：抛出异常的类
	 * 
	 * @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@
	 * ApiImplicitParam注解进行描述的时候）
	 * 
	 * @ApiModelProperty：描述一个model的属性
	 */

	/**
	 * Every Docket bean is picked up by the swagger-mvc framework - allowing
	 * for multiple swagger groups i.e. same code base multiple swagger resource
	 * listings.
	 */
	@Bean
	public Docket customDocket() {
		return new Docket(DocumentationType.SWAGGER_2);
		// 此处还有很多其他选项，包括路径过滤，api说明等

	}

}