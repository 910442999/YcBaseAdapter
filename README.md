# YcBaseAdapter
BaseAdapter

recyclerview的baseAdapter的封装 最新版为 : 1.0.1


使用方法:

gradle 引入: 最新版即可

implementation 'com.yc:YcBaseAdapter:1.0.0'

如果有下载不了的情况 在根目录下的 gradle中添加 如下即可

allprojects {
   
   repositories {
       
       maven {url "https://dl.bintray.com/910442999/YcBaseAdapter"}
   
   }
}

版本介绍 :

v1.0.6 :

    1.折叠梯形布局1

v1.0.3 :

    1.增加树形列表

v1.0.2 :

    1.增加条目长按事件

v1.0.1 :

1.对基础baseAdapter进行优化

2.增加多条目的适配器 , 本节相关代码在分支4中


v1.0.0 :

1.baseAdapter的基本功能

2.增加条目的ziview的点击事件的处理 , 本节相关代码在分支3中
