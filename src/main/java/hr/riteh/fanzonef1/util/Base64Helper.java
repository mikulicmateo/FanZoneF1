package hr.riteh.fanzonef1.util;

import hr.riteh.fanzonef1.dto.request.CredentialsDto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Helper {

    //takes Base64 encoded credentials and decodes it
    public static CredentialsDto getDecodedUsernameAndPassword(String authHeader){

        CredentialsDto credentialsDto = new CredentialsDto();
        //decode credentials
        byte[] decoded = Base64.getDecoder().decode(authHeader.substring("Basic".length()).trim());

        //save credentials as string username:password
        String usernamePassword = new String(decoded, StandardCharsets.UTF_8);

        //separate username and password
        int separatorIndex = usernamePassword.indexOf(':');
        credentialsDto.setPassword(usernamePassword.substring(separatorIndex+1));
        credentialsDto.setUsername(usernamePassword.substring(0,separatorIndex));


        return  credentialsDto;
    }
}
