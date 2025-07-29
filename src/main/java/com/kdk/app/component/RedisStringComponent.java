package com.kdk.app.component;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 7. 29. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Component
public class RedisStringComponent {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void setValueWithTimeout(String key, String value, long timeout, TimeUnit unit) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, unit);
    }

    public String getValue(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void delete(String key) {
    	stringRedisTemplate.delete(key);
    }

}
