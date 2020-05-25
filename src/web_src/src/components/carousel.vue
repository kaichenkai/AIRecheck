<template>
    <div style="width:100%;height:100%;">
        <!--        <el-row class="top-imgs" v-if="isEasyMode">-->
        <!--            <el-col-->
        <!--                    v-for="(item,idx) in imageIdObj"-->
        <!--                    :key="idx"-->
        <!--                    @click.native="imgActiveIndex=idx;"-->
        <!--                    :class="['img-wrap',{'img-actived':imgActiveIndex===idx}]"-->
        <!--                    :span="4"-->
        <!--                    :offset="idx?1:0"-->
        <!--            >-->
        <!--                <span class="pic-title">{{item.name}}</span>-->
        <!--                <img :src="item.id|imageUrl"/>-->
        <!--            </el-col>-->
        <!--        </el-row>-->

        <el-col
                class="main-img"
                :style="{'height':isEasyMode?'calc(100% - 110px)':'100%','width':isEasyMode?'100%':'80%'}"
                v-show="idx>=0"
                v-loading="loading"
                element-loading-background="rgba(0, 0, 0, 0.5)"
        >
            <el-button
                    class="prev-btn"
                    type="info"
                    @click="slideTo('prev')"
                    v-show="!isAnimate"
                    circle
                    :icon="isEasyMode?'el-icon-arrow-left':'el-icon-arrow-up'"
                    size="small"
            ></el-button>
            <!--                                @mousemove="handleMousemove({e:$event})"-->
            <!--                    @mousewheel="handleMousewheel($event)"-->
            <!--                    style="cursor: zoom-in"-->
            <img
                    :src="idx | imageUrl"
                    ref="mainImg"
                    @click="showImgPlugin"
            />

            <div
                    id="mirror"
                    :style="{width:mirrorWidth+'px',height:mirrorHeight+'px'}"
                    v-if="!isAnimate"
            >
                <img
                        :src="idx | imageUrl"
                        ref="mirrorImg"
                        :style="{transformOriginX: mx+'px',transformOriginY: my+'px',transform: 'scale('+zoomTimes+')'}"
                />
            </div>

            <!--            <Magnifier-->
            <!--                    :idx="idx"-->
            <!--                    :imgActiveIndex="imgActiveIndex"-->
            <!--                    ref="magnifier"-->
            <!--                    v-loading="isAnimate"-->
            <!--                    element-loading-spinner="el-icon-loadin"-->
            <!--                    element-loading-background="rgba(0, 0, 0, 0)"-->
            <!--            />-->

            <el-button
                    class="next-btn"
                    type="info"
                    @click="slideTo('next')"
                    v-show="!isAnimate"
                    circle
                    :icon="isEasyMode?'el-icon-arrow-right':'el-icon-arrow-down'"
                    size="small"
            ></el-button>

            <!--                               @mousemove="handleRectMousemove($event)"-->
            <!--                    @mousewheel="handleMousewheel($event)"-->
            <!--            v-for="(i,idx) in boxPointCalc"-->
            <!--标记-->
            <!--            <div class="markRect"-->
            <!--                 :style="{width:boxWidth+'px',height:boxHeight+'px',left:boxLeft+'px',top:boxTop+'px'}"-->
            <!--                 v-show="boxWidth>0 && boxHeight>0"-->
            <!--                 @click="showImgPlugin"-->
            <!--            ></div>-->
        </el-col>

        <!--        v-show="!idx || idx!==''&&!isEasyMode"-->
        <el-col
                class="secondary-img"
                style="height:100%;"

        >
            <!--                    v-if="titleName==='违法初审'||titleName==='违法复审'||titleName==='预审测试审核'"-->
            <!--            <el-checkbox-->
            <!--                    v-model="isAnimate"-->
            <!--                    @change="animate"-->
            <!--            >动态图查看-->
            <!--            </el-checkbox>-->

            <ul
                    class="tab-tool"
                    v-loading="isAnimate"
                    element-loading-spinner="el-icon-loadin"
                    element-loading-background="rgba(0, 0, 0, 0.2)"
            >
                <!--                <li-->
                <!--                        v-for="(i,idx) in imgList"-->
                <!--                        :key="idx"-->
                <!--                        @click="imgActiveIndex=idx;"-->
                <!--                        :class="{'img-actived':imgActiveIndex===idx}"-->
                <!--                        v-show="i"-->
                <!--                >-->
                <li :key="0"
                    @click="imgActiveIndex=0;"
                    :class="{'img-actived':imgActiveIndex===0}"
                >
                    <img :src="idx | imageUrl" alt=""/>
                </li>
                <!--车牌图-->
                <li :key="1"
                    @click="imgActiveIndex=1;"
                    :class="{'img-actived':imgActiveIndex===1}"
                >
                    <!--                    <img :src="i|imageUrl"/>-->
                    <img :src="idx | plateImageUrl(sdkPlateRect)" alt=""/>
                </li>
            </ul>
        </el-col>

        <!--        <img :src="idx" class="no-pic" v-show="idx !== '' || !idx"  alt=""/>-->
        <img src="../assets/img/no-pic.png" class="no-pic" v-show="!idx"/>
    </div>
