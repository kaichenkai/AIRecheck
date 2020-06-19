<template>
    <el-row class="search-body">
        <div class="center-view">
            <el-row class="height-fix">
                <!--                <el-col :span="5" class="left-part select-bar" v-show="!hideCondition">-->
                <el-col :span="5" class="left-part select-bar">
                    <el-row class="page-sub-title">
                        <el-col :span="12" class="page-sub-title-left">
                            <span style="font-weight: bolder;font-size: 18px;">通报统计</span>
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
                            @keyup.enter="query()"
                    >
                        <el-form-item label="统计时段" prop="entryTimeRange" class="width-fix">
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
                                    value-format="yyyy-MM-dd HH:mm:ss"
                            ></el-date-picker>
                        </el-form-item>


                        <el-form-item label="审核状态" class="width-fix">
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

                        <el-form-item class="width-fix" style="margin-top: 0px;margin-bottom: 0px;">
                            <el-button
                                    class="width-fix"
                                    type="primary"
                                    size="small"
                                    @click="query()"
                            >查询
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col
                        :style="{width:hideCondition?'100%':' calc(100% - 360px)'}"
                        class="table-style right-part"
                        v-show="searchData.length !== 0"
                >
                    <el-row style="height:62px;line-height:62px;background:#fff;border-bottom: 1px solid #ebecec;margin-left: 20px;">
                        <span class="search-result-total">符合条件条件目标 {{totalNum}} 个</span>
                        <div style="float: right;">
                            <el-popover placement="left" width="235" height="60" trigger="click">
                                <el-col style="text-align:center;">
                                    <el-row>
                                        <el-button
                                                size="small"
                                                :loading="exportLoading==='select'"
                                                :disabled="exportLoading==='select'"
                                                @click="outputFalse()"
                                        >导出废片
                                        </el-button>
                                        <el-button
                                                size="small"
                                                :loading="exportLoading==='all'"
                                                :disabled="exportLoading==='all'"
                                                @click="outputStatistics()"
                                        >导出统计
                                        </el-button>
                                    </el-row>
                                    <el-row
                                            v-show="exportProcess>0&&exportProcess<=100"
                                    >打包进度:{{exportProcess}}%
                                    </el-row>
                                </el-col>
                                <span class="icon-btn" title="导出" slot="reference">
                                    <i class="iconfont icon-daochu- tubiao"></i>
                                </span>
                            </el-popover>
                        </div>
                    </el-row>
                    <el-row class="table-list">
                        <div style="height: 100%;" v-show="isTableStyle">
                            <el-table
                                    :data="searchData"
                                    v-loading="loading"
                                    element-loading-text="正在查询..."
                                    class="element-table-reset"
                                    ref="multipleTable"
                                    height="100%"
                                    stripe
                                    :cell-style="rowStyle"
                                    size="small"
                                    :header-cell-style="rowStyle"
                                    style="width: 100%"
                                    highlight-current-row
                                    empty-text="-"
                            >
                                <el-table-column
                                        type="index"
                                        label="序号"
                                        align="center"
                                        :width="tableColumnWidth.sortNum"
                                ></el-table-column>
                                <!--                                prop="entryTime"-->
                                <!--                                                                        :width="tableColumnWidth.entryTime"-->
                                <el-table-column
                                        prop="officeName"
                                        align="left"
                                        label="采集机关"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="entryNum"
                                        align="left"
                                        show-overflow-tooltip
                                        label="录入量"
                                ></el-table-column>
                                <el-table-column
                                        prop="analyzeNum"
                                        align="left"
                                        label="分析量"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="reportNum"
                                        align="left"
                                        label="上报量"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="suspectErrorNum"
                                        align="left"
                                        label="疑似错误量"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="trueNum"
                                        align="left"
                                        label="正片量"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="falseNum"
                                        align="left"
                                        label="废片量"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="precisionRate"
                                        align="left"
                                        label="准确率"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="recallRate"
                                        align="left"
                                        label="召回率"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="detectionRate"
                                        align="left"
                                        label="检出率"
                                        show-overflow-tooltip
                                ></el-table-column>
                            </el-table>
                            <!-- 分页 -->
                            <div class="pagination-box">
                                <el-pagination
                                        :current-page.sync="currentPage"
                                        :page-size="pageSize"
                                        :total="totalNum"
                                        :pager-count="5"
                                        layout="prev, pager, next, total, jumper"
                                        @current-change="handleCurrentChange"
                                ></el-pagination>
                            </div>
                        </div>
                    </el-row>
                </el-col>
                <el-col :span="19" v-show="searchData.length === 0" class="empty-wrap" key="empty">
                    <img src="@/assets/img/empty.png" alt="empty"/>
                </el-col>

            </el-row>
        </div>
    </el-row>
</template>

