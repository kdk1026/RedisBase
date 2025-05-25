package com.kdk.unitTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2024. 6. 11. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@SpringBootTest
class RedisTemplateTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	@DisplayName("string 테스트")
	void testString() {
		String key = "stringKey";
		redisTemplate.delete(key);

		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

		valueOperations.set(key, "hello");

		String value = (String) valueOperations.get(key);

		assertThat(value).isEqualTo("hello");

		// <redis-cli> get stringKey
	}

	@Test
	@DisplayName("list 테스트")
	void testList() {
		String key = "listKey";
		redisTemplate.delete(key);

		ListOperations<String, Object> valueOperations = redisTemplate.opsForList();

		List<Object> list = new ArrayList<>();
		list.add("user1");
		list.add("user2");
		list.add("user3");
		list.add("user4");

		valueOperations.rightPush(key, "user2");
		valueOperations.rightPush(key, "user3");
		valueOperations.rightPush(key, "user4");
		valueOperations.leftPush(key, "user1");

		List<Object> range = valueOperations.range(key, 0, 3);

		assertThat(range).usingRecursiveComparison().isEqualTo(list);

		// <redis-cli> lrange listKey 0 3
	}

	@Test
	@DisplayName("set 테스트")
	void testSet() {
		String key = "setKey";
		redisTemplate.delete(key);

		SetOperations<String, Object> valueOperations = redisTemplate.opsForSet();

		Set<Object> set = new HashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");

		valueOperations.add(key, "a");
		valueOperations.add(key, "a");
		valueOperations.add(key, "b");
		valueOperations.add(key, "b");
		valueOperations.add(key, "c");
		valueOperations.add(key, "c");

		Set<Object> members = valueOperations.members(key);

		assertThat(members).usingRecursiveComparison().isEqualTo(set);

		// <redis-cli> smembers setKey
	}

	@Test
	@DisplayName("sorted set 테스트")
	void testSortedSet() {
		String key = "userRank";
		redisTemplate.delete(key);

		ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();

		Set<Object> set = new HashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		set.add("d");
		set.add("e");
		set.add("f");

		zSet.add(key, "d", 4);
		zSet.add(key, "c", 3);
		zSet.add(key, "a", 1);
		zSet.add(key, "b", 2);
		zSet.add(key, "f", 6);
		zSet.add(key, "e", 5);

		Set<Object> range = zSet.range(key, 0, 5);

		assertThat(range.size()).isEqualTo(6);
		assertThat(range).usingRecursiveComparison().isEqualTo(set);

		// <redis-cli> zrange userRank 0 5
	}

	@Test
	@DisplayName("hash 테스트")
	void testHash() {
		String key = "gildong";
		redisTemplate.delete(key);

		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();

		hash.put(key, "level", "1");
		hash.put(key, "age", "18");
		hash.put(key, "job", "thief");

		Map<Object, Object> map = hash.entries(key);

		assertThat(map.get("level")).isEqualTo("1");
		assertThat(map.get("age")).isEqualTo("18");
		assertThat(map.get("job")).isEqualTo("thief");

		// <redis-cli> hgetall gildong
	}
}
