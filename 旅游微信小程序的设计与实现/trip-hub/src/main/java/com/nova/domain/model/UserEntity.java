package com.nova.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class UserEntity {

        /**
         * ID INT
         */
        private Integer id;

        private Boolean enable;

        private String avatar;

        private Integer balance;
}






