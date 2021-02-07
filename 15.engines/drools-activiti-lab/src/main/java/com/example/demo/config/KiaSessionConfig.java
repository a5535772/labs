package com.example.demo.config;


import java.io.File;
import java.io.IOException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Created by Youdmeng on 2020/1/7 0007.
 */
@Configuration
public class KiaSessionConfig {

    private static final String ROOT_RULES_PATH = "drools-rules";

    @Bean
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        for (Resource file : getRuleFiles()) {
            String[] currentPath = file.getFile().getAbsolutePath().split(ROOT_RULES_PATH);
            if (currentPath == null || currentPath.length != 2) {
                throw new IOException("");
            }
            kieFileSystem.write(ResourceFactory.newClassPathResource(
                    ROOT_RULES_PATH + file.getFile().getAbsolutePath().split(ROOT_RULES_PATH)[1], "UTF-8"));
        }
        return kieFileSystem;
    }

    private Resource[] getRuleFiles() throws IOException {

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        final Resource[] resources =
                resourcePatternResolver.getResources("classpath*:" + ROOT_RULES_PATH + File.separator + "**/*.*");
        return resources;

    }

    @Bean
    public KieContainer kieContainer() throws IOException {

        final KieRepository kieRepository = getKieServices().getRepository();
        kieRepository.addKieModule(new KieModule() {
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });

        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());

    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

}


