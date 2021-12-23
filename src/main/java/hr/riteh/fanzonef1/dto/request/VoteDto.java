package hr.riteh.fanzonef1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteDto {
    private int first;
    private int second;
    private int third;
}
