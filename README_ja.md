(注意：この文書は、機械で翻訳されています。だから、あなたが読むこと、これが不快な可能性がありますのでご了承下さい。あなたが英語を読むことができる場合は、https://github.com/yeoupooh/mc-launcher/blob/master/README.mdで元の文書をお読みください。)

[![Join the chat at https://gitter.im/yeoupooh/mc-launcher](https://badges.gitter.im/yeoupooh/mc-launcher.svg)](https://gitter.im/yeoupooh/mc-launcher?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/yeoupooh/mc-launcher.svg)](https://travis-ci.org/yeoupooh/mc-launcher)
[![license-GPLv2](https://img.shields.io/badge/license-GPLv2-blue.svg)](http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)
[![implementation-groovy-blue](https://img.shields.io/badge/client-groovy-blue.svg)](http://www.groovy-lang.org/)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/524f850425644312b6181a44b5374df8)](https://www.codacy.com/app/thomas-min-v1/mc-launcher)
[![Codeship Status for yeoupooh/mc-launcher](https://codeship.com/projects/327094a0-9e42-0133-4bc7-36bf3814fed7/status?branch=master)](https://codeship.com/projects/127717)

# McLauncher

これは、あなたが簡単にMinecraftのフォージや改造をダウンロードしたり、管理することができます。 これは、ビューのためのIntelliJでロジックとJavaのスイングフォームのGroovyで書かれています。

# スクリーンショット

## ランチャータブ
![Launcher tab](/docs/images/screenshot-mc-launcher-launcher-tab.png)

## モッズのダウンローダタブ
![Mods Downloader tab](/docs/images/screenshot-mc-launcher-mods-downloader-tab.png)

## 設定タブ
![Settings tab](/docs/images/screenshot-mc-launcher-settings-tab.png)

# ダウンロード

* [64ビット版のJREとWindows用のMcLauncher 1.4](http://adf.ly/12104053/mclauncher-14-for-windows-with-64bit-jre)
* [JREな ​​しのWindows用McLauncher 1.4](http://adf.ly/1VRbiC)
* [JREな ​​しMacOSXのためのMcLauncher 1.4](http://adf.ly/1VRbsy)

#  インストール

## Windowsの場合
1. Windows版をダウンロード
2. 解凍し、MC-ランチャーアプリ - 。* zipファイル。
2. ファイル名を指定して実行MC-ランチャー-app.exeの

## MacOSXの上
1. MacOSXのバージョンをダウンロードしてください
2. ダブルクリックしてMC-ランチャーアプリ - 。* DMGファイル。
3. アプリケーションフォルダにMcLauncherのアイコンをドラッグします
4. 今、ランチパッドからMc​​Launcherを実行します

# どのように...

## ダウンロードモッズ
1. 「モッズダウンローダー」タブをクリックします
2. あなたが望む改造をクリックしてください（複数選択するには、Ctrlキーを押しながらクリック）
3. 「選択した改造をダウンロード」ボタンをクリックしてください
4. ダウンロードするために、選択された改造まで待ちます。

## 鍛造をダウンロードしてインストールします。
1. 「設定」タブをクリックします
2. 鍛造リストから「フォージ」をクリックしてください
3. 「選択フォルジュのダウンロード」ボタンをクリックしてください
4. ダウンロードするために、選択された鍛冶屋まで待ちます。
5. インストールするには、鍛造リストから1鍛造をクリックしてください
6. 「ファイル名を指定して実行マインクラフトフォージインストーラ」ボタンをクリックしてください
7. Minecraftのフォージのインストーラで「OK」ボタンをクリックしてください。

## 有効（または無効）のmod
1. 「ランチャー」タブをクリックします
2. リストから選択します改造。 複数、CTRL +改造やドラッグを選択します。
3. あなたはMinecraftのを起動したときに有効/無効にするボタンを「有効/無効を選択」をクリックします
4. あなたが有効/チェックボックスをトグルすることによって一つ一つを無効にすることができます。
5. 注：「リフレッシュ」または「起動マインクラフト」をクリックすると、無効/有効改造が適用されます

## Minecraftのを起動します。
1. 打ち上げ前の設定]タブでチェックアウトMinecraftの実行可能ファイルのパス
2. ランチャータブで起動Minecraftのボタンをクリックします

## 改造のリストをカスタマイズします
1. 訪問https://jsonblob.com/を
2. コピー元のJSON https://jsonblob.com/api/jsonBlob/56a362f7e4b01190df4b0076サンプルJSONとして。
3. 保存して、今、あなたはあなたの改造リストURLを取得します
4. コピーJSONのURL https://jsonblob.com/api/jsonBlob/に「ダウンロードモッズURL "
5. 更新」ボタンをクリックしてください

## 翻訳への貢献
* 現在は韓国語、英語のみサポートされています
* あなたの翻訳を貢献したい場合は、手順の下に従ってください
1. フォークgitリポジトリ。
2. mc-launcher/mc-launcher-gui/src/main/resources/の下にstrings_.propertiesを追加します。
3. あなたをコミットし、リポジトリにプッシュ。
4. プル要求を送信します。
5. マージと次のリリースを待ちます。


# あなたのフィードバックを歓迎します
フォーラムでくださいコメントや  [![Join the chat at https://gitter.im/yeoupooh/mc-launcher](https://badges.gitter.im/yeoupooh/mc-launcher.svg)](https://gitter.im/yeoupooh/mc-launcher?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge).

# ハックス

##  Modの一覧JSON形式
* サンプル： http://bit.ly/1K25zrG
```json
{
  "format": "1.1",
  "version": "1",
  "updated": "2016-01-06 02:45:00",
  "mods": [
    {
      "name": "Mekanism: Main",
      "version": "8.1.8.259",
      "forgeVersion": "1.7.10",
      "requiredVersion": "1.7.10",
      "homepage": "http://aidancbrady.com/mekanism/",
      "url": "http://aidancbrady.com/wp-content/uploads/mekanism/259/Mekanism-1.7.10-8.1.8.259.jar"
    },
    {
      "name": "Mekanism: Mekanism Generators",
      "version": "8.1.8.259",
      "forgeVersion": "1.7.10",
      "requiredVersion": "1.7.10",
      "homepage": "http://aidancbrady.com/mekanism/",
      "url": "http://aidancbrady.com/wp-content/uploads/mekanism/259/MekanismGenerators-1.7.10-8.1.8.259.jar"
    }
  ]
}
```

# フォージリストJSON形式
* 現在、あなたがこのアプリで変更することはできません。
* サンプル： http://bit.ly/1NqXEPv
```
{
  "forges": [
    {
      "version": "1.7.10-10.13.4.1558",
      "fileName": "forge-1.7.10-10.13.4.1558-1.7.10-installer.jar",
      "url": "http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1558-1.7.10/forge-1.7.10-10.13.4.1558-1.7.10-installer.jar"
    },
    {
      "version": "1.8-11.14.4.1563",
      "url": "http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.8-11.14.4.1563/forge-1.8-11.14.4.1563-installer.jar"
    },
    {
      "version": "1.8.9-11.15.0.1692",
      "url": "http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.8.9-11.15.0.1692/forge-1.8.9-11.15.0.1692-installer.jar"
    }
  ]
}
```


# 外部リソースとライブラリ

## アプリケーションアイコン
* http://www.rw-designer.com/icon-detail/5548
* ICNSファイルから変換さhttps://iconverticons.com/online/

# プロジェクトページ
* 出典： https://github.com/yeoupooh/mc-launcher/
* ウィキ： https://github.com/yeoupooh/mc-launcher/wiki/

#  著作権
* [著作権 (C) 2016 トーマスJinwoo分](LICENSE)
