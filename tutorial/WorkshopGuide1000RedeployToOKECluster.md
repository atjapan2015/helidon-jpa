アプリケーションを修正し、再度DevCSのビルド機能（CI/CD）を使用して、OKEクラスタへのデプロイ
=====

例えば、aoneシステムのトップページのイメージを変更してみましょう。

コマンドプロンプトを開き、git repositoryフォルダーに移動します。イメージファイルを更新して、DevCSのリポジトリへCommitします。

```
cp src/main/resources/static/images/forsale_new2.jpg src/main/resources/static/images/forsale.jpg
```

DevCSの「ビルド」に移動して、ジョブ"JavaDockerOCIR"のビルドが自動的に開始されます。成功すると、ステータスが![](images/status_success.jpg "")になります。

![](images/1842.jpg "")

次に、ジョブ"OKEDeploy"のビルドが自動的に開始されます。成功すると、ステータスが![](images/status_success.jpg "")になります。

![](images/1844.jpg "")

ブラウザを開いて、ブラウザを開いて、"http://パブリックIP:サービスのポート"に移動します。

aoneシステムのトップページが表示されます。saleのイメージが変更されたことを確認できます。

![](images/1850.jpg "")

これで、OKE・ATP・DevCSのワークショップは完了しました。
お疲れ様でした！

[ワークショップTopへ](../README.md)