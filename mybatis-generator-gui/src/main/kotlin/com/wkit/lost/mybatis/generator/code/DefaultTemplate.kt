package com.wkit.lost.mybatis.generator.code

import com.wkit.lost.mybatis.generator.utils.FileUtil

enum class DefaultTemplate(val value: String) {
    
    Entity("template/lombok/system/Entity.java.vm"),
    DTOEntity("template/lombok/DtoEntity.java.vm"),
    Mapper("template/system/Mapper.java.vm"),
    Service("template/lombok/system/Service.java.vm"),
    ServiceImpl("template/lombok/system/ServiceImpl.java.vm"),
    Controller("template/lombok/system/Controller.java.vm"),
    MapperFile("template/lombok/system/Mapper.xml.vm");
    
    fun getFilePath(dir:String): String {
        return dir + FileUtil.SLASH + value
    }
}