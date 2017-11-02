# LegouMall
  整个项目采用MVC框架，实现了用户登陆、注册、购物车订单的增添、删除、商品查询、二维码扫描与生成、商品分享等功能。实现了对6.0以上的设备进行适配

## 实现功能 :

1.	登陆注册&网络框架搭建

2.	主页框架搭建

3.	数据库搭建&用户加密保存

4.	首页（横向列表&广告栏&嵌套的滑动界面）

5.	分类（代码动态创建 2、3 级分类布局）

6.	商品列表（侧滑列表）&商品详情

7.	购物车列表&清单确认

8.	订单列表对应的各种订单状态查询

### 启动页面
![image](http://ww3.sinaimg.cn/large/0060lm7Tly1fl421sixjgg30zk0hshdu.gif)

1. 欢迎界面
> 欢迎页面使用渐变动画AlphaAnimation设置为三秒，并对动画设置监听setAnimationListener实现动画完成自动转跳至登陆界面

2. 登陆及注册界面
> 登陆及注册页面使用TextInputLayout实现动态输入动画


### 整体框架
![image](http://ww1.sinaimg.cn/large/0060lm7Tly1fl4296b3fcg30zk0hs4mm.gif)

> 整体框架分为 首页、分类、购物车、我的 四大板块，通过一个HomeActivity+四个Fragment实现

### 首页
![image](http://ww4.sinaimg.cn/large/0060lm7Tly1fl42cz5mxkg30zk0hskjp.gif)

> 首页使用自定义控件ScrollView通过重写overScrollBy方法修改Y轴方向可拉伸距离,首页实现了 1.二维码扫描其他用户分享的商品链接转跳至商品详情（长按选择本地图片扫描，短按相机扫描；2.使用科大讯飞提供的API进行语音识别全局搜索；3.横向滑动的商品秒杀；4.定时更新商品列表的猜你喜欢）

### 分类
![image](http://ww3.sinaimg.cn/large/0060lm7Tly1fl42j1jbx4g30zk0hsnpe.gif)

> 分类页面通过左边RecyclerView + 右边自定义二级分类控件组合而成。具体的二级分类请看ui下的SubCategoryView，有详细的注释

### 购物车
![image](http://ww3.sinaimg.cn/large/0060lm7Tly1fl4370u7gdg30zk0hsu0y.gif)

> 购物车实现了多选结算总结，点击结算按钮会转跳至下单页面，进行地址更改以及添加地址、在线付款（因为支付宝接口升级，未实现）、货到付款


### 我的
![image](http://ww4.sinaimg.cn/large/0060lm7Tly1fl439um19og30zk0hsnpd.gif)

> 实现了展示用户信息，退出登陆以及订单列表功能。订单列表内实现了全部订单、待支付订单、待收货订单、已完成的订单四大板块，每一种状态对应一种功能。待发货订单通过“提醒买家发货”实现发货，待收货订单可以“确认收货”实现收货。

###　商品列表
![image](http://ww1.sinaimg.cn/large/0060lm7Tly1fl43duhvppg30zk0hse82.gif)

> 商品列表可以通过，首页的全局搜索、秒杀、分类页面转跳。列表可以通过顶部的四种排序进行重排序，以及DrawLayout实现品牌、价格、配送方式等进行筛选

### 商品详情页面
![image](http://ww2.sinaimg.cn/large/0060lm7Tly1fl43i8yd2ag30zk0hsu0z.gif)

> 商品详情界面：通过Viewpager实现：商品主页、详情、评论三个页面
以及分享按钮实现生成商品二维码，长按二维码进行保存，分享给朋友，朋友扫描转跳至商品详情页面

### 二维码
![image](http://ww1.sinaimg.cn/large/0060lm7Tly1fl43littcgg30zk0hse82.gif)

> 扫描二维码：在首页长按二维码实现扫描本地二维码进行转跳，短按扫描按钮实现相机扫描二维码转跳至对应的商品详情页面

## 使用的开源框架 :

1.	ButterKnife

2.	Glide

3.  Fastjson	

4.	Rvadapter

5.	Zxing


## 使用的设计模式:

1.	模版模式

2.	适配器模式

3.	构建着模式
