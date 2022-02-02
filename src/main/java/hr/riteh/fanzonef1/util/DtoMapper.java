package hr.riteh.fanzonef1.util;

import hr.riteh.fanzonef1.dto.response.UserStandingsResponseDto;
import hr.riteh.fanzonef1.entity.User;
import org.modelmapper.ModelMapper;

public class DtoMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static UserStandingsResponseDto mapUserToUserStandingsResponseDto(User user){
      return mapper.map(user, UserStandingsResponseDto.class);
    }
}
