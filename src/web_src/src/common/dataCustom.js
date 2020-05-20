//号牌归属地
// const licensePlateAbbrev = [{ name: '京' }, { name: '津' }, { name: '沪' }, { name: '渝' }, { name: '蒙' }, { name: '新' }, { name: '藏' }, { name: '宁' }, { name: '桂' }, { name: '港' }, { name: '澳' }, { name: '黑' }, { name: '吉' }, { name: '辽' }, { name: '晋' }, { name: '冀' }, { name: '青' }, { name: '鲁' }, { name: '豫' }, { name: '苏' }, { name: '皖' }, { name: '浙' }, { name: '闽' }, { name: '赣' }, { name: '湘' }, { name: '鄂' }, { name: '粤' }, { name: '琼' }, { name: '甘' }, { name: '陕' }, { name: '贵' }, { name: '云' }, { name: '川' },];
//
// const allStatusObj = {
//     wsb: 0, //未预审
//     yszp: 10, //预审正片
//     ysfp: 20, // 预审废片
//     yiszp: 30, //疑似正片
//     ysyc: 90, //预审异常
//     cszp: 300,//初审正片
//     csfp: 400,//初审废片
//     cstp: 500,//初审套牌
//     fszp: 6000,// 复审正片
//     fsfp: 7000,//复审废片
//     fstp: 8000//复审套牌
// };

// 复核状态(查询选项展示)
const recheckStatus = [
    {name: '车牌更正', code: 1},
    {name: '疑似', code: 2},
    {name: '模糊', code: 3},
    {name: '遮挡', code: 4},
    {name: '未匹配到车', code: 5}
];
// 复核状态字典(编码映射)
const recheckStatusMap = {
    0: "",
    1: "车牌更正",
    2:"疑似",
    3:"模糊",
    4:"遮挡",
    5:"未匹配到车"
};

// 审核状态(查询选项展示)
const manualCheckStatus = [
    {name: '全部', code: ''},
    {name: '未审核', code: 0},
    {name: '正片', code: 1},
    {name: '废片', code: 2}
];
// 审核状态字典（编码映射）
const manualCheckStatusMap = {
    0: "未审核",
    1: "正片",
    2: "废片"
};


const tableColumnWidth = {
    sortNum: 50,//序号
    entryTime: 200,//录入时间
    recogTime: 200,//识别时间
    sdkCarPlateNumber: 150,//识别号牌号码
    srcCarPlateNumber: 150,//原始号牌号码
    sdkReasonCode: 150,//识别结果
    manualCheckStatus: 100,//人工审核状态
    detailIcon: 50,//详情图标

    // sortNum: 50,//序号
    // carPlateNumber: 120,//车辆号牌
    // illegalTime: 150,//违法时间
    // deviceId: 200,//通行点位、路口名称
    // checkStatus: 120,//审核状态
    // uploadStatus: 200,//上报失败原因
    // detailIcon: 50,//详情图标
    // carDirect: 120,//过车方向
    // deviceCode: 200,//设备编号
    // deviceName: 200,//设备名称
    // projectName: 200,//项目名称
    // illegalCode: 120,//违法代码
    // timeRange: 150,//时段
    // operate: 250,//操作 三个按钮
    // counts: 80,//各种数量
};

export {
    recheckStatus, manualCheckStatus, recheckStatusMap, manualCheckStatusMap, tableColumnWidth
};

