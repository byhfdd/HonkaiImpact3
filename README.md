下载RetroFuturaGradle缓慢可手动下载后放入
https://github.com/GTNewHorizons/RetroFuturaGradle/releases 
# 使用 RetroFuturaGradle 的示例模组

请参考 [build.gradle.kts](build.gradle.kts) 和 [settings.gradle.kts](settings.gradle.kts) 中的构建脚本。  
如有疑问，请查阅 RFG 类的 Javadoc（在 IntelliJ 中编辑 Gradle 脚本时可直接导航查看）以及 Gradle 官方用户指南。

---

## 设置开发工作区（可选，首次构建或运行时会自动完成）

```bash
Linux:   ./gradlew setupDecompWorkspace
Windows: ./gradlew.bat setupDecompWorkspace
```

> ⚠ 首次设置工作区后，请务必**刷新 IDE 的 Gradle 模型**，否则可能找不到 Minecraft 和 Forge 的类。

---

## 编译

```bash
Linux:   ./gradlew build
Windows: ./gradlew.bat build
```

---

## 运行客户端

```bash
Linux:   ./gradlew runClient
Windows: ./gradlew.bat runClient
```

可选参数：
```bash
./gradlew runClient [--debug-jvm] [--args "minecraft 启动参数"] [--username 玩家名] [--uuid 覆盖的 UUID]
```

---

## 运行服务器

```bash
Linux:   ./gradlew runServer
Windows: ./gradlew.bat runServer
```

可选参数：
```bash
./gradlew runServer [--debug-jvm] [--args nogui]
```

---

## 发布到 Maven

本地发布：
```bash
Linux:   ./gradlew publishToMavenLocal
Windows: ./gradlew.bat publishToMavenLocal
```

远程发布：
```bash
Linux:   ./gradlew publish
Windows: ./gradlew.bat publish
```