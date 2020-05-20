/**
 * Created by ck
 * User: chenkai@seemmo.com
 * Date: 2020/05/19
 * Describe:
 * Log:
 */
import { request } from './serviceBase';
const urlHttps = window.location.origin;

// const urlHttps = window.location.origin;
// const gisService = window.location.origin + '/map/';

// window.GISService = gisService;
// window.ctxPath = urlHttps;


// export const URLS = {
    // gettollgateinfo: urlHttps + '/business/gettollgateinfo.php',//卡口
    // bs: urlHttps + '/client/bs.php',
    // push: urlHttps + '/common/push.php',
    // role: urlHttps + '/business/basic/role/get'
// };

const commonQuery = {
    //复核查询
    recheckQuery(params, axiosParams) {
        return request(urlHttps + '/api/results', params, axiosParams, { method: 'post' });
    },
    // statisticsQuery(params, axiosParams) {
    //     return request(urlHttps + '/business/illegalsearch/data', params, axiosParams, { method: 'post' });
    // }
};

export default {
    // 复核查询、统计查询的接口
    ...commonQuery,
}


// const deviceManage = {
    // imageLocation: urlHttps + '/business/image/get',
    //设备导入模板
    // importDeviceTreeUrl: urlHttps + '/system/device/import/template',
    //导入测试文件
    // systemTaskUploadUrl: urlHttps + '/test/task/upload/async',
    // 导入设备列表的url
    // systemDeviceImportUrl: urlHttps + '/system/device/import',
    // signDeviceImportUrl: urlHttps + '/system/device/import',
    // deviceRecordImportUrl: urlHttps + '/system/config/record/import',
    //待标注文件导入
    // notMarkImportUrl: urlHttps + '/system/device/records/import',
    //已标注文件导入
    // markedImportUrl: urlHttps + '/system/device/mark/import',

