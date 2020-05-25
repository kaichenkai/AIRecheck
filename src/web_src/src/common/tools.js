/**
 * Created by lyuwei
 * User: lvwei@seemmo.com
 * Date: 2018/09/10
 * Describe:
 * Log:
 *  ---- 2018/09/10 14:38 [lyuwei] 初次添加
 */
import { Loading, Message, Notification, MessageBox } from 'element-ui';
// import { carplateColorInfo, directionInfo, colorInfo, mistakeType, plateType, carKindType } from '@/common/dictionary.js';
// import { configAjax } from '@/common/tsService.ts'

let globalLoading = null;

/**
 *全屏加载动画
 * @param {String} msg 提示性文字
 */
export function showLoading(msg) {
    globalLoading = Loading.service({
        lock: true,
        text: msg,
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.9)'
    });
    return globalLoading;
}

export function closeLoading() {
    if (globalLoading) {
        globalLoading.close();
        globalLoading = null;
    }
}

/**
 *消息
 * @param {String} msg 提示文字
 * @param {String} type 类型四种 success, warning, info, error
 * @param {Number||String} duration 持续时间，时间到即自动消失，传'0'则不自动消失。
 */
export function message(msg, type = 'error', duration = 3000) {
    return Message({
        showClose: true,
        message: msg,
        type,
        duration
    });
}


/**
 * 通知
 * @param {String} msg 提示文字
 * @param {String} type 类型四种 success, warning, info, error
 * @param {String} title 通知标题
 * @param {Number||String} duration 持续时间，时间到即自动消失，传'0'则不自动消失。
 */
export function notify(msg, type, title, duration = 3000) {
    return Notification({
        message: msg,
        type,
        title,
        duration,
    });
}

/**
 *
 * 对话
 * @param {*} msg 提示内容
 * @param {*} title 提示标题
 * @param {*} option 配置项
 * @returns
 */
export function confirm(msg, title, option) {
    return MessageBox.confirm(
        msg, title, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: option.type ? option.type : 'warning',
        dangerouslyUseHTMLString: true,
        center: true,
        iconClass: option.iconClass
    }
    );
}


/**
 * 时间转换方法
 * @param val
 * @param type
 * @returns {string}
 */
export function timeFormat(val, type) {
    if (typeof val === 'string') {
        val = Number(val);
    }
    let date = new Date(val);
    let Y = date.getFullYear();
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
    let D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
    let h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours());
    let m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
    let s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
    let res;

    switch (type) {
        case 'dateTime':
            res = `${Y}-${M}-${D} ${h}:${m}:${s}`;
            break;
        default:
            res = `${Y}${M}${D}`;
            break;
    }
    return res;
}

/**
 *
 *格式转字符
 * @param {*} str
 * @returns
 */
export function getUnixDate(str) {
    const regEx = new RegExp('\\-', 'gi');

    return Math.round(Date.parse(str.replace(regEx, '/')));
}

/**
 * 时间戳转Date
 * @param {时间戳} nS
 */
export function getLocalTime(nS) {
    return new Date(parseInt(nS, 10) * 1000).toLocaleString().replace(/:\d{1,2}$/, ' ');
}

/**
 * 数据字典查询方法
 * @param {string|number} val
 * @param {string} type
 * @param {string} keyType 传进来的类型 code、gbCode、name、gbName
 * @param {string} rType 返回的类型 code、gbCode、name、gbName
 * @returns {string}
 */
// export function tableFormat(val, type, keyType = 'code', rType = 'name') {
//     let cellName = '-';
//     let dataCustom = [];
//
//     switch (type) {
//         // case 'illegalBehaviorInfo': dataCustom = illegalBehaviorInfo; break;
//         case 'carplateColorInfo': dataCustom = carplateColorInfo; break;
//         case 'directionInfo': dataCustom = directionInfo; break;
//         case 'colorInfo': dataCustom = colorInfo; break;
//         case 'mistakeType': dataCustom = mistakeType; break;
//         case 'plateType': dataCustom = plateType; break;
//         case 'carKindType': dataCustom = carKindType; break;
//     }
//     const aimArr = dataCustom.filter(item => item[keyType] === val);
//
//     //多个返回数组
//     if (aimArr.length > 1) {
//         cellName = aimArr.map(item => item[rType])
//     }
//
//     //单个返回 单个值
//     if (aimArr.length === 1) {
//         cellName = aimArr.map(item => item[rType])[0]
//     }
//
//     return cellName;
// }
/**
 * 可配置数据字典查询方法
 * @param {string|number} val
 * @param {Array} arr 需要转换的数组
 * @param {string} keyType 传进来的类型 code、gbCode、name、gbName
 * @param {string} rType 返回的类型 code、gbCode、name、gbName
 * @returns {string}
 */
