// data that is passed between client and server
package com.example.awesomitychallenge.dto;
import com.example.awesomitychallenge.entities.Role;
import jakarta.validation.constraints.*;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

    private Long id;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Role cannot be blank")
    private Role role;

}

