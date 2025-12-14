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
public class SysRoleMenuEntity {


        /**
         *  INT
         */
        private Integer id;
        /**
         * 角色编码 VARCHAR
         */
        private String roleCode;
        /**
         * 菜单编码 VARCHAR
         */
        private String menuCode;



}






