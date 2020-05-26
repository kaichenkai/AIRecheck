<template>
    <el-row class="search-body">
        <div class="center-view">
            <el-row class="height-fix">
                <!--                <el-col :span="5" class="left-part select-bar" v-show="!hideCondition">-->
                <el-col :span="5" class="left-part select-bar">
                    <el-row class="page-sub-title">
                        <el-col :span="12" class="page-sub-title-left">
                            <span style="font-weight: bolder;font-size: 18px;">复核查询</span>
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
                                    value-format="yyyy-MM-dd HH:mm:ss"
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
                                    value-format="timestamp"
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

                        <el-form-item label="审核状态" class="width-fix">
                            <el-select
                                    v-model="paramCol.preManualCheckStatus"
                                    size="small"
                                    placeholder
                                    clearable
                                    prop="manualCheckStatus"
                                    class="width-fix"
                                    @change="handleManualCheckStatusChange"
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
                                    @click="query()"
                            >查询
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col
                        :style="{width:hideCondition?'100%':' calc(100% - 360px)'}"
                        class="table-style right-part"
                        v-show="queryData.length !== 0"
                >
                    <el-row style="height:62px;line-height:62px;background:#fff;border-bottom: 1px solid #ebecec;margin-left: 20px;">
                        <span class="search-result-total">符合条件条件目标 {{totalNum}} 个</span>
                        <div style="float: right;">
                            <!--@click="changeView"-->
                            <span
                                    class="icon-btn"
                                    style="margin-right:30px;"

                                    title="切换"
                            >
                                <i class="iconfont icon-card- tubiao" v-show="isList" @click="isList=!isList"></i>
                                <i class="iconfont icon-list- tubiao" v-show="!isList" @click="isList=!isList"></i>
                            </span>
                            <el-popover placement="bottom" width="235" trigger="click">
                                <el-col style="text-align:center;">
                                    <el-row>
                                        <el-checkbox
                                                v-model="onExcelCheck"
                                                @change="(...arg)=>{exportLimit('onExcelCheck',...arg)}"
                                        >仅导出表格
                                        </el-checkbox>
                                        <el-checkbox
                                                v-model="applyExportRule"
                                                @change="(...arg)=>{exportLimit('applyExportRule',...arg)}"
                                        >应用导出规则
                                        </el-checkbox>
                                    </el-row>
                                    <el-row style="margin:5px">
                                        <el-button
                                                size="small"
                                                :loading="exportLoading==='select'"
                                                :disabled="exportLoading==='select'"
                                                @click="outputIllegal('select')"
                                        >导出勾选
                                        </el-button>
                                        <el-button
                                                size="small"
                                                :loading="exportLoading==='all'"
                                                :disabled="exportLoading==='all'"
                                                @click="outputIllegal('all')"
                                        >导出全部
                                        </el-button>
                                    </el-row>

                                    <el-row style="margin:5px">
                                        <el-input-number
                                                size="small"
                                                :precision="0"
                                                v-model="exportNum"
                                                :min="1"
                                                :max="totalNum"
                                                label="输入导出条目"
                                        ></el-input-number>
                                        <el-tooltip
                                                class="item"
                                                effect="dark"
                                                content="自定义导出条数"
                                                placement="bottom"
                                        >
                                            <el-button
                                                    size="small"
                                                    :loading="exportLoading==='number'"
                                                    @click="outputIllegal('number')"
                                            >导出
                                            </el-button>
                                        </el-tooltip>
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
                        <!--列表模式-->
                        <div style="height: 100%;" v-show="isTableStyle">
                            <el-table
                                    :data="queryData"
                                    v-loading="loading"
                                    element-loading-text="正在查询..."
                                    class="element-table-reset"
                                    ref="multipleTable"
                                    @select-all="selectAll"
                                    @select="select"
                                    height="100%"
                                    stripe
                                    :cell-style="rowStyle"
                                    size="small"
                                    :header-cell-style="rowStyle"
                                    style="width: 100%"
                                    highlight-current-row
                                    empty-text="-"
                            >
                                <el-table-column type="selection"></el-table-column>
                                <el-table-column
                                        type="index"
                                        label="序号"
                                        :width="tableColumnWidth.sortNum"
                                ></el-table-column>
                                <!--                                prop="entryTime"-->
                                <!--                                                                        :width="tableColumnWidth.entryTime"-->
                                <el-table-column
                                        prop="entryTime"
                                        align="left"
                                        label="录入时间"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <!--                                prop="sdkRecogTime"-->
                                <!--                                :formatter="formatterTime"-->
                                <!--                                :width="tableColumnWidth.sdkRecogTime"-->
                                <el-table-column
                                        prop="sdkRecogTime"
                                        align="left"
                                        show-overflow-tooltip
                                        label="识别时间"

                                ></el-table-column>
                                <!--                                prop="sdkCarPlateNumber"-->
                                <el-table-column
                                        prop="sdkCarPlateNumber"
                                        align="left"
                                        label="识别号牌号码"
                                        show-overflow-tooltip
                                        :width="tableColumnWidth.sdkCarPlateNumber"
                                ></el-table-column>
                                <!--                                prop="srcCarPlateNumber"-->
                                <el-table-column
                                        prop="srcCarPlateNumber"
                                        align="left"
                                        label="原始号牌号码"
                                        show-overflow-tooltip
                                        :width="tableColumnWidth.srcCarPlateNumber"
                                ></el-table-column>
                                <!--                                prop="sdkReasonCode"-->
                                <el-table-column
                                        prop="sdkReasonCode"
                                        :formatter="formatterSdkReasonCode"
                                        align="left"
                                        label="识别结果"
                                        show-overflow-tooltip
                                        :width="tableColumnWidth.sdkReasonCode"
                                ></el-table-column>
                                <!--                                prop="manualCheckStatus"-->
                                <el-table-column
                                        prop="manualCheckStatus"
                                        align="left"
                                        label="审核状态"
                                        :formatter="formatterManualCheckStatus"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column :width="tableColumnWidth.detailIcon">
                                    <template slot-scope="scope">
                                        <i class="el-icon-picture"
                                           @click="popDetail(scope.row)"
                                           style="cursor:pointer;font-size: 16px;"
                                        ></i>
                                    </template>
                                </el-table-column>
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

                        <!--卡片模式-->
                        <!--                        <div style="height: 100%;" v-show="!isTableStyle">-->
                        <!--                            <SearchResult-->
                        <!--                                    :queryData="queryData"-->
                        <!--                                    :SearchResultOpt="SearchResultOpt"-->
                        <!--                                    ref="searchResult"-->
                        <!--                                    :multipleSelection="multipleSelection"-->
                        <!--                                    v-on:setSelection="getSelectionChange"-->
                        <!--                                    v-on:getCurrentPage="search"-->
                        <!--                                    v-on:moreInfo="popDetail"-->
                        <!--                                    :totalNum="totalNum"-->
                        <!--                                    @search="search"-->
                        <!--                                    v-loading="loading"-->
                        <!--                                    element-loading-text="正在查询..."-->
                        <!--                            />-->
                        <!--                        </div>-->
                    </el-row>
                </el-col>
                <el-col :span="19" v-show="queryData.length === 0" class="empty-wrap" key="empty">
                    <img src="@/assets/img/empty.png" alt="empty"/>
                </el-col>

            </el-row>
        </div>
        <el-dialog
                class="detail-info"
                :close-on-click-modal="false"
                title="复核信息查看"
                center
                width="1000px"
                margin-top="5vh"
                :visible.sync="detailVisible"
        >
            <el-row class="detail-body1" v-loading="isDetailInfoLoading">
                <el-col :span="1" class="v-c m-r-5" style="height:670px">
                    <el-tooltip
                            :disabled="isLeftTipdisabled"
                            content="上一条"
                            placement="bottom"
                            effect="light"
                    >
                        <el-button
                                @click="detailInfoToggle('prev');isLeftTipdisabled = true"
                                type="primary"
                                icon="el-icon-arrow-left"
                                circle
                                :disabled="detailInfoIdx<=0"
                        ></el-button>
                    </el-tooltip>
                </el-col>
                <el-col :span="21">
                    <div class="line"></div>
                    <el-steps class="steps">
                        <el-step
                                v-for="item in stepsList"
                                v-bind:title="item.title"
                                v-bind:description="item.description"
                                :key="item.code"
                        ></el-step>
                    </el-steps>
                    <el-row style="width:100%;height:400px;;margin:0 auto;">
                        <!--                                :picPath="dialogData.carNumPicPath"-->
                        <Carousel
                                ref="swiperImg"
                                :idx="dialogData.id"
                                :sdkPlateRect="dialogData.sdkPlateRect"
                                v-show="dialogData.carNumPicPath"
                        />
                    </el-row>
                    <div class="illegal-bar">
                        <el-collapse v-model="activeNames">
                            <el-collapse-item name="original" class="original">
                                <template slot="title">原始信息<i class="header-icon el-icon-info"></i></template>
                                <el-row class="original-content">
                                    <el-col :span="5">
                                        <el-col :span="10" class="d-title">号牌号码：</el-col>
                                        <el-col :span="14">{{dialogData.srcCarPlateNumber}}</el-col>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-col :span="10" class="d-title">违法行为：</el-col>
                                        <el-col
                                                :span="14"
                                        >{{dialogData.illegalCode}}
                                        </el-col>
                                    </el-col>
                                </el-row>
                            </el-collapse-item>
                            <el-collapse-item name="discern" class="discern">
                                <template slot="title" class="item-title">识别信息<i class="header-icon el-icon-view"></i>
                                </template>
                                <el-row class="discern-content">
                                    <el-col :span="8">
                                        <el-col :span="6" class="d-title">号牌号码：</el-col>
                                        <el-col :span="18">{{dialogData.sdkCarPlateNumber}}</el-col>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-col :span="6" class="d-title">识别结果：</el-col>
                                        <el-col :span="18">{{handleSdkReasonCode(dialogData.sdkReasonCode)}}
                                        </el-col>
                                    </el-col>
                                </el-row>
                                <el-row class="discern-content">
                                    <!--                                    <el-col :span="10">-->
                                    <!--                                        <el-col :span="7" class="d-title">提取号牌图片：</el-col>-->
                                    <!--                                        <el-col :span="17">-->
                                    <!--                                            <img :src="dialogData.id | plateImageUrl(dialogData.sdkPlateRect)" alt=""/>-->
                                    <!--                                        </el-col>-->
                                    <!--                                    </el-col>-->
                                    <el-col :span="8">
                                        <el-col :span="6" class="d-title">字段得分：</el-col>
                                        <el-col :span="18">
                                            <table border="1" style="text-align: center">
                                                <tbody>
                                                <tr>
                                                    <td v-for="(item, index) in dialogData.sdkCarPlateNumber"
                                                        :key="index">{{item}}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td v-for="(item, index) in dialogData.sdkPlateScores" :key="index">
                                                        {{item}}
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </el-col>
                                    </el-col>
                                    <el-col class="manualCheck"
                                            :span="16"
                                    >
                                        <el-col :span="3" class="d-title">审核结果：</el-col>
                                        <el-col :span="5">{{dialogData.manualCheckStatus |
                                            formatterManualCheckStatus}}
                                        </el-col>
                                        <el-col :span="16">
                                            <el-button
                                                    style="width: 100px"
                                                    type="primary"
                                                    size="small"
                                                    @click="manualCheck(manualCheckStatus[2].code)"
                                            >正片
                                            </el-button>
                                            <el-button
                                                    style="width: 100px"
                                                    type="info"
                                                    size="small"
                                                    plain
                                                    @click="manualCheck(manualCheckStatus[3].code)"
                                            >废片
                                            </el-button>
                                        </el-col>
                                    </el-col>
                                </el-row>
                            </el-collapse-item>
                        </el-collapse>
                    </div>
                </el-col>
                <el-col :span="1" class="v-c m-l-5" style="height:670px">
                    <el-tooltip
                            :disabled="isRightTipdisabled"
                            content="下一条"
                            placement="bottom"
                            effect="light"
                    >
                        <el-button
                                @click="detailInfoToggle('next');isRightTipdisabled = true"
                                type="primary"
                                icon="el-icon-arrow-right"
                                circle
                                :disabled="detailInfoIdx>=queryData.length-1"
                        ></el-button>
                    </el-tooltip>
                </el-col>
            </el-row>
        </el-dialog>
    </el-row>
