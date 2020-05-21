<template>
    <viewer :images="imgList" class="img-plugin" @inited="inited">
        <img
            v-for="(src,index) in imgList"
            :src="src|imageUrl"
            :key="index"
            :ref="'childMainImg'"
            :class="{'actived':imgActiveIndex===index}"
        />
    </viewer>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'vue-property-decorator';
import Viewer from 'v-viewer';
import 'viewerjs/dist/viewer.css';
Vue.use(Viewer, {
    defaultOptions: {
        zIndex: 9999,
        navbar: true, //显示缩略图导航
        toolbar: true, //显示工具栏
        zoomRatio: 0.5, //缩放速率
        inline: false, //启用 inline 模式
        button: true, //显示右上角关闭按钮
        title: true, //显示当前图片的标题
        tooltip: true, //显示缩放百分比
        movable: true, //图片是否可移动
        zoomable: true, //图片是否可缩放
        rotatable: false, //图片是否可旋转
        scalable: false, //图片是否可翻转
        transition: true, //使用 CSS3 过度
        fullscreen: false, //播放时是否全屏
        keyboard: true //是否支持键盘
    }
});
// https://github.com/fengyuanchen/viewerjs

@Component({
    filters: {
        imageUrl(val: string) {
            if (!val) {
                return;
            }
            if (val.length > 50) {
                return val;
            } else {
                return `${window.location.origin}/business/image/get?imageId=${val}&compressRate=1`;
            }
        }
    }
})
export default class Magnifier extends Vue {
    @Prop() imgList: String[];
    @Prop() imgActiveIndex: number = 0;
    private $viewer: any;
    inited(viewer: any) {
        this.$viewer = viewer;
    }
    showImgPlugin(idx: number = 0) {
        this.$viewer.view(idx);
    }
}
</script>

<style lang="less">
.img-plugin {
    // width: 100%;
    // height: 100%;
    > img {
        width: 100%;
        height: 100%;
        object-fit: contain;
        user-select: none;
        position: absolute;
        display: none;
    }
}
</style>
