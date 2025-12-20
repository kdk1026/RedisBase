package com.kdk.app.test.service.impl;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdk.app.component.redis.queue.MessageVo;
import com.kdk.app.component.redis.queue.RedisPublisherComponent;
import com.kdk.app.component.redis.queue.RedisSubscribeComponent;
import com.kdk.app.test.service.RedisPubService;

import lombok.RequiredArgsConstructor;

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
@Service
@RequiredArgsConstructor
public class RedisPubServiceImpl implements RedisPubService {

	private final RedisMessageListenerContainer redisMessageListenerContainer;

    private final RedisPublisherComponent redisPublisherComponent;
    private final RedisSubscribeComponent redisSubscribeComponent;

    private final ObjectMapper objectMapper;

    @Override
    public void publisherMessage(String channel, MessageVo messageVo) throws JsonProcessingException {
    	redisMessageListenerContainer.addMessageListener(redisSubscribeComponent, new ChannelTopic(channel));

    	String message = objectMapper.writeValueAsString(messageVo);

    	redisPublisherComponent.publish(new ChannelTopic(channel), message);
    }

	@Override
	public void cancelSubChannel(String channel) {
		redisMessageListenerContainer.removeMessageListener(redisSubscribeComponent, new ChannelTopic(channel));
	}

	@Override
	public void cancelSubChannel() {
		redisMessageListenerContainer.removeMessageListener(redisSubscribeComponent);
	}

}
