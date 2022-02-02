package hr.riteh.fanzonef1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserStandingsResponseDto {
    private String username;
    private String points;
}