</template>

<script>
  export default {
    name: "Carousel",
    components: {},
    props: ["idx", "sdkPlateRect"],
    data() {
      return {
        // 页面控制参数
        isEasyMode: false,
        loading: false,
        isAnimate: false,

        // 图片显示参数
        imgActiveIndex: 0,
        mirrorWidth: 0,
        mirrorHeight: 0,
        mx: 0,
        my: 0,
        zoomTimes: 15
      };
    },
    mounted() {
    },
    // 侦听器
    watch: {
      // sdkPlateRect(value) {
      //   console.log(value);
      // }
    },
    filters: {
      imageUrl(imgIdx) {
        if (!imgIdx) {
          return;
        }
        if (imgIdx.length > 50) {
          return imgIdx;
        } else {
          return `${window.location.origin}/api/show/image/${imgIdx}`;
        }
      },

      plateImageUrl(imgIdx, sdkPlateRect) {
        if (!imgIdx) {
          return;
        }
        if (imgIdx.length > 50) {
          return imgIdx;
        } else {
          const queryStr = `box=${sdkPlateRect.replace("[", "").replace("]", "")}`;
          return `${window.location.origin}/api/show/image/${imgIdx}?${queryStr}`;
        }
      }
    },
    methods: {
      showImgPlugin() {
        if (!this.isAnimate) {
          // (this.$refs.magnifier as any).showImgPlugin(this.imgActiveIndex);
          // (this.$refs.magnifier).showImgPlugin(this.imgActiveIndex);
        }
      },

      // 切换
      slideTo(type) {
        console.log(type);
        // const len = this.imgList.length - 1;
        // if (type === "next" && this.imgActiveIndex < len) {
        //   this.imgActiveIndex++;
        // } else if (type === "prev" && this.imgActiveIndex > 0) {
        //   this.imgActiveIndex--;
        // } else if (this.imgActiveIndex === len && type === "next") {
        //   this.imgActiveIndex = 0;
        // } else if (this.imgActiveIndex === 0 && type === "prev") {
        //   this.imgActiveIndex = len;
        // }
      },

      handleRectMousemove(e) {
        if (this.isAnimate) {
          return;
        }
        const { offsetX, offsetY } = e;
        let { left, top } = e.target.style;
        const params = {
          rectX: parseInt(left, 10) + offsetX,
          rectY: parseInt(top, 10) + offsetY
        };

        this.handleMousemove(params);
      },

      handleMousemove(e, rectX, rectY) {
        if (this.isAnimate) {
          return;
        }
        let [offsetX, offsetY] = [0, 0];

        if (!e) {
          offsetX = rectX;
          offsetY = rectY;
        } else {
          offsetX = e.offsetX;
          offsetY = e.offsetY;
        }
        const mainImg = this.$refs.mainImg; // 主展示图片，最大的那张

        if (!mainImg) {
          return;
        }
        const naturalPro = mainImg.naturalWidth / mainImg.naturalHeight; // 原始图片宽高比例
        const currentPro = mainImg.width / mainImg.height; // 主展示图片比例

        if (isNaN(naturalPro)) {
          return;
        }
        this.mirrorWidth = 300; // 放大的区域宽度
        this.mirrorHeight = 300 / naturalPro; // 放大的区域高度
        const mirrorImg = this.$refs.mirrorImg; // 放大区域

        if (currentPro > naturalPro) {
          const overWidth = (mainImg.width - mainImg.height * naturalPro) / 2; // 图片居中导致两边留白，此处去除左右留白之影响。
          const scaleX = mirrorImg.width / (mainImg.height * naturalPro); // 主展示图片与放大区域的宽度比例
          const scaleY = mirrorImg.height / mainImg.height; // 主展示图片与放大区域的高度比例

          this.mx = (offsetX - overWidth) * scaleX; // 放大之中心点X
          this.my = offsetY * scaleY; // 放大之中心点Y
        } else {
          const overHeight =
            (mainImg.height - mainImg.width / naturalPro) / 2; // 图片居中导致两边留白，此处去除上下留白之影响。
          const scaleX = mirrorImg.width / mainImg.width; // 主展示图片与放大区域的宽度比例
          const scaleY = mirrorImg.height / (mainImg.width / naturalPro); // 主展示图片与放大区域的高度比例

          this.mx = offsetX * scaleX; // 放大之中心点X
          this.my = (offsetY - overHeight) * scaleY; // 放大之中心点Y
        }
      },
    }
  };
