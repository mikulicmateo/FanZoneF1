package hr.riteh.fanzonef1.service;


import hr.riteh.fanzonef1.dto.request.VoteDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ClientService {

    private RestTemplate restTemplate = new RestTemplate();
    private final String mainUrl = "http://ergast.com/api/f1/";
    private final String JSON_ADDON = ".json";

    public int seasonRaces(int season) {
        String result = restTemplate.getForObject(mainUrl + season + JSON_ADDON, String.class);
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getInt("total");
    }


    public VoteDto getRaceWinners(int currentRaces, int currentSeason) {
        String result = restTemplate.getForObject(mainUrl + currentSeason + "/" + currentRaces + "/results" +JSON_ADDON, String.class);
        JSONObject jsonObject = new JSONObject(result);
        VoteDto voteDto = new VoteDto();
        JSONObject jsonObject1 = jsonObject.getJSONObject("RaceTable");
        JSONArray resultArray = jsonObject1.getJSONArray("Results");
        JSONObject first = resultArray.getJSONObject(0);
        JSONObject second = resultArray.getJSONObject(1);
        JSONObject third = resultArray.getJSONObject(2);

        voteDto.setFirst(first.getJSONObject("Driver").getInt("permanentNumber"));
        voteDto.setSecond(second.getJSONObject("Driver").getInt("permanentNumber"));
        voteDto.setThird(third.getJSONObject("Driver").getInt("permanentNumber"));
        return voteDto;
    }
}