//     getProjectList(params, axiosParams) {
//         return request(urlHttps + '/system/project/list', params, axiosParams);
//     },
//     addProjectList(params) {
//         return request(urlHttps + '/system/project/add', params, { method: 'post' });
//     },
//     getDeviceTree(params, axiosParams) {
//         return request(urlHttps + '/system/device/tree', params, axiosParams);
//     },
//     addDevice(params) {
//         return request(urlHttps + '/system/device/add', params, { method: 'post' });
//     },
//     getDeviceDetail(params, axiosParams) {
//         return request(urlHttps + '/system/device/detail', params, axiosParams);
//     },
//     deleteDevice(params, axiosParams) {
//         return request(urlHttps + '/system/device/delete', params, axiosParams);
//     },
//     updateDevice(params) {
//         return request(urlHttps + '/system/device/update', params, { method: 'post' });
//     },
//     getDeviceImageList(params, axiosParams) {
//         return request(urlHttps + '/system/device/imagelist', params, axiosParams);
//     },
//     getDeviceIllegallist(params, axiosParams) {
//         return request(urlHttps + '/system/device/illegallist', params, axiosParams);
//     },
//     getDeviceCarWayCodes(params, axiosParams) {
//         return request(urlHttps + '/system/device/get', params, axiosParams);
//     },
//     getLabelpageList(params, axiosParams) {
//         return request(urlHttps + '/test/illegal/labelpage/list', params, axiosParams);
//     },
//     deleteIllegal(params) {
//         return request(urlHttps + '/test/illegal/delete', params, { method: 'post' });
//     },
//     // getTestTaskList(params, axiosParams) {
//     //     return request(urlHttps + '/test/task/list', params, axiosParams);
//     // },
//     example(params) {
//         return request(urlHttps + '/system/device/export', params);
//     },
//     taskList(params, axiosParams) {
//         return request(urlHttps + '/test/task/list', params, axiosParams);
//     },
//     taskDelete(params, axiosParams) {
//         return request(urlHttps + '/test/task/delete', params, axiosParams);
//     },
//     taskGet(params, axiosParams) {
//         return request(urlHttps + '/test/task/get', params, axiosParams);
//     },
//     taskCheck(params) {
//         return request(urlHttps + '/test/task/check', params, { method: 'post' });
//     },
//     illegalDelete(params) {
//         return request(urlHttps + '/test/illegal/delete', params, { method: 'post' });
//     },
//     // 获取厂商字典
//     factoryList(params, axiosParams) {
//         return request(urlHttps + '/system/config/company/list', params, axiosParams);
//     },
//     // 赋值车道信息
//     copyMark: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/copymark', params, axiosParams);
//     },
//     // 修改当前项目
//     updataProject: function (params, axiosParams) {
//         return request(urlHttps + '/system/project/update', params, axiosParams);
//     },
//     // 以路径导入待标注文件
//     importNotMark: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/records/import', params, axiosParams);
//     },
//     // 以路径导入已标注文件
//     importMarked: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/mark/import', params, axiosParams);
//     },
//     //导入设备
//     importDevice: function (params) {
//         return request(urlHttps + '/system/device/import', params, { method: 'post' });
//     },
//     //导入设备进度查询
//     importDeviceProgress: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/importprocess', params, axiosParams);
//     },
//     //待标注文件导出
//     notMarkExport: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/records/export', params, axiosParams);
//     },
//     //待标注文件导出进度
//     notMarkExportProgress: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/records/exportprocess', params, axiosParams);
//     },
//     //待标注文件导入进度
//     notMarkImportProgress: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/records/importprocess', params, axiosParams);
//     },
//     //已标注文件导入进度
//     markedImportProgress: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/mark/importprocess', params, axiosParams);
//     },
//     //已标注文件导出
//     markedExport: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/mark/export', params, axiosParams);
//     },
//     //已标注文件导出进度
//     markedExportProgress: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/mark/exportprocess', params, axiosParams);
//     },
//     //修改标注信息
//     updatemarkinfo: function (params) {
//         return request(urlHttps + '/system/device/updatemarkinfo', params, { method: 'post' });
//     },
//     //预审测试下载模板
//     getImportTemplet: function (params) {
//         return request(urlHttps + '/test/task/getImportTemplet', params);
//     },
//     //切割图片
//     splitImage: function (params) {
//         return request(urlHttps + '/business/image/splitImage', params, { method: 'post' });
//     },
//     //获取标注的数据
//     getmarkinfo: function (params) {
//         return request(urlHttps + '/system/device/getmarkinfo', params);
//     },
//     //获取标注的数据(现场)
//     getmarkinfoline: function (params) {
//         return request(urlHttps + '/system/device/getmarkinfoline', params);
//     },
//     //获取标注的数据
//     exportcustom: function (params) {
//         return request(urlHttps + '/system/device/mark/exportcustom', params);
//     },
//     //获取标注的数据
//     deleteProject: function (params) {
//         return request(urlHttps + '/system/project/delete', params, { method: 'post' });
//     },
//     //查询某个项目的全局配置
//     getDeviceConfig: function (params) {
//         return request(urlHttps + '/system/device/config/list', params, { method: 'get' });
//     },
//     //保存为全局配置
//     saveDeviceConfig: function (params) {
//         return request(urlHttps + '/system/device/updatemarkconfig', params, { method: 'post' });
//     },
//     //新增某项全局配置
//     addDeviceConfig: function (params) {
//         return request(urlHttps + '/system/device/config/add', params, { method: 'post' });
//     },
//     //删除某个全局配置
//     deleteDeviceConfig: function (params) {
//         return request(urlHttps + '/system/device/config/delete', params, { method: 'get' });
//     },
//     //查询可复制标线的设备列表
//     getMarkableDevice: function (params) {
//         return request(urlHttps + '/system/device/markrela/list', params, { method: 'post' });
//     },
//     //修改 标注信息关联的设备
//     updateMarkableDevice: function (params) {
//         return request(urlHttps + '/system/device/markrela/update', params, { method: 'post' });
//     },
//     //查询 标注信息关联的设备
//     getCollectDevices: function (params) {
//         return request(urlHttps + '/system/device/markrela/get', params, { method: 'post' });
//     },
//     //查询进度
//     getProcess: function (params) {
//         return request(urlHttps + '/system/task/process', params, { method: 'get' });
//     },
// };

