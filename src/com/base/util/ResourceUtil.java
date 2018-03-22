package com.base.util;

import java.io.IOException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourceUtil
{
  private static ResourceLoader rloader = new DefaultResourceLoader();

  private static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

  public static Resource getResource(String path)
  {
    return rloader.getResource(path);
  }

  public static Resource[] getResources(String path)
    throws IOException
  {
    return resolver.getResources(path);
  }
}