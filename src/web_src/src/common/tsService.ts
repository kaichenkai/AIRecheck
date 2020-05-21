/**
 * Created by jintao
 * User: jintao@seemmo.com
 * Date: 2019/2/5
 * Describe:
 * Log:
 *  ---- 2019/2/5 17:54 [jintao] 初次添加
 **/
import { request } from './tsServiceBase';


const urlHttps = window.location.origin;

const deviceManage: any = {
    getDeviceTree: { url: urlHttps + '/system/device/tree', method: 'get' },
    getDeviceTree3: { url: urlHttps + '/system/device/tree3', method: 'get' },
    getCollectDevices: { url: urlHttps + '/system/device/markrela/get', method: 'post' },
    getDeviceConfig: { url: urlHttps + '/system/device/config/list', method: 'get' },
    getCarwayList: { url: urlHttps + '/system/device/get', method: 'post' },
    deleteProject: { url: urlHttps + '/system/project/delete', method: 'post' },
    //导入设备进度查询
    deviceImportProgress: { url: urlHttps + '/system/device/importprocess', method: 'get' },
    //已标注文件导入进度
    markedImportProgress: { url: urlHttps + '/system/device/mark/importprocess', method: 'get' },
    //待标注文件导入进度
    waitMarkImportProgress: { url: urlHttps + '/system/device/records/importprocess', method: 'get' },
    // 以路径导入待标注文件
    importWaitMark: { url: urlHttps + '/system/device/records/import', method: 'post' },
    // 以路径导入已标注文件
    importMarked: { url: urlHttps + '/system/device/mark/import', method: 'post' },

   //上次待标注数据导入时间
    waitMarkDataImportTime: { url: urlHttps + '/system/device/get_markexport_starttime', method: 'get' },
    //已标注文件导出
    markedFileExport: { url: urlHttps + '/system/device/mark/export', method: 'get' },
    //已标注文件导出进度
    markedFileExportProgress: { url: urlHttps + '/system/device/mark/exportprocess', method: 'get' },

    //算法标注格式导出
    algorithmFileExport: { url: urlHttps + '/system/device/mark/exportcustom', method: 'get' },
    algorithmFileExportProgress: { url: urlHttps + '/system/device/mark/exportprocess', method: 'get' },

    //待标注文件导出
    waitMarkFileExport: { url: urlHttps + '/system/device/records/export', method: 'post' },
    waitMarkFileExportProgress: { url: urlHttps + '/system/device/records/exportprocess', method: 'get' },
    //无法标注设备列表导出
    canotMarkDeviceListExport: { url: urlHttps + '/system/device/exportnomark', method: 'post' },
    canotMarkDeviceListExportProgress: { url: urlHttps + '/system/device/records/exportprocess', method: 'get' },
    getDeviceDetail: { url: urlHttps + '/system/device/detail2', method: 'get' },
    //获取标注的数据
    getmarkinfo: { url: urlHttps + '/system/device/getmarkinfo', method: 'get' },
    //获取无法标注原因
    getCannotMarkReason: { url: urlHttps + '/system/device/get_nomark_reason', method: 'get' },
    //更新无法标注原因
    updateCannotMarkReason: { url: urlHttps + '/system/device/update_nomark_reason', method: 'post' },
    //更新标注信息
    updateMarkInfo: { url: urlHttps + '/system/device/updatemarkinfo', method: 'post' },
    //切割图片
    splitImage: { url: urlHttps + '/business/image/splitImage', method: 'post' },
    //保存为全局配置
    saveDeviceConfig: { url: urlHttps + '/system/device/updatemarkconfig', method: 'post' },
    //新增某项全局配置
    addDeviceConfig: { url: urlHttps + '/system/device/config/add', method: 'post' },
    //获取标注的数据(现场)
    getmarkinfoline: { url: urlHttps + '/system/device/getmarkinfoline', method: 'get' },
    // 获取厂商字典
    factoryList: { url: urlHttps + '/system/config/company/list', method: 'get' },
    // 添加设备
    addDevice: { url: urlHttps + '/system/device/add', method: 'post' },
    // 删除设备
    deleteDevice: { url: urlHttps + '/system/device/delete', method: 'get' },
    //查询删除进度
    getProcess: { url: urlHttps + '/system/task/process', method: 'get' },
    //查询可复制标线的设备列表
    getMarkableDevice: { url: urlHttps + '/system/device/markrela/list', method: 'post' },
    //修改 标注信息关联的设备
    updateMarkableDevice: { url: urlHttps + '/system/device/markrela/update', method: 'post' },
    //获取某种违法的30条记录
    getmarkimagelist: { url: urlHttps + '/system/device/getmarkimagelist', method: 'get' },
    //获取单个设备的违法切图配置
    getSingleIllegalConfig: { url: urlHttps + '/system/device/getconfiglist', method: 'get' },
    //跟据算法获取坐标
    getCoordinateCore: { url: urlHttps + '/system/device/markconfig/get_from_core', method: 'post' },
    //更新设备
    updateDevice: { url: urlHttps + '/system/device/update', method: 'post' },
    //分片上传预检测
    preimport: { url: urlHttps + '/system/device/mark_split_import/preimport', method: 'post' },
    //分片上传
    chunkImport: { url: urlHttps + '/system/device/mark_split_import/import', method: 'post' },
    //通知合并分片
    chunkMergeNotify: { url: urlHttps + '/system/device/mark_split_import/importmerge', method: 'post' },
    // //分片上传预检测
    // preimport: { url: urlHttps + '/test/preimport', method: 'post' },
    // //分片上传
    // chunkImport: { url: urlHttps + '/test/import', method: 'post' },
    // //通知合并分片
    // chunkMergeNotify: { url: urlHttps + '/test/importmerge', method: 'post' },

}
const personalIndex = {
}