// export function dataTranslate(val, arr, keyType = 'code', rType = 'name') {
//     let cellName = '-';
//     const aimArr = arr.filter(item => item[keyType] === val);
//
//     //多个返回数组
//     if (aimArr.length > 1) {
//         cellName = aimArr.map(item => item[rType])
//     }
//
//     //单个返回 单个值
//     if (aimArr.length === 1) {
//         cellName = aimArr.map(item => item[rType])[0]
//     }
//
//     return cellName;
// }
//

/**
* 防抖函数
* @param {Function} func  防抖动函数
* @param {Number} delay 延时触发时长
*/
export function debounce(func, delay = 200) {
    let timer = null;

    return function () {
        const _this = this;

        clearTimeout(timer);
        timer = setTimeout(() => {
            func.apply(_this, arguments);
        }, delay);
    };
}

/**
* 节流函数
* @param {Function} func  需节流之函数
* @param {Number} delay 延时触发时长
* @param {Number} duration 节流之时间
*/
export function throttle(func, delay = 200, duration = 500) {
    let timer = null;
    let beginTime = new Date().getTime();

    return function () {
        const _this = this;
        let currentTime = new Date().getTime();

        if (currentTime - beginTime >= duration) {
            func.apply(_this, arguments);
            beginTime = currentTime;
        } else {
            clearTimeout(timer);
            timer = setTimeout(() => {
                func.apply(this, arguments);
            }, delay);
        }
    };
}


/**
 *aes加密 ecb模式
 * @param {String} pwd 需要加密的字符串
 * @param {String} key 密钥
 */
// const CryptoJS = require('crypto-js');

// export function Encrypt(pwd, key) {
//     const ekey = CryptoJS.enc.Utf8.parse(key);
//     const encryptedData = CryptoJS.AES.encrypt(pwd, ekey, {
//         mode: CryptoJS.mode.ECB,
//         padding: CryptoJS.pad.Pkcs7
//     });
//
//     return encryptedData.toString();
// }
/**
 *aes解密密 ecb模式
 * @param {String} encryptedData 加密的字符串
 * @param {String} key 密钥
 */
// export function Decrypt(encryptedData, key) {
//     const ekey = CryptoJS.enc.Utf8.parse(key);
//     const decryptedData = CryptoJS.AES.decrypt(encryptedData, ekey, {
//         mode: CryptoJS.mode.ECB,
//         padding: CryptoJS.pad.Pkcs7
//     });
//
//     return decryptedData.toString(CryptoJS.enc.Utf8);
//
// }
/**
 *
 * 合并系统code，解决国标code对应多个系统code问题。
 * @param {*} arr
 * @param {*} type
 */
// export function mergeArr(arr, type = 'gbCode') {
//     let gbCodeArr = [];
//     let newArr = []
//
//     arr.map(item => {
//         if (gbCodeArr.includes(item.gbCode)) {
//             newArr = newArr.map(item2 => {
//                 if (item2.gbCode === item.gbCode) {
//                     return {
//                         ...item,
//                         code: `${item.code},${item2.code}`
//                     }
//                 } else {
//                     return item2
//                 }
//             })
//         } else {
//             gbCodeArr.push(item.gbCode);
//             newArr.push(item);
//         }
//     })
//     return newArr
// }

/**
 * 获取不同项目的配置违法行为
 * @param {number} projectId //项目Id
 */
// export async function getProjectIllegall(projectId = 0) {
//     let arr = [];
//     const res = await configAjax('getdictlist', { projectId });
//
//     if (res.errorCode === 0) {
//         const { illegalType } = res.data;
//
//         arr = illegalType
//     }
//     return arr;
//
// }

export default {
    // getUnixDate,
    timeFormat,
    // tableFormat,
    // showLoading,
    closeLoading,
    message,
    notify,
    confirm,
    // getLocalTime,
    // debounce,
    // throttle,
    // Encrypt,
    // Decrypt,
    // mergeArr,
    // dataTranslate,
    // getProjectIllegall,
};
