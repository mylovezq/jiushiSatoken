package com.jiushi.core.common.config.utils.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfigureProperties {

	private String password;

	private String database;

	private int port = 6379;

	private String host;

}
