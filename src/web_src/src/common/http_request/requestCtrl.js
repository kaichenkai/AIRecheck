/**
 * Created by wei
 * User: wei@seemmo.com
 * Date: 2018/10/09
 * Describe:
 * Log:
 *  ---- 2018/10/09 10:55 [wei] 初次添加
 **/
// import { request } from "./serviceBase";
// import tools from "../tools";
//
// const urlLoaction = window.location.origin;
// const URLS = {
//   illegal: "/system/config/illegal/",
//   plateColor: "/system/config/plateColor/",
//   direction: "/system/config/direction/",
//   record: "/system/config/record/",
//   company: "/system/config/company/",
//   carColor: "/system/config/carColor/",
//   plateType: "/system/config/plateType/",
//   carKindType: "/system/config/carKindType/"
// };
//
//
// const requestCommon = {
//   configAjax(params, ctrltype, pageType) {
//     let url = URLS[pageType];
//     let method = "GET";
//
//     url = url + ctrltype;
//     if (ctrltype === "add" || ctrltype === "update" || ctrltype === "import") {
//       method = "POST";
//     }
//     if (ctrltype === "list" || ctrltype === "import") {
//       return request(urlLoaction + url, params, { method: method })
//         .then((res) => {
//           if (parseInt(res.errorCode, 10) !== 0) {
//             tools.message(res.message, "error");
//           }
//           return res;
//         });
//     } else {
//       return request(urlLoaction + url, params, { method: method })
//         .then((res) => {
//           if (parseInt(res.errorCode, 10) === 0) {
//             if (ctrltype === "add") {
//               tools.message("新增成功", "success");
//             } else if (ctrltype === "update") {
//               tools.message("更新成功", "success");
//             } else if (ctrltype === "delete") {
//               tools.message("删除成功", "success");
//             }
//             return true;
//           } else {
//             tools.message(res.message, "error");
//             return false;
//           }
//         })
//         .catch(() => {
//           return false;
//         });
//     }
//
//   }
// };
//
// export default {
//   ...requestCommon
// };