// 首页和统计的接口
// const homeAndStatistics = {
//     getErrorReason(params) {
//         return request(urlHttps + '/business/statistics/error/reason', params, { method: 'get' });
//     },
//     recogDetail(params) {
//         return request(urlHttps + '/business/statistics/recog/detail', params, { method: 'get' });
//     },
//     recoginfo(params) {
//         return request(urlHttps + '/business/statistics/recoginfo', params, { method: 'get' });
//     },
//     detail(params) {
//         return request(urlHttps + '/business/statistics/detail', params);
//     },
//     audittrend(params) {
//         return request(urlHttps + '/business/statistics/audittrend', params);
//     },
//     index(params) {
//         return request(urlHttps + '/business/statistics/index', params, { method: 'get' });
//     },
//     waiter(params) {
//         return request(urlHttps + '/business/statistics/waiter', params);
//     },
//     errorRank(params) {
//         return request(urlHttps + '/business/illegalstatistics/errorrank', params, { method: 'post' });
//     },
//     multiple(params) {
//         return request(urlHttps + '/business/illegalstatistics/multiple', params, { method: 'post' });
//     },
//     illegalUploadData(params) {
//         return request(urlHttps + '/business/illegalstatistics/upload', params, { method: 'post' });
//     },
//     getIllegalCountlist(params) {
//         return request(urlHttps + '/business/illegalstatistics/illegalCountList', params, { method: 'post' });
//     },
//     getTitleIcon(params) {
//         return request(urlHttps + '/system/config/configinfo/getjson', params, { method: 'get' });
//     },
//     //导出
//     illegalCountExport(params) {
//         return request(urlHttps + '/business/illegalstatistics/illegalCountExport', params, { method: 'post' });
//     },
//     //查询导出进度
//     getExportProgress(params) {
//         return request(urlHttps + '/business/illegalstatistics/exportprocess', params, { method: 'get' });
//     },
//     //查询设备统计
//     getDeviceStatistics(params) {
//         return request(urlHttps + '/business/statistics/device', params, { method: 'get' });
//     },
// };

