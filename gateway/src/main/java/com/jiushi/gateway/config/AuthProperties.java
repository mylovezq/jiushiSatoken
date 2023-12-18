
package com.jiushi.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "gatewayurl" )
@Component
public class AuthProperties {


	private String name;
	/**
	 * 放行API集合
	 */
	private  List<String> skipUrl = new ArrayList<>();


}
