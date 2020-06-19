package com.seemmo.airecheck.constant;

import java.util.Arrays;
import java.util.List;

public interface BusinessConstant {

    /**
     * 序列图张数
     */
    int SEQUENCE_IMAGE_COUNT = BaseConstant.CONST5;


    String SPLIT_STR = "#";
    /**
     * 节省人力算法系数
     */
    double MANSAVER_COEFFICIENT = BaseConstant.CONST2000;

    /**
     * 30分钟间隔毫秒数
     */
    long HALFHOUR_INTEVAL = BaseConstant.CONST1800000;

    /**
     * 复核结果数据下载存放位置
     */
    String RECHECK_DATA = "recheck_data";

    /**
     * 复核结果数据下载(图片文件前缀)
     */
    String IMG_FILE_PREFIX = "airecheck";

    /**
     * 导出表格的第一个 sheet 页名称
     */
    String SHEET_ONE_NAME = "recheckInfo";





    /**
     * 标注数据下载存放位置
     */
    String MARK_IMAGE = "markimage";

    /**
     * 标注数据下载存放位置
     */
    String MARK_DATA = "markdata";

    /**
     * 深度学习图片下载存放位置
     */
    String ML_IMAGE = "mlimage";
    /**
     * 标注数据上传存放位置
     */
    String MARK_TEMP_UPLOAD = "marktempupload";

    /**
     * 预审测试数据下载存放位置
     */
    String TRAFFIC_TEST = "traffictest";

    /**
     * 临时目录
     */
    String TMEP_DIR = "data_tmp";

    /**
     * 标注图 存放目录
     */
    String IMAGE_DEVICEMARK = "markimage";
    /**
     * 业务图片 存放目录
     */
    String IMAGE_AITRAFFIC = "aitraffic";

    /**
     * 预审测试图片 存放目录
     */
    String IMAGE_TRAFFIC_TEST = "traffictest";

    /**
     * 切图 存放目录
     */
    String IMAGE_SPLIT_IMAGE = "splitImage";

    /**
     * 导出的Excel的默认文件名
     */
    String EXPORT_EXCEL_FILENAME = "record.xlsx";

    /**
     * 文件结尾字符
     */
    String LOWER_XLS = "xls";
    String LOWER_XLSX = "xlsx";
    String LOWER_SPOTXLS = ".xls";
    String LOWER_SPOTXLSX = ".xlsx";
    String LOWER_SPOTZIP = ".zip";
    String LOWER_SPOTJPG = ".jpg";
    String LOWER_SPOTPNG = ".png";
    String LOWER_SPOTJSON = ".json";

    String SEEMMO_MARK = "IUAjJCVeJiooKXNlZW1tbw==";

    /**
     * 批量插入sql语句时，每次最大处理条数
     */
    int SIZE = BaseConstant.CONST200;

    /**
     * 未知路口
     */
    String DEFAULT_ROAD_NAME = "未知路口";
    /**
     * 批量导入最大记录条数
     */
    int TEST_IMP_MAXCOUNT = BaseConstant.CONST3000;

    /**
     * 用户密码最小长度
     */
    int PASSWORD_MIN_LENGTH = 6;
    /**
     * 用户密码最大长度
     */
    int PASSWORD_MAX_LENGTH = 15;

    String WEB_ICON = "iconUrl";

    String WEB_ICON_PRE = "data:image/jpeg;base64,";

    String WEB_ICON_PRE_KEY = ";base64,";

    String WEB_ICON_SPLIT = "##";

    String FIELD_KEY_PROJECT_ID = "projectId";
    String FIELD_KEY_MANUFACTURERCODE = "manufacturerCode";

    String EXPORT_DEVICE_MARK_DEVICES = "devices";
    String EXPORT_DEVICE_MARK_MARKS = "device_marks";
    String EXPORT_DEVICE_MARK_ILLEAGALS = "device_illegals";
    String EXPORT_DEVICE_MARK_MARKCONFIGS = "device_markconfig";
    String EXPORT_DEVICE_MARK_MARKRELAS = "device_markrelas";

    /**
     * 中横杠常量
     */
    String EXPORT_SPLIT = "_";
    String EXPORT_PREFIXX = "export_";
    String EXPORT_AITEST_NAME = "aitest";
    String EXPORT_SAMPLE_NAME = "sample";
    String EXPORT_AITEST_LABEL = "aitest_label";


    String INTERCEPTOR_WEBLOG = "WebLogInterceptor";
    String INTERCEPTOR_HANDLER = "HandlerInterceptorAdapter";

    int MAX_THREAD_COUNT = BaseConstant.CONST10;
    /**
     * 数据目录basedir
     */
    String DATABASEDIR = "/data/";

    /**
     * 道路线
     * 白实线 WSL
     * 黄实线 YSL
     * WLVRSL 白线-左虚右实线
     * YLVRSL 黄线-左虚右实线
     * WLSRVL 白线-左实右虚线
     * YLSRVL 黄线-左实右虚线
     */
    String[] DLX_VALUE = {"WSL", "YSL", "WLVRSL", "YLVRSL", "WLSRVL", "YLSRVL"};

    List<String> DLX_VALUE_LIST = Arrays.asList(DLX_VALUE);


    String KEY_REQUEST_COREPARAMS = "to_be_updated";
    String KEY_RESPONSE_COREPARAMS = "updated_param";
}
