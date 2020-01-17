## MUSE Community

## 资料
+ [完成项目](https://github.com/codedrinker/community.git)
+ [Spring文档](ttps://spring.io/guides)
+ [Spring web](ttps://spring.io/guides/gs/serving-web-content/)
+ [Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
+ [OkHttp](https://square.github.io/okhttp/)
+ [Meaven库](https://mvnrepository.com/)
+ [MySQL](https://www.runoob.com/mysql/mysql-tutorial.html)
+ [H2](http://www.h2database.com/html/main.html)
+ [MyBatis spring boot](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
+ [Flyway](https://flywaydb.org/getstarted/firststeps/mavencreate table USER
                                                           (
                                                               ID           INTEGER default NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_E09166CE_1D90_4E66_A49C_2A994953B9AB" auto_increment,
                                                               ACCOUNT_ID   VARCHAR(100),
                                                               NAME         VARCHAR(50),
                                                               TOKEN        CHAR,
                                                               GMT_CREATE   BIGINT,
                                                               GMT_MODIFIED BIGINT,
                                                               constraint USER_PK
                                                                   primary key (ID)
                                                           );)
## 工具
+ [Visual Paradigm](https://www.visual-paradigm.com/cn/)

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