<script>
  import {
    tableColumnWidth,
    manualCheckStatus,
    manualCheckStatusMap
  } from "../common/dataCustom";

  const moment = require("moment");
  export default {
    name: "ReportStatistics",
    data() {
      return {
        tableColumnWidth,  //表格列宽度
        manualCheckStatus, //审核状态

        // 主页标签控制参数
        labelPosition: "top",
        hideCondition: false,//跳转过来隐藏条件
        isTableStyle: true, // true: 以表格形式展示, false: 以 medium icons 形式展示
        exportProcess: 0,//打包进度
        exportLoading: false,
        loading: false,//加载状态

        // 请求参数
        paramCol: {
          // 录入时间, 识别时间
          entryTimeRange: [this.tools.timeFormat(new Date(new Date(1585732024000).setHours(0, 0, 0, 0)), "dateTime"),
            this.tools.timeFormat(new Date(new Date(1588151224000).setHours(23, 59, 59, 0)), "dateTime")],
          preManualCheckStatus: "",
          illegalCode: ""                // 违法类型编码
        },

        // 页面数据
        searchData: [],
        totalNum: 0,
        pageSize: 24,
        currentPage: 1,
        multipleSelection: [],  // 多选

        // 规则参数
        rowStyle: {
          "font-size": "12px"
        },
        rules: {
          entryTimeRange: [
            {
              required: true,
              message: "请选择时间",
              trigger: "blur"
            }
          ]
        },
        pickerOptions: {
          onPick: ({ maxDate, minDate }) => {
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
              text: "本周",
              onClick(picker) {
                const end = moment()
                  .endOf("week")
                  .valueOf();
                const start = new Date();

                start.setTime(
                  moment()
                    .startOf("week")
                    .valueOf()
                );
                picker.$emit("pick", [start, end]);
              }
            },
            {
              text: "近半个月",
              onClick(picker) {
                const end = new Date();
                const start = new Date();

                start.setTime(
                  moment()
                    .subtract(15, "days")
                    .valueOf()
                );
                picker.$emit("pick", [start, end]);
              }
            }
          ]
        }
      };
    },

    filters: {},

    mounted() {
      this.search();
    },

    methods: {
      //查询数据
      async search() {
        let _this = this;
        _this.searchData = [];//清空数据
        this.loading = true;
        const { entryTimeRange: [entryStartTime, entryEndTime] } = this.paramCol;
        const { preManualCheckStatus } = this.paramCol;
        //
        const { pageSize, currentPage } = this;
        // 拼接参数，兼容 python 后台接口
        let params = {
          pageSize,
          current: (currentPage - 1) * pageSize,
          start_time: entryStartTime, // 时间字符串 YYYY-MM-DD HH:mm:ss
          end_time: entryEndTime, // 时间字符串 YYYY-MM-DD HH:mm:ss
          manual_check_status: preManualCheckStatus === "" ? parseInt(Object.keys(manualCheckStatusMap)[3]) : preManualCheckStatus
        };
        const resp = await this._services.statisticsQuery(params, { method: "post" });
        _this.loading = false;
        const { code, total, data: { result } } = resp;
        const { data } = resp;
        if (parseInt(code, 10) !== 200) {
          _this.tools.message(resp.message, "error");
          return;
        }
        if (result.length === 0) {
          _this.searchData = [];
          _this.tools.message("无数据", "warning");
          return;
        }
        _this.totalNum = total;
        // 响应结果处理, 适应 python 后台接口
        for (const record of result) {
          const convertRecord = {
            officeName: record.name,
            entryNum: record.insert_count,
            analyzeNum: record.ana_count,
            reportNum: record.report_count,
            suspectErrorNum: record.err_count,
            trueNum: record.m1_count,
            falseNum: record.m2_count,
            precisionRate: record.m2_p,
            recallRate: record.recall,
            detectionRate: record.jianchu
          };
          _this.searchData.push(convertRecord);
        }
        // 统计数据
        _this.searchData.push({
          officeName: "总计",
          entryNum: data.inserts,
          analyzeNum: data.ana_counts,
          reportNum: data.report_total,
          suspectErrorNum: data.errs,
          trueNum: data.m1s,
          falseNum: data.m2s,
          precisionRate: data.m2_p,
          recallRate: data.recall,
          detectionRate: data.jianchu
        });
      },

      // 重置
      reset() {
        this.paramCol = {
          entryTimeRange: [this.tools.timeFormat(new Date(new Date().setHours(0, 0, 0, 0)), "dateTime"), this.tools.timeFormat(new Date(new Date().setHours(23, 59, 59, 0)), "dateTime")]
        };
      },

      //翻页
      handleCurrentChange(pagenum) {
        // this.detailInfoIdx = 0;
        this.currentPage = pagenum;
        this.search();
      },

      // 导出废片
      outputFalse() {
        console.log("aaa");
      },

      // 导出统计
      async outputStatistics() {
        let params = {};
        const { entryTimeRange: [entryStartTime, entryEndTime] } = this.paramCol;
        const { preManualCheckStatus } = this.paramCol;
        // 是否有数据
        if (this.searchData.length <= 0) {
          this.tools.message("请先查询数据", "info");
          return;
        }
        params = {
          start_time: entryStartTime,
          end_time: entryEndTime,
          manual_check_status: preManualCheckStatus === "" ? parseInt(Object.keys(manualCheckStatusMap)[3]) : preManualCheckStatus
        };
        console.log(params);
        await this._services.outputFalseTable(params);
      }
    }
  };
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
                                font-size: 20px;
                                cursor: pointer;

                                img {
                                    /*margin-top: 5px;*/
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

                .empty-wrap {
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
                /*padding: 20px 0 0 20px;*/
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

            .original {
                .original-content {
                    margin-bottom: 5px;
                }
            }

            .discern {
                .discern-content {
                    margin-bottom: 5px;

                    .manualCheck {
                        .width-fix {
                            width: 200px;
                        }
                    }
                }
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
