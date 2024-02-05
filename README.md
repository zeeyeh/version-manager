### 说明
使用Spring Boot 3 + Spring Data Jpa + Redis技术栈的程序后端版本及授权管理后端程序
### 使用方法
1. 克隆本仓库
   '''shell
   git clone https://github.com/zeeyeh/version-manager
   '''
2. 进入目录
  '''shell
   cd version-manager
  '''
3. 打包项目
   '''shell
   mvn clean build
   '''
4. 进入目录
   '''shell
   cd build && cd libs
   '''
5. 创建并修改配置文件
   '''shell
   touch application.yml
   '''
6. 复制下面代码到'application.yml'文件中
   '''yaml
     server:
      port: 3650
    
      spring:
        application:
          name: Version Manager
        datasource:
          url: jdbc:mysql://localhost:3306/versionManager?useUnicode=true&characterEncoding=utf-8&useSSL=false
          username: versionManager
          password: versionManager
          driver-class-name: com.mysql.cj.jdbc.Driver
          type: com.zaxxer.hikari.HikariDataSource
          hikari:
            minimum-idle: 5
            maximum-pool-size: 10
            auto-commit: true
            idle-timeout: 30000
            pool-name: versionManager
            max-lifetime: 2000000
            connection-timeout: 30000
            connection-test-query: SELECT 1
        data:
          redis:
            database: 0
            host: localhost
            port: 6379
            password: root
        jpa:
          database: mysql
          show-sql: true
          hibernate:
            ddl-auto: update
          database-platform: org.hibernate.dialect.MySQLDialect
          properties:
            hibernate:
              dialect: org.hibernate.dialect.MySQLDialect
              naming:
                implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
      #          physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      token:
        key: AFb7U8z91l08NVqeOzZ
      key:
        public: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCl5PZ5LCIaG4MGRHBbwY50jyqazlkoSGBbeFki0KX/mq+Z6H6km6ODmIw4v8Gwvo6UjSAOivM+vm9RzO5vG8oQQeW2ipAemECKOjr65WxPg0NLqt504rFx1NWgnUXdNDC/TNyvaUEqVm24oJwVm366xz8TQ8kntPn2SBYqbnfMaQIDAQAB
        private: MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKXk9nksIhobgwZEcFvBjnSPKprOWShIYFt4WSLQpf+ar5nofqSbo4OYjDi/wbC+jpSNIA6K8z6+b1HM7m8byhBB5baKkB6YQIo6OvrlbE+DQ0uq3nTisXHU1aCdRd00ML9M3K9pQSpWbbignBWbfrrHPxNDySe0+fZIFipud8xpAgMBAAECgYAS4+udCLApa5CT73R2k6fLu1xinF9SqEswn0JR0JMryUrNqnJ2qV0JPcmvJ0bAVYfu1ShyqifgsSwD6I4OC4q1TWOqEH9ohaXaehUPbxDceu+h/spPK9kHfLHL1rosgdwFA1HJARseNfMhmanDC1RkwY8WYoTNDoyzy9UJer8c8QJBAOMte5HLY9Cqpo0IdQ2C/jPfapQC6RbUfHUPN2LR5MTDbE1g4gPufnskduLbR0fVLQFru40fXhRIAJdLe+vfxNMCQQC68Q6CElA3vzuiazuPLTWlJYFdybttivi+0mwL0mbNQ9UCopPk+cdPGLKY6NlP+g0WmwpEtcklmPOdL9tLu5RTAkEAxL/kcE1dQiA5pJV5gt07OUO6czveEXav31XxWvV7kunJR26r8EnCYvYevLS6lDzNAJkEUuGiwh/l2yJ1zb/8HwJAePs6RWluqrVC9bjqIZ3Dgu5Dy5ubhagTlQL+06PFzf+hIgRvLBeOKh00sAq5YK3VvJR3z8HJvGBBALAQ/vEVawJBAMwqgCz2F8bG9UEm+AJZ4YEcIsFBQ+digO3F8peGS2bjFFZZARB/xd99MwelTWphox+4Mg5lQxtx5bU5C9Yc0Yo=
      
      redis-token:
        host: localhost
        port: 6379
        password: root
        database: 0
   '''
7. 启动程序
   '''shell
   java -jar --spring.config.location=application.yml {程序名称}-{程序版本}.jar
   '''
### 备注
严禁私自修改，二次售卖分发
