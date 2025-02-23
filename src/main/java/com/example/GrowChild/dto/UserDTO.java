
package com.example.GrowChild.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private String user_id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String gender;
    private String address;
    private String roleName;
    private boolean isDelete;


}
