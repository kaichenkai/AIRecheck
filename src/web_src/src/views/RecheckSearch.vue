<template>
    <el-row class="search-body">
        <div class="center-view">
            <el-row class="height-fix">
                <!--                <el-col :span="5" class="left-part select-bar" v-show="!hideCondition">-->
                <el-col :span="5" class="left-part select-bar">
                    <el-row class="page-sub-title">
                        <el-col :span="12" class="page-sub-title-left">
                            <span style="font-weight: 550;font-size: 18px;">复核查询</span>
                        </el-col>
                        <el-col :span="12" class="page-sub-title-right">
                            <el-tooltip class="item" effect="dark" content="重置" placement="top">
                                <span @click="reset()">
                                    <img src="@/assets/img/reset.png" alt/>
                                </span>
                            </el-tooltip>
                        </el-col>
                    </el-row>

                    <el-form
                            :label-position="labelPosition"
                            :model="paramCol"
                            :rules="rules"
                            label-width="80px"
                            :inline="true"
                            ref="form"
                            :show-message="true"
                            class="search-conditions"
                    >
                        <el-form-item label="录入时段" prop="entryTimeRange" class="width-fix">
                            <el-date-picker
                                    v-model="paramCol.entryTimeRange"
                                    type="datetimerange"
                                    range-separator="-"
                                    :clearable="false"
                                    size="small"
                                    :default-time="['00:00:00', '23:59:59']"
                                    :picker-options="pickerOptions"
                                    style="width:100%;"
                                    format="yyyy-MM-dd HH:mm:ss"
                            ></el-date-picker>
                        </el-form-item>

                        <el-form-item label="识别时段" prop="recogTimeRange" class="width-fix">
                            <el-date-picker
                                    v-model="paramCol.recogTimeRange"
                                    type="datetimerange"
                                    range-separator="-"
                                    start-placeholder="开始时间"
                                    end-placeholder="结束时间"
                                    :clearable="false"
                                    size="small"
                                    :default-time="['00:00:00', '23:59:59']"
                                    :picker-options="pickerOptions"
                                    style="width:100%;"
                                    format="yyyy-MM-dd HH:mm:ss"
                            ></el-date-picker>
                        </el-form-item>

                        <el-form-item label="复核状态" class="width-fix">
                            <el-popover placement="bottom" width="430" trigger="click">
                                <el-row class="m-t-10 check-status">
                                    <!--                                    <el-col :span="5" class="l-h-32">复核状态:</el-col>-->
                                    <el-col :span="24">
                                        <el-checkbox-group
                                                v-model="paramCol.preRecheckStatus"
                                                size="small"
                                                @change="handleRecheckStatusChange"
                                        >
                                            <el-checkbox-button
                                                    v-for="item in recheckStatus"
                                                    :key="item.code"
                                                    :label="item.code"
                                            >{{item.name}}
                                            </el-checkbox-button>
                                        </el-checkbox-group>
                                    </el-col>
                                </el-row>

                                <el-input
                                        slot="reference"
                                        size="small"
                                        v-model="paramCol.recheckStatusName"
                                        readonly
                                ></el-input>
                            </el-popover>
                        </el-form-item>

                        <el-form-item label="人工审核状态" class="width-fix">
                            <el-select
                                    v-model="paramCol.preManualCheckStatus"
                                    size="small"
                                    placeholder
                                    clearable
                                    prop="manualCheckStatus"
                                    class="width-fix"
                            >
                                <el-option
                                        v-for="item in manualCheckStatus"
                                        :key="item.code"
                                        :label="item.name"
                                        :value="item.code"
                                ></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="违法类型" prop="illegalCode" class="width-fix">
                            <el-input
                                    placeholder="请输入违法编码"
                                    size="small"
                                    v-model="paramCol.illegalCode"
                            ></el-input>
                        </el-form-item>

                        <el-form-item class="width-fix" style="margin-top: 0px;margin-bottom: 0px;">
                            <el-button
                                    class="width-fix"
                                    type="primary"
                                    size="small"
                                    @click="search()"
                            >查询
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="19" class="right-part" key="empty">
                    <img src="@/assets/img/empty.png" alt="empty"/>
                    <!--                    TODO 复核数据渲染-->
                </el-col>

            </el-row>
        </div>
    </el-row>
</template>

