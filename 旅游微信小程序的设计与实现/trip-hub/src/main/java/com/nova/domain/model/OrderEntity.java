package com.nova.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class OrderEntity {


        /**
         * 主键 INT
         */
        private Long id;
        /**
         * 关联id INT
         */
        private Long relationId;
        /**
         * 订单类型，1 景点、2 美食、3 酒店 INT
         */
        private Integer type;

        private Long userId;

        private Integer status;
}






