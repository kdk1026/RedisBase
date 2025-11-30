package com.kdk.app.component.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 6. 11. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
@Component
public class RedisComponent {

	private final RedisTemplate<String, Object> redisTemplate;

	public RedisComponent(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setValueWithTimeout(String key, String value, long timeout, TimeUnit unit) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, unit);
    }

    public Object getValue(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void delete(String key) {
    	redisTemplate.delete(key);
    }

}
