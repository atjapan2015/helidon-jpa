使用DevCS为构建功能(CI / CD)准备虚拟机
=====
在此步骤中，准备在DevCS中使用构建功能(CI / CD)所需的构建虚拟机。

在DevCS中选择“组织”菜单，转到“虚拟机模板”选项卡，然后单击“ +创建模板”按钮。

![](images/1150.jpg)

在对话框中输入以下项目，然后单击“创建”按钮。

+名称：任意(例如OKE)
+平台：默认的Oracle Linux 7(根据您的情况选择其他平台)

![](images/1160.jpg)

选择创建的模板“ OKE”，然后单击“软件配置”按钮以添加所需的软件包。

![](images/1170.jpg)

选择下一个包。

+ Docker 17.12
+ Kubectl
+ OCIcli ==>将会要求您同时安装Python3
+ SQLcl 18

点击“完成”按钮。

![](images/1180.jpg)

转到“构建虚拟机”选项卡。

![](images/1190.jpg)

点击“ +创建虚拟机”按钮。

![](images/1200.jpg)

在显示的对话框中，输入以下项目。

+数量：1
+ VM模板：OKE
+地区：ap-tokyo-1(要在日本使用东京数据中心)
+形状：VM.Standard2.1(根据您的情况选择其他形状)

点击“添加”按钮。

![](images/1210.jpg)

这样就完成了DevCS的设置。

然后继续[使用Terraform创建OKE群集](WorkshopGuide500ProvisionOKECluster.md)。

[转到README](../ README.md)