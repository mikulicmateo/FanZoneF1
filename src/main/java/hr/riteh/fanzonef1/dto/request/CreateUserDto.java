package hr.riteh.fanzonef1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
    private String dateOfBirth;
}
