[![Build Status](https://travis-ci.org/yeoupooh/mc-launcher.svg)](https://travis-ci.org/yeoupooh/mc-launcher)
[![license-GPLv2](https://img.shields.io/badge/license-GPLv2-blue.svg)](http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)
[![implementation-groovy-blue](https://img.shields.io/badge/client-groovy-blue.svg)](http://www.groovy-lang.org/)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/524f850425644312b6181a44b5374df8)](https://www.codacy.com/app/thomas-min-v1/mc-launcher)
[![Codeship Status for yeoupooh/mc-launcher](https://codeship.com/projects/327094a0-9e42-0133-4bc7-36bf3814fed7/status?branch=master)](https://codeship.com/projects/127717)

# mc-launcher

[![Join the chat at https://gitter.im/yeoupooh/mc-launcher](https://badges.gitter.im/yeoupooh/mc-launcher.svg)](https://gitter.im/yeoupooh/mc-launcher?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
This allows you to download or manage Minecraft Forge and mods easily. It is written in Groovy for logic and Java swing form in intellij for view.

# Icons
* http://www.rw-designer.com/icon-detail/5548
* ICNS file converted from https://iconverticons.com/online/

# Mod List JSON format
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
