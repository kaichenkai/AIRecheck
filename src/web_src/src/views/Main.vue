<template>
    <div class="main">
        <el-container>
            <el-header>
                <div :style="{height:'100%', width:'100%', margin:'auto', transition:'500ms linear'}">
                    <el-col :span="1" class="logo-wrap">
                        <img src="../assets/img/lg.png"/>
                    </el-col>
                    <el-col :span="4" class="title-wrap">
                        AI车牌复核系统
                    </el-col>
                    <el-col :span="15" style="text-align:right;transition: 500ms;">
                        <Menu></Menu>
                    </el-col>
                    <el-col :span="4" class="head-control">
                        <el-col :span="3">
                            <el-tooltip effect="light" content="识别服务异常" placement="left">
                                <i class="el-icon-s-opportunity light"></i>
                            </el-tooltip>
                        </el-col>
                        <el-col :span="3" class="setting">
                            <router-link to="#" target="_blank">
                                <el-tooltip
                                        class="item"
                                        effect="light"
                                        content="设置"
                                        placement="left"
                                >
                                    <img src="../assets/img/icon/manage.png" class="empty-img1"/>
                                </el-tooltip>
                            </router-link>
                        </el-col>
                        <el-col :span="14" class="hc-one">
                            <el-col :span="6">
                                <img src="../assets/img/userhead.png"/>
                            </el-col>
                            <el-col :span="16">seemmo</el-col>
                        </el-col>
                        <el-col :span="4" class="hc-three">
                            <div></div>
                        </el-col>
                    </el-col>
                </div>
            </el-header>
            <el-main>
                <transition name="slide-fade" mode="out-in">
                    <router-view/>
                </transition>
            </el-main>
        </el-container>
    </div>
</template>

<script lang="ts">
    import Menu from "../components/menu.vue"
    export default {
        name: "Main",
        components: {
            Menu
        },
        mounted() {
        }
    }
</script>

<style lang="less">
    .main {
        height: 100%;

        .el-container {
            height: 100%;

            .el-header {
                /*position: fixed;*/
                /*top: 0;*/
                /*left: 0;*/

                width: 100%;
                line-height: 60px;
                overflow: hidden;
                background-color: #0f2744;
                color: #fff;
                padding: 0;
                font-family: '黑体';

                .setting {
                    a {
                        cursor: pointer;
                        // font-size: 25px;
                        // line-height: 60px;
                        display: inline-block;
                        width: 18px;
                        height: 18px;

                        .empty-img1 {
                            width: 100%;
                            height: 100%;
                            vertical-align: middle;

                            &:hover {
                                transform: rotateZ(60deg);
                                transition: 1s;
                                //   transform-origin: center center;
                            }
                        }
                    }
                }
            }

            .el-main {
                /*display: block;*/
                height: 100%;
                padding: 0;
                background-color: #fff;
            }

            .logo-wrap {
                text-align: center;

                img {
                    vertical-align: middle;
                    height: 35px;
                    width: 35px;
                    object-fit: contain;
                }
            }

            .title-wrap {
                color: #fff;
                font-size: 22px;
            }

            .head-control {
                background-image: url('../assets/img/topmenu-texture.png');
                background-size: 100% 100%;
                transition: 500ms ease-in-out;
                text-align: left;

                .light {
                    animation: redlight 6500ms infinite;
                    vertical-align: middle;
                    font-size: 20px;
                    color: red;
                }

                .hc-one {
                    img {
                        vertical-align: middle;
                    }
                }

                .hc-three {
                    text-align: center;
                    height: 100%;

                    div {
                        width: 20px;
                        height: 20px;
                        vertical-align: middle;
                        margin-top: 20px;
                        background-image: url('../assets/img/logout.png');
                        background-size: 100% 100%;
                        cursor: pointer;

                        &:hover {
                            background-image: url('../assets/img/logoutred.png');
                        }
                    }
                }
            }
        }
    }


    .slide-fade-enter-active {
        transition: all 300ms ease;
    }

    .slide-fade-leave-active {
        transition: all 300ms ease;
    }

    .slide-fade-enter {
        transform: translateX(-10px);
        opacity: 0;
    }

    .slide-fade-leave-to {
        transform: translateX(10px);
        opacity: 0;
    }

    @keyframes redlight {
        0% {
            transform: scale(1);
        }
        28% {
            transform: scale(1.28);
        }
        35% {
            transform: scale(1.33);
        }
        60% {
            transform: scale(1.2);
        }
        80% {
            transform: scale(1.05);
        }
        100% {
            transform: scale(1);
        }
    }
</style>
