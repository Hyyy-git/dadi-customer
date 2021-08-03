###客户管理系统
 ##master 
    用于生产环境发布
 ##uat
    用于uat测试，并用于master的合并
 ##sit
    用于sit测试，不能用于master的合并，主要是测试maven、linux下的代码
 ##dev
    用于针对需求任务，建立个人的开发分支
        分支命名规则是：分类类型-厂商名称-功能名称-作者
        例 dev-ws-trans-chenjl 微商的授信功能陈加林开发
        生产环境发版后，该分支建议删除
