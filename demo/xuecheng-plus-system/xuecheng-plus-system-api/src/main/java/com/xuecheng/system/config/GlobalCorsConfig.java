package com.xuecheng.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 11:27
 */

/**
 * 跨域请求配置
 * * 这段Java代码定义了一个`corsFilter`方法，用于创建并配置一个CORS过滤器，以处理跨域请求。具体功能如下：
 * 1. **创建CorsConfiguration对象**：配置跨域请求的基本设置。
 * 2. **允许所有来源**：通过`config.addAllowedOrigin("*")`允许所有域名进行跨域调用。
 * 3. **允许携带Cookie**：通过`config.setAllowCredentials(true)`允许跨域请求携带Cookie。
 * 4. **允许所有头部信息**：通过`config.addAllowedHeader("*")`允许所有头部信息。
 * 5. **允许所有HTTP方法**：通过`config.addAllowedMethod("*")`允许所有HTTP方法（GET, POST, PUT, DELETE等）。
 * 6. **创建UrlBasedCorsConfigurationSource对象**：用于基于URL路径配置跨域请求。
 * 7. **注册跨域配置**：将上述配置应用到所有URL路径上。
 * 8. **返回CorsFilter实例**：返回一个CORS过滤器实例，用于实际的跨域请求处理。
 */
@Configuration
public class GlobalCorsConfig {

    /**
     * ### 代码解释
     * <p>
     * <p>
     * <p>
     * ### 控制流图
     * ```mermaid
     * flowchart TD
     * A[开始] --> B[创建CorsConfiguration对象]
     * B --> C[允许所有来源]
     * C --> D[允许携带Cookie]
     * D --> E[允许所有头部信息]
     * E --> F[允许所有HTTP方法]
     * F --> G[创建UrlBasedCorsConfigurationSource对象]
     * G --> H[注册跨域配置]
     * H --> I[返回CorsFilter实例]
     * I --> J[结束]
     * ```
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {

        // 创建CorsConfiguration对象，用于配置跨域请求
        CorsConfiguration config = new CorsConfiguration();
        //允许白名单域名进行跨域调用
        config.addAllowedOrigin("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");

        // 创建UrlBasedCorsConfigurationSource对象，用于基于URL路径配置跨域请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 将跨域配置应用到所有的URL路径上
        source.registerCorsConfiguration("/**", config);

        // 返回CorsFilter实例，用于实际的跨域请求处理
        return new CorsFilter(source);

    }
}
