#Document
---
## 系统功能说明

+ 提供查询服务，用户输入组号，系统查询并返回该组成员名单
+ 系统记录如下数据
	+ 用户请求次数
	+ 系统提供服务次数
	+ 系统拒绝服务次数
+ 生成系统日志

##开发环境依赖

+ JavaSE-1.7
+ [QueryServer.jar](https://github.com/TJSoftwareReuse/2012T09/blob/master/TeamQueryServer/lib/QueryServer.jar) - Team 9
+ [CM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/CM/T9/1.0) - Team 9
+ [FM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/FM/T2/1.0) - Team 2
+ [FM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/PM/T9/1.0) - Team 9
+ [License.jar](https://github.com/TJSoftwareReuse/DeliverComponents/tree/master/License/T8/lastest_version) - Team 8

##系统配置

####配置文件路径

+ 配置文件路径为``config.properties``

####配置文件信息
	
	DATA_PATH=data.txt #数据文件路径
	FM_PATH=fm.log #fm构件输出文件路径
	PM_PATH=pm #pm构件输出文件路径
	LICENSE_NUM=5 # License构件容量
	
####修改配置文件

+ 直接修改配置信息
+ 修改后需重启系统

##数据导入

####数据文件路径

+ 在配置文件中 ``DATA_PATH`` 更改数据文件路径

####数据文件信息

+ 数据文件格式 ``Name:TeamMember\n``
+ 修改数据文件后需重启系统

##系统流程

1. 设置配置文件 (如果需要)
2. 启动系统
3. 用户输入
	+ 查询： 组号
	+ 退出： “quit” -> `6`
4. 系统返回结果
	+ 查询成功： 组员名单
	+ 查询失败：	无法找到该组
	+ 拒绝服务： 超出`License`限制
5. 返回 `3`
6. 系统结束
