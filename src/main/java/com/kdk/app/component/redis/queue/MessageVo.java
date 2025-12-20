package com.kdk.app.component.redis.queue;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String content;

}
