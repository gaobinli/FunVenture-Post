package com.nova.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class FoodEntity {


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
         * 地址 VARCHAR
         */
        private String address;
        /**
         * 简短推荐 VARCHAR
         */
        private String shortRecommend;
        /**
         * 详情内容 TEXT
         */
        private String content;

        private Integer inventory;

}






