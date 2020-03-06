DevCSのビルド機能（CI/CD）を使用して、アプリケーションをOKEクラスタへのデプロイ
=====
このステップでは、新しいビルドジョブを作成して、セットアップしたOKEクラスタにアプリケーションをデプロイします。

以下の手順で実行します。

1. OKEクラスタにアプリケーションをデプロイするビルドジョブを構成する
2. ビルドジョブを実行する
3. デプロイしたアプリケーションを検証する

### 1. OKEクラスタにアプリケーションをデプロイするビルドジョブを構成する

DevCSで、「ビルド」に遷移して、「＋ジョブの作成」ボタンをクリックします。

![](images/1700.jpg "")

下記項目を入力して、「作成」ボタンをクリックします。

+ 名前：任意、例えば、OKEDeploy
+ 説明：任意、例えば、"Deploy application to OKE"
+ テンプレート：OKE

![](images/1710.jpg "")

「Git追加」から「Git」を選択します。

![](images/1720.jpg "")

下記項目を入力します。

次のステップを追加します。「ステップ」をクリックします。

+ リポジトリ：ご利用のリポジトリを選択する
+ SCMコミット時に自動的にビルドを実行：チェックオンにする

![](images/1730.jpg)「ステップの追加」から「Docker」⇒「Dockerログイン」を選択します。

![](images/1740.jpg)

下記項目を入力します。

+ レジスト・ホスト：入力したレジストリ名を選択する。例えば、WorkshopOCIR

![](images/1750.jpg)

「ステップの追加」から「OCIcli」を選択します。

![](images/1760.jpg)

下記項目を入力します。

+ ユーザーOCID：ユーザーOCID
+ フィンガープリント：API Signingキーのフィンガープリント
+ テナンシ：テナントOCID
+ 秘密キー：API SigningのPrivateキーの内容（API SigningのPrivateキーのローカルパスではない）
+ リージョン：リージョン識別子。例えば、`ap-tokyo-1`

![](images/1770.jpg "")

「ステップの追加」から「UNIXシェル」を選択します。

![](images/1780.jpg)

下記項目を入力して、「保存」ボタンをクリックします。

+ スクリプト：--cluster-idのあるコマンドは書き留めたコマンドへ差し替えてください（⇒OKEクラスタのkubeconfigファイルを作成するためのコマンド）
+ `kubectl create secret docker-registry workshop-ocirsecret`コマンドはは各自の情報へ差し替えてください。

```
mkdir -p $HOME/.kube
oci ce cluster create-kubeconfig --cluster-id ocid1.cluster.oc1.ap-tokyo-1.aaaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx --file $HOME/.kube/config --region ap-tokyo-1 --token-version 2.0.0
export KUBECONFIG=$HOME/.kube/config
wget https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
chmod +x kubectl
./kubectl version
./kubectl config view
./kubectl get nodes
./kubectl delete secret customized-db-cred --ignore-not-found=true
./kubectl delete deployment oke-atp-helidon --ignore-not-found=true
kubectl create secret docker-registry workshop-ocirsecret --docker-server=<your-registry-server> --docker-username=<your-name> --docker-password=<your-pword> --docker-email=<your-email>
./kubectl apply -f oke-atp-helidon.yaml
./kubectl get services oke-atp-helidon
./kubectl get pods
./kubectl describe pods
```

![](images/1790.jpg)

### 2. ビルドジョブを実行する

「今すぐビルド」ボタンをクリックします。

![](images/1810.jpg)

成功すると、ステータスが![](images/status_success.jpg "")になります。

「ビルド・ログ」をクリックします。

![](images/1820.jpg)

成功すると、"Status:DONE Result:SUCCESSFUL"が表示されます。

![](images/1830.jpg)

### 3. デプロイしたアプリケーションを検証する

`kubectl get services | grep oke-atp-helidon`でデプロイしたサービスのポートを確認します。

ワークショップでは、サービスのポートは"31205"になります。

```
oke-atp-helidon                      NodePort    10.2.142.208   <none>        8080:31205/TCP   6m37s
```

`kubectl get nodes -o wide`でOKEクラスタのワーカーノードのパブリックIP（EXTERNAL-IP）を確認します。

```
NAME        STATUS   ROLES   AGE   VERSION   INTERNAL-IP   EXTERNAL-IP      OS-IMAGE                  KERNEL-VERSION                   CONTAINER-RUNTIME
10.0.24.2   Ready    node    24h   v1.14.8   10.0.24.2     xxx.xxx.xxx.xx   Oracle Linux Server 7.6   4.14.35-1902.2.0.el7uek.x86_64   docker://18.9.8
```

ブラウザを開いて、"http://パブリックIP:サービスのポート"に移動します。（※OKEクラスタのワーカーノードのサブネットが使用するセキュリティ・リストのイングレス・ルールに対して、サービスのポートを解放する必要があります）

Helidon＆JETで開発したシステムのトップページが表示されます。

![](images/1840.jpg)

これで、アプリケーションをOKEへのデプロイは完了しました。

続いて [アプリケーションを修正し、再度DevCSのビルド機能（CI/CD）を使用して、OKEクラスタへのデプロイ](WorkshopGuide1000RedeployToOKECluster.md) に進んでください。

[ワークショップTopへ](../README.md)