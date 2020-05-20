<template>
    <div class="search-result" style="height:100%;position:relative;">
        <el-row class="tool-box" v-show="totalNum>0 && SearchResultOpt.check">
            <el-checkbox v-model="allCheck" @change="handleCheckAllChange">全选</el-checkbox>
        </el-row>
                <el-row class="item-wrap">
                    <el-checkbox-group v-model="checkedItems" @change="handleCheckedChange">
                        <el-col
                            v-for="(item,index) in searchData"
                            :class="['items',{'shake':activeIndex===index&&isShake}]"
                            :span="5"
                            :key="item.id"
                            :offset="index%4==0?0:1"
                            :ref="`item${index}`"
                        >
                            <el-card
                                :body-style="{ padding: '0px' }"
                                shadow="never"
                                style="border: 0;"
                                @mouseover.native="removeShake(index)"
                            >
                                <el-checkbox
                                    :key="item.id"
                                    :label="item.id"
                                    class="item-checkbox"
                                    v-show="SearchResultOpt.check"
                                ></el-checkbox>
                                <span class="wait-check" v-show="SearchResultOpt.waitCheck">
                                    <span class="wait-check-left"></span>
                                    <span
                                        class="wait-check-word"
                                    >{{ item.checkStatus | checkStatus(item.recogCheckStatus) }}</span>
                                </span>
                                <div class="image-wrap">
                                    <img
                                        :src="item.id|imageUrl"
                                        :ref="'mainImg'+index"
                                        @dblclick="showImages(item,index)"
                                        @mousemove="handleMousemove($event,index)"
                                        @mousewheel="handleMousewheel($event)"
                                    />
                                    <div
                                        :id="'mirror'+index"
                                        :class="index%4==0?'mirror1':'mirror'"
                                        :style="{width:mirrorWidth+'px',height:mirrorHeight+'px'}"
                                    >
                                        <img
                                            :src="item.id|imageUrl"
                                            :ref="'mirrorImg'+index"
                                            :style="{transformOriginX: mx+'px',transformOriginY: my+'px',transform: 'scale('+zoomTimes+')'}"
                                        />
                                    </div>
                                </div>
                                <div class="bottom-part">
                                    <p class="attr-item" v-show="SearchResultOpt.carNum">
                                        号牌号码:
                                        <b
                                            class="car-card"
                                            :class="'card' + item.plateColorCode"
                                            :title="item.carPlateNumber"
                                        >{{item.carPlateNumber}}</b>
                                    </p>
                                    <p
                                        class="attr-item"
                                        v-show="SearchResultOpt.illegalBehavior"
                                        :title="item.illegalCode | formatterIllegal"
                                    >违法行为:{{item.illegalCode | formatterIllegal}}</p>
                                    <p
                                        class="attr-item"
                                        v-show="SearchResultOpt.checkResult"
                                    >审核结果:{{item.checkStatus | checkStatus }}</p>
                                    <!-- <p
                                        class="attr-item"
                                        v-show="SearchResultOpt.carType"
                                    >车辆款型:{{item.carStyleName}}</p>
                                    <p
                                        class="attr-item"
                                        v-show="SearchResultOpt.carColor"
                                    >车辆颜色:{{item.carColorCode | formatterCarColor}}</p>-->
        <!--                            <p-->
        <!--                                class="attr-item"-->
        <!--                                v-show="SearchResultOpt.passTime"-->
        <!--                            >过车时间:{{timeFormat(item.snapshotTime,'dateTime')}}</p>-->
                                    <p class="attr-item" v-show="SearchResultOpt.passPlace">
                                        违法点位:{{item.roadName}}
                                        <span
                                            v-show="item.carDirect"
                                        >-{{item.carDirect | carDirect}}</span>
                                    </p>
                                    <p
                                        class="attr-item"
                                        v-show="SearchResultOpt.recogUnpassCode"
                                    >废片原因:{{item.recogUnpassCode|filterMistake}}</p>
                                    <p
                                        @click="moreInfo(item)"
                                        class="more-info"
                                        v-show="SearchResultOpt.moreInfo"
                                    >...</p>
                                </div>
                            </el-card>
                        </el-col>
                    </el-checkbox-group>
                </el-row>
        <!--        <el-row v-show="totalNum>0" class="pagenation">-->
        <!--            <div class="pagination-box">-->
        <!--                <el-pagination-->
        <!--                    :current-page.sync="currentPage2"-->
        <!--                    :page-size="pageSize"-->
        <!--                    :pager-count="5"-->
        <!--                    :total="totalNum"-->
        <!--                    layout="prev, pager, next, total, jumper"-->
        <!--                    @current-change="handlePageChange"-->
        <!--                ></el-pagination>-->
        <!--            </div>-->
        <!--        </el-row>-->
        <!--        <el-dialog-->
        <!--            :visible.sync="isBigImgShow"-->
        <!--            width="100%"-->
        <!--            class="big-img"-->
        <!--            :close-on-click-modal="false"-->
        <!--            @close="isShake = true"-->
        <!--        >-->
        <!--            <el-row class="big-img-top">-->
        <!--&lt;!&ndash;                <Carousel ref="swiperImg" :imgList="imageIdList" />&ndash;&gt;-->
        <!--            </el-row>-->
        <!--            <el-row class="big-img-bottom">-->
        <!--                <el-button-->
        <!--                    plain-->
        <!--                    round-->
        <!--                    icon="el-icon-arrow-left"-->
        <!--                    :disabled="activeIndex===0"-->
        <!--                    @click="nextPrev('prev')"-->
        <!--                >上一条</el-button>-->
        <!--                {{activeIndex+1}}/{{searchData.length}}-->
        <!--                <el-button-->
        <!--                    plain-->
        <!--                    round-->
        <!--                    :disabled="activeIndex===searchData.length-1"-->
        <!--                    @click="nextPrev('next')"-->
        <!--                >-->
        <!--                    下一条-->
        <!--                    <i class="el-icon-arrow-right"></i>-->
        <!--                </el-button>-->
        <!--            </el-row>-->
        <!--        </el-dialog>-->
    </div>
