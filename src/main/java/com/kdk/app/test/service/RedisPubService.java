package com.kdk.app.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kdk.app.component.redis.queue.MessageVo;

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
public interface RedisPubService {

	/**
	 * Channel 구독 및 Message 전송
	 * @param channel
	 * @param messageVo
	 * @throws JsonProcessingException
	 */
	public void publisherMessage(String channel, MessageVo messageVo) throws JsonProcessingException;

	/**
	 * Channel 구독 취소
	 * @param channel
	 */
	public void cancelSubChannel(String channel);

	/**
	 * Channel 구독 취소
	 * @param channel
	 */
	public void cancelSubChannel();

}