// 违法查询、预审测试的接口
// const searchAndCheckTest = {
//     //违法查询
//     illegalSearch(params, axiosParams) {
//         return request(urlHttps + '/business/illegalsearch/data', params, axiosParams);
//     },
    // illegalSearchDetail(params, axiosParams) {
    //     return request(urlHttps + '/business/illegalsearch/detail', params, axiosParams);
    // },
    // userGetpreorreusers(params, axiosParams) {
    //     return request(urlHttps + '/business/basic/user/getpreorreusers', params, axiosParams);
    // },
    // illegalsearchExportAdd(params, axiosParams) {
    //     return request(urlHttps + '/business/illegalsearch/export/add', params, { method: 'post' });
    // },
    // illegalsearchExportGet(params, axiosParams) {
    //     return request(urlHttps + '/business/illegalsearch/export/get', params, axiosParams);
    // },
    // //任务中断恢复
    // proceedrecog(params) {
    //     return request(urlHttps + '/test/task/executor/proceedrecog', params, { method: 'post' });
    // },
    // getnew(params, axiosParams) {
    //     return request(urlHttps + '/test/task/executor/getnew', params, axiosParams);
    // },
    // //新建测试任务
    // addRecords(params) {
    //     return request(urlHttps + '/test/task/addrecords', params, { method: 'post' });
    // },
    // //预审测试结果
    // resultpageStatistics(params, axiosParams) {
    //     return request(urlHttps + '/test/illegal/resultpage/statistics', params, axiosParams);
    // },
    // resultpagelList(params, axiosParams) {
    //     return request(urlHttps + '/test/illegal/resultpage/list', params, axiosParams);
    // },
    // //预审测试结果 弹窗
    // taskDetail(params, axiosParams) {
    //     return request(urlHttps + '/test/task/stat/detail', params, { method: 'get' });
    // },
    // //预审测试标注
    // labelpageList(params, axiosParams) {
    //     return request(urlHttps + '/test/illegal/labelpage/list', params, axiosParams);
    // },
    // //预审测试图片下载
    // imagepageList(params, axiosParams) {
    //     return request(urlHttps + '/test/illegal/imagepage/list', params, axiosParams);
    // },
    // taskExecutorAdd(params, axiosParams) {
    //     return request(urlHttps + '/test/task/executor/add', params, axiosParams);
    // },
    // taskExecutorGet(params, axiosParams) {
    //     return request(urlHttps + '/test/task/executor/get', params, axiosParams);
    // },
    // //创建样本集
    // sampleCreate(params) {
    //     return request(urlHttps + '/test/sample/create', params, { method: 'post' });
    // },
    // //样本删除
    // sampleDelete(params) {
    //     return request(urlHttps + '/test/sample/delete', params, { method: 'get' });
    // },
    // //样本参数配置
    // sampleUpdate(params) {
    //     return request(urlHttps + '/test/sample/update', params, { method: 'post' });
    // },
    // //样本重新分析
    // sampleReAnalyze(params) {
    //     return request(urlHttps + '/test/task/executor/add', params, { method: 'post' });
    // },
    // //样本列表查询
    // sampleList(params) {
    //     return request(urlHttps + '/test/sample/listPage', params, { method: 'post' });
    // },
    // //样本详情列表查询
    // sampleDetailList(params) {
    //     return request(urlHttps + '/test/sample/result/listPage', params, { method: 'post' });
    // },
    // //样本详情列表删除
    // sampleDetailDelete(params) {
    //     return request(urlHttps + '/test/sample/result/delete', params, { method: 'get' });
    // },
    // //预审结果 导出
    // taskExport(params) {
    //     return request(urlHttps + '/test/sample/result/export', params, { method: 'post' });
    // },
    // //预审结果 导出
    // taskExportStatus(params) {
    //     return request(urlHttps + '/test/sample/result/exportprocess', params, { method: 'get' });
    // },
    // //重置初审状态
    // resetStatus(params) {
    //     return request(urlHttps + '/test/sample/result/changeCheckStatus', params, { method: 'post' });
    // },
    // //*路口异常数据屏蔽
    // //获取异常路口配置列表
    // getAbnormalRoad(params) {
    //     return request(urlHttps + '/system/config/missRoad/list', params, { method: 'get' });
    // },
    // //删除异常设备
    // deleteAbnormalRoad(params) {
    //     return request(urlHttps + '/system/config/missRoad/delete', params, { method: 'get' });
    // },
    // //添加异常设备
    // addAbnormalRoad(params) {
    //     return request(urlHttps + '/system/config/missRoad/add', params, { method: 'post' });
    // },
    // //关闭异常设备
    // closeAbnormalRoad(params) {
    //     return request(urlHttps + '/system/config/missRoad/close', params, { method: 'get' });
    // },
    // //关闭异常设备
    // getAbnormalRoadList(params) {
    //     return request(urlHttps + '/system/config/missRoad/roadList', params, { method: 'get' });
    // },
    // //添加异常路口
    // addLimitRoad(params) {
    //     return request(urlHttps + '/system/config/limit/add', params, { method: 'post' });
    // },
    // //添加抽样
    // createSample(params) {
    //     return request(urlHttps + '/business/illegalsearch/sample/create', params, { method: 'post' });
    // },
    // //精准化创建样本
    // exactcreate(params) {
    //     return request(urlHttps + '/test/sample/exactcreate', params, { method: 'get' });
    // },
    // //预审测试路径导入文件
    // urlPretestImport(params) {
    //     return request(urlHttps + '/test/task/upload/backend', params, { method: 'post' });
    // },
    // //预审测试路径导入文件进度
    // urlPretestImportProgress(params) {
    //     return request(urlHttps + '/test/task/progress', params, { method: 'get' });
    // },
// };

