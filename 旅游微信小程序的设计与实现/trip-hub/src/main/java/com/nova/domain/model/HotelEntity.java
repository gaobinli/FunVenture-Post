package com.nova.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class HotelEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 封面 VARCHAR
         */
        private String url;
        /**
         * 名称 VARCHAR
         */
        private String name;
        /**
         * 价格 DECIMAL
         */
        private Long price;
        /**
         * 简要推荐 VARCHAR
         */
        private String shortRecommend;
        /**
         * 地址 VARCHAR
         */
        private String address;
        /**
         * 详情内容 TEXT
         */
        private String content;

        private Integer inventory;

}






