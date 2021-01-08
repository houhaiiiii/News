<template>
    <div class="body" @click="onClick" :style="getBodyStyle">
        <div class="bg-ops" :style="getBodyStyle"></div>
        <image v-if="img!=''" class="img" :src="img"></image>
        <text v-else class="icon" :style="getIconStyle">{{icon}}</text>
        <text class="text">搜 索</text>
    </div>
</template>

<script>
    export default {
        props:{
            flex:{
                type:Number,
                default:1
            },
            height:{
                type:Number,
                default:70
            },
            icon:{
              type:String,
              default:'\uf044'
            },
            img:{
                type:String,
                default:''
            }
        },
        computed:{
            getBodyStyle:function(){
                return {
                    marginLeft:this.leftWidth+'px',
                    marginRight:this.rightWidth+'px',
                    borderColor:this.color,
                    'flex':this.flex,
                    'height':this.height+'px'
                }
            },
            getIconStyle:function(){
                return {
                    width:(this.height-30)+"px",
                    height:(this.height-10)+"px",
                    lineHeight:(this.height-10)+"px",
                    fontSize:(this.height/70)*32+"px"
                }
            }
        },
        methods:{
            onClick : function(){
                if(this.inpValue!=''){
                    this.$emit('onClick', {});
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    @import '../../styles/common';
    .body{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .img{
        width: 32px;
        height: 32px;
        margin-right: 10px;
    }
    .bg-ops{
        position: absolute;
        top: 0px;
        left: 0px;
        background-color: #ffffff;
        opacity: 0.2;
        width: 100%;
        height: 30px;
        border-radius: @top-height - 30;
    }
    .icon{
        color: #ffffff;
        font-size: 28px;
    }
    .text{
        font-family: MicrosoftYaHei;
        color: #eeeeee;
        font-size: 28px;
    }
</style>
