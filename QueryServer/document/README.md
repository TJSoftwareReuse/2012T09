#v1.1更新
增加QueryServer类各私有变量的get方法



#文档
---
1. 功能：
用户输入学生姓名，接收到学生后，如果能提供服务，则查询学生所在Team并输出，如果该学生不存在，则输出提示改学生未能找到；如果不能提供服务，则输出提示“Query Reject : Out of license limi


1. 服务端配置文件为~/config.properties，初始配置信息如下：

		DATA_PATH=data.txt  
		FM_PATH=fm.log  
		PM_PATH=pm  
		LICENSE_NUM=5  
	分别表示：
	+ 学生姓名对应的Team编号的资源文件路径，格式为“姓名：Team”，每行代表一个学生；
	![Aaron Swartz](https://github.com/TJSoftwareReuse/2012T09/raw/master/QueryServer/document/1.png)
	+ 服务端日志文件路径；
	+ PM路径
	+ license数量  
	<br>
2.	更改配置文件需重新启动服务才能生效；
