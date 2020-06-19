package com.seemmo.airecheck.commons.model;


import com.google.common.collect.Lists;

import java.util.List;

public class SystemEnums {

    public enum CODE_TYPE {
        //其他
        NONE(-1),
        //过车方向
        CAR_DIRECTION(0),
        //号牌种类
        PLATE_TYPE(1),
        //号牌颜色
        PLATE_COLOR(2),
        //车辆颜色
        CAR_COLOR(3),
        //违法行为
        ILLEGAL_TYPE(7),
        //废片原因
        ERROR_REASON(8),
        //使用性质
        USE_PROPERTY(9),
        //道路类型
        ROAD_TYPE(10),
        //车管所车辆类型
        CARADMINOFFICE_CAR_TYPE(11),
        //预审车辆类型
        CAR_TYPE(12),
        /**
         * 设备类型
         */
        DEVICE_TYPE(13),
        // 不能标注原因
        NOMARK_REASON(14),

        //厂商
        MANUFACTURER_CODE(920),
        //设备编码
        DEVICE_CODE(921),
        //设备编号(路口)
        DEVICE_BAH(922);
        private int code;

        CODE_TYPE(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static CODE_TYPE getByCode(int type) {
            for (CODE_TYPE dataType : CODE_TYPE.values()) {
                if (dataType.code == type) {
                    return dataType;
                }
            }
            return NONE;
        }
    }

    public enum ExtractStrategy {
        CopySrcField, DictMapping, SysGenerate, Recgonize, ImageSave, ImageGet, NONE;
    }

    public enum ImageCategory {
        NORMAL, TEST
    }

    public enum VALUETYPE {
        STRING("String"), INTEGER("Integer");
        String name;

        VALUETYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static VALUETYPE getByCode(String type) {
            for (VALUETYPE dataType : VALUETYPE.values()) {
                if (dataType.name.equals(type)) {
                    return dataType;
                }
            }
            return STRING;
        }
    }

    /**
     * 违法记录的状态
     */
    public enum RecordStatus {
        /**
         * 违法抓拍
         */
        PIC(0),
        /**
         * 接入
         */
        JOIN(1),
        /**
         * 预审
         */
        PRECHECK(2),
        /**
         * 违法初审
         */
        CHECK(3),
        /**
         * 违法复审
         */
        RECHECK(4),
        /**
         * 数据发布
         */
        PUBLISH(5);

        public int code;

        RecordStatus(int code) {
            this.code = code;
        }

        public static RecordStatus parse(int code) {
            for (RecordStatus recordStatus : RecordStatus.values()) {
                if (recordStatus.code == code) {
                    return recordStatus;
                }
            }
            return PIC;
        }
    }

    public enum CheckStage {
        // 0预审，1初审，2复审
        PRECHECK(0, CheckStatus.PRE_PASS, CheckStatus.PRE_UNPASS, CheckStatus.PRE_SUSPECT_PASS),
        CHECK(1, CheckStatus.CHECK_PASS, CheckStatus.CHECK_UNPASS, CheckStatus.CHECK_DECK),
        RECHECK(2, CheckStatus.RECHECK_PASS, CheckStatus.RECHECK_UNPASS, CheckStatus.RECHECK_DECK);

        public int code;
        public CheckStatus[] statusList;

        CheckStage(int code, CheckStatus... status) {
            this.code = code;
            this.statusList = status;
        }

        public static CheckStage getByCode(int type) {
            for (CheckStage dataType : CheckStage.values()) {
                if (dataType.code == type) {
                    return dataType;
                }
            }
            return null;
        }

