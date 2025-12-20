package com.kdk.app.component.redis.queue;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
@Slf4j
public class RedisSubscribeComponent implements MessageListener {

	private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String publishMessage = redisTemplate
				.getStringSerializer().deserialize(message.getBody());

		try {
			MessageVo messageVo = objectMapper.readValue(publishMessage, MessageVo.class);

			log.info("[Redis Subscribe Message] : {}", messageVo.toString());

		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}

}
