#QueryServer2.0

###Dependency

+ [CM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/CM/T9/1.0) - Team 9
+ [FM2.4.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/FM/T2/1.0) - Team 2
+ [PM_v1.2.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/PM/T9/1.2) - Team 9
+ [License.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/License/T8/lastest_version) - Team 8
+ log4j-1.2.17.jar 

###jdk1.7

###配置

+ config.properties  
  1. data文件路径  
  2. PM路径  
  3. CM路径  
  4. LicenseLimit  
  5. PM输出时间间隔：单位 ms  
  6. 服务类型：“TEAM”或者“MEMBER”；TEAM表示通过队名查询成员，MEMBER表示通过成员名查询队名
+ data文件读入格式"name:TeamNumber\n"

###note
+ 支持动态加载配置