</script>


<!--<script lang="ts">-->
<!--import { Vue, Component, Prop, Watch } from 'vue-property-decorator';-->
<!--import { State } from 'vuex-class';-->
<!--import { configAjax } from '../common/tsService';-->
<!--import { message, debounce } from '../common/tools';-->
<!--import Magnifier from '../components/magnifier.vue';-->
<!--const noPic = require('../assets/no-pic.png');-->

<!--@Component({-->
<!--    components: {-->
<!--        Magnifier-->
<!--    },-->
<!--    filters: {-->
<!--        imageUrl(val: string) {-->
<!--            if (!val) {-->
<!--                return;-->
<!--            }-->
<!--            if (val.length > 50) {-->
<!--                return val;-->
<!--            } else {-->
<!--                return `${window.location.origin}/business/image/get?imageId=${val}&compressRate=0.1`;-->
<!--            }-->
<!--        }-->
<!--    }-->
<!--})-->
<!--export default class Carousel extends Vue {-->
<!--    @Prop() imageIdObj: object;-->
<!--    @Prop() imgList: string[];-->
<!--    @Prop() currentId: string;-->
<!--    @State(state => state.initial.isEasyMode) isEasyMode1: boolean;-->
<!--    @State(state => state.confirm.isEasyMode) isEasyMode2: boolean;-->
<!--    @State(state => state.preTestCheck.isEasyMode) isEasyMode3: boolean;-->

<!--    isEasyMode: boolean = false;-->

<!--    private imgActiveIndex: number = 0;-->
<!--    // private isPrevDisabled: boolean = true;-->
<!--    // private isNextDisabled: boolean = false;-->
<!--    private activeImgSrc: string = '';-->
<!--    private mx: number = 0;-->
<!--    private my: number = 0;-->
<!--    private zoomTimes: number = 15;-->
<!--    private mirrorWidth: number = 0;-->
<!--    private mirrorHeight: number = 0;-->
<!--    private boxPointCalc: Array<any> = [];-->
<!--    private boxPointNature: Array<any> = [];-->
<!--    private processPic: Array<string> = [];-->
<!--    private isAnimate: boolean = false;-->
<!--    private timer: any = null;-->
<!--    private loading: boolean = false;-->
<!--    private titleName: string = '违法初审';-->
<!--    @Watch('imgActiveIndex')-->
<!--    imgActiveIndexChange(val: number) {-->
<!--        const len = this.imgList.length;-->

<!--        val = val + 1;-->
<!--        // this.isNextDisabled = val === len;-->
<!--        // this.isPrevDisabled = val === 1;-->
<!--        this.getImgPoint(this.imgList[this.imgActiveIndex], 'imgActiveIndex');-->
<!--    }-->
<!--    @Watch('imgList')-->
<!--    imgListChange(val: string[]) {-->
<!--        if (val.length > 0) {-->
<!--            this.imgActiveIndex === 0 &&-->
<!--                this.getImgPoint(val[0], 'imgListChange');-->
<!--            this.imgActiveIndex = 0;-->
<!--            // this.isNextDisabled = val.length > 1 ? false : true;-->
<!--            // this.isPrevDisabled = true;-->
<!--            this.mx = 0;-->
<!--            this.my = 0;-->
<!--            this.zoomTimes = 15;-->
<!--            this.mirrorWidth = 0;-->
<!--            this.mirrorHeight = 0;-->
<!--        } else {-->
<!--            this.activeImgSrc = noPic;-->
<!--        }-->
<!--        this.animate(false, true, true);-->
<!--    }-->
<!--    @Watch('isEasyMode1')-->
<!--    isEasyMode1Change(val: boolean) {-->
<!--        if (this.titleName === '违法初审') {-->
<!--            setTimeout(() => {-->
<!--                this.drawLine(false);-->
<!--            }, 300);-->
<!--            this.isEasyMode = val;-->
<!--        }-->
<!--    }-->
<!--    @Watch('isEasyMode2')-->
<!--    isEasyMode2Change(val: boolean) {-->
<!--        if (this.titleName === '违法复审') {-->
<!--            setTimeout(() => {-->
<!--                this.drawLine(false);-->
<!--            }, 300);-->
<!--            this.isEasyMode = val;-->
<!--        }-->
<!--    }-->
<!--    @Watch('isEasyMode3')-->
<!--    isEasyMode3Change(val: boolean) {-->
<!--        if (this.titleName === '预审测试审核') {-->
<!--            setTimeout(() => {-->
<!--                this.drawLine(false);-->
<!--            }, 300);-->
<!--            this.isEasyMode = val;-->
<!--        }-->
<!--    }-->

<!--    mounted() {-->
<!--        setTimeout(-->
<!--            () =>-->
<!--                this.getImgPoint(this.imgList[this.imgActiveIndex], 'mounted'),-->
<!--            300-->
<!--        );-->

<!--        // if (this.imgList.length > 1) {-->
<!--        //   this.isNextDisabled = false;-->
<!--        //   this.isPrevDisabled = true;-->
<!--        // } else if (this.imgList.length <= 1) {-->
<!--        //   this.isNextDisabled = true;-->
<!--        //   this.isPrevDisabled = true;-->
<!--        // }-->
<!--        window.onresize = debounce(this.drawLine, 300);-->
<!--        this.titleName = this.$route.name as string;-->
<!--    }-->
<!--    handleEasyMode() {-->
<!--        switch (this.titleName) {-->
<!--            case '违法初审':-->
<!--                this.isEasyMode = this.isEasyMode1;-->
<!--                break;-->
<!--            case '违法复审':-->
<!--                this.isEasyMode = this.isEasyMode2;-->
<!--                break;-->
<!--            case '预审测试审核':-->
<!--                this.isEasyMode = this.isEasyMode3;-->
<!--                break;-->
<!--            default:-->
<!--                this.isEasyMode = false;-->
<!--                break;-->
<!--        }-->
<!--        setTimeout(() => {-->
<!--            this.drawLine(false);-->
<!--        }, 300);-->
<!--    }-->
<!--    async getImgPoint(imageId: string, from: string) {-->
<!--        const _this = this;-->

<!--        this.loading = true;-->
<!--        const params = {-->
<!--            imageId,-->
<!--            boxPoint: 'true',-->
<!--            compressRate: 1-->
<!--        };-->
<!--        const res: any = await configAjax('getImgPoint', params);-->

<!--        if (parseInt(res.errorCode, 10) !== 0) {-->
<!--            // message(res.message);-->
<!--            return;-->
<!--        }-->
<!--        const { boxPoint, imageData } = res.data;-->

<!--        this.loading = false;-->
<!--        this.activeImgSrc = 'data:image/png;base64,' + imageData;-->
<!--        if (boxPoint.length > 0) {-->
<!--            this.boxPointNature = boxPoint.map((i: any) => ({ ...i }));-->
<!--        }-->
<!--        setTimeout(() => {-->
<!--            _this.drawLine(boxPoint);-->
<!--        }, 300);-->
<!--    }-->
<!--    drawLine(boxPoint: any) {-->
<!--        if (-->
<!--            boxPoint instanceof Array === false &&-->
<!--            this.boxPointNature.length > 0-->
<!--        ) {-->
<!--            //当窗口大小改变即重新用原始坐标计算框线-->
<!--            boxPoint = this.boxPointNature.map(i => ({ ...i }));-->
<!--        }-->
<!--        if (boxPoint.length === 0) {-->
<!--            this.boxPointCalc = boxPoint;-->
<!--            return;-->
<!--        }-->

<!--        const mainImg: any = this.$refs.mainImg; // 主展示图片，最大的那张-->
<!--        // if (this.isAnimate) {-->
<!--        // } else {-->
<!--        //   const imgPlugin: any = this.$refs.magnifier as any; // 主展示图片，最大的那张-->

<!--        //   mainImg = imgPlugin.$refs.childMainImg[this.imgActiveIndex];-->
<!--        // }-->
<!--        if (!mainImg || boxPoint === false) {-->
<!--            return;-->
<!--        }-->
<!--        const naturalPro = mainImg.naturalWidth / mainImg.naturalHeight; // 原始图片宽高比例-->
<!--        const currentPro = mainImg.width / mainImg.height; // 主展示图片比例-->

<!--        if (isNaN(naturalPro)) {-->
<!--            return;-->
<!--        }-->
<!--        const overWidth =-->
<!--            (mainImg.width - (mainImg.height + 4) * naturalPro) / 2; // 图片居中导致两边留白，此处去除左右留白之影响。-->
<!--        const overHeight = (mainImg.height - mainImg.width / naturalPro) / 2; // 图片居中导致两边留白，此处去除上下留白之影响。-->

<!--        boxPoint.map((item: any) => {-->
<!--            if (currentPro > naturalPro) {-->
<!--                item.x =-->
<!--                    (item.x / mainImg.naturalWidth) *-->
<!--                        (mainImg.width - overWidth * 2) +-->
<!--                    overWidth;-->
<!--                item.y =-->
<!--                    (item.y / mainImg.naturalHeight) * (mainImg.height + 2);-->
<!--                item.w =-->
<!--                    (item.w / mainImg.naturalWidth) *-->
<!--                    (mainImg.width - overWidth * 2);-->
<!--                item.h =-->
<!--                    (item.h / mainImg.naturalHeight) * (mainImg.height + 2);-->
<!--            } else {-->
<!--                item.x = (item.x / mainImg.naturalWidth) * mainImg.width;-->
<!--                item.y =-->
<!--                    (item.y / mainImg.naturalHeight) *-->
<!--                        (mainImg.height - overHeight * 2) +-->
<!--                    overHeight;-->
<!--                item.w = (item.w / mainImg.naturalWidth) * mainImg.width;-->
<!--                item.h =-->
<!--                    (item.h / mainImg.naturalHeight) *-->
<!--                    (mainImg.height - overHeight * 2);-->
<!--            }-->
<!--        });-->
<!--        this.boxPointCalc = boxPoint;-->
<!--    }-->

<!--    handleRectMousemove(e: any) {-->
<!--        if (this.isAnimate) {-->
<!--            return;-->
<!--        }-->
<!--        const { offsetX, offsetY } = e;-->
<!--        let { left, top } = e.target.style;-->
<!--        const params = {-->
<!--            rectX: parseInt(left, 10) + offsetX,-->
<!--            rectY: parseInt(top, 10) + offsetY-->
<!--        };-->

<!--        this.handleMousemove(params);-->
<!--    }-->
<!--    handleMousemove({-->
<!--        e,-->
<!--        rectX,-->
<!--        rectY-->
<!--    }: {-->
<!--        e?: any;-->
<!--        rectX: number;-->
<!--        rectY: number;-->
<!--    }) {-->
<!--        if (this.isAnimate) {-->
<!--            return;-->
<!--        }-->
<!--        let [offsetX, offsetY] = [0, 0];-->

<!--        if (!e) {-->
<!--            offsetX = rectX;-->
<!--            offsetY = rectY;-->
<!--        } else {-->
<!--            offsetX = e.offsetX;-->
<!--            offsetY = e.offsetY;-->
<!--        }-->
<!--        const mainImg: any = this.$refs.mainImg; // 主展示图片，最大的那张-->

<!--        if (!mainImg) {-->
<!--            return;-->
<!--        }-->
<!--        const naturalPro = mainImg.naturalWidth / mainImg.naturalHeight; // 原始图片宽高比例-->
<!--        const currentPro = mainImg.width / mainImg.height; // 主展示图片比例-->

<!--        if (isNaN(naturalPro)) {-->
<!--            return;-->
<!--        }-->
<!--        this.mirrorWidth = 300; // 放大的区域宽度-->
<!--        this.mirrorHeight = 300 / naturalPro; // 放大的区域高度-->
<!--        const mirrorImg: any = this.$refs.mirrorImg; // 放大区域-->

<!--        if (currentPro > naturalPro) {-->
<!--            const overWidth = (mainImg.width - mainImg.height * naturalPro) / 2; // 图片居中导致两边留白，此处去除左右留白之影响。-->
<!--            const scaleX = mirrorImg.width / (mainImg.height * naturalPro); // 主展示图片与放大区域的宽度比例-->
<!--            const scaleY = mirrorImg.height / mainImg.height; // 主展示图片与放大区域的高度比例-->

<!--            this.mx = (offsetX - overWidth) * scaleX; // 放大之中心点X-->
<!--            this.my = offsetY * scaleY; // 放大之中心点Y-->
<!--        } else {-->
<!--            const overHeight =-->
<!--                (mainImg.height - mainImg.width / naturalPro) / 2; // 图片居中导致两边留白，此处去除上下留白之影响。-->
<!--            const scaleX = mirrorImg.width / mainImg.width; // 主展示图片与放大区域的宽度比例-->
<!--            const scaleY = mirrorImg.height / (mainImg.width / naturalPro); // 主展示图片与放大区域的高度比例-->

<!--            this.mx = offsetX * scaleX; // 放大之中心点X-->
<!--            this.my = (offsetY - overHeight) * scaleY; // 放大之中心点Y-->
<!--        }-->
<!--    }-->
<!--    handleMousewheel(e: any) {-->
<!--        if (this.isAnimate) {-->
<!--            return;-->
<!--        }-->
<!--        const { wheelDelta } = e;-->
<!--        // wheelDelta 正代表向上滚动，负代表向下滚动-->
<!--        // 放大倍数为1~35倍-->

<!--        if (wheelDelta > 0) {-->
<!--            this.zoomTimes *= 1.5;-->
<!--        } else if (wheelDelta < 0 && this.zoomTimes > 1) {-->
<!--            this.zoomTimes *= 0.5;-->
<!--        }-->
<!--        if (this.zoomTimes > 35) {-->
<!--            this.zoomTimes = 35;-->
<!--        } else if (this.zoomTimes < 1) {-->
<!--            this.zoomTimes = 1;-->
<!--        }-->
<!--    }-->
<!--    slideTo(type: string) {-->
<!--        const len = this.imgList.length - 1;-->

<!--        if (type === 'next' && this.imgActiveIndex < len) {-->
<!--            this.imgActiveIndex++;-->
<!--        } else if (type === 'prev' && this.imgActiveIndex > 0) {-->
<!--            this.imgActiveIndex&#45;&#45;;-->
<!--        } else if (this.imgActiveIndex === len && type === 'next') {-->
<!--            this.imgActiveIndex = 0;-->
<!--        } else if (this.imgActiveIndex === 0 && type === 'prev') {-->
<!--            this.imgActiveIndex = len;-->
<!--        }-->
<!--    }-->
<!--    animate(val: boolean, event: any, cut = false) {-->
<!--        const _this = this;-->
<!--        let params = {-->
<!--            type: 1,-->
<!--            id: this.currentId,-->
<!--            boxPoint: true-->
<!--        };-->

<!--        if (this.$route.path.includes('preTestCheck')) {-->
<!--            //预审测试里面的type为2-->
<!--            params.type = 2;-->
<!--        }-->
<!--        _this.loading = true;-->
<!--        _this.isAnimate = val;-->
<!--        if (!val) {-->
<!--            clearInterval(_this.timer);-->
<!--            if (cut === false) {-->
<!--                _this.getImgPoint(-->
<!--                    _this.imgList[_this.imgActiveIndex],-->
<!--                    'animate'-->
<!--                );-->
<!--            }-->
<!--            _this.loading = false;-->
<!--            return;-->
<!--        }-->
<!--        (configAjax as Function)('getDynamicImage', params).then((res: any) => {-->
<!--            if (parseInt(res.errorCode, 10) !== 0) {-->
<!--                message(res.message);-->
<!--                return;-->
<!--            }-->
<!--            if (res.data.length === 0) {-->
<!--                //无图取消播放-->
<!--                _this.loading = false;-->
<!--                _this.isAnimate = false;-->
<!--                message('无数据!', 'warning');-->
<!--                return;-->
<!--            }-->
<!--            _this.processPic = res.data;-->
<!--            let i = 0;-->

<!--            _this.timer = setInterval(() => {-->
<!--                _this.loading = false;-->
<!--                if (_this.processPic.length === 1) {-->
<!--                    //只有一张图取消动态播放-->
<!--                    _this.isAnimate = false;-->
<!--                    clearInterval(_this.timer);-->
<!--                    return;-->
<!--                }-->
<!--                const { imageData, boxPoint } = _this.processPic[i] as any;-->

<!--                if (imageData) {-->
<!--                    _this.activeImgSrc = 'data:image/jpeg;base64,' + imageData;-->
<!--                }-->
<!--                setTimeout(() => {-->
<!--                    //图片切换需要时间，否则若图片大小不一则导致画图的框线不准。-->
<!--                    if (boxPoint) {-->
<!--                        const copyBoxPoint = JSON.stringify(boxPoint);-->

<!--                        _this.drawLine(JSON.parse(copyBoxPoint));-->
<!--                    } else {-->
<!--                        _this.boxPointCalc = [];-->
<!--                    }-->
<!--                }, 50);-->
<!--                i++;-->
<!--                i = i >= _this.processPic.length ? 0 : i;-->
<!--            }, 1000);-->
<!--        });-->
<!--    }-->
<!--    showImgPlugin() {-->
<!--        if (!this.isAnimate) {-->
<!--            (this.$refs.magnifier as any).showImgPlugin(this.imgActiveIndex);-->
<!--        }-->
<!--    }-->
<!--    beforeDestroy() {-->
<!--        clearInterval(this.timer);-->
<!--    }-->
<!--}-->
<!--</script>-->

