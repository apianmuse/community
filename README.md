## MUSE Community

## 资料
+ [完成项目](https://github.com/codedrinker/community.git)
+ [Spring guides](ttps://spring.io/guides)
+ [Spring web](ttps://spring.io/guides/gs/serving-web-content/)
+ [Spring文档](https://docs.spring.io/spring/docs/5.2.3.RELEASE/spring-framework-reference/web.html#mvc-config-interceptors)
+ [Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
+ [OkHttp](https://square.github.io/okhttp/)
+ [Meaven库](https://mvnrepository.com/)
+ [MySQL](https://www.runoob.com/mysql/mysql-tutorial.html)
+ [H2](http://www.h2database.com/html/main.html)
+ [MyBatis spring boot](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
+ [Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)
+ [HTML字符转义](https://www.w3school.com.cn/tags/html_ref_symbols.html)

## 工具
+ [Visual Paradigm](https://www.visual-paradigm.com/cn/)
+ [Flyway](https://flywaydb.org/getstarted/firststeps/mavencreate)
+ [Lombok](https://projectlombok.org/setup/maven)
+ [Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)
+ [MyBatis Generator](https://mybatis.org/generator/)

## 脚本
```sql
create table USER
(
    ID int auto_increment,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN VARCHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```