// //预审状态(正废)
// const firStatusZf = [
//     { name: '预审正片', code: 10 },
//     { name: '预审废片', code: 20 }
// ];
// //预审状态(无异常，有疑似)
// const firStatusSy = [
//     { name: '预审正片', code: 10 },
//     { name: '预审废片', code: 20 },
//     { name: '疑似正片', code: 30 },
// ];
// //预审状态(含未识别)
// const firStatusW = [
//     { name: '未识别', code: 0 },
//     { name: '预审异常', code: 90 },
//     { name: '预审正片', code: 10 },
//     { name: '预审废片', code: 20 }
// ];
// //预审状态(含疑似正片)
// const firStatusS = [
//     { name: '预审异常', code: 90 },
//     { name: '预审正片', code: 10 },
//     { name: '预审废片', code: 20 },
//     { name: '疑似正片', code: 30 },
// ];
// //初审状态
// const secStatus = [
//     { name: '初审正片', code: 300 },
//     { name: '初审废片', code: 400 }
// ];
// // 初审状态（含套牌）
// const secStatusT = [
//     { name: '初审正片', code: 300 },
//     { name: '初审废片', code: 400 },
//     { name: '初审套牌', code: 500 }
// ];
// //复审状态
// const thirdStatus = [
//     { name: '复审正片', code: 6000 },
//     { name: '复审废片', code: 7000 }
// ];
// //复审状态（含套牌）
// const thirdStatusT = [
//     { name: '复审正片', code: 6000 },
//     { name: '复审废片', code: 7000 },
//     { name: '复审套牌', code: 8000 }
// ];
// //废片状态
// const wasteArr = [
//     { name: '预审废片', code: 20 },
//     { name: '初审废片', code: 400 },
//     { name: '复审废片', code: 7000 },
//
// ]
//
// const statusArr = [
//     { name: '所有', code: '' },
//     { name: '运行中', code: 1 },
//     { name: '停止', code: 0 }
// ];
//
// const attributionArr = [
//     { name: '本省本市车辆', code: 1 },
//     { name: '本省外市车辆', code: 2 },
//     { name: '外省车辆', code: 3 },
// ]
//
// const aimArr = [
//     { name: '路口', code: 1 },
//     { name: '设备', code: 2 },
// ]
//
// const initialConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: true, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: true, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: true, //违法车牌
//     isCarPlateType: true, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: true,//申请条数
//     isSearchBtnName: '申请',//搜索按钮名称
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const preTestCheckConfig = {
//     isTitle: true, //名称
//     isTime: false, //违法时段
//     isDeviceId: false, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: true, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const illStatTableConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: false, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const illegalDataAnalyzeConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: false, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const reportStatisticsConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isTimeTitle: '上报时段',
//     isDeviceId: true, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: true, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const illegalCarTypeStatisticsConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: true, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: true, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const clonePlateStatisticsConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: true, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const checkStatisticsConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isTimeTitle: '审核时间',
//     isDeviceId: false, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型,
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const illegalDataUploadConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: true, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: true, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: true,//违法条数
//     isRequestCount: false,//申请条数
//     isSearchBtnName: '添加',//搜索按钮名称
//     isUploadStatus: true,//上报状态
//     isWasteReason: false,//废片原因
//
// }
// const wasteStatisticsConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: false, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isSearchBtnName: '查询',//搜索按钮名称
//     isUploadStatus: false,//上报状态
//     isMistakeType: true,//废片原因
// }
// const offenceStatisticsConfig = {
//     isTitle: false, //名称
//     isTime: true, //违法时段
//     isDeviceId: false, // 违法地点
//     isIllegalBehavior: true, //违法行为
//     isCheckStatus: false, //审核状态
//     isCheckStatusAll: false, //审核状态所有
//     isCarPlateNumber: false, //违法车牌
//     isCarPlateType: false, //号牌颜色
//     isSsd: false, // 所属地
//     isAim: false, //目标累型
//     isIllegalCount: false,//违法条数
//     isRequestCount: false,//申请条数
//     isSearchBtnName: '查询',//搜索按钮名称
//     isUploadStatus: false,//上报状态
//     isWasteReason: false,//废片原因
// }
//
// const specialCarArr = [
//     {
//         name: '消防车',
//         code: 1
//     },
//     {
//         name: '工程抢修车',
//         code: 2
//     },
//     {
//         name: '救援车',
//         code: 3
//     },
//     {
//         name: '救护车',
//         code: 4
//     },
//     {
//         name: '警车',
//         code: 5
//     },
//     {
//         name: '军车',
//         code: 6
//     },
//     {
//         name: '公交车',
//         code: 7
//     },
//     {
//         name: '出租车',
//         code: 8
//     },
// ]
//
// const tagArr = [
//     {
//         value: 'badxs',
//         label: '不按道行驶',
//         disabled: true,
//         color: '#ff0',
//         children: [
//             {
//                 value: 'SL',
//                 label: '直行 SL',
//                 quickKey: '1'
//             },
//             {
//                 value: 'LL',
//                 label: '左转 LL',
//                 quickKey: '2'
//             },
//             {
//                 value: 'RL',
//                 label: '右转 RL',
//                 quickKey: '3'
//             },
//             {
//                 value: 'SL/LL',
//                 label: '直行/左转 SL/LL',
//                 quickKey: '4'
//             },
//             {
//                 value: 'SL/RL',
//                 label: '直行/右转 SL/RL',
//                 quickKey: '5'
//             },
//             {
//                 value: 'LA',
//                 label: '左转区 LA',
//                 quickKey: '6'
//             },
//             {
//                 value: 'TA',
//                 label: '过渡区 TA',
//                 quickKey: '7'
//             },
//             {
//                 value: 'SA',
//                 label: '直行区 SA',
//                 quickKey: '8'
//             },
//             {
//                 value: 'RA',
//                 label: '右转区 RA',
//                 quickKey: '9'
//             },
//             {
//                 value: 'UA',
//                 label: '调头区 UA',
//                 quickKey: '0'
//             },
//             {
//                 value: 'UL',
//                 label: '调头 UL',
//                 quickKey: 'I'
//             },
//             {
//                 value: 'LWL',
//                 label: '左转待转道 LWL',
//                 quickKey: 'O'
//             },
//             {
//                 value: 'SWL',
//                 label: '直行待行道 SWL',
//                 quickKey: 'P'
//             },
//             {
//                 value: 'VL',
//                 label: '可变车道 VL',
//                 quickKey: 'J'
//             },
//             {
//                 value: 'RL/LL',
//                 label: '右转/左转 RL/LL',
//                 quickKey: 'K'
//             },
//             {
//                 value: 'UL/LL',
//                 label: '掉头/左转 UL/LL',
//                 quickKey: 'L'
//             },
//             {
//                 value: 'SL/UL',
//                 label: '直行掉头 SL/UL'
//             },
//             {
//                 value: 'SL/LL/UL',
//                 label: '直行左转掉头 SL/LL/UL'
//             },
//             {
//                 value: 'SL/RL/LL',
//                 label: '直行/右转/左转 SL/RL/LL'
//             },
//             {
//                 value: 'VWL',
//                 label: '可变待行道 VWL'
//             },
//             {
//                 value: 'UL/RL',
//                 label: '掉头/右转 UL/RL'
//             },
//             {
//                 value: 'UWL',
//                 label: '掉头待行区 UWL'
//             },
//             {
//                 value: 'LWL/UWL',
//                 label: '左转/掉头待行道 LWL/UWL',
//             },
//             {
//                 value: 'LWL/SWL',
//                 label: '左转/直行待转道 LWL/SWL',
//             },
//             {
//                 value: 'SL/RL/LL/UL',
//                 label: '直行/右转/左转/掉头 SL/RL/LL/UL',
//             },
//         ]
//     },
//     {
//         value: 'dlx',
//         label: '道路线',
//         disabled: true,
//         color: '#0000E3',
//         children: [
//             {
//                 value: 'WSL',
//                 label: '白实线 WSL',
//                 quickKey: '1'
//             },
//             {
//                 value: 'YSL',
//                 label: '黄实线 YSL',
//                 quickKey: '2'
//             },
//             {
//                 value: 'WLVRSL',
//                 label: 'WLVRSL 白线-左虚右实线',
//                 quickKey: '3'
//             },
//             {
//                 value: 'YLVRSL',
//                 label: 'YLVRSL 黄线-左虚右实线',
//                 quickKey: '4'
//             },
//             {
//                 value: 'WLSRVL',
//                 label: 'WLSRVL 白线-左实右虚线',
//                 quickKey: '5'
//             },
//             {
//                 value: 'YLSRVL',
//                 label: 'YLSRVL 黄线-左实右虚线',
//                 quickKey: '6'
//             },
//         ]
//     },
//     {
//         value: 'bmx',
//         label: '斑马线',
//         disabled: true,
//         color: '#ff6100',
//         children: [
//             {
//                 value: 'ZCA',
//                 label: '水平中心线 ZCA',
//                 quickKey: '1'
//             },
//             {
//                 value: 'PCA',
//                 label: '行人冲突区  PCA',
//                 quickKey: '2'
//             },
//         ]
//     },
//     {
//         value: 'xhd',
//         label: '信号灯',
//         disabled: true,
//         color: '#f00',
//         children: [
//             {
//                 value: 'A-LVL',
//                 label: '箭头灯-左转/可变颜色 A-LVL',
//             },
//
//
//             {
//                 value: 'A-RVL',
//                 label: '箭头灯-右转/可变颜色 A-RVL',
//             },
//
//
//             {
//                 value: 'A-UVL',
//                 label: '箭头灯-调头/可变颜色 A-UVL',
//             },
//
//
//             {
//                 value: 'A-SVL',
//                 label: '箭头灯-直行/可变颜色 A-SVL',
//             },
//             {
//                 value: 'A-LRL',
//                 label: '箭头灯-左转/红灯 A-LRL',
//                 quickKey: '1'
//             },
//             {
//                 value: 'A-LYL',
//                 label: '箭头灯-左转/黄灯 A-LYL',
//                 quickKey: '2'
//
//             },
//             {
//                 value: 'A-LGL',
//                 label: '箭头灯-左转/绿灯 A-LGL',
//                 quickKey: '3'
//
//             },
//             {
//                 value: 'C-SRL',
//                 label: '圆灯-直行/红灯 C-SRL',
//                 quickKey: '4'
//
//             },
//             {
//                 value: 'C-SYL',
//                 label: '圆灯-直行/黄灯 C-SYL',
//                 quickKey: '5'
//
//             },
//             {
//                 value: 'C-SGL',
//                 label: '圆灯-直行/绿灯 C-SGL',
//                 quickKey: '6'
//
//             },
//             {
//                 value: 'C-LRL/SRL',
//                 label: '圆灯-左转/直行/红灯 C-LRL/SRL',
//                 quickKey: '7'
//
//             },
//             {
//                 value: 'C-LYL/SYL',
//                 label: '圆灯-左转/直行/黄灯 C-LYL/SRL',
//                 quickKey: '8'
//
//             },
//             {
//                 value: 'C-LGL/SGL',
//                 label: '圆灯-左转/直行/绿灯 C-LGL/SGL',
//                 quickKey: '9'
//
//             },
//             {
//                 value: 'RDR',
//                 label: '红灯大框 RDR',
//                 quickKey: '0'
//             },
//             {
//                 value: 'A-SRL',
//                 label: '箭头灯-直行/红灯 A-SRL',
//                 quickKey: 'I'
//
//             },
//             {
//                 value: 'A-SYL',
//                 label: '箭头灯-直行/黄灯 A-SYL',
//                 quickKey: 'O'
//             },
//             {
//                 value: 'A-SGL',
//                 label: '箭头灯-直行/绿灯 A-SGL',
//                 quickKey: 'P'
//             },
//             {
//                 value: 'A-RRL',
//                 label: '箭头灯-右转/红灯 A-RRL',
//                 quickKey: 'J'
//             },
//             {
//                 value: 'A-RYL',
//                 label: '箭头灯-右转/黄灯 A-RYL',
//                 quickKey: 'K'
//             },
//             {
//                 value: 'A-RGL',
//                 label: '箭头灯-右转/绿灯 A-RGL',
//                 quickKey: 'L'
//             },
//             {
//                 value: 'C-LRL',
//                 label: '圆灯-左转/红灯 C-LRL',
//                 quickKey: 'B'
//             },
//             {
//                 value: 'C-LYL',
//                 label: '圆灯-左转/黄灯 C-LYL',
//                 quickKey: 'N'
//             },
//             {
//                 value: 'C-LGL',
//                 label: '圆灯-左转/绿灯 C-LGL',
//                 quickKey: 'M'
//             },
//             {
//                 value: 'A-URL',
//                 label: '箭头灯-调头/红灯 A-URL',
//                 quickKey: 'F'
//             },
//             {
//                 value: 'A-UYL',
//                 label: '箭头灯-调头/黄灯 A-UYL',
//                 quickKey: 'G'
//             },
//             {
//                 value: 'A-UGL',
//                 label: '箭头灯-调头/绿灯 A-UGL',
//                 quickKey: 'H'
//             },
//             {
//                 value: 'C-LVL',
//                 label: '圆灯-左转/可变颜色 C-LVL',
//             },
//
//             {
//                 value: 'C-RRL',
//                 label: '圆灯-右转/红灯 C-RRL',
//             },
//             {
//                 value: 'C-RYL',
//                 label: '圆灯-右转/黄灯 C-RYL',
//             },
//             {
//                 value: 'C-RGL',
//                 label: '圆灯-右转/绿灯 C-RGL',
//             },
//             {
//                 value: 'C-RVL',
//                 label: '圆灯-右转/可变颜色 C-RVL',
//             },
//
//             {
//                 value: 'C-URL',
//                 label: '圆灯-调头/红灯 C-URL',
//             },
//             {
//                 value: 'C-UYL',
//                 label: '圆灯-调头/黄灯 C-UYL',
//             },
//             {
//                 value: 'C-UGL',
//                 label: '圆灯-调头/绿灯 C-UGL',
//             },
//             {
//                 value: 'C-UVL',
//                 label: '圆灯-调头/可变颜色 C-UVL',
//             },
//
//
//             {
//                 value: 'C-SVL',
//                 label: '圆灯-直行/可变颜色 C-SVL',
//             },
//
//             {
//                 value: 'C-LRL/RRL',
//                 label: '圆灯-左转/右转/红灯 C-LRL/RRL',
//             },
//             {
//                 value: 'C-LYL/RYL',
//                 label: '圆灯-左转/右转/黄灯 C-LYL/RYL',
//             },
//             {
//                 value: 'C-LGL/RGL',
//                 label: '圆灯-左转/右转/绿灯 C-LGL/RGL',
//             },
//             {
//                 value: 'C-LVL/RVL',
//                 label: '圆灯-左转/右转/可变颜色 C-LVL/RVL',
//             },
//
//             {
//                 value: 'C-LRL/URL',
//                 label: '圆灯-左转/调头/红灯 C-LRL/URL',
//             },
//             {
//                 value: 'C-LYL/UYL',
//                 label: '圆灯-左转/调头/黄灯 C-LYL/UYL',
//             },
//             {
//                 value: 'C-LGL/UGL',
//                 label: '圆灯-左转/调头/绿灯 C-LGL/UGL',
//             },
//             {
//                 value: 'C-LVL/UVL',
//                 label: '圆灯-左转/调头/可变颜色 C-LVL/UVL',
//             },
//
//
//             {
//                 value: 'C-LVL/SVL',
//                 label: '圆灯-左转/直行/可变颜色 C-LVL/SVL',
//             },
//
//             {
//                 value: 'C-RRL/URL',
//                 label: '圆灯-右转/调头/红灯 C-RRL/URL',
//             },
//             {
//                 value: 'C-RYL/UYL',
//                 label: '圆灯-右转/调头/黄灯 C-RYL/URL',
//             },
//             {
//                 value: 'C-RGL/UGL',
//                 label: '圆灯-右转/调头/绿灯 C-RGL/UGL',
//             },
//             {
//                 value: 'C-RVL/UVL',
//                 label: '圆灯-右转/调头/可变颜色 C-RVL/UVL',
//             },
//
//             {
//                 value: 'C-RRL/SRL',
//                 label: '圆灯-右转/直行/红灯 C-RRL/SRL',
//             },
//             {
//                 value: 'C-RYL/SYL',
//                 label: '圆灯-右转/直行/黄灯 C-RYL/SRL',
//             },
//             {
//                 value: 'C-RGL/SGL',
//                 label: '圆灯-右转/直行/绿灯 C-RGL/SGL',
//             },
//             {
//                 value: 'C-RVL/SVL',
//                 label: '圆灯-右转/直行/可变颜色 C-RVL/SVL',
//             },
//
//             {
//                 value: 'C-URL/SRL',
//                 label: '圆灯-调头/直行/红灯 C-URL/SRL',
//             },
//             {
//                 value: 'C-UYL/SYL',
//                 label: '圆灯-调头/直行/黄灯 C-UYL/SRL',
//             },
//             {
//                 value: 'C-UGL/SGL',
//                 label: '圆灯-调头/直行/绿灯 C-UGL/SGL',
//             },
//             {
//                 value: 'C-UVL/SVL',
//                 label: '圆灯-调头/直行/可变颜色 C-UVL/SVL',
//             },
//
//             {
//                 value: 'C-LRL/RRL/URL',
//                 label: '圆灯-左转/右转/调头/红灯 C-LRL/RRL/URL',
//             },
//             {
//                 value: 'C-LYL/RYL/UYL',
//                 label: '圆灯-左转/右转/调头/黄灯 C-LYL/RYL/UYL',
//             },
//             {
//                 value: 'C-LGL/RGL/UGL',
//                 label: '圆灯-左转/右转/调头/绿灯 C-LGL/RGL/UGL',
//             },
//             {
//                 value: 'C-LVL/RVL/UVL',
//                 label: '圆灯-左转/右转/调头/可变颜色 C-LVL/RVL/UVL',
//             },
//
//             {
//                 value: 'C-LRL/RRL/SRL',
//                 label: '圆灯-左转/右转/直行/红灯 C-LRL/RRL/SRL',
//             },
//             {
//                 value: 'C-LYL/RYL/SYL',
//                 label: '圆灯-左转/右转/直行/黄灯 C-LYL/RYL/SYL',
//             },
//             {
//                 value: 'C-LGL/RGL/SGL',
//                 label: '圆灯-左转/右转/直行/绿灯 C-LGL/RGL/SGL',
//             },
//             {
//                 value: 'C-LVL/RVL/SVL',
//                 label: '圆灯-左转/右转/直行/可变颜色 C-LVL/RVL/SVL',
//             },
//
//             {
//                 value: 'C-LRL/URL/SRL',
//                 label: '圆灯-左转/调头/直行/红灯 C-LRL/URL/SRL',
//             },
//             {
//                 value: 'C-LYL/UYL/SYL',
//                 label: '圆灯-左转/调头/直行/黄灯 C-LYL/UYL/SYL',
//             },
//             {
//                 value: 'C-LGL/UGL/SGL',
//                 label: '圆灯-左转/调头/直行/绿灯 C-LGL/UGL/SGL',
//             },
//             {
//                 value: 'C-LVL/UVL/SVL',
//                 label: '圆灯-左转/调头/直行/可变颜色 C-LVL/UVL/SVL',
//             },
//
//             {
//                 value: 'C-RRL/URL/SRL',
//                 label: '圆灯-右转/调头/直行/红灯 C-RRL/URL/SRL',
//             },
//             {
//                 value: 'C-RYL/UYL/SYL',
//                 label: '圆灯-右转/调头/直行/黄灯 C-RYL/UYL/SYL',
//             },
//             {
//                 value: 'C-RGL/UGL/SGL',
//                 label: '圆灯-右转/调头/直行/绿灯 C-RGL/UGL/SGL',
//             },
//             {
//                 value: 'C-RVL/UVL/SVL',
//                 label: '圆灯-右转/调头/直行/可变颜色 C-RVL/UVL/SVL',
//             },
//
//             {
//                 value: 'C-LRL/RRL/URL/SRL',
//                 label: '圆灯-左转/右转/调头/直行/红灯 C-LRL/RRL/URL/SRL',
//             },
//             {
//                 value: 'C-LYL/RYL/UYL/SYL',
//                 label: '圆灯-左转/右转/调头/直行/黄灯 C-LYL/RYL/UYL/SYL',
//             },
//             {
//                 value: 'C-LGL/RGL/UGL/SGL',
//                 label: '圆灯-左转/右转/调头/直行/绿灯 C-LGL/RGL/UGL/SGL',
//             },
//             {
//                 value: 'C-LVL/RVL/UVL/SVL',
//                 label: '圆灯-左转/右转/调头/直行/可变颜色 C-LVL/RVL/UVL/SVL',
//             },
//
//             {
//                 value: 'A-LRL/RRL',
//                 label: '箭头灯-左转/右转/红灯 A-LRL/RRL',
//             },
//             {
//                 value: 'A-LYL/RYL',
//                 label: '箭头灯-左转/右转/黄灯 A-LYL/RYL',
//             },
//             {
//                 value: 'A-LGL/RGL',
//                 label: '箭头灯-左转/右转/绿灯 A-LGL/RGL',
//             },
//             {
//                 value: 'A-LVL/RVL',
//                 label: '箭头灯-左转/右转/可变颜色 A-LVL/RVL',
//             },
//
//             {
//                 value: 'A-LRL/URL',
//                 label: '箭头灯-左转/调头/红灯 A-LRL/URL',
//             },
//             {
//                 value: 'A-LYL/UYL',
//                 label: '箭头灯-左转/调头/黄灯 A-LYL/UYL',
//             },
//             {
//                 value: 'A-LGL/UGL',
//                 label: '箭头灯-左转/调头/绿灯 A-LGL/UGL',
//             },
//             {
//                 value: 'A-LVL/UVL',
//                 label: '箭头灯-左转/调头/可变颜色 A-LVL/UVL',
//             },
//
//             {
//                 value: 'A-LRL/SRL',
//                 label: '箭头灯-左转/直行/红灯 A-LRL/SRL',
//             },
//             {
//                 value: 'A-LYL/SYL',
//                 label: '箭头灯-左转/直行/黄灯 A-LYL/SRL',
//             },
//             {
//                 value: 'A-LGL/SGL',
//                 label: '箭头灯-左转/直行/绿灯 A-LGL/SGL',
//             },
//             {
//                 value: 'A-LVL/SVL',
//                 label: '箭头灯-左转/直行/可变颜色 A-LVL/SVL',
//             },
//
//             {
//                 value: 'A-RRL/URL',
//                 label: '箭头灯-右转/调头/红灯 A-RRL/URL',
//             },
//             {
//                 value: 'A-RYL/UYL',
//                 label: '箭头灯-右转/调头/黄灯 A-RYL/URL',
//             },
//             {
//                 value: 'A-RGL/UGL',
//                 label: '箭头灯-右转/调头/绿灯 A-RGL/UGL',
//             },
//             {
//                 value: 'A-RVL/UVL',
//                 label: '箭头灯-右转/调头/可变颜色 A-RVL/UVL',
//             },
//
//             {
//                 value: 'A-RRL/SRL',
//                 label: '箭头灯-右转/直行/红灯 A-RRL/SRL',
//             },
//             {
//                 value: 'A-RYL/SYL',
//                 label: '箭头灯-右转/直行/黄灯 A-RYL/SRL',
//             },
//             {
//                 value: 'A-RGL/SGL',
//                 label: '箭头灯-右转/直行/绿灯 A-RGL/SGL',
//             },
//             {
//                 value: 'A-RVL/SVL',
//                 label: '箭头灯-右转/直行/可变颜色 A-RVL/SVL',
//             },
//
//             {
//                 value: 'A-URL/SRL',
//                 label: '箭头灯-调头/直行/红灯 A-URL/SRL',
//             },
//             {
//                 value: 'A-UYL/SYL',
//                 label: '箭头灯-调头/直行/黄灯 A-UYL/SRL',
//             },
//             {
//                 value: 'A-UGL/SGL',
//                 label: '箭头灯-调头/直行/绿灯 A-UGL/SGL',
//             },
//             {
//                 value: 'A-UVL/SVL',
//                 label: '箭头灯-调头/直行/可变颜色 A-UVL/SVL',
//             },
//
//             {
//                 value: 'A-LRL/RRL/URL',
//                 label: '箭头灯-左转/右转/调头/红灯 A-LRL/RRL/URL',
//             },
//             {
//                 value: 'A-LYL/RYL/UYL',
//                 label: '箭头灯-左转/右转/调头/黄灯 A-LYL/RYL/UYL',
//             },
//             {
//                 value: 'A-LGL/RGL/UGL',
//                 label: '箭头灯-左转/右转/调头/绿灯 A-LGL/RGL/UGL',
//             },
//             {
//                 value: 'A-LVL/RVL/UVL',
//                 label: '箭头灯-左转/右转/调头/可变颜色 A-LVL/RVL/UVL',
//             },
//
//             {
//                 value: 'A-LRL/RRL/SRL',
//                 label: '箭头灯-左转/右转/直行/红灯 A-LRL/RRL/SRL',
//             },
//             {
//                 value: 'A-LYL/RYL/SYL',
//                 label: '箭头灯-左转/右转/直行/黄灯 A-LYL/RYL/SYL',
//             },
//             {
//                 value: 'A-LGL/RGL/SGL',
//                 label: '箭头灯-左转/右转/直行/绿灯 A-LGL/RGL/SGL',
//             },
//             {
//                 value: 'A-LVL/RVL/SVL',
//                 label: '箭头灯-左转/右转/直行/可变颜色 A-LVL/RVL/SVL',
//             },
//
//             {
//                 value: 'A-LRL/URL/SRL',
//                 label: '箭头灯-左转/调头/直行/红灯 A-LRL/URL/SRL',
//             },
//             {
//                 value: 'A-LYL/UYL/SYL',
//                 label: '箭头灯-左转/调头/直行/黄灯 A-LYL/UYL/SYL',
//             },
//             {
//                 value: 'A-LGL/UGL/SGL',
//                 label: '箭头灯-左转/调头/直行/绿灯 A-LGL/UGL/SGL',
//             },
//             {
//                 value: 'A-LVL/UVL/SVL',
//                 label: '箭头灯-左转/调头/直行/可变颜色 A-LVL/UVL/SVL',
//             },
//
//             {
//                 value: 'A-RRL/URL/SRL',
//                 label: '箭头灯-右转/调头/直行/红灯 A-RRL/URL/SRL',
//             },
//             {
//                 value: 'A-RYL/UYL/SYL',
//                 label: '箭头灯-右转/调头/直行/黄灯 A-RYL/UYL/SYL',
//             },
//             {
//                 value: 'A-RGL/UGL/SGL',
//                 label: '箭头灯-右转/调头/直行/绿灯 A-RGL/UGL/SGL',
//             },
//             {
//                 value: 'A-RVL/UVL/SVL',
//                 label: '箭头灯-右转/调头/直行/可变颜色 A-RVL/UVL/SVL',
//             },
//
//             {
//                 value: 'A-LRL/RRL/URL/SRL',
//                 label: '箭头灯-左转/右转/调头/直行/红灯 A-LRL/RRL/URL/SRL',
//             },
//             {
//                 value: 'A-LYL/RYL/UYL/SYL',
//                 label: '箭头灯-左转/右转/调头/直行/黄灯 A-LYL/RYL/UYL/SYL',
//             },
//             {
//                 value: 'A-LGL/RGL/UGL/SGL',
//                 label: '箭头灯-左转/右转/调头/直行/绿灯 A-LGL/RGL/UGL/SGL',
//             },
//             {
//                 value: 'A-LVL/RVL/UVL/SVL',
//                 label: '箭头灯-左转/右转/调头/直行/可变颜色 A-LVL/RVL/UVL/SVL',
//             },
//         ]
//     },
//     {
//         value: 'nx',
//         label: '逆行',
//         disabled: true,
//         color: '#fff',
//         children: [
//             {
//                 value: 'AA',
//                 label: '前行区域 AA',
//                 quickKey: '1'
//             },
//             {
//                 value: 'BA',
//                 label: '后行区域 BA',
//                 quickKey: '2'
//             },
//         ]
//     },
//     {
//         value: 'wt',
//         label: '违停',
//         disabled: true,
//         color: '#ff00ff',
//         children: [
//             {
//                 value: 'FPA',
//                 label: '违停 FPA',
//                 quickKey: '1'
//             },
//             {
//                 value: 'YFPA',
//                 label: '黄网格违停止 YFPA',
//                 quickKey: '2'
//             },
//         ]
//     },
//     {
//         value: 'zycd',
//         label: '占用车道',
//         disabled: true,
//         color: '#00FFFF',
//         children: [
//             {
//                 value: 'BKL',
//                 label: '非机动车道 BKL',
//                 quickKey: '1'
//             },
//             {
//                 value: 'BSL',
//                 label: '公交车道 BSL',
//                 quickKey: '2'
//             },
//             {
//                 value: 'CRL',
//                 label: '小车车道 CRL',
//                 quickKey: '3'
//             },
//             {
//                 value: 'EVL',
//                 label: '应急车道 EVL',
//                 quickKey: '4'
//             }
//         ]
//     }
// ]
//
// const cutModeArr = [
//     {
//         mode: '1x1',
//         value: 1 * 1,
//         icon: 'icon-opo',
//     },
//     {
//         mode: '1x2',
//         value: 1 * 2,
//         icon: 'icon-optwo',
//     },
//     {
//         mode: '1+2',
//         value: 1 + 2,
//         icon: 'icon-oat',
//     },
//     {
//         mode: '1x3',
//         value: 1 * 3,
//         icon: 'icon-opt',
//     },
//     {
//         mode: '1x4',
//         value: 1 * 4,
//         icon: 'icon-opf',
//     },
//     {
//         mode: '2x1',
//         value: 2 * 1,
//         icon: 'icon-tpo',
//     },
//     {
//         mode: '2x2',
//         value: 2 * 2,
//         icon: 'icon-tptwo',
//     },
//     {
//         mode: '2x3',
//         value: 2 * 3,
//         icon: 'icon-tpt',
//     },
//     {
//         mode: '3x1',
//         value: 3 * 1,
//         icon: 'icon-threepo',
//     },
//     {
//         mode: '4x1',
//         value: 4 * 1,
//         icon: 'icon-fpo',
//     },
// ]
//
// const uploadFileReasonArr = [
//     { code: 0, name: '-' },
//     { code: 1, name: '备案编号不对应' },
//     { code: 2, name: '其它备案信息不对应' },
//     { code: 3, name: 'url不全' },
//     { code: 4, name: '无主图' },
//     { code: 5, name: '接口异常' },
//     { code: 6, name: '该备案设备未备案此违法行为' },
//     { code: 7, name: '其它上报错误' },
//     { code: 8, name: '-' },
// ]
// const uploadReasonArr = [
//     { code: 0, name: '入库' },
//     { code: 1, name: '备案编号不对应' },
//     { code: 2, name: '其它备案信息不对应' },
//     { code: 3, name: 'url不全' },
//     { code: 4, name: '无主图' },
//     { code: 5, name: '接口异常' },
//     { code: 6, name: '该备案设备未备案此违法行为' },
//     { code: 7, name: '其它上报错误' },
//     { code: 8, name: '上报成功' },
// ]
//
// const uploadStatusArr = [
//     { code: 0, name: '未上报' },
//     { code: 1, name: '上报失败' },
// ]
//
// const tableColumnWidth = {
//     sortNum: 50,//序号
//     carPlateNumber: 120,//车辆号牌
//     illegalTime: 150,//违法时间
//     deviceId: 200,//通行点位、路口名称
//     checkStatus: 120,//审核状态
//     uploadStatus: 200,//上报失败原因
//     detailIcon: 50,//详情图标
//     carDirect: 120,//过车方向
//     deviceCode: 200,//设备编号
//     deviceName: 200,//设备名称
//     projectName: 200,//项目名称
//     illegalCode: 120,//违法代码
//     timeRange: 150,//时段
//     operate: 250,//操作 三个按钮
//     counts: 80,//各种数量
//
// }

// export {
//     licensePlateAbbrev, firStatus, secStatus, secStatusT, thirdStatus, thirdStatusT, statusArr, attributionArr, aimArr,
//     initialConfig, initialConfig as confirmConfig, preTestCheckConfig, illStatTableConfig, illegalDataAnalyzeConfig, firStatusW, firStatusS, firStatusSy,
//     specialCarArr, allStatusObj, reportStatisticsConfig, illegalCarTypeStatisticsConfig, firStatusZf, clonePlateStatisticsConfig, checkStatisticsConfig, tagArr, cutModeArr, illegalDataUploadConfig,
//     uploadFileReasonArr, uploadStatusArr, tableColumnWidth, wasteStatisticsConfig, wasteArr, offenceStatisticsConfig, uploadReasonArr,
// };