<style lang="less">
    .top-imgs {
        height: 100px;
        margin-bottom: 10px;

        .img-wrap {
            height: 100%;
            background-color: #000;

            .pic-title {
                position: absolute;
                top: -20px;
                color: #78859c;
                font-size: 14px;
            }

            > img {
                height: 100%;
                width: 100%;
                object-fit: contain;
                display: block;
                margin: auto;
            }
        }

        .img-actived {
            border: 2px solid #18a5d6;
        }
    }

    .main-img {
        background: #000;
        position: relative;

        &:hover {
            #mirror {
                opacity: 1;
                transform: translateX(100%);
                z-index: 10;
            }
        }

        .markRect {
            border: 2px solid red;
            /*position: absolute;*/
            position: absolute;
            transition: 0.3s ease-out;
        }

        #mirror {
            width: 200px;
            height: 123px;
            position: absolute;
            bottom: 0;
            right: 0;
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

        > img {
            width: 100%;
            height: 100%;
            object-fit: contain;
            user-select: none;
        }

        .prev-btn {
            background-color: #000;
            color: #fff;
            border-color: #000;
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            z-index: 5;
        }

        .next-btn {
            background-color: #000;
            color: #fff;
            border-color: #000;
            position: absolute;
            top: 50%;
            right: 0;
            transform: translateY(-50%);
            cursor: pointer;
            z-index: 5;
        }
    }

    .secondary-img {
        margin-left: 2%;
        width: 15%;

        .tab-tool {
            margin-top: 7px;
            /*height: 94%;*/
            height: 78%;
            list-style: none;

            li {
                width: 100%;
                /*height: 18%;*/
                height: 26%;
                border: 2px solid transparent;
                border-radius: 2px;
                box-sizing: border-box;
                margin-bottom: 10px;

                img {
                    object-fit: contain;
                    height: 100%;
                    width: 100%;
                    background-color: #000;
                    user-select: none;
                }
            }

            .img-actived {
                border: 2px solid #18a5d6;
            }
        }
    }

    .no-pic {
        object-fit: contain;
        width: 100%;
        height: 100%;
    }

    .dis {
        opacity: 0.35;
        cursor: auto;
        pointer-events: none;
    }
</style>
