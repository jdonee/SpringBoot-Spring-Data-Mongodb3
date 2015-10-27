本示例是Spring Boot增加对Mongodb3.x的全面支持，包括用户密码的验证。

准备工作：Windows安装Mongodb
1、下载[msi](https://www.mongodb.org/downloads)包直接安装，选择Windows 64-bit  2008 R2+。

2、选择安装目录C:\dev-tools\MongoDB3.0。

3、增加环境变量。
     3.1 MONGO_HOME=C:\dev-tools\MongoDB3.0
     3.2 Path变量的内容前面增加%MONGO_HOME%\bin;

4、检查mongodb是否已安装。
          命令行输入 mongo -version

5、启动mongodb。
     5.1、启动前先增加一个数据库存储的目录。默认在C盘data目录下。我们推荐使用存储空间大的磁盘，所以新建一个目录，如D:\mongodb\dev\data
     5.2、在命令行启动服务器  mongod --dbpath D:\mongodb\dev\data
     5.3、新开一个窗口，在命令行输入mongo 测试服务器连接是否成功 
         
6、安装mongodb服务。
     6.1、因为mongodb在使用中过程中会产生大量的日志，我们同样推荐使用存储空间大的磁盘，所以新创建日志目录 D:\mongodb\dev\log。
     6.2、在mongodb安装目录下增加一个配置文件 mongod.cfg来管理数据库存储目录、日志等等。
		systemLog:
		    destination: file
		    path: d:\mongodb\dev\log\mongod.log
		storage:
		    dbPath: d:\mongodb\dev\data
     6.3、关闭启动的mongodb服务器，准备安装服务。
           6.3.1.一定要使用管理员模式打开cmd窗口。
           6.3.2.执行 mongod --config "C:\dev-tools\MongoDB3.0\mongod.cfg" --install  
        
    6.4、用户登录认证服务
          6.4.1.执行 mongod --config "C:\dev-tools\MongoDB3.0\mongod.cfg" --nohttpinterface --auth --install  
          6.4.2.新建用户命令行输入
                6.4.2.1 mongo
                6.4.2.2 show admin;
                6.4.2.3 db.createUser(
					  {
					    user: "root",
					    pwd: "root",
					    roles: [
					          { role: "root", db: "admin" }
					          ]
					  });
       			 6.4.2.4 exit;
          
 7、启动、关闭和移除服务
      在命令行执行如下操作即可 
      7.1、net start MongoDB
      7.2、net stop MongoDB
      7.3、mongod --remove
