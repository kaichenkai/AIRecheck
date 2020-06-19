package com.seemmo.airecheck.config.constant;
/**
 * @Description 配置级别，与数据库配置表中的level字段对应，标记一个配置更改后，是否可被修改；
 * 以及修改后是否需要重启，又或者仅需同时刷新服务缓存。
 * @author ming.xin
 * @date 2016年4月29日 下午3:06:05
 */
public enum Level {
	/**未定义*/
	NONE(0), 
	/**不允许修改*/
	STATIC(1),
	/**需要重启服务*/
	RESTART(2),
	/**需要刷新缓存*/
	RELOAD(3);
	private int value = 0;
    private Level(int value) {
        this.value = value;
    }
	public int getValue() {
		return value;
	}
}