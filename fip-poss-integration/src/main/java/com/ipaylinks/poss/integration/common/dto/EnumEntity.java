package com.ipaylinks.poss.integration.common.dto;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用枚举实体
 *
 * @author Alan
 * @date 2018/8/22 18:05
 */
public class EnumEntity {
    /**
     * 枚举code
     */
    private String code;
    /**
     * 枚举值中文名
     */
    private String name;

    /**
     * 消息
     */
    private String message;
    /**
     * 枚举描述
     */
    private String desc;
    /**
     * 枚举值英文名
     */
    private String enName;

    public static List convert(Enum[] enumValues) {
        List<EnumEntity> list = new ArrayList<>();
        for (Object obj : enumValues) {
            EnumEntity entity = new EnumEntity();
            BeanUtils.copyProperties(obj, entity);
            list.add(entity);
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public EnumEntity setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public EnumEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public EnumEntity setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getEnName() {
        return enName;
    }

    public EnumEntity setEnName(String enName) {
        this.enName = enName;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EnumEntity setMessage(String message) {
        this.message = message;
        return this;
    }
}
