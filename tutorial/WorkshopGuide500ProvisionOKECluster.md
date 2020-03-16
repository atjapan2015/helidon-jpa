使用Terraform创建OKE集群
=====

此步骤描述了如何使用Terraform创建OKE集群，包括所有必需的网络元素，例如虚拟云网络，子网和访问列表。

!!!注意
    OCI提供了基于Terraform的资源管理服务，称为资源管理器。资源管理器使用Terraform脚本通过“基础结构即代码”模型帮助安装，配置和管理资源。这次，我将不会使用资源管理器来体验编辑Terraform脚本。

请按照以下步骤操作。

1.打开一个新的终端窗口，然后转到git repository文件夹中的terraform_oke文件夹
2.编辑terraform.tfvars，然后输入收集的OCID和其他信息
3.执行terraform init，terraform计划和terraform应用以创建OKE集群
4.使用创建的文件workshop_cluster_kubeconfig通过kubectl命令访问OKE。

### 1.打开一个新的终端窗口，然后转到git存储库文件夹中的terraform_oke文件夹

```sh
cd terraform_oke
```

### 2.编辑terraform.tfvars并输入信息，例如收集的OCID

可以通过Terraform参数文件`terraform.tfvars`中描述的设置来更改OKE群集的配置。 (*在研讨会上，尽可能地设置了默认值，并减少了需要更改的参数。*)

复制基本参数文件并进行编辑。使用[步骤2](WorkshopGuide200GatherInformation.md)中收集的信息输入诸如OCID之类的信息。

```sh
cp terraform.tfvars.example terraform.tfvars
```
```sh
vi terraform.tfvars
```

目标参数如下。
关键|值
-|-
tenancy_ocid |租户OCID
隔离专区|隔离专区OCID
指纹| API签名密钥指纹
private_key_path | API签名私钥的本地路径
user_ocid |用户OCID
地区|地区标识符

下面显示了参数文件的描述示例。

```属性
# OCI authentication

tenancy_ocid = "ocid1.tenancy.oc1..xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
compartment_ocid = "ocid1.compartment.oc1..xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
fingerprint = "00:11:22:33:44:55:66:77:88:99:aa:bb:cc:dd:ee:ff"
private_key_path = "~/.oci/oci_api_key.pem"
user_ocid = "ocid1.user.oc1..xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
region = "ap-seoul-1"
```

### 3.执行Terraform初始化，terraform计划和Terraform应用以获得OKE集群

在该目录中运行`terraform init`将下载所有依赖项。

运行“ terraform plan”以验证配置。

运行“ terraform apply”以启动基础架构。

输入“是”。

将创建以下两个文件。

+ terraform.tfstate：此文件包含创建的元素的详细信息。 Terraform在更新配置文件时需要此文件，并且必须将此更改应用于基础结构。
+ workshop_cluster_kubeconfig：这是用于连接到新创建的Kubernetes集群的配置文件。

### 4。使用创建的文件workshop_cluster_kubeconfig使用kubectl命令访问OKE

要访问OKE集群，您需要设置OCI配置文件。

```sh
oci setup config
```

按顺序填写以下信息。

1. 输入您的配置[/home/opc/.oci/config]的位置：`Enter`
2. 输入用户OCID：用户OCID。例如，`ocid1.user.oc1..xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`
3. 输入租户OCID：租户OCID。例如，`ocid1.tenancy.oc1..xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`
4. 输入地区：地区标识符。例如，`ap-seoul-1`
5. 您要生成一个新的RSA密钥对吗(如果拒绝，将要求您提供现有密钥的路径。)[Y / n]：`n`
6. 输入私钥文件的位置：`/home/opc/.oci/oci_api_key.pem`



验证是否已使用kubectl命令创建了OKE集群。

```
kubectl get nodes --kubeconfig=workshop_cluster_kubeconfig
```

等待几分钟，您将看到类似以下的内容：

```
NAME        STATUS   ROLES   AGE   VERSION
10.0.24.2   Ready    node     3m   v1.14.8
```

OKE集群的创建现已完成。

注意：如何从浏览器获取kubeconfig

需要kubeconfig文件才能将应用程序部署到OKE集群。至于kubeconfig文件，除了使用Terraform创建的`workshop_cluster_kubeconfig`文件之外，您还可以从浏览器获取kubeconfig。

展开OCI控制台右上方的汉堡菜单，转到“开发人员服务”⇒“容器集群(OKE)”，选择一个隔离专区，然后选择适用的OKE集群，然后单击“访问Kubeconfig”点击按钮。

记下红框内的命令。点击“关闭”按钮。

![](images/1220.jpg)

然后继续[使用Service Broker创建ATP](WorkshopGuide600ProvisionATPDatabase.md)。

[转到README](../ README.md)