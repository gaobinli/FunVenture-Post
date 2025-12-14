package com.nova.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ScenicSpotEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 名称 VARCHAR
         */
        private String name;
        /**
         * 景点导航图 VARCHAR
         */
        private String url;
        /**
         * 景点地址 VARCHAR
         */
        private String address;
        /**
         * 景点介绍 TEXT
         */
        private String introduce;

        private Integer price;

        private String shortRecommend;

        private String coverUrl;

        private Integer inventory;
}






