package com.seemmo.airecheck.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ExceptionInfo {
	/**
	 * 未知错误
	 */
	Unknown(100, "Unknown Exception"),
	/**
	 * 未定义的错误
	 */
	UNDEFINE(101, "undefine system exception"),
	/**
	 * 违法记录id重复
	 */
	INSTESV_REPEATED_RECORD(102,"current record is repeated, recordId: %s"),
	/**
	 * 无效的图片url
	 */
	IMAGE_URL_INVALID(103,"image url is invalid, recordId{%s}, url{%s}"),
	/**
	 * 图片路径没有找到
	 */
	IMAGE_PATH_NOT_FOUND(104, "image path not found, please contact the administrator"),
	/**
	 * 图片保存失败 (先检查磁盘容量)
	 */
	IMAGE_SAVE_ERROR(105, "image saving error, please check disk capacity"),
	/**
	 * 服务内部错误, 请联系管理员
	 */
	SERVER_INTERNAL_ERROR(500, "server internal error, please contact administrator"),
	/**
	 * 数据库操作错误
	 */
	DATABASE_OPERATION_ERROR(510, "database operation error, please contact the administrator"),


	/* ******************** 复核结果查询********************* */
	/**
	 * 查询时间范围超出界限
	 */
	SEARCH_TIME_INTERVAL_OUT(201, "the search time interval is out of bounds"),
//	SEARCH_TIME_INTERVAL_OUT(201, "查询时间范围超过一个月"),














	/**
	 * 切割合成图失败
	 */
	RECOG_REQ_IMAGE_COMBINEDPIC_SPLIT_FAILED(408,"combinedPic image split failed record_id{%s}"),
	/**
	 * 请求参数无效
	 */
	RECOG_REQ_PARAMINVALID(203,"request data is invalid"),
	/**
	 * 请求参数无效
	 */
	RECOG_REQ_PARAMERR(203,"request data invalid,value( %s) of field (%s) is invalid or not exist"),
	/**
	 * http请求失败
	 */
	RECOG_HTTPERR(201, "recognize request exception"),
	/**
	 * 识别服务返回结果解析错误
	 */
	RECOG_RESP_PARSE_ERROR(212, "recognize response parse error,response:%s"),
	/**
	 * 返回内容结构无效，违法代码不存在
	 */
	RECOG_INVALIDRESP(202,"invalid reponse data struct,illegalCode data is not exist"),
	/**
	 * 标注不可用
	 */
	RECOG_STATUS(204,"markilleage disenable for device %s illegal:%s"),
	/**
	 * 设备未标注
	 */
	RECOG_REQ_IMAGEMARK(205,"mark info is empty for device %s illegal:%s"),
	/**
	 * 识别服务解析请求参数错误
	 */
	RECOG_RESP_ERR(211, "recognize request parse error,response=%s"),
	/**
	 * 标注信息缺失
	 */
	INSTESV_MISS_MARKINFO(301,"mark info is absent for device %s",true),
	/**
	 * 厂商不存在
	 */
	INSTESV_MISS_DICTMAP(302,"value {%s} of field{%s} for manufacturer{%s} is not exist in system",true),
	/**
	 * 设备编码不存在
	 */
	INSTESV_INVALID_DEVICE(305,"device is not exists in system,{%s}",true),
	/**
	 * 恢复数据找不到
	 */
	INSTESV_RECOVERY_DUMPNOTFOUND(303,"recovery error data(%s,%s) failed because of dump file is not found"),
	/**
	 * 恢复数据过期
	 */
	INSTESV_RECOVERY_DUMPEXPIRED(309,"recovery error data(%s,%s) failed because of dump file is not found and expired"),
	/**
	 * 图片不存在
	 */
	INSTESV_MISS_IMAGEDATA(304,"at least one of combine image Data or seria image data must be notnull"),
	/**
	 * 根据图片id获取标注图片失败
	 */
	MISS_MARK_IMAGE(306,"get mark image failed for mark image id(%s)"),
	/**
	 * 设备失效
	 */
	INSTESV_MISS_DEVICE(307,"device is miss for devicecode %s",true),
	/**
	 * 违法记录id重复
	 */
//	INSTESV_REPEATED_RECORD(308,"current record is repeated,record_id: %s"),
	/**
	 * 违法记录保存失败
	 */
	INSTESV_DBSAVE(309,"save record to db failed,record_id:%s,manufact:%s"),
	/**
	 * 保存违法记录详情失败
	 */
	INSTESV_DBSAVE_DETAIL(400,"save record detail to db failed,record_id:%s,manufact:%s"),
	/**
	 * 事件对象为空
	 */
	EVENT_NULLEVENT(901,"event object can not be null"),
	/**
	 * 发布http错误
	 */
	PUBLISH_HTTPERROR(401,"http error when publish data:%s; url:%s"),
	/**
	 * publishMeta.json 文件找不到
	 */
	PUBLISH_METAFILENOTFOUND(402,"data struct can't be found in meta file:publishMeta.json"),
	/**
	 * 没有匹配的字段名
	 */
	PUBLISH_INVALIDMETA(403,"field(%s)'s meta desc is wrong, srcProperty(%s) can not match a right fieldname"),
	/**
	 * 国标编码不存在
	 */
	PUBLISH_GBCODENOTEXIST(404,"value {%s} of field{%s} is not exist in system"),
	/**
	 * 发布违法设备编码不存在
	 */
	PUBLISH_WFSBBHNOTEXIST(405,"value {%s} of field{%s} is not exist in system"),
	/**
	 * 发布时找不到图片
	 */
	PUBLISH_NOIMAGE(406,"image is not exist for field{%s}"),
	/**
	 * 初始化未完成
	 */
	UN_INITIALIZE(999,"aiservice is starting,please try again later ");
	public int code;
	public String  message;
	public boolean isInnerError=false;
	public boolean recoverable=false;
	private static Map<Integer, ExceptionInfo> instances=new ConcurrentHashMap<Integer, ExceptionInfo>();
	ExceptionInfo(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	ExceptionInfo(int code, String message, boolean isInnerError) {
		this.code = code;
		this.message = message;
		this.isInnerError=isInnerError;
		this.recoverable=isInnerError;
	}
	
	ExceptionInfo(int code, String message, boolean isInnerError, boolean recoverable) {
		this.code = code;
		this.message = message;
		this.isInnerError=isInnerError;
		this.recoverable=recoverable;
	}
	
	public static ExceptionInfo getByCode(Integer code) {
		if(!instances.containsKey(code)) {
			for(ExceptionInfo e: ExceptionInfo.values()) {
				if(e.code==code) {
					instances.put(code, e);
					return e;
				}
			}
			return null;
		}else {
			return instances.get(code);
		}
	}
	
	public boolean isInnerError() {
		return isInnerError;
	}
	public boolean recoverable() {
		return recoverable;
	}
}