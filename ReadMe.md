#启动
    #检查端口
        lsof -i:26275
    #杀进程
        kill -9 id
    #启动服务
        sh /Program/app/start_acct-fund.sh
        #SIT和UAT打开时间调试参数
        java -jar -Xmx1024m -Xmn256m -DSYSTEM_DEBUG_DATE /Program/app/gd-acct-fund.jar >/dev/null 2>&1 &
        #生产环境不能打开时间调试参数
        #java -jar -Xms2048m -Xmx2048m -Xmn512m /Program/app/gd-acct-fund.jar >/dev/null 2>&1 &
        

    # 日志记录
        tail -f /ccicall/Applog/gd-acct-fund/{主机名}/gd-zcct-fund.log

#打包成环境
    #gd-acct
    mvn clean install  -am -Dmaven.test.skip=true -Psit
    #打包测试环境
    mvn clean install  -am -Dmaven.test.skip=true -Pdev
#安装jar包
#影像平台client包
mvn install:install-file -DgroupId=com.sinosoft.image -DartifactId=h5img-wsc-client -Dversion=1.1.0 -Dpackaging=jar -Dfile=h5img-wsc-client-1.1.0.jar
#Oracle驱动
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1.0 -Dpackaging=jar -Dfile=ojdbc8.jar
#bcprov包
mvn install:install-file -DgroupId=com.ccic.bcprov -DartifactId=bcprov -Dversion=1.0.0 -Dpackaging=jar -Dfile=bcprov-jdk14-150.jar  
#cebenc包
mvn install:install-file -DgroupId=com.ccic.cebenc -DartifactId=cebenc -Dversion=1.0.0 -Dpackaging=jar -Dfile=cebenc.jar#maven仓库地址

maven的settings.xml中修改mirror,配置如下
<mirror>
    <id>ccicmaven</id>
    <name>ccic maven</name>
    <url>http://10.1.13.7:27897/nexus/repository/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror> 