        public static CheckStage getByCheckStatus(CheckStatus checkStatus) {
            if (checkStatus == null) {
                return null;
            }
            for (CheckStage dataType : CheckStage.values()) {
                for (CheckStatus checkStatus1 : dataType.statusList) {
                    if (checkStatus1 == checkStatus) {
                        return dataType;
                    }
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }
    }

    public enum CheckResult {
        UNCHECK(2, CheckStatus.UNPRECHECK),
        PASS(0, CheckStatus.PRE_PASS, CheckStatus.CHECK_PASS, CheckStatus.RECHECK_PASS),
        UNPASS(1, CheckStatus.PRE_UNPASS, CheckStatus.CHECK_UNPASS, CheckStatus.RECHECK_UNPASS);
        public int code;
        public CheckStatus[] statusList;

        CheckResult(int code, CheckStatus... status) {
            this.code = code;
            this.statusList = status;
        }

        public static CheckResult getByCode(int type) {
            for (CheckResult dataType : CheckResult.values()) {
                if (dataType.code == type) {
                    return dataType;
                }
            }
            return null;
        }

        public static CheckResult getByStatus(int status) {
            for (CheckResult dataType : CheckResult.values()) {
                for (CheckStatus checkStatus : dataType.statusList) {
                    if (checkStatus.code == status) {
                        return dataType;
                    }
                }

            }
            return null;
        }
    }

    /**
     * 审核状态
     */
    public enum CheckStatus {
        /**
         * 未预审
         */
        UNPRECHECK(0, "未预审"),
        //=========初审状态码规则为==整百：10-90
        /**
         * 预审正片
         */
        PRE_PASS(10, "预审正片"),
        /**
         * 预审废片
         */
        PRE_UNPASS(20, "预审废片"),
        /**
         * 预审疑似正片
         */
        PRE_SUSPECT_PASS(30, "疑似正片"),
        /**
         * 预审异常
         */
        PRE_FAIL(90, "预审异常"),
        //=========初审状态码规则为==整百：100-900
        /**
         * 初审正片
         */
        CHECK_PASS(300, "初审正片"),
        /**
         * 初审废片
         */
        CHECK_UNPASS(400, "初审废片"),
        /**
         * 初审套牌
         */
        CHECK_DECK(500, "初审套牌"),
        //=========复审状态码规则为==整千：1000-9000
        /**
         * 复审正片
         */
        RECHECK_PASS(6000, "复审正片"),
        /**
         * 复审废片
         */
        RECHECK_UNPASS(7000, "复审废片"),
        /**
         * 复审套牌
         */
        RECHECK_DECK(8000, "复审套牌");


        public int code;
        public String name;

        CheckStatus(int code) {
            this.code = code;
        }

        CheckStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static CheckStatus getByCode(int type) {
            for (CheckStatus dataType : CheckStatus.values()) {
                if (dataType.code == type) {
                    return dataType;
                }
            }
            return null;
        }

        public static List<Integer> getCodes() {
            List<Integer> checkStatuslist = Lists.newArrayList();
            for (CheckStatus checkStatus : CheckStatus.values()) {
                checkStatuslist.add(checkStatus.code);
            }
            return checkStatuslist;
        }

        public static String codeToDesc(int code) {
            for (CheckStatus checkStatus : CheckStatus.values()) {
                if (checkStatus.code == code) {
                    return checkStatus.name;
                }
            }
            return "";
        }

        public static Integer descToCode(String desc) {
            for (CheckStatus checkStatus : CheckStatus.values()) {
                if (checkStatus.name.equals(desc)) {
                    return checkStatus.code;
                }
            }
            return null;
        }

        public static CheckStatus scodeToCode(Integer scode) {
            switch (scode) {
                case 0:
                    return PRE_UNPASS;
                case 1:
                    return PRE_PASS;
                case 2:
                    return PRE_SUSPECT_PASS;
                default:
                    return PRE_FAIL;
            }
        }

    }

    /**
     * 发布类型枚举类，外挂系统和执法平台
     */
    public enum PublishType {
        /**
         * 外挂系统
         */
        ADDON_SYS,
        /**
         * 执法平台
         */
        FORMAL_SYS;


        public static PublishType getByCode(int type) {
            for (PublishType dataType : PublishType.values()) {
                if (dataType.ordinal() == type) {
                    return dataType;
                }
            }
            return null;
        }
    }

    /**
     * 发布类型枚举类，外挂系统和执法平台
     */
    public enum PublishMethod {
        /**
         * 手动上报
         */
        MANUAL_PUSH,
        /**
         * 自动上报
         */
        AUTO_PUSH;


        public static PublishType getByCode(int type) {
            for (PublishType dataType : PublishType.values()) {
                if (dataType.ordinal() == type) {
                    return dataType;
                }
            }
            return null;
        }
    }

    /**
     * 是否发布原始信息
     */
    public enum PublishSrcRecord {
        /**
         * 不发布
         */
        NOT_PUSH,
        /**
         * 发布
         */
        PUSH;

        public static PublishSrcRecord getByCode(int type) {
            for (PublishSrcRecord dataType : PublishSrcRecord.values()) {
                if (dataType.ordinal() == type) {
                    return dataType;
                }
            }
            return null;
        }
    }

    public enum UseCodeVer {
        SRCCODE, SYSCODE, GBCODE
    }

    public enum PublishStatus {
        // 上报状态。0 入库 1 备案编号不对应 2 其它备案信息不对应 3 url不全 4 无主图 5 接口异常 6 该备案设备未备案此违法行为 7 其它上报错误 8 上报成功'
        UNPUBLISH(0, "未上报"), INVALIDRECORDNO(1, "备案编号不对应"), OTHERINFOMAPERR(2, "其它备案信息不对应"), URLERR(3, "url不全"), NOIMAGE(4, "无主图"), HTTPERR(5, "接口异常"), UNRECORD(6, "该备案设备未备案此违法行为"), UNKNOWNFAIL(7, "其它上报错误"), PUBLISHED(8, "上报成功");

        public int code;
        public String name;

        PublishStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static PublishStatus getByCode(int type) {
            for (PublishStatus dataType : PublishStatus.values()) {
                if (dataType.code == type) {
                    return dataType;
                }
            }
            return null;
        }

        public static List<Integer> getCodes() {
            List<Integer> codeList = Lists.newArrayList();
            for (PublishStatus status : PublishStatus.values()) {
                codeList.add(status.code);
            }
            return codeList;
        }

    }

    public enum Period {
        // 本日，本周，本月
        /***/
        DAY(1),
        WEEK(2), MONTH(3);

        public int code;

        Period(int code) {
            this.code = code;
        }

        public static Period parse(int code) {
            switch (code) {
                case 1:
                    return DAY;
                case 2:
                    return WEEK;
                case 3:
                    return MONTH;
                default:
                    return DAY;
            }
        }
    }

    /**
     * 数据发布的 发布类型
     */
    public enum DataType {
        NONE(-1),
        /**
         * 预审
         */
        PRECHECK(0),
        /**
         * 初审
         */
        CHECK(1),
        /**
         * 复审
         */
        RECHECK(2);

        public int code;

        DataType(int code) {
            this.code = code;
        }

        public static DataType parse(int code) {
            switch (code) {
                case 0:
                    return PRECHECK;
                case 1:
                    return CHECK;
                case 2:
                    return RECHECK;
                default:
                    return NONE;
            }
        }
    }

    /**
     * 开启和关闭
     */
    public enum OPEN_STATUS {

        /**
         * 打开
         */
        OPEN(1),
        /**
         * 关闭
         */
        CLOSE(0);

        private int code;

        OPEN_STATUS(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum TASK_STATUS {
        /**
         * 关闭/暂停
         */
        CLOSE(0),
        /**
         * 进行中
         */
        DOING(1),
        /**
         * 已完成
         */
        DONE(2),
        /**
         * 不存在
         */
        NON_EXISTENT(3);
        private int code;

        TASK_STATUS(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
