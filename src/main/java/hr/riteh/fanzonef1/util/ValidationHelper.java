package hr.riteh.fanzonef1.util;

import hr.riteh.fanzonef1.dto.request.CredentialsDto;
import hr.riteh.fanzonef1.dto.request.VoteDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ValidationHelper {

    public static boolean checkVote(VoteDto voteDto) {
        if(voteDto == null) return false;
        if(voteDto.getFirst() == 0 || voteDto.getSecond() == 0 || voteDto.getThird() == 0) return false;

        if(voteDto.getFirst() == voteDto.getSecond() || voteDto.getFirst() == voteDto.getThird()
                || voteDto.getSecond() == voteDto.getThird()) return false;

        return true;
    }

    public static boolean checkCredentialsDto(CredentialsDto credentialsDto){
        if(credentialsDto == null) return false;
        if(credentialsDto.getPassword() == null || credentialsDto.getUsername() == null) return false;
        if(credentialsDto.getUsername().equals("") || credentialsDto.getPassword().equals("")) return false;

        return true;
    }

    public static boolean checkDate(String date) {
        String pattern = "dd.MM.uuuu.";
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate dateOfBirth = LocalDate.parse(date, dtFormatter);
        LocalDate dateNow = LocalDate.now();
        return !dateNow.minusYears(18).isBefore(dateOfBirth);
    }
}
