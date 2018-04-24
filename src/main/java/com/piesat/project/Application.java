package com.piesat.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.piesat.project"})
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        GFPreHandle GFPreHandle = new GFPreHandle();
//        GFPreHandle.start();
//        IDataStoreHandler dataStoreHandler = null;
//        String path = "C:\\Users\\gersy\\Desktop\\新建文件夹 (5)";
//        String distPath = "C:\\Users\\gersy\\Desktop\\新建文件夹 (5)";
////        dataStoreHandler = DataStoreFactory.get("GF4");
////        dataStoreHandler.onStore(path);
//
//        IMultiDataTypeStoreHandler handler = new BaseMultiDataTypeStoreHandler();
//        handler.onStore(distPath);

    }

}
//@EnableDiscoveryClient
//public class Application {
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
////        SpringApplication app = new SpringApplication(Application.class);
////        app.setBannerMode(Banner.Mode.OFF);
////        app.run(args);
////        logger.info("PortalApplication is success!");
//        System.err.println("sample started. http://localhost:8080/user/test");
//    }
//
//}
