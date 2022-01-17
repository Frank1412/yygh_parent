package edu.rutgers.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Shouzhi Fang(frank)
 * @create: 2022-01-15 23:07
 * @description:
 */

@Configuration
@MapperScan("edu.rutgers.yygh.hosp.mapper")
public class HospConfig {
}
