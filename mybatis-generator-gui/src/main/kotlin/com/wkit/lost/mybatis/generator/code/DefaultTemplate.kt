package com.wkit.lost.mybatis.generator.code

import com.wkit.lost.mybatis.generator.utils.FileUtil

enum class DefaultTemplate(val value: String) {
    
    Entity("template/lombok/system/Entity.java.vm"),
    DTOEntity("template/lombok/DtoEntity.java.vm"),
    Mapper("template/system/Mapper.java.vm"),
    Service("template/system/Service.java.vm"),
    ServiceImpl("template/system/ServiceImpl.java.vm"),
    Controller("template/system/Controller.java.vm"),
    MapperFile("template/system/Mapper.xml.vm"),
    Log4j2File("template/system/log4j2.xml.vm"),
    ApplicationFile("template/system/Application.java.vm"),
    ApplicationYmlFile("template/system/application.yml.vm"),
    GradleFile("template/system/build.gradle.vm");
    
    fun getFilePath(dir:String): String {
        return dir + FileUtil.SLASH + value
    }
}