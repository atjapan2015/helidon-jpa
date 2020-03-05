DevCSでプロジェクトとリポジトリの作成
=========

このステップでは、GitHubリポジトリに基づいてDevCSで新しい開発者プロジェクトをセットアップする方法を説明します。

一つDevCSのインスタンスは、複数のプロジェクトを管理できます。また、一つのプロジェクトは複数のリポジトリを管理できます。

プロジェクトを作成するには、「組織」メニューを選択し、「プロジェクト」タブで右側の「＋作成」ボタンをクリックします。

![](images/1070.jpg "")

下記項目を入力して、「次」をクリックします。

+ 名前：任意（例えば、Workshop）

![](images/1080.jpg)

デフォルトのままにして、「次」をクリックします。

![](images/1090.jpg "")

デフォルトのままにして、「終了」をクリックします。

![](images/1100.jpg)

すべてのモジュールがプロビジョンされるまでお待ちください。

![](images/1110.jpg)

プロジェクトの作成が完了すると、自動的にこちらの画面に移動されます。

リポジトリを作成するには、「＋作成」をクリックします。

![](images/1120.jpg "")

![](images/1115.jpg)

下記項目を入力して、「作成」をクリックします。

+ 名前：任意（例えば、`oke-atp-helidon-handson`）
+ 説明：任意（例えば、`OKE ATP Helidon Handson`）
+ 既存のリポジトリのインポート：`https://github.com/oracle-japan/oke-atp-helidon-handson.git`

![](images/1130.jpg "")

インポートが完了すると、既存のリポジトリのファイルがインポートされます。

![](images/1140.jpg "")

作成したリポジトリをご利用のマシンへコピーします。

DevCSで「Git」に移動して、右側の「クローン」を選択し、"HTTPSでクローニングします"の「コピー」アイコンをクリックします。

![](images/1145.jpg "")

ご利用のマシンの作業フォルダに移動して、クローンを実行します。

```
git clone コピーされたURL
```

ワークショップで使用するJavaアプリケーションからATPデータベースへ接続するのは、ojdbc8.jarなどのjarファイルが必要です。

[Oracle Database 18c (18.3) JDBC Driver and UCP Downloads](https://www.oracle.com/database/technologies/appdev/jdbc-ucp-183-downloads.html)から下記jarファイルをダウンロードし、oke-atp-microservices-handsonディレクトリ下の`libs`に格納して、gitリポジトリへコミットしてください。

+ ojdbc8.jar
+ oraclepki.jar
+ osdt_cert.jar
+ osdt_core.jar

```
git add . 
```
```
git commit -m "JDBCドライバの追加" 
```
これで、DevCSでプロジェクトとリポジトリの作成は完了しました。

続いて[DevCSでビルド機能（CI/CD）使用の仮想マシンの準備](WorkshopGuide400CreateVirtualMachine.md)に進んでください。

[ワークショップTopへ](../README.md)