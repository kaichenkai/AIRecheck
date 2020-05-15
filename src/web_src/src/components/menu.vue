<template>
    <!--            :default-active="$route.path.split('/')[2]"-->
<!--                router-->
    <el-menu
            :default-active="defaultIndex"
            mode="horizontal"
            background-color="#0F2744"
            text-color="#7f8fa4"
            active-text-color="#fff"
    >
        <el-menu-item index="1">复核查询</el-menu-item>
        <el-menu-item index="2">通报统计</el-menu-item>
        <!--        <el-submenu-->
        <!--            v-for="item2 in mainMenuDataSub"-->
        <!--            :index="item2.menuName"-->
        <!--            :key="item2.menuId"-->
        <!--            :id="item2.menuPath"-->
        <!--        >-->
        <!--            <template slot="title">{{item2.menuName}}</template>-->
        <!--            <el-menu-item-->
        <!--                v-for="i in item2 .children"-->
        <!--                :index="i.menuPath"-->
        <!--                :key="i.menuId"-->
        <!--                :id="i.menuPath"-->
        <!--            >{{i.menuName}}</el-menu-item>-->
        <!--        </el-submenu>-->
    </el-menu>
</template>

<script lang="ts">
    export default {
        name: "Menu",
        data() {
            return {
                defaultIndex: '1'
            }
        }
    }
</script>

<style lang="less">
    .el-menu-item {
        font-size: 16px !important;
    }

    .el-menu-item.is-active {
        /*border: none 0 !important;*/
    }

    .el-submenu__title {
        font-size: 16px !important;
    }

    .el-menu--horizontal {
        border: none 0 !important;
        // 子选项样式
        /*.el-menu {*/
        /*    background-color: #fff !important;*/
        /*    border-radius: 3px;*/

        /*    .el-menu-item {*/
        /*        background-color: #fff !important;*/
        /*        font-size: 14px !important;*/

        /*        &:hover {*/
        /*            background-color: #f2f4f7 !important;*/
        /*        }*/
        /*    }*/

        /*    .el-menu-item.is-active {*/
        /*        color: #000 !important;*/
        /*    }*/
        /*}*/
    }
</style>


