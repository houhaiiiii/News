<template>
    <div class="art-page" @touchmove.stop>
        <div class="art-top"><TopBar :text="title"/></div>
        <scroller @touchmove.self class="scroller" ref="scroller" @scroll="scroller" :style="{height:scrollerHeight+'px'}" >
            <text class="title">{{title}}</text>
            <div class="info">
                <image src="https://p3.pstatp.com/thumb/1480/7186611868" class="head"></image>
                <div class="more">
                    <text class="author">{{source}}</text>
                    <text class="time">{{formatDate(date)}}</text>
                </div>
                <div class="empty"></div>
                <wxc-button class="button" v-if="relation.isfollow" @wxcButtonClicked="follow" text="取消关注" size="small"></wxc-button>
                <wxc-button class="button" v-if="!relation.isfollow" @wxcButtonClicked="follow" text="+ 关注" size="small" :btnStyle="buttonStyle"></wxc-button>
            </div>
            <div class="content">
                <template v-for="item in content">
                    <text class="text" :style="getStyle(item.style)" v-if="item.type=='text'">{{item.value}}</text>
                    <image @load="imageLoad(item,$event)" class="image" :style="{width:'710px',height:imageHeight[item.value]}" v-if="item.type=='image'" :src="item.value"></image>
                </template>
            </div>
            <div class="tools">
                <Button text="点赞" @onClick="like" img='like' :active="relation.islike" active-text="取消赞"/>
                <Button text="不喜欢" @onClick="unlike" img='unlike' :active="relation.isunlike" />
<!--                <Button text="微信" :icon='icon.wechat' @onClick="share(0)"/>-->
<!--                <Button text="朋友圈" :icon='icon.friend' @onClick="share(1)"/>-->
            </div>
            <div class="comment">
                <Comment author="晚风吻尽南瓜叶" comment="感谢分享  围观大牛！" like="23" replay="342" image="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571214312&di=245cc8e17914ba75aec488365109705a&imgtype=jpg&er=1&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170507%2F0339342T9-4.jpg"/>
                <Comment author="古墓小杨过" comment="文章超赞，解决我了问题" like="18" replay="348" image="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2912336383,677544998&fm=15&gp=0.jpg"/>
                <Comment author="飞翔的雾" comment="说的太详细了，学习了，感谢" like="17" replay="12" image="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570619607318&di=513c5ddd390fbe8e384f6a7469be7d82&imgtype=0&src=http%3A%2F%2Fimg1.2345.com%2Fduoteimg%2FqqTxImg%2F2013%2F04%2F22%2F13667761941.jpg"/>
                <Comment author="少女在怒力" comment="好文，作者加个好友呗" like="15" replay="3743" image="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570619616857&di=ad0811951239a8970f8107114d755040&imgtype=0&src=http%3A%2F%2Fwww.qqkubao.com%2Fuploadfile%2F2014%2F06%2F1%2F20140612080155182.jpg"/>
            </div>
        </scroller>
        <div class="art-bottom"><BottomBar @onSubmit="onCommint" :forward="test.isforward" @clickForward="forward"
                                           :collection="relation.iscollection" @clickCollection="collection" /></div>
    </div>
</template>