</template>

<script>
  // 数据识别状态
  const recogStatusMap = {
    1: "识别中",
    2: "识别完成",
    3: "识别异常",
    4: "没有图片"
  };

  // 数据上报状态
  const reportStatusMap = {
    1: "上报完成", 2: "上报失败"
  };

  import {
    manualCheckStatus,
    manualCheckStatusMap,
    recheckStatus,
    recheckStatusMap,
    tableColumnWidth
  } from "../common/dataCustom";

  const moment = require("moment");
  import Carousel from "../components/carousel"
  export default {
    name: "RecheckSearch",
    components: {
      Carousel
    },
    data() {
      return {
        recheckStatus, //复核状态
        manualCheckStatus, //审核状态
        tableColumnWidth,  //表格列宽度

        // 主页标签控制参数
        labelPosition: "top",
        hideCondition: false,//跳转过来隐藏条件
        isList: true,
        isTableStyle: true, // true: 以表格形式展示, false: 以 medium icons 形式展示
        onExcelCheck: false, //仅导出表格
        applyExportRule: false, //应用导出规则
        exportNum: 1, // 导出条数
        exportProcess: 0,//打包进度
        exportLoading: false,
        loading: false,//加载状态

        // 复核结果详情页控制参数
        detailVisible: false,
        isDetailInfoLoading: false,//加载状态
        isLeftTipdisabled: false,//是否有上一页
        isRightTipdisabled: false,//是否有下一页
        detailInfoIdx: 0,//当前详情页索引
        activeNames: ["original", "discern"],
        dialogData: {},
        stepsList: [],
        tableData: [
          "-N697A9",//识别号牌号码
          [99, 99, 99, 99, 99, 99, 99]
        ],

        // 请求参数
        paramCol: {
          // 录入时间, 识别时间
          entryTimeRange: [this.tools.timeFormat(new Date(new Date(1585732024000).setHours(0, 0, 0, 0)), "dateTime"),
            this.tools.timeFormat(new Date(new Date(1588151224000).setHours(23, 59, 59, 0)), "dateTime")],
          recogTimeRange: [],
          recheckStatusName: "全部",      // 复核
          preRecheckStatus: [],
          manualCheckStatusName: "全部",  // 人工审核
          preManualCheckStatus: "",
          illegalCode: ""                // 违法类型编码
        },

        // 页面数据
        queryData: [],
        totalNum: 0,
        pageSize: 24,
        currentPage: 1,
        multipleSelection: [],  // 多选

        //设置组件SearchResult卡片展示字段
        SearchResultOpt: {
          checkType: 1,
          imgType: 1,
          check: true,
          waitCheck: true,
          moreInfo: true,
          carNum: true,
          illegalBehavior: true,
          carType: true,
          carColor: true,
          checkResult: true,
          passTime: true,
          passPlace: true,
          recogUnpassCode: true
        },

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
          ],
          recogTimeRange: [
            {
              required: false,
              message: "请选择时间",
              trigger: "blur"
            }
          ],
          illegalCode: [
            {
              required: false,
              message: "请输入违法编码",
              trigger: "blur"
            },
            {
              pattern: /^[A-Za-z0-9_]{1,20}$/,
              message: "违法编码有误"
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
    mounted() {
      this.query();
    },
    filters: {
      formatterManualCheckStatus(manualCheckStatus) {
        return manualCheckStatusMap[manualCheckStatus];
      }
    },
    methods: {
      //查询数据
      async query() {
        let _this = this;
        _this.queryData = [];//清空数据
        this.loading = true;
        const { entryTimeRange: [entryStartTime, entryEndTime], recogTimeRange: [recogStartTime, recogEndTime] } = this.paramCol;
        const { preRecheckStatus, preManualCheckStatus, illegalCode } = this.paramCol;
        //
        const { pageSize, currentPage } = this;
        // 拼接参数，兼容 python 后台接口
        let params = {
          pageSize,
          current: (currentPage - 1) * pageSize,
          start_time: entryStartTime, // 时间字符串 YYYY-MM-DD HH:mm:ss
          end_time: entryEndTime, // 时间字符串 YYYY-MM-DD HH:mm:ss
          recog_start_time: recogStartTime,  // 时间戳 1234567890123
          recog_end_time: recogEndTime, // 时间戳 1234567890123
          reason_code: preRecheckStatus,
          manual_check_status: preManualCheckStatus !== "" ? [preManualCheckStatus] : [],
          action: illegalCode
        };
        const resp = await this._services.recheckQuery(params, { method: "post" });
        _this.loading = false;
        const { code, total, data: { result } } = resp;
        if (parseInt(code, 10) !== 200) {
          _this.tools.message(resp.message, "error");
          return;
        }
        if (result.length === 0) {
          _this.tools.message("无数据", "warning");
          return;
        }
        _this.totalNum = total;
        // 响应结果处理
        for (const record of result) {
          const convertRecord = {
            id: record.id,
            entryTime: record.data_entry_time,
            recordId: record.src_record_id,
            manualCheckStatus: record.manual_check_status,
            srcCarPlateNumber: record.src_car_plate_number,
            srcCatPlateType: record.src_car_plate_type,
            srcIllegalCode: record.src_illegal_action,

            sdkCarPlateNumber: record.sdk_car_plate_number,
            sdkCarPlateType: record.sdk_car_plate_type,
            sdkPlateRect: record.sdk_plate_rect,
            sdkReasonCode: record.sdk_reason_code,
            sdkRecogTime: record.sdk_recog_time,
            sdkRecogStatus: record.recog_status,

            sdkPlateScores: record.plate_scores,
            carNumPicPath: record.car_num_pic_path,
            carNumPicUrl: record.car_num_pic_url,
            entryPerson: record.entry_person,

            reportStatus: record.report_status,
            reportTime: record.report_time
          };
          _this.queryData.push(convertRecord);
        }


        // 合成图放在首位，车牌特写图放在末尾
        // for (let i = 0; i < _this.queryData.length; i++) {
        //   if (_this.queryData[i].imageIdList) {
        //     if (_this.queryData[i].combinedPicId) {
        //       _this.queryData[i].imageIdList.unshift(
        //         _this.queryData[i].combinedPicId
        //       );
        //     }
        //     if (_this.queryData[i].carNumPicId) {
        //       _this.queryData[i].imageIdList.push(
        //         _this.queryData[i].carNumPicId
        //       );
        //     }
        //   }
        // }
        // 定时器
        // setTimeout(() => {
        //   let selectedData = _this.getSelectedData;
        //
        //   if (selectedData.length > 0) {
        //     if (!_this.isTableStyle) {
        //       _this.$refs.searchResult.getPageChange(
        //         _this.currentPage
        //       );
        //       _this.$refs.searchResult.checkedItems = [];
        //       if (selectedData[_this.currentPage - 1]) {
        //         selectedData[_this.currentPage - 1].map(
        //           i => {
        //             _this.queryData.map(data => {
        //               if (data.id === i) {
        //                 _this.$refs.searchResult.checkedItems.push(
        //                   i
        //                 );
        //               }
        //             });
        //           }
        //         );
        //         if (
        //           _this.$refs.searchResult.checkedItems
        //             .length === _this.queryData.length
        //         ) {
        //           _this.$refs.searchResult.allCheck = true;
        //         }
        //       }
        //     } else {
        //       _this.$refs.multipleTable.selection.splice(
        //         0,
        //         _this.$refs.multipleTable.selection.length
        //       );
        //       _this.multipleSelection = [];
        //       if (selectedData[_this.currentPage - 1]) {
        //         selectedData[_this.currentPage - 1].map(
        //           i => {
        //             _this.queryData.map(data => {
        //               if (data.id === i) {
        //                 _this.$refs.multipleTable.store.states.selection.push(
        //                   data
        //                 );
        //                 _this.multipleSelection.push(
        //                   i
        //                 );
        //               }
        //             });
        //           }
        //         );
        //       }
        //       if (
        //         _this.multipleSelection.length ===
        //         _this.queryData.length
        //       ) {
        //         _this.$refs.multipleTable.store.states.isAllSelected = true;
        //       }
        //     }
        //   }
        // }, 200);
      },

      //弹出详情
      async popDetail(row) {
        this.detailInfoIdx = this.queryData.findIndex(item => item.id === row.id);

        this.$refs["multipleTable"].setCurrentRow(row);//标识列表模式下当前项
        // this.$refs["searchResult"].parentToggle(this.detailInfoIdx);//标识卡片模式下当前项
        this.detailVisible = true;
        this.isDetailInfoLoading = true;
        this.dialogData = row;
        // 步骤条
        this.stepsList = [];
        const { entryPerson, entryTime, sdkRecogStatus, sdkRecogTime, reportStatus, reportTime } = this.dialogData;
        // 录入
        this.stepsList.push({
            title: "数据录入",
            description: `${entryPerson}\n${entryTime}`
          }
        );
        // 识别
        const recheckStatusName = recogStatusMap[sdkRecogStatus];
        if (recheckStatusName) {
          this.stepsList.push(
            {
              title: "AI复核",
              description:
                `${recheckStatusName}\n${sdkRecogTime}`
            }
          );
        }
        // 上报
        const reportStatusName = reportStatusMap[reportStatus];
        if (reportStatusName) {
          this.stepsList.push({
            title: "数据上报",
            description:
              `${reportStatusName}\n${reportTime}`
          });
        }
        //
        this.isDetailInfoLoading = false;

        // const resp = await this._services.illegalSearchDetail(
        //         {
        //             wfId: row.id
        //         },
        //         {
        //             method: "post"
        //         }
        //     )
        //     .then(res => {
        //         this.isDetailInfoLoading = false;
        //         if (parseInt(res.errorCode, 10) !== 0) {
        //             this.tools.message(res.message, "error");
        //             return;
        //         }
        //
        //         this.stepsList = [];
        //         let dataList = res.data;
        //         let uploadMsg = "";
        //
        //         this.$nextTick(() => {
        //             this.multipleSelection = [];
        //             if (dataList.manufacturerCodeName) {
        //                 this.stepsList.push({
        //                     code: 0,
        //                     title: "违法抓拍",
        //                     description:
        //                         dataList.manufacturerCodeName +
        //                         "\n" +
        //                         this.timeFormat(
        //                             dataList.snapshotTime,
        //                             "dateTime"
        //                         )
        //                 });
        //             }
        //             if (dataList.autoCheckStatus) {
        //                 let autoCheckStatus = "";
        //                 const aim = firStatusS.find(
        //                     i => i.code === dataList.autoCheckStatus
        //                 );
        //
        //                 if (aim && aim.name) {
        //                     autoCheckStatus = aim.name;
        //                 }
        //                 this.stepsList.push({
        //                     code: 1,
        //                     title: "AI预审",
        //                     description:
        //                         autoCheckStatus +
        //                         "\n" +
        //                         this.timeFormat(
        //                             dataList.autoCheckTime,
        //                             "dateTime"
        //                         )
        //                 });
        //             }
        //             if (dataList.firstCheckStatus) {
        //                 let firstCheckStatus = "";
        //                 let firstCheckOperater = "";
        //                 const aim = secStatusT.find(
        //                     i => i.code === dataList.firstCheckStatus
        //                 );
        //
        //                 if (aim && aim.name) {
        //                     firstCheckStatus = aim.name;
        //                 }
        //                 if (dataList.firstCheckOperater) {
        //                     firstCheckOperater =
        //                         dataList.firstCheckOperater;
        //                 } else {
        //                     firstCheckOperater = "";
        //                 }
        //                 this.stepsList.push({
        //                     code: 2,
        //                     title: "违法初审",
        //                     description:
        //                         firstCheckStatus +
        //                         "          " +
        //                         firstCheckOperater +
        //                         "\n" +
        //                         this.timeFormat(
        //                             dataList.firstCheckTime,
        //                             "dateTime"
        //                         )
        //                 });
        //             }
        //             if (dataList.secondCheckStatus) {
        //                 let secondCheckStatus = "";
        //                 let secondCheckOperater = "";
        //                 const aim = thirdStatusT.find(
        //                     i => i.code === dataList.secondCheckStatus
        //                 );
        //
        //                 if (aim && aim.name) {
        //                     secondCheckStatus = aim.name;
        //                 }
        //                 if (dataList.secondCheckOperater) {
        //                     secondCheckOperater =
        //                         dataList.secondCheckOperater;
        //                 } else {
        //                     secondCheckOperater = "";
        //                 }
        //                 this.stepsList.push({
        //                     code: 3,
        //                     title: "违法复审",
        //                     description:
        //                         secondCheckStatus +
        //                         "          " +
        //                         secondCheckOperater +
        //                         "\n" +
        //                         this.timeFormat(
        //                             dataList.secondCheckTime,
        //                             "dateTime"
        //                         )
        //                 });
        //             }
        //             if (row.uploadStatus !== 0) {
        //                 let releaseStatus = "";
        //                 let releaseOperater = "";
        //
        //                 const {uploadStatus, uploadMsg: uploadMsg2, uploadTime} = row;
        //
        //                 const aim = this.uploadReasonArr.find(
        //                     item => item.code === uploadStatus
        //                 );
        //                 let val = "-";
        //
        //                 if (aim && aim.name) {
        //                     val = aim.name;
        //                 }
        //                 // uploadStatus 不为0-8  取msg
        //                 if (uploadStatus < 0 || uploadStatus > 8) {
        //                     const {message: msg} = JSON.parse(uploadMsg2);
        //
        //                     val = msg;
        //                 }
        //                 uploadMsg = row.uploadStatus + ":" + val;
        //
        //                 releaseOperater = "";
        //                 this.stepsList.push({
        //                     code: 4,
        //                     title: "数据发布",
        //                     description:
        //                         uploadMsg +
        //                         "\n" +
        //                         this.timeFormat(
        //                             uploadTime,
        //                             "dateTime"
        //                         )
        //                 });
        //
        //             }
        //         });
        //         this.dialogData = row;
        //     });
      },

      // 复核状态条件改变
      handleRecheckStatusChange() {
        const { preRecheckStatus } = this.paramCol;
        const nameArray = this.recheckStatus
          .filter(i => preRecheckStatus.includes(i.code))
          .map(item => item.name);
        this.paramCol.recheckStatusName = [...nameArray].toString() || "全部";
      },
      // 审核状态条件改变
      handleManualCheckStatusChange() {
      },

      // 重置
      reset() {
        this.paramCol = {
          entryTimeRange: [this.tools.timeFormat(new Date(new Date().setHours(0, 0, 0, 0)), "dateTime"), this.tools.timeFormat(new Date(new Date().setHours(23, 59, 59, 0)), "dateTime")],
          recogTimeRange: [],
          recheckStatusName: "全部",
          preRecheckStatus: [],
          manualCheckStatusName: "全部",
          preManualCheckStatus: "",
          illegalCode: ""
        };
      },

      //设置选中项
      select(val) {
        this.multipleSelection = [];
        val.map(i => {
          this.multipleSelection.push(i.id);
        });
        // this.updateStore();//更新storex存储数据
      },

      //全选
      selectAll(val) {
        this.multipleSelection = [];
        val.map(i => {
          this.multipleSelection.push(i.id);
        });
        // this.updateStore();//更新storex存储数据
      },

      //获取卡片子组件的选中项
      getSelectionChange(val) {
        this.multipleSelection = [];
        this.$refs.multipleTable.clearSelection();
        val.map(i => {
          this.queryData.map(data => {
            if (data.id === i) {
              this.$refs.multipleTable.toggleRowSelection(data, true);
            }
          });
          this.multipleSelection.push(i);
        });
      },

      //表格翻页
      handleCurrentChange(pagenum) {
        // this.detailInfoIdx = 0;
        this.currentPage = pagenum;
        this.query();
      },

      // 处理时间格式
      formatterTime(row, column, cellValue) {
        return this.timeFormat(cellValue, "dateTime");
      },
      timeFormat(val, type) {
        return this.tools.timeFormat(val, type);
      },

      // 处理识别结果编码
      formatterSdkReasonCode(row, column, cellValue) {
        return recheckStatusMap[cellValue];
      },
      handleSdkReasonCode(sdkReasonCode) {
        return recheckStatusMap[sdkReasonCode];
      },

      // 处理审核状态编码
      formatterManualCheckStatus(row, column, cellValue) {
        return manualCheckStatusMap[cellValue];
      },

      // 详情信息切换
      detailInfoToggle(type) {
        const nextIdx = type === "next" ? this.detailInfoIdx + 1 : this.detailInfoIdx - 1;
        // nextIdx 超出范围时关闭对话框
        if (nextIdx < 0 || nextIdx >= this.queryData.length) {
          this.detailVisible = false;
        } else {
          this.popDetail(this.queryData[nextIdx]);
        }
      },

      // 手动审核
      async manualCheck(manualCheckStatus) {
        // 拼接参数，兼容 python 后台接口
        let params = {
          ids: [this.dialogData.id],
          manual_check_status: manualCheckStatus
        };
        const resp = await this._services.manualCheck(params, { method: "patch" });
        const { code } = resp;
        if (code === 200) {
          // 刷新详情页数据
          this.dialogData.manualCheckStatus = manualCheckStatus;
          // 刷新表格中数据
          for (const record of this.queryData) {
            if (record.id === this.dialogData.id) {
              record.manualCheckStatus = manualCheckStatus;
            }
          }
          // 提示操作成功
          this.tools.message("操作成功", "success");
          // 跳转到下一条
          this.detailInfoToggle("next");
        }
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
