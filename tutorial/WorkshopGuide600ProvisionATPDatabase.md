使用Service Broker创建ATP
=====

此步骤描述了如何使用Service Broker创建ATP数据库。

了解有关Service Broker，Service Catalog和OCI Service Broker的信息。

-Service Broker是第三方提供和管理的一组管理服务的端点。

-Service Catalog是扩展的API，允许在Kubernetes集群上运行的应用程序轻松使用外部管理软件，例如云提供商提供的数据存储服务。

-OCI Service Broker是[开放服务代理API规范](https://github.com/openservicebrokerapi/servicebroker/blob/v2.14/spec.md)的开源实现，用于OCI(Oracle云基础架构)服务。客户可以使用此实现为[用于Kubernetes的Oracle容器引擎](https://docs.cloud.oracle.com/iaas/Content/ContEng/Concepts/contengoverview.htm)或其他Kubernetes集群创建开放服务代理。可以安装。

请按照以下步骤操作。

0.提前准备
1.安装头盔
2.安装服务目录和svcat工具
3.安装OCI Service Broker
4.获取ATP数据库
5.获取ATP数据库钱包文件

### 0.预先准备

转到oke-atp-helidon-handson目录，然后将workshop_cluster_kubeconfig复制到$ HOME / .kube / config以使用``kubectl''直接访问研讨会中使用的OKE集群。

```sh
cp ./terraform_oke/workshop_cluster_kubeconfig $HOME/.kube/config
```

检查``kubectl获取节点''。

```sh
kubectl get nodes
```

输出OKE集群的节点信息。

```
NAME        STATUS   ROLES   AGE   VERSION
10.0.24.2   Ready    node    43m   v1.14.8
```

准备工作现已完成。

### 1.安装HELM

安装头盔。

```sh
curl https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get | bash
```

使用``舵机版本''检查舵机版本信息。 (如果在$ HOME / .kube / config中设置kubeconfig，则无需指定--kubeconfig。从此处开始同样适用。)

```sh
helm version
```

如果客户端版本(例如v2.16.1)和服务器版本(例如v2.14 +未发行)不同，则需要升级服务器版本。如果它们相同，则无需升级。

```
Client: &version.Version{SemVer:"v2.16.3", GitCommit:"1ee0254c86d4ed6887327dabed7aa7da29d7eb0d", GitTreeState:"clean"}
Server: &version.Version{SemVer:"v2.14+unreleased", GitCommit:"", GitTreeState:"clean"}
```

升级HELM的服务器版本。

```sh
helm init --upgrade
```

再次，用``helm version''检查头盔版本信息。

```sh
helm version
```

确保客户端版本(例如v2.16.1)和服务器版本(例如v2.16.1)相同。

```
Client: &version.Version{SemVer:"v2.16.3", GitCommit:"1ee0254c86d4ed6887327dabed7aa7da29d7eb0d", GitTreeState:"clean"}
Server: &version.Version{SemVer:"v2.16.3", GitCommit:"1ee0254c86d4ed6887327dabed7aa7da29d7eb0d", GitTreeState:"clean"}
```

头盔的安装现已完成。

### 2.安装服务目录和svcat工具

添加服务目录存储库。

```sh
helm repo add svc-cat https://svc-catalog-charts.storage.googleapis.com
```

安装服务目录。

```sh
helm install svc-cat/catalog --set controllerManager.verbosity="4" --timeout 300 --name catalog
```

安装svcat工具。

-svcat是用于与服务目录资源进行交互的命令行界面(CLI)。

```sh
curl -sLO https://download.svcat.sh/cli/latest/linux/amd64/svcat
```

```sh
chmod +x ./svcat
```

```sh
sudo mv ./svcat /usr/local/bin/
```

检查svcat工具的客户端版本信息。

```sh
svcat version --client
```

检查是否输出了svcat的客户端版本信息。

```
Client Version: v0.3.0-beta.2
```

这样就完成了Service Catalog和svcat工具的安装。

### 3.安装OCI Service Broker

转到oke-atp-helidon-handson目录并克隆OCI Service Broker存储库。

```sh
git clone https://github.com/oracle/oci-service-broker.git
```

切换到oci-service-broker目录。

```sh
cd oci-service-broker
```

要安装OCI Service Broker，您需要创建一个包含oci帐户信息等的秘密。创建相应的秘密。

```
kubectl create secret generic ocicredentials \
  --from-literal=tenancy=<tenancy_ocid> \
  --from-literal=user=<user_ocid> \
  --from-literal=fingerprint=<fingerprint> \
  --from-literal=region=<region> \
  --from-literal=passphrase=<passphrase> \
  --from-file=privatekey=<private_key_path>   	
```

目标参数如下。
关键|值
-|-
tenancy_ocid |租户OCID
user_ocid |用户OCID
指纹| API签名私钥指纹
地区|地区标识符，例如ap-tokyo-1
API签名密钥的密码|密码(如果未设置，请输入“”)
private_key_path | API签名密钥的本地路径

安装OCI Service Broker。

```sh
helm install charts/oci-service-broker/. --name oci-service-broker \
	--set ociCredentials.secretName=ocicredentials \
	--set storage.etcd.useEmbedded=true \
	--set tls.enabled=false
```

设置OCI Service Broker注册目标的名称空间。例如，在Workshop中，在默认名称空间中注册。

```sh
sed -i -e 's/<NAMESPACE_OF_OCI_SERVICE_BROKER>/default/g' ./charts/oci-service-broker/samples/oci-service-broker.yaml
```

执行OCI Service Broker注册。

```sh
kubectl create -f ./charts/oci-service-broker/samples/oci-service-broker.yaml
```

检查`brokers`的信息。

```sh
svcat get brokers
```

确认显示`brokers`的信息。状态变为“就绪”。

```
         NAME          NAMESPACE                    URL                     STATUS  
+--------------------+-----------+----------------------------------------+--------+
  oci-service-broker               http://oci-service-broker.default:8080   Ready 
```

检查`classes`的信息。

```sh
svcat get classes
```

确认显示“类别”的信息。显示可用的OCI服务。

```
          NAME           NAMESPACE                 DESCRIPTION                 
+----------------------+-----------+------------------------------------------+
  atp-service                        Autonomous Transaction                    
                                     Processing Service                        
  object-store-service               Object Storage Service                    
  adw-service                        Autonomous Data Warehouse                 
                                     Service                                   
  oss-service                        Oracle Streaming Service
```

检查`plans`中的信息。

```sh
svcat get plans
```

确认已显示`plans`信息。显示由OCI Service Broker提供的服务计划。

```
          NAME           NAMESPACE                 DESCRIPTION                 
+----------------------+-----------+------------------------------------------+
  atp-service                        Autonomous Transaction                    
                                     Processing Service                        
  object-store-service               Object Storage Service                    
  adw-service                        Autonomous Data Warehouse                 
                                     Service                                   
  oss-service                        Oracle Streaming Service
```

OCI Service Broker的安装现已完成。

### 4.获取ATP数据库

根据oci-service-broker示例获取ATP数据库。

更新样本文件中的隔离专区。

(请将ocid1.compartment.oc1..aaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx更改为您自己的隔离专区。)

```sh
sed -i -e 's/CHANGE_COMPARTMENT_OCID_HERE/ocid1.compartment.oc1..aaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx/g' ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

更新示例文件中的ATP数据库名称。 (例如，WORKSHOPATP)

```sh
sed -i -e 's/osbdemo/WORKSHOPATP/g' ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

更新样本文件中的ATP数据库密码。 (例如，WOrkshop__8080)

```sh
sed -i -e 's/s123456789S@/WOrkshop__8080/g' ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

获取ATP数据库。

```sh
kubectl create -f ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

检查采集状态。

```sh
svcat get instances --all-namespaces
```

最初，状态为“正在设置”。

```
       NAME        NAMESPACE      CLASS        PLAN        STATUS     
+----------------+-----------+-------------+----------+--------------+
  osb-atp-demo-1   default     atp-service   standard   Provisioning
```

一段时间后，状态最终变为“就绪”。

```
       NAME        NAMESPACE      CLASS        PLAN     STATUS  
+----------------+-----------+-------------+----------+--------+
  osb-atp-demo-1   default     atp-service   standard   Ready
```

绑定ATP数据库。

```sh
kubectl create -f ./charts/oci-service-broker/samples/atp/atp-binding-plain.yaml
```

检查`bindings`的信息。

```sh
svcat get bindings
```

确认显示`bindings`信息。

```
        NAME         NAMESPACE      INSTANCE      STATUS  
+------------------+-----------+----------------+--------+
  atp-demo-binding   default     osb-atp-demo-1   Ready
```

绑定包含用于连接到ATP数据库的钱包文件。您可以使用``kubectl get''进行检查。

```sh
kubectl get secrets atp-demo-binding -o yaml
```

创建一个秘密以存储ATP数据库密码。

例如，设置“ WOrkshop__8080”。用``base64''加密。输出V09ya3Nob3BfXzgwODAK

```sh
echo "WOrkshop__8080" | base64
```

更新示例文件中的纯文本和密文。

```sh
sed -i -e 's/s123456789S@/WOrkshop__8080/g' ./charts/oci-service-broker/samples/atp/atp-demo-secret.yaml
```

```sh
sed -i -e 's/czEyMzQ1Njc4OVNACg==/V09ya3Nob3BfXzgwODAK/g' ./charts/oci-service-broker/samples/atp/atp-demo-secret.yaml
```

创建一个秘密。

```sh
kubectl create -f ./charts/oci-service-broker/samples/atp/atp-demo-secret.yaml
```

ATP数据库的获取现已完成。

### 5.获取ATP数据库钱包文件

需要钱包文件才能连接到ATP数据库。用于下一步。

执行fetch_wallet.sh获取钱包文件。

```sh
cd ..
```

```
chmod +x fetch_wallet.sh
```

```
./fetch_wallet.sh
```

创建一个钱包文件“ Wallet_tfOKEATPP.zip”。

```
git add wallet.zip
```

```
git commit -m “添加钱包文件”
```

您可以使用创建的文件“ wallet.zip”连接到ATP数据库。

您可以将此文件与DevCS构建功能一起使用，以引入和验证数据。我们将在下一步进行解释。

ATP数据库钱包文件的获取现已完成。

接下来，继续[使用DevCS(CI / CD)的构建功能将数据引入ATP](WorkshopGuide700LoadData.md)。

[转到README](../ README.md)