const exportRulesConfig = {
    getExportRules: { url: urlHttps + '/system/dictionary/image/rule', method: 'get' },
    setExportRules: { url: urlHttps + '/system/dictionary/image/update', method: 'post' },
    resetExportRules: { url: urlHttps + '/system/dictionary/image/default', method: 'get' },
}

const initial = {
    // 违法初审数据查询
    firstCheckQuery: { url: urlHttps + '/business/illegalcheck/search', method: 'get' },
    // 违法初审修改数据
    firstCheck: { url: urlHttps + '/business/illegalcheck/firstcheck', method: 'post' },
    //获取车管所数据
    getVehicleData: { url: urlHttps + '/business/vehicledata/get', method: 'get' },
    //添加申请
    addApplication: { url: urlHttps + '/business/application/addnew', method: 'post' },
    //获取进度
    getApplicationProgress: { url: urlHttps + '/business/application/progress', method: 'post' },
    //查询申请记录
    searchApplication: { url: urlHttps + '/business/application/list', method: 'get' },
    //提交申请
    submitApplication: { url: urlHttps + '/business/relation/submit', method: 'get' },
    //获取车管所图片
    getVehicleImage: { url: urlHttps + '/business/vehicledata/getImage', method: 'get' },
    //获取审核详细
    getCheckDetail: { url: urlHttps + '/business/application/detail', method: 'get' },
    //判断有无未提交的任务
    checkMission: { url: urlHttps + '/business/application/check', method: 'get' },
}

const confirm = {
    // 违法复核数据查询
    secondCheckQuery: { url: urlHttps + '/business/illegalcheck/search', method: 'get' },
    // 违法复核修改数据
    secondCheck: { url: urlHttps + '/business/illegalcheck/secondcheck', method: 'post' },
}