<script lang="ts">
    import {recheckStatus, manualCheckStatus} from '../common/dataCustom.js';

    const moment = require('moment');
    export default {
        name: "RecheckSearch",
        data() {
            return {
                recheckStatus, // 复核状态
                manualCheckStatus, // 人工审核状态

                // 标签参数
                labelPosition: 'top',
                // 请求参数
                paramCol: {
                    // 录入时间, 识别时间
                    entryTimeRange: [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 0))],
                    recogTimeRange: [],
                    // 复核
                    recheckStatusName: "全部",
                    preRecheckStatus: '',
                    // 人工审核
                    manualCheckStatusName: "全部",
                    preManualCheckStatus: '',
                    // 违法类型
                    illegalCode: '',

                    // self
                    // pageSize: 24,
                    // current: 0,
                    // start_time: "2020-05-14 00:00:00",
                    // end_time: "2020-05-14 23:59:59",
                    // recog_start_time: 0,
                    // recog_end_time: 0,
                    // reason_code: [],
                    // manual_check_status: [],
                    // action: "",
                },
                // 参数规则
                rules: {
                    entryTimeRange: [
                        {
                            required: true,
                            message: '请选择时间',
                            trigger: 'blur'
                        }
                    ],
                    recogTimeRange: [
                        {
                            required: false,
                            message: '请选择时间',
                            trigger: 'blur'
                        }
                    ],
                    illegalCode: [
                        {
                            required: false,
                            message: '请输入违法编码',
                            trigger: 'blur'
                        },
                        {
                            pattern: /^[A-Za-z0-9_]{1,20}$/,
                            message: '违法编码有误'
                        }
                    ],
                },
                pickerOptions: {
                    onPick: ({maxDate, minDate}) => {
                        this.choiceDate = minDate.getTime();
                        if (maxDate) {
                            this.choiceDate = 0;
                        }
                    },
                    disabledDate: (time) => {
                        if (this.choiceDate) {
                            const one = 86400000 * 30 * 6;
                            const minTime = this.choiceDate - one;
                            const maxTime = this.choiceDate + one;

                            return time.getTime() < minTime || time.getTime() > maxTime;
                        }
                    },
                    shortcuts: [
                        {
                            text: '本周',
                            onClick(picker) {
                                const end = moment()
                                    .endOf('week')
                                    .valueOf();
                                const start = new Date();

                                start.setTime(
                                    moment()
                                        .startOf('week')
                                        .valueOf()
                                );
                                picker.$emit('pick', [start, end]);
                            }
                        },
                        {
                            text: '近半个月',
                            onClick(picker) {
                                const end = new Date();
                                const start = new Date();

                                start.setTime(
                                    moment()
                                        .subtract(15, 'days')
                                        .valueOf()
                                );
                                picker.$emit('pick', [start, end]);
                            }
                        }
                    ]
                },
            }
        },
        mounted() {
        },

        methods: {
            handleRecheckStatusChange() {
                const {preRecheckStatus} = this.paramCol;
                const nameArray = this.recheckStatus
                    .filter(i => preRecheckStatus.includes(i.code))
                    .map(item => item.name);
                this.paramCol.recheckStatusName = [...nameArray].toString() || '全部';
            },

            reset() {
                this.paramCol = {
                    entryTimeRange: [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 0))],
                    recogTimeRange: [],
                    recheckStatusName: "全部",
                    preRecheckStatus: '',
                    manualCheckStatusName: "全部",
                    preManualCheckStatus: '',
                    illegalCode: ''
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: '';
    }

    .clearfix:after {
        clear: both;
    }

    .search-body {
        width: 100%;
        height: 100%;
        overflow: auto;
        margin: 0 auto;
        box-sizing: border-box;
        padding-right: 30px;

        .center-view {
            height: 100%;
            width: 100%;
            margin: 0 auto;
            overflow: auto;
            padding: 0;

            .height-fix {
                height: 100%;

                .left-part {
                    height: 100%;
                    width: 360px;
                    box-shadow: -9px -3px 20px 15px #dad3d3;
                    background-color: white;
                    padding: 20px 10px 0 15px;
                    overflow-y: auto;
                    overflow-x: hidden;
                    box-sizing: border-box;

                    .page-sub-title {
                        height: 46px;

                        .page-sub-title-left {
                            height: 100%;
                        }

                        .page-sub-title-right {
                            height: 100%;
                            text-align: right;

                            .item {
                                display: inline-block;
                                height: 100%;
                                font-size: 20px;
                                cursor: pointer;

                                img {
                                    /*margin-top: 5px;*/
                                    vertical-align: middle;
                                }
                            }
                        }
                    }

                    .el-form {
                        .el-form-item {
                            .el-button.width-fix {
                                background-color: #18a5d6;
                                border-color: #5bafdb;
                            }

                            .el-button.width-fix:hover {
                                background-color: #5bafdb;
                                border-color: #5bafdb;
                            }
                        }
                    }
                }

                .right-part {
                    text-align: center;
                    margin-top: 16%;
                }
            }
        }

        .search-card {
            margin-left: 2%;
            width: 23.5%;
            margin-top: 10px;

            .time {
                font-size: 13px;
                color: #999;
            }

            .bottom {
                margin-top: 13px;
                line-height: 12px;
            }

            .button {
                padding: 0;
                float: right;
            }

            .image {
                width: 100%;
                display: block;
            }
        }

        .search-card:nth-child(4n + 1) {
            margin-left: 0;
        }

        .search-card:nth-child(1),
        .search-card:nth-child(2),
        .search-card:nth-child(3),
        .search-card:nth-child(4) {
            margin-top: 0;
        }

        .width-fix {
            width: 100% !important;
        }

        .page-sub-title {
            color: #333c48;
            padding-bottom: 20px;
        }

        .table-style {
            height: 100%;
            padding-right: 30px; // background-color: #f2f6f7;
            .search-result-total {
                margin-left: 38%;
                font-size: 14px;
                color: #7f8fa4;
            }

            .icon-btn {
                display: inline-block;
                width: 34px;
                height: 34px;
                line-height: 34px;
                border-radius: 20px;
                text-align: center;
                background-color: #5fb870;
                -webkit-box-shadow: 1px 2px 5px 0px rgba(0, 0, 0, 0.25);
                box-shadow: 1px 2px 5px 0px rgba(0, 0, 0, 0.25);
                border: solid 0px #47ad5a;
                cursor: pointer;

                .tubiao {
                    font-size: 18px;
                    color: #fff;
                }
            }

            .table-list {
                padding: 20px 0 0 10px;
                height: 735px;
            }
        }


        .el-form--label-top .el-form-item__label {
            padding-bottom: 0;
        }

        .el-form-item--small.el-form-item {
            margin-bottom: 2px;
        }
    }

    .detail-body1 {
        width: 100%;
        height: 100%;

        .line {
            width: 100%;
            height: 2px;
            background: #f0f0f0;
            margin: 0 auto;
        }

        .steps {
            margin-top: 40px;
            margin-bottom: 35px;
            width: 94%;

            .el-step__icon.is-text {
                color: #18a5d6;
                border-color: #18a5d6;
            }

            .el-step__title {
                color: #18a5d6;
                font-size: 14px;
                line-height: 38px;
                position: absolute;
                top: -34px;
                left: -10%;
            }

            .el-step__description {
                color: #333c48;
                position: relative;
                top: 15px;
                white-space: pre;
                font-size: 14px;
            }
        }

        .illegal-bar {
            width: 100%;
            font-size: 12px;

            .d-title {
                font-weight: bold;
            }

            .illegal-title {
                height: 40px;
                line-height: 40px;
                background: #f6f6f6;
                text-align: center;
                border: 1px solid #e7e8e9;
                border-right: 0;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            .illegal-content {
                height: 40px;
                line-height: 40px;
                text-align: center;
                border: 1px solid #e7e8e9;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
        }
    }

    .l-h-32 {
        line-height: 32px;
    }
</style>
<style lang="less">
    .search-body {
        .detail-info {
            .el-dialog {
                margin: 0 auto 0 !important;
            }
        }

        .search-conditions {
            // 重置lebal样式样式-----start
            .el-form-item {
                margin-bottom: 16px;
            }

            .el-form--label-top .el-form-item__label {
                padding: 0;
            }

            .el-form-item__label {
                line-height: 15px;
            }
        }

        .tool-box {
            position: absolute;
            top: -60px;
        }

        .el-checkbox__input.is-checked .el-checkbox__inner,
        .el-checkbox__input.is-indeterminate .el-checkbox__inner {
            background-color: #61b972;
            border-color: #61b972;
        }

        .el-dialog--center .el-dialog__body {
            padding: 10px 25px 30px;
        }

        // .el-button--primary {
        //     background-color: #18a5d6;
        //     border-color: #18a5d6;
        //     height: 31px;
        //     line-height: 0px;
        // }
        .el-button--primary:hover {
            background-color: #5bafdb;
            border-color: #5bafdb;
        }

        // 重置button--primary样式-----end
        // 重置step__icon样式-----start
        .el-step__head.is-wait {
            color: #18a5d6;
            border-color: #18a5d6;
        }

        .el-step__title.is-wait {
            color: #18a5d6;
            position: absolute;
            top: -36px;
        }

        .el-step__description.is-wait {
            color: #333c48;
            position: relative;
            top: 15px;
            white-space: pre;
            font-size: 14px;
        }

        .el-table--enable-row-hover .el-table__body tr:hover > td {
            background-color: #e3f6fb !important;

            &:first-child {
                border-left: 4px solid #18a5d6;
                border-radius: 4px 0 0 4px;
            }
        }
    }

    .check-status .el-checkbox-button.is-checked .el-checkbox-button__inner {
        background-color: #18a5d6;
        border-color: #18a5d6;
    }
</style>

