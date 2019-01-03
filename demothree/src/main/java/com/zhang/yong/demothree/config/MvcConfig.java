package com.zhang.yong.demothree.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.ibeetl.starter.BeetlTemplateCustomize;
import com.zhang.yong.demothree.ext.DateFormatExt;
import com.zhang.yong.demothree.ext.ShiroExt;
import com.zhang.yong.demothree.tag.PermissionButtonTag;
import com.zhang.yong.demothree.tag.PermissionTag;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.beetl.core.GroupTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;
import static com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;


    @Bean
    public MappingJackson2HttpMessageConverter MappingJsonpHttpMessageConverter() {
        /** 解决Spring Mvc使用Jackson进行json转对象时，遇到的字符串转日期的异常处理
         *  yyyy-mm-dd HH:mm:ss 的日期字符串无法转为java.util.Date日期对象 */
        ObjectMapper mapper = jackson2ObjectMapperBuilder.build();
        // ObjectMapper为了保障线程安全性，里面的配置类都是一个不可变的对象
        // 所以这里的setDateFormat的内部原理其实是创建了一个新的配置类
        DateFormat dateFormat = mapper.getDateFormat();
        mapper.setDateFormat(new DateFormatExt(dateFormat));
        return new MappingJackson2HttpMessageConverter(mapper);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        /** 解决默认设置MediaType.ALL 导致的Content-type 不能为"*"报错。*/
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_OCTET_STREAM,
                MediaType.APPLICATION_PDF,MediaType.APPLICATION_RSS_XML, MediaType.APPLICATION_XHTML_XML,
                MediaType.APPLICATION_XML,MediaType.IMAGE_GIF,MediaType.IMAGE_JPEG,MediaType.IMAGE_PNG,
                MediaType.TEXT_EVENT_STREAM,MediaType.TEXT_HTML,MediaType.TEXT_MARKDOWN,MediaType.TEXT_PLAIN,MediaType.TEXT_XML));
        //序列化配置
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                QuoteFieldNames,// 输出key时是否使用双引号
//                WriteMapNullValue,// 是否输出值为null的字段
                WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null
                WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null
                WriteNullStringAsEmpty,//字符类型字段如果为null,输出为"",而非null
                //WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null
                //WriteNullStringAsEmpty,// null String不输出
                //WriteMapNullValue,//null String也要输出
                //WriteDateUseDateFormat,//Date的日期转换器
                DisableCircularReferenceDetect//禁止循环引用
        );
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }
    @Bean
    public BeetlTemplateCustomize beetlTemplateCustomize(){
        return (GroupTemplate groupTemplate) -> {
            groupTemplate.registerFunctionPackage("shiro",ShiroExt.getInstance());
            groupTemplate.registerTag("PermissionTag",PermissionTag.class);
            groupTemplate.registerTag("PermissionButtonTag",PermissionButtonTag.class);
        };
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 注册MyBatis分页插件PageHelper
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
