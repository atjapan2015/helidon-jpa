使用DevCS构建功能(CI/CD)将数据引入ATP
=====
在这一步中，我们将使用DevCS的构建功能(CI/CD)创建一些表并使用数据填充ATP。

使用DevCS构建引擎在数据库中创建所需的对象，并设置流程以将数据引入表中如果存储库中这些元素发生更改，脚本将再次触发并重新创建数据库元素。

请按照以下步骤操作。

1. 创建并执行构建作业并创建数据库对象
2. 通过SQL Developer Web进行验证

### 1.创建并执行构建作业并创建数据库对象

在DevCS中，转到“构建”，然后单击“ +创建作业”按钮。

![](images/1300.jpg)

输入以下项目，然后单击“创建”按钮。

+名称：任意(例如`CreateDBObjects`)
+模板：`OKE`

![](images/1310.jpg)

添加一个git源存储库。从“添加Git”中选择“ git”。

![](images/1320.jpg)

输入以下项目。

+ 储存库：选择您的储存库

添加以下步骤：点击“步骤”。

![](images/1330.jpg)

从添加步骤中选择SQLcl。

![](images/1340.jpg)

输入以下项目，然后单击“保存”按钮。

+ 用户名：ATP数据库用户名(例如`admin`)
+ 密码：ATP数据库密码(例如，`WOrkshop__8080`)
+ 凭证文件：钱包文件路径(例如`./wallet.zip`)
+ 连接字符串：由数据库名称和`_high` /`_low`组成的连接字符串(例如`workshopatp_high`)
+ SQL文件路径：包含创建脚本的sql文件的路径(例如`sql/create_schema.sql`)

![](images/1350.jpg)

单击“立即构建”按钮。

如果这是您环境中的第一个构建作业，则构建引擎最多可能需要10分钟才能完成启动。

![](images/1360.jpg)

您可以在构建进行中或完成时单击“构建日志”图标来检查日志。

![](images/1370.jpg)

如果成功，日志末尾将显示“状态：完成结果：成功”。您还可以检查SQL执行的详细信息。

![](images/1380.jpg)

您还可以检查构建计算机的日志。

在DevCS中，选择“组织”⇒“构建虚拟机”⇒“构建VM”中使用的模板，然后从右侧的菜单图标中选择“查看日志”。

![](images/1390.jpg)

显示虚拟机的日志。

![](images/1400.jpg)

您已完成数据部署。

### 2.通过SQL Developer Web进行验证

您可以使用SQL Developer Web连接到数据库并验证是否正确创建了对象。

OCI ATP数据库实例的详细信息⇒单击“服务控制台”按钮。

![](images/1410.jpg)

点击“部署”。

![](images/1420.jpg)

单击“ SQL Developer Web”。

![](images/1430.jpg)

输入以下项目，然后单击“登录”。

+ 用户名：ATP数据库用户名，例如`admin`
+ 密码：ATP数据库密码，例如`WOrkshop__8080`

![](images/1440.jpg)

在SQL Developer Web工作表中，输入“ select * from ITEMS”，然后单击绿色箭头“ Execute Statement”图标。

显示ITEMS表的结果。

![](images/1450.jpg)

现在，您已经成功部署并验证了数据。

然后继续[使用DevCS构建功能(CI/CD)创建应用程序的Docker映像并将其注册到OCIR](WorkshopGuide800CreateImageToOCIR.md)。

[转到工作坊顶部](../ README.md)