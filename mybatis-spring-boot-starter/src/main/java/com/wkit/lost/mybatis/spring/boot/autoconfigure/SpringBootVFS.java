package com.wkit.lost.mybatis.spring.boot.autoconfigure;

import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpringBootVFS extends VFS {

    private final ResourcePatternResolver resourceResolver;

    public SpringBootVFS() {
        this.resourceResolver = new PathMatchingResourcePatternResolver();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<String> list(URL url, String forPath) throws IOException {
        Resource[] resources = resourceResolver.getResources("classpath*:" + forPath + "/**/*.class");
        return Stream.of(resources).map(resource -> preserveSubpackageName(resource, forPath)).collect(Collectors.toList());
    }

    private static String preserveSubpackageName(final Resource resource, final String rootPath) {
        try {
            URI uri = resource.getURI();
            final String uriStr = uri.toString();
            final int start = uriStr.indexOf(rootPath);
            return uriStr.substring(start);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
