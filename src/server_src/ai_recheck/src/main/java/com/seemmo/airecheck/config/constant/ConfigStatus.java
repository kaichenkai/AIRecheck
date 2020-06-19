package com.seemmo.airecheck.config.constant;

/** 
 * @Description 字段的状态，标记一个字段在当前版本是否已被删除，默认是NORMAL.
 * @author ming.xin
 * @date 2016年4月29日 下午2:44:41  
 */
public enum ConfigStatus {
	/** 正常的*/
	NORMAL(1),
	/** 过时的*/
	DEPRECATED(2),
	/** 已删除*/
	DELETEED(3);
	private int value = 0;

	private ConfigStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
