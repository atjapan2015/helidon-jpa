使用DevCS创建项目和存储库
=========

此步骤显示了如何基于GitHub存储库在DevCS中设置新的开发人员项目。

一个DevCS实例可以管理多个项目。一个项目可以管理多个存储库。

要创建项目，请选择“组织”菜单，然后单击“项目”选项卡右侧的“ +创建”按钮。

![](images/1070.jpg)

输入以下项目，然后单击“下一步”。

+名称：任意（例如Workshop)

![](images/1080.jpg)

保留默认值，然后单击“下一步”。

![](images/1090.jpg)

保留默认值，然后单击完成。

![](images/1100.jpg)

等到所有模块都配置好。

![](images/1110.jpg)

项目创建完成后，您将自动移至该屏幕。

要创建存储库，请单击“ +创建”。

![](images/1120.jpg)

![](images/1115.jpg)

输入以下项目，然后单击“创建”。

+名称：任意（例如“ oke-atp-helidon-handson”)
+说明：可选（例如，“ OKE ATP Helidon Handson”)
+导入现有存储库：`https://gitlab.k8scloud.site/devops_admin/oke-atp-helidon-handson.git`

![](images/1130.jpg)

导入完成后，将从现有存储库中导入文件。

![](images/1140.jpg)

将创建的存储库复制到您的计算机。

转到DevCS中的“ Git”，选择右侧的“克隆”，然后单击“使用HTTPS克隆”的“复制”图标。

![](images/1145.jpg)

转到计算机上的工作文件夹并运行克隆。

```
git clone复制的URL
```

现在，您已经在DevCS中创建了一个项目和存储库。

接下来，继续[准备将虚拟机与DevCS一起使用构建功能（CI / CD)](WorkshopGuide400CreateVirtualMachine.md)。

[转到README](../ README.md)

