package com.kdk.app.component.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 11. 30. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Component
public class RedisSessionComponent {

	private final  StringRedisTemplate stringRedisTemplate;

	private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

	public RedisSessionComponent(StringRedisTemplate stringRedisTemplate, FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.sessionRepository = sessionRepository;
	}

	private static final String SESSION_PREFIX = "custom:sessions:";

	public void registerSession(String userId, String sessionId) {
		this.invalidateUserSession(userId);

		String key = SESSION_PREFIX + userId;

		String existingSession = this.getSession(key);

		if ( existingSession != null && !existingSession.equals(sessionId) ) {
			// 기존 세션 무효화
			sessionRepository.deleteById(sessionId);

			// 사용자 맵핑만 지우기
			stringRedisTemplate.delete(key);
		}

		stringRedisTemplate.opsForValue().set(key, sessionId);
	}

	private String getSession(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 사용자 강제 로그아웃
	 * @param userId
	 */
	private void invalidateUserSession(String userId) {
        String key = SESSION_PREFIX + userId;
        String sessionId = stringRedisTemplate.opsForValue().get(key);

        if (sessionId != null) {
            sessionRepository.deleteById(sessionId);
            stringRedisTemplate.delete(key);
        }
    }

}
