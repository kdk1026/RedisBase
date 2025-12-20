package com.kdk.app.component.redis.queue;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 12. 20. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Component
public class RedisPublisherComponent {

	private final RedisTemplate<String, Object> redisTemplate;

	public RedisPublisherComponent(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void publish(ChannelTopic topic, String data) {
		redisTemplate.convertAndSend(topic.getTopic(), data);
    }

}
