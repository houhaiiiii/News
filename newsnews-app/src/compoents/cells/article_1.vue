<template>
    <div class="list-item">
        <div class="list-lr">
            <div class="item-l">
                <text class="title" :style="flexStyle(data.title)">{{formatTitle(data.title)}}</text>
                <div class="tags">
                    <text class="tags-text tags-icon">置顶</text>
                    <text class="tags-text">{{data.source}}</text>
                    <text class="tags-text">评论 {{data.commit}}</text>
                </div>
            </div>
            <div class="item-r">
                <image class="image" :src="data.image[0]"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "article_1",
        props:{
            data:{
                type:Object
            }
        },
        methods : {
            formatDate:function(time){
                return this.$date.format13(time);
            },
            formatTitle:function(title){
                if(title.length>32){
                    return title.substring(0,31);
                }
                return title;
            },
            flexStyle : function(title){
                if(title.length>12){
                    return {flex:1}
                }
                return {flex:'none',marginBottom:'-5px'};
            }
        }
    }
</script>

<style lang="less" scoped>
    @import '../../styles/article';
    .list-lr{
        flex-direction: row;
        justify-content: space-around;
    }
    .item-l{
        flex: 1;
    }
    .item-r{
        width: 180px;
        margin: @list-tb-margin @list-lr-margin;
    }
</style>
