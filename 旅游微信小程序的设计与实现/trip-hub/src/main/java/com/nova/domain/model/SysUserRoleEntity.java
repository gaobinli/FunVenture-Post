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
public class SysUserRoleEntity {


        /**
         *  INT
         */
        private Integer id;
        /**
         * 用户编码 VARCHAR
         */
        private String userCode;
        /**
         * 角色编码 VARCHAR
         */
        private String roleCode;



}






