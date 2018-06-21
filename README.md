Android下的公众号按键精灵,自动点击公众号历史消息


## 说明
1. 与Python爬取微信公众号文章(中间人代理法)结合使用，详细介绍请看https://www.jianshu.com/p/667f3668cd70
2. Python爬取微信公众号文章(中间人代理法)的github地址：https://github.com/zjhpure/crawler_public_number


## 启动前准备
1. 设置wifi的代理，ip设置为anyproxy服务器的ip，端口设置为anyproxy服务器的端口（一般为8001）
2. 微信版本要求：6.66
3. 使用前先杀掉微信的进程，避免此软件使用时自动启动微信而打开两个微信启动类


## 运行过程
1. 设置请求服务器的地址
2. 定时进行网络请求获取要点击的公众号，点击获取到的公众号的历史消息
3. 由于设置了wifi的代理，在点击公众号的历史消息时，anyproxy服务器会拦截到对应的请求信息，请求信息被发送到redis队列上

## 注意事项
1. 若用了非6.66版本的微信可以使用Android Device Monitor查看微信按钮、节点等的信息，修改对应的源码
2. 若要查看微信某个页面的activity类名，可以执行命令adb shell dumpsys activity | grep mFocusedActivity获取手机屏幕当前的activity






