package hr.riteh.fanzonef1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserDto {
    private String username;
    private String email;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
}