const passportFilter = {
    //获取通行证列表
    getPassportList: { url: urlHttps + '/system/config/pass/list', method: 'get' },
    //添加通行证列表
    addPassport: { url: urlHttps + '/system/config/pass/add', method: 'post' },
    //删除通行证列表
    deletePassport: { url: urlHttps + '/system/config/pass/delete', method: 'get' },
    //导出通行证列表
    exportPassport: { url: urlHttps + '/system/config/pass/export', method: 'get' },
    //导出通行证列表
    importPassport: { url: urlHttps + '/system/config/pass/import', method: 'post' },
    //通行证列表详细
    detailPassport: { url: urlHttps + '/system/config/pass_detail/list', method: 'post' },
    //通行证列表详细添加
    detailPassportAdd: { url: urlHttps + '/system/config/pass_detail/add', method: 'post' },
    //通行证列表详细更新
    detailPassportUpdate: { url: urlHttps + '/system/config/pass_detail/update', method: 'post' },
    //通行证列表详细删除
    detailPassportDelete: { url: urlHttps + '/system/config/pass_detail/delete', method: 'post' },
}

const illegalDataUpload = {
    submitUploadConfig: { url: urlHttps + '/system/config/push/submit', method: 'post' },
    getUploadConfig: { url: urlHttps + '/system/config/push/list', method: 'get' },
    closeUploadConfig: { url: urlHttps + '/system/config/push/close', method: 'get' },
    deleteUploadConfig: { url: urlHttps + '/system/config/push/deletetask', method: 'get' },
    getUploadCount: { url: urlHttps + '/system/config/push/count', method: 'post' },
}

const prePerform = {
    prePerformTest: { url: urlHttps + '/system/preview/test', method: 'get' },
    getPreStatus: { url: urlHttps + '/system/preview/switch/get', method: 'get' },
    switchPreStatus: { url: urlHttps + '/system/preview/switch/change', method: 'get' },
    prePerformStep1: { url: urlHttps + '/system/preview/loadproject', method: 'get' },
    prePerformStep2: { url: urlHttps + '/system/preview/loadmark', method: 'get' },

}

const deviceTypeConfig = {
    getVendorArr: { url: urlHttps + '/system/config/company/list', method: 'get' },
    getDeviceTypeList: { url: urlHttps + '/system/config/device_type/list', method: 'get' },
    addDeviceType: { url: urlHttps + '/system/config/device_type/add', method: 'post' },
    updateDeviceType: { url: urlHttps + '/system/config/device_type/update', method: 'post' },
    deleteDeviceType: { url: urlHttps + '/system/config/device_type/delete', method: 'get' },
}
const notIncludeDevice = {
    getNotIncludeDevice: { url: urlHttps + '/system/not_record_device/get', method: 'get' },
    includeDevice: { url: urlHttps + '/system/not_record_device/add', method: 'post' },
}
const algorithmConfig = {
    getAlgorithmConfig: { url: urlHttps + '/system/config/coreparams/detail', method: 'get' },
    saveAlgorithmConfig: { url: urlHttps + '/system/config/coreparams/save', method: 'post' },
    resetAlgorithmConfig: { url: urlHttps + '/system/config/coreparams/reset', method: 'post' },
}

const repeatFilter = {
    getRepeatList: { url: urlHttps + '/system/config/push_illegal_filter/list', method: 'get' },
    addRepeatConfig: { url: urlHttps + '/system/config/push_illegal_filter/add', method: 'post' },
    deleteRepeatConfig: { url: urlHttps + '/system/config/push_illegal_filter/delete', method: 'post' },

}

