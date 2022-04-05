package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import com.example.onlineshopping.onlineshoppingsystem.entities.user.EnumRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDTOResponse {
    private Long roleId;
    private EnumRole name;
}
