package com.nova.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class CarouselEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 名称 VARCHAR
         */
        private String name;
        /**
         * 轮播图 VARCHAR
         */
        private String url;
        /**
         * 关联跳转id INT
         */
        private Integer relationId;

        private Integer sort;

}