const URLS: any = {
    ...personalIndex,
    ...deviceManage,
    ...exportRulesConfig,
    ...initial,
    ...confirm,
    ...passportFilter,
    ...illegalDataUpload,
    ...prePerform,
    ...deviceTypeConfig,
    ...notIncludeDevice,
    ...algorithmConfig,
    ...repeatFilter,

    //login
    login: { url: urlHttps + '/business/basic/user/login', method: 'post' },
    verify: { url: urlHttps + '/business/basic/verify/validate', method: 'get' },
    getAlgorithmVersion: { url: urlHttps + '/system/config/server_version', method: 'get' },
    getKey: { url: urlHttps + '/business/basic/user/get_key', method: 'get' },
    findPassword: { url: urlHttps + '/business/basic/user/retrieve_password', method: 'post' },
    saveproblem: { url: urlHttps + '/business/basic/user/saveproblem', method: 'post' },
    checkAnswer: { url: urlHttps + '/business/basic/user/checkproblem', method: 'post' },
    updateAnswer: { url: urlHttps + '/business/basic/user/update_problem', method: 'post' },
    // 更新密码
    updatePasswd: { url: urlHttps + '/business/basic/user/updatepassword', method: 'post' },
    setNewPwd: { url: urlHttps + '/business/basic/user/retrieve_password', method: 'post' },


    //App
    getMenu: { url: urlHttps + '/business/basic/menu/get', method: 'get' },
    sysLogAdd: { url: urlHttps + '/business/basic/systemlog/add', method: 'post' },
    isLogin: { url: urlHttps + '/business/basic/user/islogin', method: 'get' },
    //abnormal
    getAbnormalList: { url: urlHttps + '/system/config/recogstatus/get', method: 'get' },
    resetAbnormalStatus: { url: urlHttps + '/system/config/recogstatus/reset', method: 'get' },
    searchTaskStatus: { url: urlHttps + '/system/config/recogstatus/process', method: 'get' },
    saveAbnormalList: { url: urlHttps + '/system/config/recogstatus/save', method: 'post' },
    //manageplatform //main
    getTitleIcon: { url: urlHttps + '/system/config/configinfo/getjson', method: 'get' },
    logout: { url: urlHttps + '/business/basic/user/logout', method: 'get' },
    getEnv: { url: urlHttps + '/system/config/current_env', method: 'get' },
    //roadAbnormal
    getDeviceCodeList: { url: urlHttps + '/system/config/missRoad/deviceCodeList', method: 'post' },
    getAbnormalRoad: { url: urlHttps + '/system/config/missRoad/list', method: 'get' },
    addAbnormalRoad: { url: urlHttps + '/system/config/missRoad/add', method: 'post' },
    deleteAbnormalRoad: { url: urlHttps + '/system/config/missRoad/delete', method: 'get' },
    closeAbnormalRoad: { url: urlHttps + '/system/config/missRoad/close', method: 'get' },
    getAbnormalRoadList: { url: urlHttps + '/system/config/missRoad/roadList', method: 'get' },
    //carousel
    getImgPoint: { url: urlHttps + '/business/image/get', method: 'get' },
    getDynamicImage: { url: urlHttps + '/business/image/getDynamicImage', method: 'get' },
    //illstatable
    illegalCountExport: { url: urlHttps + '/business/illegalstatistics/illegalCountExport', method: 'post' },
    getExportProgress: { url: urlHttps + '/business/illegalstatistics/exportprocess', method: 'get' },
    getIllegalCountlist: { url: urlHttps + '/business/illegalstatistics/illegalCountList', method: 'post' },
    // illgealDataAnalyze
    getRecogstatus: { url: urlHttps + '/business/statistics/recogstatus/list', method: 'post' },
    searchTaskProcess: { url: urlHttps + '/system/task/process', method: 'get' },
    // searchCondition
    getIllegalPlace: { url: urlHttps + '/system/device/listroadname', method: 'get' },
    getIllegalPlaceTree: { url: urlHttps + '/system/device/road_select_tree', method: 'get' },
    //systemSouceInfo
    getUserate: { url: urlHttps + '/system/monitor/syssource/userate', method: 'post' },
    //serviceStatus
    getRunstatus: { url: urlHttps + '/system/monitor/service/runstatus', method: 'post' },

    //specialCar
    getCarList: { url: urlHttps + '/system/config/specialcar/list', method: 'get' },
    specialCarUpdate: { url: urlHttps + '/system/config/specialcar/update', method: 'post' },
    specialCarAdd: { url: urlHttps + '/system/config/specialcar/add', method: 'post' },
    specialCarDelete: { url: urlHttps + '/system/config/specialcar/delete', method: 'get' },
    getSpecialCarData: { url: urlHttps + '/system/dict/type/get', method: 'get' },
    //dpconfig
    getDpconfig: { url: urlHttps + '/system/config/push/get', method: 'get' },
    addConfig: { url: urlHttps + '/system/config/push/add', method: 'post' },
    deleteConfig: { url: urlHttps + '/system/config/push/delete', method: 'get' },
    setPushType: { url: urlHttps + '/system/config/push/setting', method: 'post' },
    // setPushType: { url: urlHttps + '/system/config/push/set_target', method: 'get' },
    //sampleAnalyze
    createSampleExactly: { url: urlHttps + '/test/sample/result/comparelistpage', method: 'post' },
    //illegalConfig
    getIllegalList: { url: urlHttps + '/system/config/illegaldict/list', method: 'get' },
    getRecogList: { url: urlHttps + '/system/dic/recogillegal/list', method: 'get' },
    addIllegalConfig: { url: urlHttps + '/system/config/illegaldict/add', method: 'post' },
    updateIllegalConfig: { url: urlHttps + '/system/config/illegaldict/update', method: 'post' },
    getRecogillegal: { url: urlHttps + '/system/config/illegaldict/getdictlist', method: 'get' },

    //roadAbnormalFilter
    getRoadFilterList: { url: urlHttps + '/system/config/miss_road_filter/list', method: 'get' },
    getFilterRoadList: { url: urlHttps + '/system/config/miss_road_filter/roadList', method: 'get' },
    getFilterRoadDevice: { url: urlHttps + '/system/config/miss_road_filter/deviceCodeList', method: 'post' },
    addFilterRoad: { url: urlHttps + '/system/config/miss_road_filter/add', method: 'post' },
    deleteFilterRoad: { url: urlHttps + '/system/config/miss_road_filter/delete', method: 'get' },
    closeFilterRoad: { url: urlHttps + '/system/config/miss_road_filter/close', method: 'get' },
    openFilterRoad: { url: urlHttps + '/system/config/miss_road_filter/open', method: 'get' },
    // check
    //获取车管所数据
    getVehicleData: { url: urlHttps + '/business/vehicledata/get', method: 'get' },
    getCarPlateImg: { url: urlHttps + '/business/image/car_plate', method: 'get' },
    //statistics
    illegalStatList: { url: urlHttps + '/business/illegalstatistics/illegalStatList', method: 'post' },
    illegalSearch: { url: urlHttps + '/business/illegalsearch/data', method: 'post' },
    illegalsearchExportAdd: { url: urlHttps + '/business/illegalsearch/export/add', method: 'post' },
    illegalsearchExportGet: { url: urlHttps + '/business/illegalsearch/export/get', method: 'get' },
    illegalSearchDetail: { url: urlHttps + '/business/illegalsearch/detail', method: 'post' },
    statisticsExport: { url: urlHttps + '/business/illegalstatistics/illegalStatExport', method: 'post' },

    getReuploadArr: { url: urlHttps + '/business/illegalsearch/uploadStatusList', method: 'post' },
    reuploadAdd: { url: urlHttps + '/client/publish/fail_republish/add', method: 'post' },
    reuploadProgress: { url: urlHttps + '/business/task/process/get', method: 'get' },

    //carWhiteList
    getWhiteList: { url: urlHttps + '/system/config/whitelist/list', method: 'get' },
    addWhiteList: { url: urlHttps + '/system/config/whitelist/add', method: 'post' },
    deleteWhiteList: { url: urlHttps + '/system/config/whitelist/delete', method: 'get' },
    //illegalBahaviorFilter
    getIllegalFilterList: { url: urlHttps + '/system/config/illegal_filter/list', method: 'get' },
    addFilterIllegal: { url: urlHttps + '/system/config/illegal_filter/add', method: 'post' },
    deleteFilterIllegal: { url: urlHttps + '/system/config/illegal_filter/delete', method: 'get' },
    //departmentManage
    getDepartList: { url: urlHttps + '/business/basic/dept/list', method: 'get' },
    addDepart: { url: urlHttps + '/business/basic/dept/add', method: 'post' },
    updateDepart: { url: urlHttps + '/business/basic/dept/update', method: 'post' },
    deleteDepart: { url: urlHttps + '/business/basic/dept/delete', method: 'post' },
    getNoDepUserList: { url: urlHttps + '/business/basic/user/no_dept_list', method: 'get' },

    //roleManage
    getRoleList: { url: urlHttps + '/business/basic/role/list', method: 'get' },
    getUpdateRole: { url: urlHttps + '/business/basic/role/update', method: 'post' },
    getDisplayMenu: { url: urlHttps + '/business/basic/menu/get_display_menu', method: 'get' },
    getRoleByUser: { url: urlHttps + '/business/basic/role/getRoleByUserName', method: 'get' },
    //deviceRulesConfig
    getProjectList: { url: urlHttps + '/system/project/list', method: 'get' },
    addProject: { url: urlHttps + '/system/project/add', method: 'post' },
    exportFile: { url: urlHttps + '/system/project/export', method: 'get' },
    getTaskExportProgress: { url: urlHttps + '/system/task/process', method: 'get' },
    getDeviceRules: { url: urlHttps + '/system/device/rule/get', method: 'get' },
    updateDeviceRules: { url: urlHttps + '/system/project/rule/update', method: 'post' },
    getProjectArr: { url: urlHttps + '/system/project/get', method: 'get' },

    //App
    getStatus: { url: urlHttps + '/system/monitor/service/corestatus', method: 'get' },

    //illegalRelationship
    getIllegalRelationship: { url: urlHttps + '/system/config/business/list', method: 'get' },
    saveIllegalRelationship: { url: urlHttps + '/system/config/business/save', method: 'post' },

    //abnormalDeviceWarning
    getAbnormalData: { url: urlHttps + '/system/device/warn/stat', method: 'post' },
    reasonstat: { url: urlHttps + '/system/device/warn/reasonstat', method: 'post' },
    exportWarn: { url: urlHttps + '/system/device/warn/export', method: 'get' },
    exportWarnProgress: { url: urlHttps + '/system/device/warn/exportprogress', method: 'get' },
    //abnormalDeviceconfig
    getAbnormalConfig: { url: urlHttps + '/system/device/warn/config', method: 'get' },
    saveAbnormalData: { url: urlHttps + '/system/device/warn/modconfig', method: 'post' },
    addAbnormalData: { url: urlHttps + '/system/device/warn/addconfig', method: 'post' },
    //iacongfig
    addIaIllegal: { url: urlHttps + '/system/config/illegal/add', method: 'post' },
    updateIaIllegal: { url: urlHttps + '/system/config/illegal/update', method: 'post' },
    getdictlist: { url: urlHttps + '/system/config/illegal/getdictlist', method: 'get' },
    //taskDispatch
    getTaskTemplate: { url: urlHttps + '/system/crontask/get_template_list', method: 'get' },
    addTaskJob: { url: urlHttps + '/system/crontask/add_job', method: 'post' },
    updateTaskJob: { url: urlHttps + '/system/crontask/update_job', method: 'post' },
    deleteTaskJob: { url: urlHttps + '/system/crontask/delete_job', method: 'get' },
    getTaskJob: { url: urlHttps + '/system/crontask/get_task_list', method: 'get' },
    getTaskJobLog: { url: urlHttps + '/system/crontask/exec/status/log', method: 'get' },
    executeJobNow: { url: urlHttps + '/system/crontask/execute_job_now', method: 'get' },
    switchTaskJob: { url: urlHttps + '/system/crontask/onoff', method: 'post' },


};

export function configAjax(urlType: string, params?: any) {
    const { url, method } = URLS[urlType];

    return request(url, method, params);
}
