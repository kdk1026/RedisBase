package com.kdk.app.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kdk.app.component.redis.queue.MessageVo;
import com.kdk.app.test.service.RedisPubService;

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
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/redis/pub")
public class RedisPubController {

	private final RedisPubService redisPubService;

	@PostMapping("/send-message/{channel}")
	public ResponseEntity<String> sendMessage(@PathVariable String channel, @RequestBody MessageVo vo) throws JsonProcessingException {
		log.info("channel : {}, vo : {}", channel, vo);

		redisPubService.publisherMessage(channel, vo);

		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

	@PostMapping("/cancel-sub-channel/{channel}")
	public ResponseEntity<String> cancelSubChannel(@PathVariable String channel) {

		redisPubService.cancelSubChannel(channel);

		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

}
