package com.kdk.config.app.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2024. 6. 10. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	@Value("${spring.data.redis.password}")
	private String redisPassword;

	@Bean
	RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		config.setPassword(redisPassword);
		return new LettuceConnectionFactory(config);
	}

	/**
	 * <pre>
	 * 직렬화 참고
	 * https://velog.io/@choidongkuen/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-Redis-%EC%A7%81%EB%A0%AC%ED%99%94-%EB%B0%A9%EB%B2%95%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C
	 * </pre>
	 * @return
	 */
	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setEnableTransactionSupport(true);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	@Bean
	ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

	@Bean
	RedisSerializer<Object> defaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

	/**
	 * Redis pub/sub 메시지 처리 Listener
	 * @return
	 */
	@Bean
    RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        return container;
    }

	// Redis sub 메시지만 처리하는 Listener
	/*
	@Bean
	RedisMessageListenerContainer redisMessageListenerContainer(RedisSubscribeComponent redisSubscribeComponent) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());

		// "order."으로 시작하는 모든 토픽 구독 (예: order.created, order.cancelled)
		// container.addMessageListener(redisSubscribeComponent, new PatternTopic("order.*"));

		// 개별 토픽 리스트
		List<Topic> topics = Arrays.asList(
		    new ChannelTopic("topic1"),
		    new ChannelTopic("topic2"),
		    new ChannelTopic("topic3")
		);
		container.addMessageListener(redisSubscribeComponent, topics);

		return container;
	}
	*/

}
