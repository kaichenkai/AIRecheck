package com.seemmo.airecheck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//使用MapperScan批量扫描所有的Mapper接口
@MapperScan({"com.seemmo.airecheck.dataGate.mapper",
             "com.seemmo.airecheck.recheckSearch.mapper"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