// 基础配置
// const baseConfig = {
//     // 获取数据发布配置
//     dataPushGet(params) {
//         return request(urlHttps + '/system/config/push/get', params);
//     },
//     // 数据发布配置更改
//     dataPushChange(params) {
//         return request(urlHttps + '/system/config/push/change', params, { method: 'POST' });
//     },
//     // 查询配置列表
//     getSystemList(params) {
//         return request(urlHttps + '/system/config/configinfo/list', params, { method: 'get' });
//     },
//     // 更新配置
//     updateSystemConfig(params) {
//         return request(urlHttps + '/system/config/configinfo/update', params, { method: 'POST' });
//     },
//     // 获取初审状态异常列表
//     getAbnormalList(params) {
//         return request(urlHttps + '/system/config/recogstatus/get', params, { method: 'get' });
//     },
//     // 保存配置
//     saveAbnormalList(params) {
//         return request(urlHttps + '/system/config/recogstatus/save', params, { method: 'post' });
//     },
//     // 重置成未初审状态
//     resetAbnormalStatus(params) {
//         return request(urlHttps + '/system/config/recogstatus/reset', params, { method: 'get' });
//     },
//     // 查询重置状态进度
//     searchTaskStatus(params) {
//         return request(urlHttps + '/system/config/recogstatus/process', params, { method: 'get' });
//     },
//     //获取限行配置列表
//     getLimtlist(params) {
//         return request(urlHttps + '/system/config/limit/list', params, { method: 'get' });
//     },
//     //获取路口名称列表
//     getRoadList(params) {
//         return request(urlHttps + '/system/config/limit/roadList', params, { method: 'get' });
//     },
//     //获取设备列表
//     getDeviceCodeList(params) {
//         return request(urlHttps + '/system/config/missRoad/deviceCodeList', params, { method: 'post' });
//     },
//     //删除限行
//     deleteLimit(params) {
//         return request(urlHttps + '/system/config/limit/delete', params, { method: 'get' });
//     },
//     //关闭限行
//     closeLimit(params) {
//         return request(urlHttps + '/system/config/limit/close', params, { method: 'get' });
//     },
// };


// 任务调度
// const crontaskUrls = {
//     // getAllJobs: '/system/crontask/getAllJobs',
//     getJobDetails: '/system/crontask/getJobDetailsByTaskId',
//     addJob: '/system/crontask/addJob',
//     updateJob: '/system/crontask/updateJob',
//     deleteJob: '/system/crontask/deleteJob',
//     pauseJob: '/system/crontask/pauseJob',
//     resumeJob: '/system/crontask/resumeJob',
//     getJob: '/system/crontask/getJob',
//     executeJobNow: '/system/crontask/executeJobNow',
//     searchJob: '/system/crontask/searchJob'
// };

// function crontaskRequest(params, type) {
//     let url = urlHttps + crontaskUrls[type];
//     let method = 'GET';
//
//     if (type === 'updateJob' || type === 'addJob') {
//         method = 'POST';
//     }
//     if (type === 'updateJob' || type === 'searchJob') {
//         url = urlHttps + crontaskUrls[type]
//     }
//     return request(url, params, { method: method });
// }


