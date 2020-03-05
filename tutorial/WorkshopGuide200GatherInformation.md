ワークショップで利用するアカウント情報の収集
=====

OCIを使用するための一連のOCIDなど情報の収集
------
ワークショップを進める上で下記の情報が必要となります。**取得した情報は後ほど使用するためメモ帳などに控えておいてください。**

1. テナントOCID
2. オブジェクト・ストレージ・ネームスペース
3. リージョン識別子
4. コンパートメントOCID
5. ユーザーOCID
6. API SigningのPrivateキーのローカルパス
7. API Signingキーのフィンガープリント

#### 1. テナントOCID　/　2. オブジェクト・ストレージ・ネームスペース

OCIコンソール右上のハンバーガーメニューを展開し、「管理」⇒「テナント詳細」に移動します。

以下をコピーしてメモしてください。

+ OCID：テナントOCID
+ オブジェクト・ストレージ・ネームスペース：オブジェクト・ストレージ・ネームスペース

![](images/1010.jpg)

#### 3. リージョン識別子

OCIコンソール右上のハンバーガーメニューを展開し、「管理」⇒「地域管理」に移動します。

以下をコピーしてメモしてください。
+ Region Identifier：リージョン識別子

日本にあるデータセンター"Japan East (Tokyo)"を使用する場合"ap-tokyo-1"になります。

![](images/1020.jpg "")

#### 4. コンパートメントOCID

OCIコンソール右上のハンバーガーメニューを展開し、「アイデンティティ」⇒「コンパートメント」に移動します。コンパートメント一覧から使用するコンパートメントに移動します。

以下をコピーしてメモしてください。
+ OCID：コンパートメントOCID
![](images/1030.jpg)

#### 5. ユーザーOCID

OCIコンソール右上のハンバーガーメニューを展開し、「アイデンティティ」⇒「ユーザー」に移動します。ユーザー一覧から使用するユーザーに移動します。

以下をコピーしてメモしてください。
+ OCID：ユーザーOCID

![](images/1040.jpg)

#### 6. API SigningのPrivateキーのローカルパス

API Signingキーを持っていない場合、[API Signingキーの作成方法](https://docs.cloud.oracle.com/iaas/Content/API/Concepts/apisigningkey.htm)を参考にして、作成します。

例：

パスフレーズなしでAPI SigningのPrivateキーを生成するには、
```
mkdir ~/.oci
openssl genrsa -out ~/.oci/oci_api_key.pem 2048
chmod go-rwx ~/.oci/oci_api_key.pem
```

API SigningのPublicキーを生成するには、
```
openssl rsa -pubout -in ~/.oci/oci_api_key.pem -out ~/.oci/oci_api_key_public.pem
```

~/.oci/oci_api_key.pemはAPI SigningのPrivateキーのローカルパスになります。絶対パスを取得して、メモしてください。

API SigningのPrivateキーを有効にするために、ユーザー詳細画面で該当Publicキーを追加する必要があります。

ユーザー詳細画面で「APIキー」に移動します。

![](images/1050.jpg)

API SigningのPublicキーを入力して、「追加」ボタンをクリックします。

![](images/1060.jpg)

#### 7. API Signingキーのフィンガープリント

API Signingキーのフィンガープリントを取得するには、下記コマンドを実行します。

以下の出力結果をコピーしてメモしてください。
```
openssl rsa -pubout -outform DER -in ~/.oci/oci_api_key.pem | openssl md5 -c
```

#### 8. 認証トークン

マシンもしくはOKEからOCIRを使用するために、認証トークンの収集を行います（ユーザー認証用のパスワードではなく、認証トークンが必要です）。

OCIコンソール右上のハンバーガーメニューを展開し、「アイデンティティ」⇒「ユーザー」に移動します。ユーザー一覧から使用するユーザーへ移動します。

「リソース」の「認証トークン」に移動し、「トークンの生成」ボタンをクリックします。

![](images/1062.jpg)

下記項目を入力して、「トークンの生成」ボタンをクリックします。

+ 説明：任意（例えば、workshop token）

![](images/1064.jpg)

**生成されたトークンは一回のみ表示されます。「コピー」をクリックしてトークンがコピーされ、どこに保存してください。完了したら、「閉じる」ボタンをクリックします。（注：忘れたときは作成されたトークンを削除して、再度生成してください。）**

![](images/1066.jpg)

これで、ワークショップで利用するアカウント情報の収集が完了しました。

続いて[DevCSでプロジェクトとリポジトリの作成](WorkshopGuide300CreateProjectRepository.md)に進んでください。

[ワークショップTopへ](../README.md)