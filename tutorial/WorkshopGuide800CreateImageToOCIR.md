使用DevCS构建功能(CI / CD)创建应用程序的Docker映像并注册到OCIR
=======
此步骤描述如何在DevCS上创建Java Web应用程序的Docker映像(使用ATP数据库作为数据源)。

在这里，使用Helidon＆JET开发的应用程序创建了容器映像。

请按照以下步骤操作。

1. 配置与OCIR信息库的连接
2. 配置Docker构建作业以创建和推送映像
3. 执行构建作业

### 1.配置与OCIR信息库的连接

在DevCS中转到“Docker”，然后单击“外部注册表链接”按钮。

![](images/1500.jpg)

输入以下项目，然后单击“创建”按钮。

+ 注册表名称：任意(例如`WorkshopOCIR`)
+ 注册表URL：OCIR注册表URL。 https：// <区域代码> .ocir.io(例如，东京数据中心为`https://nrt.ocir.io`)
+ 简短说明：任意(例如`Workshop OCIR`)
+ 用户名：`用于登录对象存储命名空间/ OCI的用户名`(例如，`对象存储命名空间/oracleidentitycloudservice/aaaa.bbbb@oracle.com`或`对象存储命名空间/oracleidentitycloudservice/aaaa.bbbb@oracle.com`)
+ 密码：您写下的身份验证令牌

![](images/1510.jpg)

如果成功，将显示来自外部注册表的信息。

![](images/1520.jpg)

### 2.配置Docker构建作业以创建和推送映像

转到“构建”，然后单击“ +创建作业”。

![](images/1530.jpg)

输入以下项目，然后单击“创建”按钮。

+ 名称：任意(例如，JavaDockerOCIR)
+ 说明：可选(例如“构建Docker映像并推送到OCIR”)
+ 模板：OKE

![](images/1540.jpg)

从右侧的“添加Git”中选择“ Git”。

![](images/1550.jpg)

输入以下项目。

添加以下步骤：点击“步骤”。

+ 储存库：选择您的储存库
+ 在SCM提交上自动运行构建：选中

![](images/1560.jpg)

从“添加步骤”中，选择“ Docker”->“ Docker登录”。

![](images/1590.jpg)

输入以下项目。

+ 注册表主机：选择输入的注册表名称。例如，WorkshopOCIR

![image-20200306153258197](images/1610.jpg)

从“添加步骤”中，选择“ Docker”->“ Docker Build”。

![image-20200306153411938](images/1620.jpg)

输入以下项目。

+ 注册表主机：选择输入的注册表名称(例如WorkshopOCIR)
+ 图像名称：由“对象存储名称空间/选项/图像名称”组成(例如，“对象存储名称空间/workshop/ okeatpapp”)

![](images/1630.jpg)

从“添加步骤”中，选择“ Docker”->“ Docker Push”。

![](images/1632.jpg)

输入以下项目。

+ 注册表主机：选择输入的注册表名称。例如，WorkshopOCIR
+ 映像名称：由“对象存储命名空间/选项/映像名称”组成(例如，“对象存储命名空间/workshop/oke-atp-helidon”)

点击“保存”按钮。

![](images/1640.jpg)

### 3.执行构建作业

单击“立即构建”按钮。

![](images/1650.jpg)

如果成功，状态将为![](images/status_success.jpg)。

![](images/1660.jpg)

转到OCI的OCIR，您可以看到“ oke-atp-helidon”图像已被推送。

![](images/1690.jpg)

您已经在图像中创建了一个应用程序并将其推送到OCIR。

接下来，继续[使用DevCS构建功能(CI / CD)将应用程序部署到OKE集群](WorkshopGuide900DeployToOKECluster.md)。

[转到README](../ README.md)