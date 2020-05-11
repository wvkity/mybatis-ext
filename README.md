# mybatis扩展框架

## 介绍
<p align="left">
  欢迎使用mybatias扩展框架，为简化开发工作、提高生产率而生，提供更丰富的API
</p>
<p align="left">
  <a href="https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.wvkity">
    <img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.wvkity/mybatis-core">
  </a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

## 使用示例
请访问项目: [mybatis-ext-example](https://gitee.com/wvkit/mybatis/tree/master/mybatis-spring-boot-starter-example)

## MyBatis-Ext是什么?
* MyBatis-Ext是在原生MyBatis的基础上做增强扩展，不影响任何原生的使用，尽可能快速方便集成MyBatis。
* MyBatis-Ext提供了`单表基础数据`的`CURD`操作以及`批量保存数据`的操作，除此之外也支持联表查询、子查询、子查询联表查询等查询方式，尽可能提供更全面的API。

## 支持的数据库
* mysql、mariadb、oracle、sqlserver、db2、h2、hsql、sqlite 、 postgresql

## 使用环境
`SpringBoot`的发展趋势已经势如破竹，这里只做`SpringBoot`集成展示，当然也支持`Spring MVC`
* JDK1.8以上版本
* SpringBoot2.X以上版本(1.X暂未测试)

## 安装
安装相对比较简单，项目添加对应依赖即可。
* 使用`Maven`构建项目方式
  ```
    <dependency>
        <groupId>com.wvkity</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${lastVersion}</version>
    </dependency>
  ```
* 使用`Gradle`构建项目方式
  ```
    implementation 'com.wvkity:mybatis-spring-boot-starter:$lastVersion'
  ```
## 核心功能
### 代码生成器
**目前还在开发中，敬请期待...**
   