</template>

<script>
  // import Carousel from '../carousel';
  // import emptyImg from "@/assets/img/empty_img.png";
  // import {
  //   firStatusW,
  //   firStatusS,
  //   secStatusT,
  //   thirdStatusT
  // } from "../../common/dataCustom";

  // const mergeStatus = [
  //   ...firStatusW,
  //   ...firStatusS,
  //   ...secStatusT,
  //   ...thirdStatusT
  // ];
  // let _this;

  export default {
    // props: ['searchData', 'totalNum', 'SearchResultOpt', 'multipleSelection'],
    props: ["searchData", "totalNum", "SearchResultOpt"],
    components: {
      // Carousel
    },
    data() {
      // _this = this;
      return {
        // pageSize: 24,
        // currentPage2: 1,
        allCheck: false,
        checkedItems: [], //所有勾选的Id集合
        // bigImgId: '',
        // isBigImgShow: false,
        // mx: 0,
        // my: 0,
        // zoomTimes: 15,
        // mirrorWidth: 0,
        // mirrorHeight: 0,
        // path: '',
        // imageIdList: [],
        // activeIndex: 0,
        // timer: null,
        // isShake: false
      };
    },
    // computed: {
    //     getSelectedData() {
    //         return this.$store.state.selectedData;
    //     },
    //     illegalBehaviorInfo() {
    //         return this.$store.state.illegalBehaviorInfoRecog
    //     }
    // },
    // created() {
    //     const { path } = this.$route;
    //
    //     this.path = path;
    // },
    // watch: {
    //     searchData(newVal) {
    //         if (newVal && this.SearchResultOpt.check) {
    //             let flag = true;
    //
    //             newVal.map(i => {
    //                 flag = this.checkedItems.includes(i['faceId']);
    //             });
    //             this.allCheck = flag;
    //         }
    //     }
    // },
    // filters: {
    //     imageUrl(val) {
    //         if (!val) {
    //             return emptyImg;
    //         }
    //         let imgUrl = `${
    //             window.location.origin
    //             }/business/image/getImage?id=${val}&type=${
    //             _this.SearchResultOpt.imgType
    //             }&compressRate=0.8`;
    //
    //         if (_this.path.includes('preTestResult')) {
    //             imgUrl = imgUrl + '&showReturnImage=true';
    //         }
    //         return imgUrl;
    //     },
    //     checkStatus(val, val2) {
    //         //当chekType===1处于违法查询页面只采用初审状态的值。
    //         //当checkType===2处于预审测试结果页面并且初审状态未0时，采用预审状态的值。
    //         if (_this.SearchResultOpt.checkType === 2 && val === 0) {
    //             val = val2;
    //         }
    //         const aim = mergeStatus.find(i => i.code === val);
    //
    //         if (aim && aim.name) {
    //             val = aim.name;
    //         }
    //         return val;
    //     },
    //     carDirect(val) {
    //         switch (val) {
    //             case 2:
    //                 val = '东向西';
    //                 break;
    //             case 1:
    //                 val = '西向东';
    //                 break;
    //             case 4:
    //                 val = '南向北';
    //                 break;
    //             case 3:
    //                 val = '北向南';
    //                 break;
    //             case 8:
    //                 val = '东南到西北';
    //                 break;
    //             case 7:
    //                 val = '西北到东南';
    //                 break;
    //             case 6:
    //                 val = '东北到西南';
    //                 break;
    //             case 5:
    //                 val = '西南到东北';
    //                 break;
    //             case 9:
    //                 val = '其他';
    //                 break;
    //             default:
    //                 val = '其他';
    //                 break;
    //         }
    //         return val;
    //     },
    //     formatterIllegal(val) {
    //         return _this.tools.dataTranslate(val, _this.illegalBehaviorInfo, 'code', 'gbName');
    //     },
    //     formatterCarColor(val) {
    //         return _this.tools.tableFormat(val, 'colorInfo');
    //     },
    //     filterMistake(val) {
    //         return _this.tools.tableFormat(val, 'mistakeType', 'gbCode', 'gbName')
    //
    //     }
    // },
    methods: {
      // removeShake(index) {
      //     if (index === this.activeIndex) {
      //         setTimeout(() => {
      //             this.isShake = false;
      //         }, 800);
      //     }
      // },
      // handleMousemove(e, index) {
      //     const { offsetX, offsetY } = e;
      //     const mainImg = this.$refs['mainImg' + index][0]; // 主展示图片，最大的那张
      //
      //     if (!mainImg) {
      //         return;
      //     } //切换的时候会报红
      //     const naturalPro = mainImg.naturalWidth / mainImg.naturalHeight; // 原始图片宽高比例
      //     const currentPro = mainImg.width / mainImg.height; // 主展示图片比例
      //
      //     if (isNaN(naturalPro)) {
      //         return;
      //     } // 图片加载失败无宽高
      //     this.mirrorWidth = 248; // 放大的区域宽度
      //     this.mirrorHeight = 248 / naturalPro; // 放大的区域高度
      //     const mirrorImg = this.$refs['mirrorImg' + index][0]; // 放大区域
      //
      //     if (currentPro > naturalPro) {
      //         const overWidth =
      //             (mainImg.width - mainImg.height * naturalPro) / 2; // 图片居中导致两边留白，此处去除左右留白之影响。
      //         const scaleX = mirrorImg.width / (mainImg.height * naturalPro); // 主展示图片与放大区域的宽度比例
      //         const scaleY = mirrorImg.height / mainImg.height; // 主展示图片与放大区域的高度比例
      //
      //         this.mx = (offsetX - overWidth) * scaleX; // 放大之中心点X
      //         this.my = offsetY * scaleY; // 放大之中心点Y
      //     } else {
      //         const overHeight =
      //             (mainImg.height - mainImg.width / naturalPro) / 2; // 图片居中导致两边留白，此处去除上下留白之影响。
      //         const scaleX = mirrorImg.width / mainImg.width; // 主展示图片与放大区域的宽度比例
      //         const scaleY = mirrorImg.height / (mainImg.width / naturalPro); // 主展示图片与放大区域的高度比例
      //
      //         this.mx = offsetX * scaleX; // 放大之中心点X
      //         this.my = (offsetY - overHeight) * scaleY; // 放大之中心点Y
      //     }
      // },
      // handleMousewheel(e) {
      //     //阻止冒泡事件
      //     e = e || window.event;
      //     if (e.stopPropagation) {
      //         e.stopPropagation();
      //     } else {
      //         e.cancelBubble = true;
      //     }
      //     if (e.preventDefault) {
      //         e.preventDefault();
      //     } else {
      //         e.returnValue = false;
      //     }
      //     const { wheelDelta } = e;
      //     // wheelDelta 正代表向上滚动，负代表向下滚动
      //     // 放大倍数为1~35倍
      //
      //     if (wheelDelta > 0) {
      //         this.zoomTimes *= 1.5;
      //     } else if (wheelDelta < 0 && this.zoomTimes > 1) {
      //         this.zoomTimes *= 0.5;
      //     }
      //     if (this.zoomTimes > 35) {
      //         this.zoomTimes = 35;
      //     } else if (this.zoomTimes < 1) {
      //         this.zoomTimes = 1;
      //     }
      // },
      // timeFormat(val, type) {
      //     return this.tools.timeFormat(val, type);
      // },
      // handlePageChange(currentPage) {
      //     this.$emit('getCurrentPage', currentPage);
      // },
      // getPageChange(currentPage) {
      //     this.currentPage2 = currentPage;
      // },
      handleCheckAllChange(val) {
        if (val) {
          this.searchData.map(i => {
            if (!this.checkedItems.includes(i.id)) {
              this.checkedItems.push(i.id);
            }
          });
        } else {
          this.checkedItems = [];
        }
        this.$emit("setSelection", this.checkedItems);
        this.updateStore();
      },
      handleCheckedChange(value) {
          let checkedCount = value.length;

          this.allCheck = checkedCount === this.searchData.length;
          this.updateStore();
      },
      // updateStore() {
      //     let selectedData = this.getSelectedData;
      //
      //     selectedData[this.currentPage2 - 1] = this.checkedItems;
      //     this.$store.commit('saveSelectedData', selectedData);
      // },
      // getCheckedChange(value) {
      //     this.checkedItems = value;
      //     let checkedCount = value.length;
      //
      //     this.allCheck = checkedCount === this.searchData.length;
      // },
      // mergeArray(a, b) {
      //     let ai = 0,
      //         bi = 0;
      //     let result = [];
      //
      //     while (ai < a.length && bi < b.length) {
      //         if (a[ai] < b[bi]) {
      //             ai++;
      //         } else if (a[ai] > b[bi]) {
      //             bi++;
      //         } /* they're equal */ else {
      //             result.push(a[ai]);
      //             ai++;
      //             bi++;
      //         }
      //     }
      //     return result;
      // },
      // moreInfo(row) {
      //     //第一个参数名为调用的方法名，第二个参数为需要传递的参数
      //     this.$emit('moreInfo', row);
      // },
      //
      // showImages(item, index) {
      //     // if (this.SearchResultOpt.checkType === 2) { return; } //预审结果页不需要这个功能
      //     this.isBigImgShow = true;
      //     this.activeIndex = index;
      //     const { imageIdList } = item;
      //
      //     this.imageIdList = imageIdList;
      // },
      // nextPrev(type) {
      //     this.activeIndex =
      //         type === 'prev' ? this.activeIndex - 1 : this.activeIndex + 1;
      //
      //     clearTimeout(this.timer);
      //     this.timer = setTimeout(() => {
      //         const { imageIdList } = this.searchData[this.activeIndex];
      //
      //         this.imageIdList = imageIdList;
      //     }, 500);
      // },
      // parentToggle(idx) {
      //     this.activeIndex = idx
      //     this.isShake = true
      // }

    }
  };