// export default {
//
//     // 用户登录
//     getDeptList: function (params) {
//         return request(urlHttps + '/business/basic/dept/list', params, { method: 'get' });
//     },
//     // 用户登录
//     login: function (params, axiosParams) {
//         return request(urlHttps + '/business/basic/user/login', params, axiosParams);
//     },
//     // 用户退出
//     logout: function (params, axiosParams) {
//         return request(urlHttps + '/business/basic/user/logout', params, axiosParams);
//     },
//     // 获取登陆态
//     isLogin: function (params, axiosParams) {
//         return request(urlHttps + '/business/basic/user/islogin', params, axiosParams);
//     },
//     // 获取菜单
//     getMenu: function (params, axiosParams) {
//         return request(urlHttps + '/business/basic/menu/get', params, axiosParams);
//     },
//     // 获取违法审核查询结果
//     getCheckResult: function (params, axiosParams) {
//         return request(urlHttps + '/test/illegal/checkpage/list', params, axiosParams);
//     },
//     // 违法测试审核修改
//     illegalCheck: function (params, axiosParams) {
//         return request(urlHttps + '/test/illegal/checkpage/check', params, axiosParams);
//     },
//     // 违法初审数据查询
//     firstCheckQuery: function (params, axiosParams) {
//         return request(urlHttps + '/business/illegalcheck/firstquery', params, axiosParams);
//     },
//     // 违法初审修改数据
//     firstCheck: function (params, axiosParams) {
//         return request(urlHttps + '/business/illegalcheck/firstcheck', params, axiosParams);
//     },
//     // 违法复核数据查询
//     secondCheckQuery: function (params, axiosParams) {
//         return request(urlHttps + '/business/illegalcheck/secondquery', params, axiosParams);
//     },
//     // 违法复核修改数据
//     secondCheck: function (params, axiosParams) {
//         return request(urlHttps + '/business/illegalcheck/secondcheck', params, axiosParams);
//     },
//     // 获取带有框线的图片
//     getImgPoint: function (params) {
//         return request(urlHttps + '/business/image/get', params, { method: 'get' });
//     },
//     // 获取动态图
//     getDynamicImage: function (params) {
//         return request(urlHttps + '/business/image/getDynamicImage', params, { method: 'get' });
//     },
//
//     // 删除任务
//     deleteTask: function (params, axiosParams) {
//         return request(urlHttps + 'test/task/delete', params, axiosParams);
//     },
//     // 违法地点
//     getIllegalPlace: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/listroadname', params, axiosParams);
//     },
//     // 违法地点 1.1.8
//     getIllegalPlaceTree: function (params, axiosParams) {
//         return request(urlHttps + '/system/device/road_select_tree', params, axiosParams);
//     },
//     getStatistics: function (params, axiosParmas) {
//         return request(URLS.statistics, params, axiosParmas);
//     },
//     getBSLogic: function (params, axiosParmas) {
//         return request(URLS.bs, params, axiosParmas);
//     },
//     getPushLogic: function (params, axiosParmas) {
//         return request(URLS.push, params, axiosParmas);
//     },
//     getGettollgateinfo: function (params, axiosParmas) {
//         return request(URLS.gettollgateinfo, params, axiosParmas);
//     },
//     getRole: function () {
//         return request(URLS.role);
//     },
//     // 获取用户详情
//     getUserDetail: function (params) {
//         return request(urlHttps + '/business/basic/user/detail', params, { method: 'get' });
//     },
//     // 获取用户
//     getUser: function (params) {
//         return request(urlHttps + '/business/basic/user/search', params, { method: 'POST' });
//     },
//     // 新增用户
//     userAdd: function (params) {
//         return request(urlHttps + '/business/basic/user/add', params, { method: 'POST' });
//     },
//     // 删除用户
//     userDelete: function (params) {
//         return request(urlHttps + '/business/basic/user/delete', params);
//     },
//     // 更新用户
//     userUpdate: function (params) {
//         return request(urlHttps + '/business/basic/user/update', params, { method: 'POST' });
//     },
//     // 查询用户
//     userSearch: function (params) {
//         return request(urlHttps + '/business/basic/user/search', params, { method: 'POST' });
//     },
//     // 更新密码
//     updatePasswd: function (params) {
//         return request(urlHttps + '/business/basic/user/updatepassword', params, { method: 'POST' });
//     },
//     // 重置密码
//     resetPsd: function (params) {
//         return request(urlHttps + '/business/basic/user/resetpassword', params);
//     },
//     // 查询系统日志
//     sysLogSearch(params) {
//         return request(urlHttps + '/business/basic/systemlog/search', params);
//     },
//     // 新增操作日志
//     sysLogAdd(params) {
//         return request(urlHttps + '/business/basic/systemlog/add', params, { method: 'POST' });
//     },
//     //获取车管所数据
//     getVehicleData(params) {
//         return request(urlHttps + '/business/vehicledata/get', params, { method: 'GET' });
//     },
//     //获取车管所图片
//     getVehicleImage(params) {
//         return request(urlHttps + '/business/vehicledata/getImage', params, { method: 'GET' });
//     },
//
//     // 任务调度
//     crontaskRequest,
//     // 设备管理
//     ...deviceManage,
//     // 首页和统计的接口
//     ...homeAndStatistics,
//     // 违法查询、预审测试的接口
//     ...searchAndCheckTest,
//     // 基础配置
//     ...baseConfig
// };