<script>
    import TopBar from '@/compoents/bars/article_top_bar'
    import BottomBar from '@/compoents/bars/article_bottom_bar'
    import Button from '@/compoents/buttons/button'
    import Comment from '@/compoents/cells/comment'
    import { WxcButton ,Utils } from 'weex-ui'
    import Api from '@/apis/article/api'

    const modal = weex.requireModule("modal")

    export default {
        name: "index",
        components:{TopBar,BottomBar,WxcButton,Button,Comment},
        props:['id','title','date','comment','type','source','authorId'],
        data(){
            return {
                scrollerHeight:'500px',
                icon : {
                    like : '\uf164',
                    unlike : '\uf1f6',
                    wechat : '\uf086',
                    friend : '\uf268'
                },
                imageHeight : {},
                config:{},//文章配置
                content:{},//文章内容
                relation:{
                    islike: false,
                    isunlike: false,
                    iscollection: false,
                    isfollow: false,
                    isforward:false
                },//关系
                time : {
                    timer:null,//定时器
                    timerStep:100,//定时器步长
                    readDuration:0,//阅读时长
                    percentage:0,//阅读比例
                    loadDuration:0,//加载时长
                    loadOff:true//加载完成控制
                },//时间相关属性
                test : {
                    isforward : false
                },
                buttonStyle :{
                    backgroundColor: '#3296FA'
                }
            }
        },
        created(){
            Api.setVue(this);
            this.loadInfo();
            this.loadBehavior();
            let _this = this;
            this.time.timer = setInterval(function(){
                _this.time.readDuration+=_this.time.timerStep
                if(_this.time.loadOff){
                    _this.time.loadDuration+=_this.time.timerStep
                }
            },this.time.timerStep)
        },
        destroyed(){
            this.read();
        },
        mounted(){
            this.scrollerHeight=(Utils.env.getPageHeight()-177)+'px';
        },
        methods : {
            imageLoad : function(item,e){
                console.log(item)
                console.log(e)
                if(e.success){
                    if(e.size.naturalWidth>150){
                        let height = parseInt(e.size.naturalHeight*(750/e.size.naturalWidth))+'px'
                        this.$set(this.imageHeight,item.value,height)
                    }else{
                        this.$set(e.target,'resize','contain')
                    }
                }
            },
            loadInfo : function(){
                Api.loadinfo(this.id).then((d)=>{
                    if(d.code==0){
                        this.config = d.data['config']
                        let temp = d.data['content']
                        if(temp){
                            temp = temp.content
                            this.content = eval("("+temp+")")
                            this.time.loadOff=false;//关闭加载时间的记录
                        }else{
                            modal.toast({message:'文章已被删除',duration:3})
                        }
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            loadBehavior : function(){
                Api.loadbehavior(this.id,this.authorId).then((d)=>{
                    if(d.code==0){
                        this.relation = d.data
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            // 点赞
            like : function(){
                Api.like({articleId:this.id,operation:this.relation.islike?1:0}).then(d=>{
                    if(d.code==0){
                        this.relation.islike = !this.relation.islike
                        let message = (this.relation.islike?"点赞":"取消点赞")+"操作成功！";
                        modal.toast({ message:message,duration:3})
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            // 不喜欢
            unlike : function(){
                Api.unlike({articleId:this.id,type:this.relation.isunlike?1:0}).then(d=>{
                    if(d.code==0){
                        this.relation.isunlike = !this.relation.isunlike
                        let message = (this.relation.isunlike?"不喜欢":"取消不喜欢")+"操作成功！";
                        modal.toast({ message:message,duration:3})
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            // 分享
            share : function(type){
                Api.share({articleId:this.id,type:type}).then(d=>{
                    if(d.code==0){
                        modal.toast({message: '分享成功',duration: 3})
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            // 收藏
            collection : function(){
                Api.collection({articleId:this.id,publishedTime:this.date,operation:this.relation.iscollection?1:0}).then(d=>{
                    if(d.code==0){
                        this.relation.iscollection = !this.relation.iscollection
                        let message = (this.relation.iscollection?"收藏":"取消收藏")+"操作成功！";
                        modal.toast({ message:message,duration:3})
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            // 转发
            forward : function(){
                this.tip()
                // Api.forward({articleId:this.id}).then(d=>{
                //     this.test.isforward = !this.test.isforward
                // }).catch((e)=>{
                //     console.log(e)
                // })
            },
            // 关注
            follow : function(){
                Api.follow({articleId:this.id,authorId:this.authorId,operation:this.relation.isfollow?1:0}).then(d=>{
                    if(d.code==0){
                        this.relation.isfollow = !this.relation.isfollow
                        modal.toast({message:this.relation.isfollow?'成功关注':'成功取消关注',duration: 3})
                    }else{
                        modal.toast({message: d.error_message,duration: 3})
                    }
                }).catch((e)=>{
                    console.log(e)
                })
            },
            // 阅读行为
            read : function(){
                clearInterval( this.time.timer)
                Api.read({articleId:this.id,readDuration:this.time.readDuration,percentage:this.time.percentage,loadDuration:this.time.loadDuration});
            },
            formatDate:function(time){
                return this.$date.format13(time);
            },
            getStyle:function(item){
                if(item){
                    if( typeof(item) == 'string'){
                        try{
                            item = item.replace(/(-.)/g,function($1){return $1.replace('-','').toLocaleUpperCase()})
                            item=eval('({'+item.replace(/-/ig,'')+'})');
                        }catch (e) {
                            console.log(e)
                        }
                    }
                    return item;
                }else{
                    return {}
                }
            },
            getImgStyle:function(item){
                item = this.getStyle();
                item['width']='750px'
                item['height']='1px'
                // 处理动态图片高度
                let temp = this.imageHeight[item.value]
                if(temp){
                    item['height']=temp
                }
                return item;
            },
            scroller : function(e){
                let y = Math.abs(e.contentOffset.y)+(Utils.env.getPageHeight()-180)
                let height = e.contentSize.height
                this.time.percentage = Math.max(parseInt((y*100)/height),this.time.percentage)
            },
            onCommint:function(){
                this.tip();
            },
            tip : function(){
                modal.toast({ message:'该功能暂未实现！',duration:3})
            }
        }
    }
</script>

<style scoped>
    .art-page{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        width: 750px;
        flex-direction: column;
        font-family: "微软雅黑";
    }
    .art-top{}
    .art-bottom{
        bottom: 0;
        position: fixed;
        width: 750px;
    }
    .scroller{
        flex: 1;
        flex-direction: column;
        width: 750px;
        padding: 0px 29px;
        margin: 0px 0px 87px;
    }
    .title{
        color: #3A3A3A;
        font-size: 40px;
        line-height: 65px;
        font-weight: bold;
        font-family: "微软雅黑-高亮";
        margin: 48px 0px 29px;
    }
    .info{
        margin-top: 15px;
        margin-bottom: 15px;
        line-height: 48px;
        align-items: center;
        flex-direction: row;
    }
    .head{
        width: 48px;
        height: 48px;
        border-radius: 48px;
    }
    .more{
        flex-direction: column;
    }
    .author{
        font-size: 25px;
        color: #383839;
        margin-left: 15px;
    }
    .time{
        font-size: 21px;
        color: #b5b5b5;
        margin-left: 15px;
    }
    .empty{
        flex: 1;
    }
    .content{
        flex-direction: column;
        font-size: 30px;
        justify-content:flex-start;
        margin-top: 20px;
        word-wrap: break-word;
        text-align: justify;
        font-family: "微软雅黑";
    }
    .text {
        margin: 15px 0px;
        color: #3A3A3A;
    }
    .image{
        display:inline-block;
        margin: 15px 0px;
        border-radius: 5px;
        height: 300px;
    }
    .tools{
        margin: 20px 0px 30px;
        flex-direction: row;
        height: 60px;
        justify-content: center;
    }
</style>
