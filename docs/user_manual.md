# 使用手册

## 运行指南

### 设备模拟器

设备模拟器在`client_simulator`目录下：

<img src="./user_manual.assets/image-20231210173715961.png" alt="image-20231210173715961" style="zoom:50%;" />

1. 代码编译 `mvn clean package`
2. 运行 `java -jar iotclient-1.0.0.jar`

### 应用

导航到`src`目录，然后`bash deploy.sh`，这个脚本只支持MacOS 和 Linux

![image-20231210173944836](./user_manual.assets/image-20231210173944836.png)

部署成功的话应该是长这样的：

![image-20231210175250304](./user_manual.assets/image-20231210175250304.png)

不确定的话`docker ps`检查一下确保5个容器都在运行中

![image-20231210174216106](./user_manual.assets/image-20231210174216106.png)

到浏览器输入http://localhost:4200

![image-20231210174300564](./user_manual.assets/image-20231210174300564.png)

如果看到此画面，说明应该是成功的了。

### 如果要关闭或删除

直接`docker-compose down`就行了

![image-20231210175601907](./user_manual.assets/image-20231210175601907.png)



## 注册/登录

在浏览器输入前端的网址，会自动跳转到验证服务器的页面

<img src="./user_manual.assets/image-20231210155145573.png" alt="image-20231210155145573" style="zoom:33%;" />

### 登录

![image-20231210155308253](./user_manual.assets/image-20231210155308253.png)

### 注册

<img src="./user_manual.assets/image-20231210163134243.png" alt="image-20231210163134243" style="zoom: 33%;" />

## 主页

![image-20231210171747647](./user_manual.assets/image-20231210171747647.png)

![image-20231210171940286](./user_manual.assets/image-20231210171940286.png)

![image-20231210164142800](./user_manual.assets/image-20231210164142800.png)

## 设备管理

### 设备查询

![image-20231210164435659](./user_manual.assets/image-20231210164435659.png)

### 创建设备

![image-20231210170058269](./user_manual.assets/image-20231210170058269.png)

![image-20231210170139059](./user_manual.assets/image-20231210170139059.png)

![image-20231210164724958](./user_manual.assets/image-20231210164724958.png)

### More

![image-20231210171056557](./user_manual.assets/image-20231210171056557.png)

### 修改设备

<img src="./user_manual.assets/image-20231210170246385.png" alt="image-20231210170246385" style="zoom:33%;" />

## Statistics页面

![image-20231210170750147](./user_manual.assets/image-20231210170750147.png)

### 选项

<img src="./user_manual.assets/image-20231210171128807.png" alt="image-20231210171128807" style="zoom:50%;" />

## 关于

![image-20231210171204139](./user_manual.assets/image-20231210171204139.png)

## Personal Info

![image-20231210171227535](./user_manual.assets/image-20231210171227535.png)

