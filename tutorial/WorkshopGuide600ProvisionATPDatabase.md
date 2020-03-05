Service Brokerを使用したATPの作成
=====

このステップでは、Service Brokerを使用してATPデータベースを作成する方法について説明します。

Service Broker、Service CatalogおよびOCI Service Brokerについて、説明します。

- Service Brokerは、サードパーティが提供および管理する一連の管理サービスのエンドポイントです。

- Service Catalogは、Kubernetesクラスタで実行されているアプリケーションが、クラウドプロバイダーが提供するデータストアサービスなどの外部管理ソフトウェアを簡単に使用できるようにする拡張APIです。

- OCI Service Brokerは、OCI（Oracle Cloud Infrastructure）サービス用の[Open service broker API Spec](https://github.com/openservicebrokerapi/servicebroker/blob/v2.14/spec.md)のオープンソース実装です。お客様はこの実装を使用して、[Oracle Container Engine for Kubernetes](https://docs.cloud.oracle.com/iaas/Content/ContEng/Concepts/contengoverview.htm)または他のKubernetesクラスタにOpen Service Brokerをインストールできます。

下記手順で実行します。

0. 事前準備
1. helmをインストールする
2. Service Catalogとsvcatツールをインストールする
3. OCI Service Brokerをインストールする
4. ATPデータベースを取得する
5. ATPデータベースのWalletファイルを取得する

### 0. 事前準備

oke-atp-microservices-handsonディレクトリに移動して、直接``kubectl``でワークショップで使用するOKEクラスタにアクセスするために、workshop_cluster_kubeconfigを$HOME/.kube/configにコピーします。

```
mkdir $HOME/.kube
```

```
cp ./terraform_oke/workshop_cluster_kubeconfig $HOME/.kube/config
```

``kubectl get nodes``で確認します。

```
kubectl get nodes
```

OKEクラスタのnodes情報が出力されます。

```
NAME        STATUS   ROLES   AGE    VERSION
10.0.24.2   Ready    node    5m   v1.13.5
```

これで、事前準備は完了しました。

### 1. helmをインストールする

helmをインストールします。

```
curl https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get | bash
```

``helm version``でhelmバージョン情報を確認します。（$HOME/.kube/configでkubeconfigを設定した場合、--kubeconfigを指定する必要はありません。これ以降は同様です。）

```
helm version
```

もしクライアントバージョン（たとえば、v2.16.1）とサーバーバージョン（たとえば、v2.14+unreleased）が違う場合、サーバーバージョンをアップグレードする必要です。同じ場合、アップグレードする必要がありません。

```
Client: &version.Version{SemVer:"v2.16.1", GitCommit:"bbdfe5e7803a12bbdf97e94cd847859890cf4050", GitTreeState:"clean"}
Server: &version.Version{SemVer:"v2.14+unreleased", GitCommit:"", GitTreeState:"clean"}
```

helmのサーバーバージョンをアップグレードします。

```
helm init --upgrade
```

再度、``helm version``でhelmバージョン情報を確認します。

```
helm version 
```

クライアントバージョン（たとえば、v2.16.1）とサーバーバージョン（たとえば、v2.16.1）が同じであることを確認します。

```
Client: &version.Version{SemVer:"v2.16.1", GitCommit:"bbdfe5e7803a12bbdf97e94cd847859890cf4050", GitTreeState:"clean"}
Server: &version.Version{SemVer:"v2.16.1", GitCommit:"bbdfe5e7803a12bbdf97e94cd847859890cf4050", GitTreeState:"clean"}
```

これで、helmのインストールは完了しました。

### 2. Service Catalogとsvcatツールをインストールする

Service Catalogのリポジトリを追加します。

```
helm repo add svc-cat https://svc-catalog-charts.storage.googleapis.com
```

Service Catalogをインストールします。

```
helm install svc-cat/catalog --set controllerManager.verbosity="4" --timeout 300 --name catalog
```

svcatツールをインストールします。

- svcatは、Service Catalogリソースと対話するためのコマンドラインインターフェイス（CLI）です。

```
curl -sLO https://download.svcat.sh/cli/latest/linux/amd64/svcat
```

```
chmod +x ./svcat
```

```
sudo mv ./svcat /usr/local/bin/
```

svcatツールのクライアントバージョン情報を確認します。

```
svcat version --client
```

svcatのクライアントバージョン情報が出力されることを確認します。

```
Client Version: v0.3.0-beta.2
```

これで、Service Catalogとsvcatツールのインストールは完了しました。

### 3. OCI Service Brokerをインストールする

oke-atp-microservices-handsonディレクトリに移動して、OCI Service Brokerリポジトリのクローンを実行します。

```
git clone https://github.com/oracle/oci-service-broker.git
```

``oci-service-broker``ディレクトリに移動します。

```
cd oci-service-broker
```

OCI Service Brokerをインストールするのは、ociアカウント情報などが含まれるsecretを作成する必要があります。該当secretを作成します。

```
kubectl create secret generic ocicredentials \
  --from-literal=tenancy=<tenancy_ocid> \
  --from-literal=user=<user_ocid> \
  --from-literal=fingerprint=<fingerprint> \
  --from-literal=region=<region> \
  --from-literal=passphrase=<passphrase> \
  --from-file=privatekey=<private_key_path>   	
```

対象のパラメータは以下のとおりです。
key|value
-|-
tenancy_ocid|テナントOCID
user_ocid|ユーザーOCID
fingerprint|API SigningのPrivateキーのフィンガープリント
region|リージョン識別子、たとえば、ap-tokyo-1
passphrase|API Signingキーのパスワード（設定されない場合は""を入力）
private_key_path|API Signingキーのローカルパス

OCI Service Brokerをインストールします。

```
helm install charts/oci-service-broker/. --name oci-service-broker \
	--set ociCredentials.secretName=ocicredentials \
	--set storage.etcd.useEmbedded=true \
	--set tls.enabled=false
```

OCI Service Brokerの登録先のnamespaceを設定します。たとえば、ワークショップではdefaultのnamespaceに登録します。

```
sed -i -e 's/<NAMESPACE_OF_OCI_SERVICE_BROKER>/default/g' ./charts/oci-service-broker/samples/oci-service-broker.yaml
```

OCI Service Brokerの登録を実行します。

```
kubectl create -f ./charts/oci-service-broker/samples/oci-service-broker.yaml
```

``brokers``の情報を確認します。

```
svcat get brokers
```

``brokers``の情報が表示されることを確認します。ステータスが"Ready"になります。

```
         NAME          NAMESPACE                    URL                     STATUS  
+--------------------+-----------+----------------------------------------+--------+
  oci-service-broker               http://oci-service-broker.default:8080   Ready 
```

``classes``の情報を確認します。

```
svcat get classes
```

``classes``の情報が表示されることを確認します。利用できるOCIサービスが表示されます。

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

``plans``の情報を確認します。

```
svcat get plans
```

``plans``の情報が表示されることを確認します。OCI Service Brokerで提供するサービスのプランが表示されます。

```
    NAME     NAMESPACE          CLASS                    DESCRIPTION            
+----------+-----------+----------------------+--------------------------------+
  standard               atp-service            OCI Autonomous Transaction      
                                                Processing                      
  archive                object-store-service   An Archive type Object Storage  
  standard               object-store-service   A Standard type Object Storage  
  standard               adw-service            OCI Autonomous Data Warehouse   
  standard               oss-service            Oracle Streaming Service
```

これで、OCI Service Brokerのインストールは完了しました。

### 4. ATPデータベースを取得する

``oci-service-broker``のサンプルをベースにして、ATPデータベースを取得します。

サンプルファイルにあるコンパートメントを更新します。

（ocid1.compartment.oc1..aaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxを各自のコンパートメントへ変更してください。）

```
sed -i -e 's/CHANGE_COMPARTMENT_OCID_HERE/ocid1.compartment.oc1..aaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx/g' ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

サンプルファイルにあるATPデータベースの名称を更新します。（たとえば、tfOKEATPDB）

```
sed -i -e 's/osbdemo/tfOKEATPDB/g' ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

サンプルファイルにあるATPデータベースのパスワードを更新します。（たとえば、TFWorkshop__2000）

```
sed -i -e 's/s123456789S@/TFWorkshop__2000/g' ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

ATPデータベースを取得します。

```
kubectl create -f ./charts/oci-service-broker/samples/atp/atp-instance-plain.yaml
```

取得状況を確認します。

```
svcat get instances --all-namespaces
```

最初、ステータスは"Provisioning"になります。

```
       NAME        NAMESPACE      CLASS        PLAN        STATUS     
+----------------+-----------+-------------+----------+--------------+
  osb-atp-demo-1   default     atp-service   standard   Provisioning
```

ちょっと時間が経つと、最後はステータスは"Ready"になります。

```
       NAME        NAMESPACE      CLASS        PLAN     STATUS  
+----------------+-----------+-------------+----------+--------+
  osb-atp-demo-1   default     atp-service   standard   Ready
```

ATPデータベースのBindingを行います。

```
kubectl create -f ./charts/oci-service-broker/samples/atp/atp-binding-plain.yaml
```

``bindings``の情報を確認します。

```
svcat get bindings
```

``bindings``の情報が表示されることを確認します。

```
        NAME         NAMESPACE      INSTANCE      STATUS  
+------------------+-----------+----------------+--------+
  atp-demo-binding   default     osb-atp-demo-1   Ready
```

``bindings``はATPデータベースに接続用のWalletファイルが含まれています。``kubectl get``で確認できます。

```
kubectl get secrets atp-demo-binding -o yaml
```

ATPデータベースのパスワードが格納されるsecretを作成します。

たとえば、"TFWorkshop__2000"を設定します。``base64``で暗号化にします。

```
echo "TFWorkshop__2000" | base64
```

サンプルファイルにあるプレーンテキストと暗号化テキスト両方を更新します。

```
sed -i -e 's/s123456789S@/TFWorkshop__2000/g' ./charts/oci-service-broker/samples/atp/atp-demo-secret.yaml
```

```
sed -i -e 's/czEyMzQ1Njc4OVNACg==/VEZXb3Jrc2hvcF9fMjAwMAo=/g' ./charts/oci-service-broker/samples/atp/atp-demo-secret.yaml
```

secretを作成します。

```
kubectl create -f ./charts/oci-service-broker/samples/atp/atp-demo-secret.yaml
```

これで、ATPデータベースの取得は完了しました。

### 5. ATPデータベースのWalletファイルを取得する

ATPデータベースに接続するのは、Walletファイルが必要です。次のステップで使用されます。

``fetch_wallet.sh``を実行して、Walletファイルを取得します。

```
cd ..
```

```
chmod +x fetch_wallet.sh
```

```
./fetch_wallet.sh
```

Walletファイル"Wallet_tfOKEATPDB.zip"が作成されます。

```
git add Wallet_tfOKEATPDB.zip
```

```
git commit -m "Walletファイルの追加" 
```

作成されたファイルWallet_tfOKEATPDB.zipを使用して、ATPデータベースへ接続できます。

DevCSのビルド機能でこのファイルを使用して、データを導入し確認できます。次のステップで説明します。

これで、ATPデータベースのWalletファイルの取得は完了しました。

続いて[DevCSのビルド機能（CI/CD）を使用して、ATPへデータ導入](WorkshopGuide700LoadData.md)に進んでください。

[ワークショップTopへ](../README.md)