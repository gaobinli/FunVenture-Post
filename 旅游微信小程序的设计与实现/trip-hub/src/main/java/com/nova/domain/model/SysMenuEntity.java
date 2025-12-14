package com.nova.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class SysMenuEntity {


        /**
         *  INT
         */
        private Integer id;
        /**
         * 菜单编码 VARCHAR
         */
        private String code;
        /**
         * 菜单名称 VARCHAR
         */
        private String name;
        /**
         * 上级菜单 VARCHAR
         */
        private String parentCode;
        /**
         * 菜单路径 VARCHAR
         */
        private String path;

        private List<SysMenuEntity> childrens;

        private Boolean hasChildren;

        private Integer sort;

        private String icon;
}






