package aoc;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadLeaderboard {
    public static void main(String[] args) throws Exception {
        final JSONParser parser = new JSONParser();
//        final File inputFile = new File("C:\\Users\\sramkrishna\\OneDrive - FactSet\\Documents\\CentralAuthWork\\AoC\\Leaderboard\\179122.json");
        final File inputFile = new File("C:\\Users\\sramkrishna\\OneDrive - FactSet\\Documents\\CentralAuthWork\\AoC\\Leaderboard\\33637.json");
//        final File inputFile = new File("C:\\Users\\sramkrishna\\OneDrive - FactSet\\Documents\\CentralAuthWork\\AoC\\Leaderboard\\2018-33637.json");
        JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(inputFile)));

        JSONObject obj = (JSONObject) jsonObject.get("members");
        Set<Map.Entry> memberEntries = obj.entrySet();

        Map<String, Map<Long, String>> dayToUserTimings = new HashMap<>();
        Map<String, Map<String, Long>> userTimingsByUserId = new HashMap<>();

        for (Map.Entry entry : memberEntries) {
            JSONObject userDetails = (JSONObject) entry.getValue();
            String userId = (String) userDetails.get("name");

            if (userId == null) {
                userId = (String) entry.getKey();
            }

            JSONObject userLevelDetails = (JSONObject) userDetails.get("completion_day_level");

            Set<Map.Entry> levelDetailEntries = userLevelDetails.entrySet();

            for (Map.Entry levelDetail : levelDetailEntries) {
                String day = (String) levelDetail.getKey();
                JSONObject puzzlePartsObject = (JSONObject) levelDetail.getValue();

                Set<Map.Entry> puzzlePartsEntries = puzzlePartsObject.entrySet();

                for (Map.Entry puzzlePartDetails : puzzlePartsEntries) {
                    String puzzlePart = (String) puzzlePartDetails.getKey();
                    JSONObject object = (JSONObject) puzzlePartDetails.getValue();

                    String mapKey = day + puzzlePart;
                    Map<Long, String> userTimingsMap = dayToUserTimings.get(mapKey);

                    if (userTimingsMap == null) {
                        userTimingsMap = new TreeMap<>();
                        dayToUserTimings.put(mapKey, userTimingsMap);
                    }

                    String completionEpochTime = (String) object.get("get_star_ts");

                    if (completionEpochTime == null) {
                        continue;
                    }

                    Long epochTime = Long.parseLong(completionEpochTime);
                    userTimingsMap.put(epochTime, userId);
                }

                for (Map.Entry puzzlePartDetails : puzzlePartsEntries) {
                    String puzzlePart = (String) puzzlePartDetails.getKey();
                    JSONObject object = (JSONObject) puzzlePartDetails.getValue();

                    String mapKey = day + puzzlePart;
                    Map<String, Long> userTimingsMap = userTimingsByUserId.get(mapKey);

                    if (userTimingsMap == null) {
                        userTimingsMap = new HashMap<>();
                        userTimingsByUserId.put(mapKey, userTimingsMap);
                    }

                    String completionEpochTime = (String) object.get("get_star_ts");

                    if (completionEpochTime == null) {
                        continue;
                    }

                    Long epochTime = Long.parseLong(completionEpochTime);
                    userTimingsMap.put(userId, epochTime);
                }
            }
        }

        String day = "25";
        String part1 = "1";
        String part2 = "2";

        Map<Long, String> userTimings = dayToUserTimings.get(day + part1);
        System.out.println("Day " + day + " Part " + part1);

        for (Map.Entry<Long, String> timingsEntry : userTimings.entrySet()) {
            Date time = new Date(timingsEntry.getKey() * 1000);

            System.out.println(timingsEntry.getValue() + " - " + time.toInstant().atZone(ZoneId.of("America/New_York")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        userTimings = dayToUserTimings.get(day + part2);
        System.out.println();
        System.out.println("Day " + day + " Part " + part2);

        for (Map.Entry<Long, String> timingsEntry : userTimings.entrySet()) {
            Date time = new Date(timingsEntry.getKey() * 1000);

            System.out.println(timingsEntry.getValue() + " - " + time.toInstant().atZone(ZoneId.of("America/New_York")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        Map<String, Long> part1UserTimings = userTimingsByUserId.get(day + part1);
        Map<String, Long> part2UserTimings = userTimingsByUserId.get(day + part2);

        System.out.println();
        System.out.println("Time between part 1 and part 2 on Day " + day);

        Map<Long, String> differenceMap = new TreeMap<>();

        for (Map.Entry<String, Long> timingsEntry : part1UserTimings.entrySet()) {
            String user = timingsEntry.getKey();

            if (part2UserTimings.get(user) == null) {
                continue;
            }

            long difference = part2UserTimings.get(user) - timingsEntry.getValue();
            differenceMap.put(difference, user);
        }

        for (Map.Entry<Long, String> differenceEntry : differenceMap.entrySet()) {
            System.out.println(differenceEntry.getValue() + " - " + differenceEntry.getKey());
        }
    }
}
