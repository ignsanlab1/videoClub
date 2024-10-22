package com.example.videoClub.domain.dto.response;

import com.example.videoClub.domain.validators.ValidateAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@ValidateAge
public class UserDto {
    private Long id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "Password must be at least 6 characters long, " +
                    "and include at least one uppercase letter, one lowercase letter, and one number")
    private String password;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Birthday cannot be null")
    private LocalDate birth;
}
