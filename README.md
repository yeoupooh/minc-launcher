[![Join the chat at https://gitter.im/yeoupooh/mc-launcher](https://badges.gitter.im/yeoupooh/mc-launcher.svg)](https://gitter.im/yeoupooh/mc-launcher?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Documentation Status](https://readthedocs.org/projects/mc-launcher/badge/?version=latest)](http://mc-launcher.readthedocs.org/en/latest/?badge=latest)
[![Build Status](https://travis-ci.org/yeoupooh/mc-launcher.svg)](https://travis-ci.org/yeoupooh/mc-launcher)
[![license-GPLv2](https://img.shields.io/badge/license-GPLv2-blue.svg)](http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)
[![implementation-groovy-blue](https://img.shields.io/badge/client-groovy-blue.svg)](http://www.groovy-lang.org/)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/524f850425644312b6181a44b5374df8)](https://www.codacy.com/app/thomas-min-v1/mc-launcher)
[![Codeship Status for yeoupooh/mc-launcher](https://codeship.com/projects/327094a0-9e42-0133-4bc7-36bf3814fed7/status?branch=master)](https://codeship.com/projects/127717)
[![Coverage Status](https://coveralls.io/repos/github/yeoupooh/mc-launcher/badge.svg?branch=master)](https://coveralls.io/github/yeoupooh/mc-launcher?branch=master)


# McLauncher

This allows you to download or manage Minecraft Forge and mods easily. It is written in Groovy for logic and Java swing form in intellij for view.

# Screenshots

## Launcher tab
![Launcher tab](/docs/images/screenshot-mc-launcher-launcher-tab.png)

## Mods Downloader tab
![Mods Downloader tab](/docs/images/screenshot-mc-launcher-mods-downloader-tab.png)

## Settings tab
![Settings tab](/docs/images/screenshot-mc-launcher-settings-tab.png)

# Downloads

## McLauncherFX 2.0 
* [McLauncherFX 2.0 for Windows x64 with JRE](http://adf.ly/1Z2GMq)
* [McLauncherFX 2.0 for Windows x64 without JRE](http://adf.ly/1Z2GRl)
* [McLauncherFX 2.0 for MacOSX without JRE](http://adf.ly/1Z2G41)

## McLauncher 1.4
* [McLauncher 1.4 for Windows with 64bit JRE](http://adf.ly/12104053/mclauncher-14-for-windows-with-64bit-jre)
* [McLauncher 1.4 for Windows without JRE](http://adf.ly/1VRbiC)
* [McLauncher 1.4 for MacOSX without JRE](http://adf.ly/1VRbsy)

# Install

## On Windows
1. Download Windows version
2. Unzip mc-launcher-app-*.zip file.
2. Run mc-launcher-app.exe

## On MacOSX
1. Download MacOSX version
2. Double click mc-launcher-app-*.dmg file.
3. Drag McLauncher icon to Applications folder
4. Now run McLauncher from LaunchPad

# How to ...

## Download mods
1. Click "Mods Downloader" tab
2. Click mods you want (To select multiple, CTRL + Click)
3. Click "Download Selected Mods" button
4. Wait till selected mods to be downloaded.

## Download forge and install
1. Click "Settings" tab
2. Click "Forge" from forge list
3. Click "Download Selected Forges" button
4. Wait till selected forges to be downloaded.
5. Click one forge from forge list to install
6. Click "Run Minecraft Forge Installer" button
7. Click "OK" button in Minecraft Forge installer.

## Enable/Disable mods
1. Click "Launcher" tab
2. Select mods from list. To select multiple, CTRL + mods or drag.
3. Click "Enable/Disable selected" button to enable/disable when you launch Minecraft
4. You can enable/disable one by one by toggling checkbox.
5. NOTE: Enabled/disabled mods will be applied when you click "Refresh" or "Launch Minecraft"

## Launch Minecraft
1. Checkout Minecraft executable path in settings tab before launch
2. Click Launch Minecraft Button in Launcher tab

## Customize mods list
1. Visit https://jsonblob.com/
2. Copy json from https://jsonblob.com/api/jsonBlob/56a362f7e4b01190df4b0076 as sample json.
3. Save and now you get your mods list URL
4. Copy json url https://jsonblob.com/api/jsonBlob/<your id> to "Downloadable Mods URL"
5. Click "Update" button

## Contribute to translations
* Currently it support only English, Korean.
* If you want to contribute your translation, please follow below steps
1. Fork git repository.
2. Add your strings_<your lang>.properties under mc-launcher/mc-launcher-gui/src/main/resources/.
3. Commit yours and push to your repository.
4. Send pull request.
5. Wait for merge and next release.


# Welcome your feedback
Please comments in the forums or [![Join the chat at https://gitter.im/yeoupooh/mc-launcher](https://badges.gitter.im/yeoupooh/mc-launcher.svg)](https://gitter.im/yeoupooh/mc-launcher?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge).

# Hacks

## Mod List JSON format
* Sample: http://bit.ly/1K25zrG
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

# Forge List JSON format
* Currently it is not allowed you to change in this app. 
* Sample: http://bit.ly/1NqXEPv
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


# External resources and libraries

## Application icon
* McLauncher: http://www.rw-designer.com/icon-detail/5548
* McLauncherFX: http://www.clker.com/clipart-23525.html
* ICNS file converted from https://iconverticons.com/online/

# Project Page
* Source: https://github.com/yeoupooh/mc-launcher/
* Wiki: https://github.com/yeoupooh/mc-launcher/wiki/

# Copyright
* [Copyright (c) 2016 Thomas Jinwoo Min](LICENSE)