</script>
<style lang="less">
    .search-result {
        .item-wrap {
            height: 100%;
            overflow-x: hidden;
            overflow-y: scroll;

            .shake {
                animation: shake 800ms infinite;
                // animation-iteration-count:3;
            }

            .items {
                margin-top: 10px;
                position: relative;
                font-size: 12px;

                .image-wrap {
                    width: 100%;
                    height: 140px;
                    background-color: #000; // &:hover {
                    > img:hover + .mirror,
                    > img:hover + .mirror1 {
                        opacity: 1;
                        z-index: 2;
                    }

                    > img {
                        object-fit: contain;
                        width: 100%;
                        height: 100%;
                    }

                    .mirror,
                    .mirror1 {
                        width: 200px;
                        height: 123px;
                        z-index: -1;
                        position: absolute;
                        top: 0;
                        left: -100%;
                        opacity: 0;
                        overflow: hidden;
                        transition: 0.5s;
                        background-color: #000;

                        > img {
                            width: 100%;
                            height: 100%;
                            object-fit: contain;
                            user-select: none;
                        }
                    }

                    .mirror1 {
                        left: 100%;
                    }
                }

                .bottom-part {
                    height: 195px;
                    box-sizing: border-box;
                    padding: 10px 4px 10px 0;
                    text-align: left;
                    line-height: 25px;
                    font-size: 14px;

                    .realize {
                        margin-left: 5px;
                    }

                    .car-card {
                        display: inline-block;
                        width: 70px;
                        height: 18px;
                        line-height: 18px;
                        background-color: #dcf0fb;
                        border-radius: 4px;
                        font-size: 12px;
                        font-weight: normal;
                        text-align: center;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                        vertical-align: text-bottom;
                    }

                    .car-card.card4 {
                        background-color: #ffffff;
                        box-shadow: 2px 3px 3px 0px rgba(6, 0, 1, 0.1);
                        border-radius: 3px;
                        border: solid 1px #9a9a9a;
                    }

                    .car-card.card1 {
                        background-color: #fecb68;
                        box-shadow: 2px 3px 3px 0px rgba(6, 0, 1, 0.1);
                        border-radius: 3px;
                        border: solid 1px #9a9a9a;
                    }

                    .car-card.card2 {
                        background-color: #1167fd;
                        box-shadow: 2px 3px 3px 0px rgba(6, 0, 1, 0.1);
                        border-radius: 3px;
                        border: solid 1px #9a9a9a;
                        color: #fff;
                    }

                    .car-card.card3 {
                        background-color: #000000;
                        box-shadow: 2px 3px 3px 0px rgba(6, 0, 1, 0.1);
                        border-radius: 3px;
                        border: solid 1px #9a9a9a;
                        color: #fff;
                    }

                    .car-card.card5 {
                        background-image: linear-gradient(0deg,
                        #6bda92 0%,
                        #fbfdfc 100%);
                        box-shadow: 2px 3px 3px 0px rgba(6, 0, 1, 0.1);
                        border-radius: 3px;
                        border: solid 1px #9a9a9a;
                    }

                    .car-card.card100 {
                        background-image: linear-gradient(0deg,
                        rgba(138, 137, 137, 0.6) 0%,
                        #dfd9d9 100%);
                        box-shadow: 2px 3px 3px 0px rgba(6, 0, 1, 0.1);
                        border-radius: 3px;
                    }

                    .recog-card {
                        color: #414141;
                        font-weight: normal;
                    }
                }

                .attr-item {
                    font-size: 12px;
                    width: 100%;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    line-height: 21px;
                }

                .more-info {
                    float: right;
                    height: 15px;
                    line-height: 0;
                    cursor: pointer;
                    font-size: 20px;
                    font-weight: 600;
                    margin-right: 5px;
                }

                .item-checkbox {
                    position: absolute;
                    margin-left: 6px;
                    margin-top: 2px;
                }

                .el-checkbox__label {
                    display: none;
                }

                .reset-status {
                    cursor: pointer;
                }

                .wait-check {
                    position: absolute;
                    top: 10px;
                    right: 0;

                    .wait-check-left {
                        content: ' ';
                        color: transparent;
                        display: inline-block;
                        width: 0;
                        border-top: 10px solid #ff9f22;
                        border-bottom: 8px solid #ff9f22;
                        border-right: 3px solid #ff9f22;
                        border-left: 6px solid transparent;
                        vertical-align: top;
                    }

                    .wait-check-word {
                        display: inline-block;
                        padding: 0 3px;
                        height: 16px;
                        line-height: 16px;
                        background-color: #ff9f22;
                        border: solid 1px #ff9305;
                        border-left: 0;
                        text-align: center;
                        color: #fff;
                        font-size: 12px;
                    }
                }
            }

            .items:nth-child(1),
            .items:nth-child(2),
            .items:nth-child(3),
            .items:nth-child(4) {
                margin-top: 0;
            }
        }

        .tool-box {
            margin-bottom: 20px;
        }

        .el-checkbox.is-checked > .el-checkbox__label {
            color: #000;
            /*fonts-weight: 550;*/
            font-weight: bolder;
        }
    }

    .big-img {
        overflow: hidden;

        .el-dialog {
            margin: 0;
            height: 100% !important;
            margin-top: 0 !important;

            .el-dialog__body {
                padding: 0;
                width: 100%;
                height: 95%;
                max-height: 1000px;
                padding-top: 10px;

                img {
                    object-fit: contain;
                }
            }
        }

        .big-img-top {
            height: 100%;
        }

        .big-img-bottom {
            position: absolute;
            text-align: center;
            bottom: 5%;
            right: 1%;
        }
    }

    @keyframes shake {
        0%,
        50%,
        100% {
            transform: translateX(0) translateY(0);
        }
        25% {
            transform: translateX(-3px) translateY(3px);
        }
        75% {
            transform: translateX(3px) translateY(3px);
        }
    }
</style>
