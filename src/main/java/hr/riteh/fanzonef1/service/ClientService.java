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

        JSONObject responseObject = new JSONObject(result);
        JSONObject data = responseObject.getJSONObject("MRData");

        return data.getInt("total");
    }


    public VoteDto getRaceWinners(int currentRaces, int currentSeason) {
        String result = restTemplate.getForObject(mainUrl + currentSeason + "/" + currentRaces + "/results" +JSON_ADDON, String.class);
        JSONObject responseObject = new JSONObject(result);
        VoteDto voteDto = new VoteDto();

        JSONObject data = responseObject.getJSONObject("MRData");
        JSONObject raceTable = data.getJSONObject("RaceTable");
        JSONArray races = raceTable.getJSONArray("Races"); // races ---> in this case only 1
        JSONObject raceResult = races.getJSONObject(0); // this race
        JSONArray resultArray = raceResult.getJSONArray("Results"); //result array

        JSONObject first = resultArray.getJSONObject(0);
        JSONObject second = resultArray.getJSONObject(1);
        JSONObject third = resultArray.getJSONObject(2);

        voteDto.setFirst(first.getJSONObject("Driver").getInt("permanentNumber"));
        voteDto.setSecond(second.getJSONObject("Driver").getInt("permanentNumber"));
        voteDto.setThird(third.getJSONObject("Driver").getInt("permanentNumber"));
        return voteDto;
    }
}
