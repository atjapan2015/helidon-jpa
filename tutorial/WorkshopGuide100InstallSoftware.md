安装所需的软件
======

该研讨会需要在客户端上使用以下软件。
请按照官方网站上的说明进行操作，然后为您的设备安装软件。

- [kubectl](https://kubernetes.io/ja/docs/tasks/tools/install-kubectl/) : 用于OKE(Kubernetes)集群操作
- [git](https://git-scm.com/book/ja/v2/%E4%BD%BF%E3%81%84%E5%A7%8B%E3%82%81%E3%82%8B-Git%E3%81%AE%E3%82%A4%E3%83%B3%E3%82%B9%E3%83%88%E3%83%BC%E3%83%AB) : DevCS存储库( Git存储库)，用于更新文件
- [terraform](https://www.terraform.io/downloads.html) ： 用于创建OKE集群和ATP

以下过程描述了如何获取OCI计算实例(Oracle Linux)和安装上述软件。如果不需要此过程，请转到[收集用于研讨会的帐户信息](WorkshopGuide200GatherInformation.md)

获取OCI计算实例
-------
请按照以下步骤操作。

1.获取虚拟云网络
2.获取一个计算实例

#### 1.获取虚拟云网络

展开OCI控制台右上方的汉堡菜单，转到“网络”⇒“虚拟云网络”，然后单击“网络快速入门”按钮。

在“网络快速入门”屏幕上，选择“具有Internet连接的VCN”，然后单击“启动工作流”。

![](images/0001.jpg)

在用于创建具有Internet连接的VCN的屏幕上，输入以下项目，然后单击“下一步”按钮。

+ VCN之前：可选。例如，`workshopvcn`

+创建隔间：选择您的隔间。

+ VCN CIDR块：例如“ 10.0.0.0 / 16”

+公共子网CIDR块：例如，“ 10.0.0.0 / 24”

+专用子网CIDR块：例如“ 10.0.1.0 / 24”

![](images/0002.jpg)

在“使用Internet连接创建VCN”屏幕上，单击“创建”。

![](images/0010.jpg)

这样就完成了虚拟云网络的获取。

#### 2.获取一个计算实例

您需要SSH密钥对才能获取OCI计算实例。有关如何创建SSH密钥对的信息，请参考[创建密钥对](https://docs.oracle.com/cd/E97706_01/Content/GSG/Tasks/creatingkeys.htm)。

展开OCI控制台右上方的汉堡菜单，转到“计算”⇒“实例”，然后单击“创建实例”按钮。

在“创建计算实例”屏幕上，单击“显示形状，网络和存储选项”，输入以下各项，然后单击“创建”按钮。

输入项目|描述
-|-
实例命名|任意（例如，workshopvm）
实例形状|选择形状（例如VM.Standard2.1）
虚拟云网络隔间|选择您的隔间
虚拟云网络|选择您的虚拟云网络（例如，workshopvcn）
子网分区|选择您的分区
子网|选择具有“公共子网”的子网以从Internet访问
使用网络安全组控制流量|请勿选择
分配公共IP地址|选择以从Internet访问
粘贴或选择在SSH密钥中创建的公共密钥|创建SSH密钥对

![](images/0018.jpg)

![](images/0020.jpg)

将显示实例详细信息屏幕，创建完成后，状态将更改为“正在运行”，并显示公共IP地址。

![](images/0030.jpg)

在可以运行ssh(例如，腻子)的终端上访问OCI计算实例。例如，如果您使用的是腻子，请输入以下项目，然后单击“打开”按钮。

+会话⇒主机名(或IP地址)：计算实例的公共IP地址
+连接⇒数据⇒自动登录用户名：opc
+连接⇒SSH⇒身份验证⇒用于认证的私钥文件：SSH私钥传递

![](images/0040.jpg)

您已完成OCI计算实例的获取。

软件安装
---------

在要使用的用户的主目录中创建“ install_software.sh”脚本。

```sh
vi install_software.sh
```

复制并保存下面的脚本内容。
```sh
#!/bin/bash

echo "Install Start"
echo ""

# set terraform version, by default is 0.12.21 (latest version on 2020-03-05)
if [ "$1" != "" ]
then
    TERRAFORM_VERSION="$1"
else
    TERRAFORM_VERSION=0.12.21
fi

echo "default Terraform Version is v${TERRAFORM_VERSION}"
echo ""

# install git
echo "Installing git ..."
sudo yum install -y git > /dev/null 2>&1

echo "Installed $(git --version)"
echo ""

# install oci
#usage:
# curl -LSs https://raw.githubusercontent.com/oracle-japan/weblogic-operator-handson/scripts/master/bin/install-oci.sh | bash
echo "Installing oci cli ..."
sudo yum install python3 -y > /dev/null 2>&1
rm -rf ~/lib/oracle-cli > /dev/null 2>&1
rm -f ~/oci_install.sh > /dev/null 2>&1
curl -L https://raw.githubusercontent.com/oracle/oci-cli/master/scripts/install/install.sh --output ~/oci_install.sh > /dev/null 2>&1
chmod 755 ~/oci_install.sh > /dev/null 2>&1
~/oci_install.sh --accept-all-defaults > /dev/null 2>&1
rm -f ~/oci_install.sh > /dev/null 2>&1
mkdir -p ~/.oci > /dev/null 2>&1

echo "Installed oci cli version $(oci --version)"
echo ""

# install kubectl
echo "Installing kubectl ..."
curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl > /dev/null 2>&1
chmod +x ./kubectl > /dev/null 2>&1
sudo mv ./kubectl /usr/local/bin/kubectl > /dev/null 2>&1

echo "Installed kubectl $(kubectl version --client --short)"
echo ""

# install terraform
echo "Installing terraform ..."
wget https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip > /dev/null 2>&1
unzip terraform_${TERRAFORM_VERSION}_linux_amd64.zip > /dev/null 2>&1
sudo mv ./terraform /usr/local/bin/terraform > /dev/null 2>&1

echo "Installed $(terraform version)"
echo ""

echo "Install Complete"
```

授予“ install_software.sh”脚本执行权限并执行它。

```sh
chmod + x install_software.sh
```
```sh
./install_software.sh
```

如果成功，则git，kubectl和terraform版本信息将正确显示。

如果最新的Terraform版本是0.12.21或更高版本，则可能会看到以下消息：

```
您的Terraform版本已过时！最新版本
是x.yy.zz。您可以通过从www.terraform.io/downloads.html下载进行更新
```

在这种情况下，请将最新的Terraform版本作为参数传递给“ install_software.sh”，然后再次执行。

```
./install_software.sh x.yy.zz
```

举个例子
```
./install_software.sh 0.12.21
```

至此，软件安装完成。

接下来，继续[收集研讨会中使用的帐户信息](WorkshopGuide200GatherInformation.md)。

[转到README](../ README.md)