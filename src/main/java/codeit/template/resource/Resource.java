package codeit.template.resource;

import codeit.template.model.ContactTracing;
import codeit.template.model.Genome;
import codeit.template.model.InventoryManagementInput;
import codeit.template.model.InventoryManagementResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Resource {
   @RequestMapping(value = "/salad-spree",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> saladSpree(@RequestBody Map<String, Object> body) throws JSONException {
       Integer numSalads = Integer.parseInt(body.get("number_of_salads").toString());
       boolean changed = false;
       int price = Integer.MAX_VALUE;
       String jsonString = body.get("salad_prices_street_map").toString();
       JSONArray array = new JSONArray(jsonString);
       String[][] map = new String[array.length()][];

       for(int i = 0; i < array.length(); i++) {
           String[] x_coor = array.get(i).toString().replace("\"", "")
                   .replace("[", "")
                   .replace("]", "")
                   .split(",");
           map[i] = x_coor;
       }

       for (int i = 0; i < map.length; i++) {
           String[] currMap = map[i];

           for (int j = 0; j < currMap.length - numSalads; j++) {
               boolean hasX = false;
               int tempPrice = 0;

               for (int k = j; k < j + numSalads; k++) {
                   if (currMap[k].equals("X")) {
                       hasX = true;
                       j = j + k;
                       break;
                   } else {
                       int saladPrice = Integer.parseInt(currMap[k]);
                       tempPrice += saladPrice;
                   }
               }

               if (!hasX) {
                   if (tempPrice < price) {
                       price = tempPrice;
                       changed = true;
                   }
               }
           }
       }
       HashMap<String, Integer> resultMap = new HashMap<>();
       if (changed) {
           resultMap.put("result", price);
       } else {
           resultMap.put("result", 0);
       }

       return resultMap;
    }

    @RequestMapping(value = "/inventory-management",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<InventoryManagementResult> inventoryManagement (@RequestBody String body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InventoryManagementInput[] inventoryManagementInput = objectMapper.readValue(body, InventoryManagementInput[].class);
        ArrayList<InventoryManagementResult> result = new ArrayList<>();


        for(int n = 0; n < inventoryManagementInput.length; n++) {
            int i = 0;
            ArrayList<String> items = inventoryManagementInput[n].getItems();
            String searchItemName = inventoryManagementInput[n].getSearchItemName();
            ArrayList<String> resultString = new ArrayList<>();

            while(i < items.size()) {
                resultString.add(compareQuery(searchItemName, items.get(i)).trim());
                i++;
            }

            InventoryManagementResult inventoryManagementResult = new InventoryManagementResult();
            inventoryManagementResult.setSearchItemName(searchItemName);
            inventoryManagementResult.setSearchResult(resultString);

            result.add(inventoryManagementResult);
        }

        return result;
    }

    public static String compareWord(String origWord, String badWord) {

        String finalWord;
        int iterator1 = 0;
        int iterator2 = 0;

        while (iterator2 < origWord.length() -1  || iterator1 < badWord.length() -1 ) {
            while (badWord.charAt(iterator1) == origWord.charAt(iterator2)) {
                if(iterator1 < (badWord.length()-1)) {
                    iterator1++;
                }
                if(iterator2 < (origWord.length()-1)) {
                    iterator2++;
                }
                if(iterator1 == badWord.length()-1 && iterator2 == origWord.length()-1){
                    break;
                }
            }

            while ((iterator1 < badWord.length()|| iterator2 < origWord.length()-1 )&&(origWord.charAt(iterator2) != badWord.charAt(iterator1))) {

                switch (checkIfSubOrInsOrDel(badWord.substring(iterator1), origWord.charAt(iterator2), origWord.substring(iterator2), badWord.charAt(iterator1))) {
                    case "insert":
                        origWord = origWord.substring(0, iterator2+1) + "+" + badWord.charAt(iterator1) + origWord.substring(iterator2+1);
                        iterator1++;
                        iterator2+=2;
                        if (iterator1 >= badWord.length() || iterator2 >= origWord.length() ) {
                            break;
                        }
                        break;
                    case "del":
                        origWord = origWord.substring(0, iterator2) + "-" + origWord.substring(iterator2);
                        iterator2+=2;
                        if (iterator1 < badWord.length() -1 || iterator2 < origWord.length()-1 ) {
                            break;
                        }
                        break;
                    default:
                        break;
                }

            }

        }
        finalWord = checkIfSub(origWord);//
        return finalWord;

    }

    public static String checkIfSubOrInsOrDel(String badsubstring, char goodCompareChar, String goodsubstring, char badCompareChar) {
        int i = 0;
        while (badsubstring.charAt(i) != goodCompareChar) {
            i++;
            if(i==badsubstring.length()){
                break;
            }
        }
        if (checkIfDel(goodsubstring, badCompareChar).equals("del")) {
            return "del";
        } else if (checkIfIns(goodsubstring, badCompareChar).equals("ins")) {
            return "insert";
        } else {
            return "";
        }

    }

    public static String checkIfDel(String goodsubstring, char compareChar) {
        if (goodsubstring.indexOf(compareChar)!=-1) {
            return "del";
        }
        return "";
    }
    public static String checkIfIns(String goodsubstring, char badCompareChar) {
        if (goodsubstring.indexOf(badCompareChar)==-1) {
            return "ins";
        }
        return "";
    }
    public static String checkIfSub(String toCheck){

        String noChange = toCheck;

        try {
            String replaceWith = new StringBuilder().append(toCheck.charAt(toCheck.indexOf('+') + 3)).append(toCheck.charAt(toCheck.indexOf('+') + 1)).toString();
            if (Math.abs(toCheck.indexOf('+') - toCheck.indexOf('-')) == 2) {
                toCheck = toCheck.replaceAll("[+]\\w[-]\\w", (replaceWith));
                return toCheck;
            }
        } catch (Exception ignored){}
        return noChange;
    }

    public static String compareQuery(String properPhrase, String muddledPhrase) {
        String[] mainTerm = properPhrase.trim().toLowerCase().split(" ", 0);
        String[] muddledWords = muddledPhrase.trim().toLowerCase().split(" ", 0);
        String finalPhrase = "";
        int i = 0;
        while (i < mainTerm.length) {
            finalPhrase += compareWord(mainTerm[i], muddledWords[i]) + " ";
            i++;
        }

        return finalPhrase;
    }

    @RequestMapping(value = "/contact_trace",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<String> contactTrace(@RequestBody String body) throws JSONException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ContactTracing contactTracing = objectMapper.readValue(body, ContactTracing.class);

        ArrayList<Genome> genomeCluster = contactTracing.getCluster();
        ArrayList<Genome> resultGenomes = new ArrayList<>();
        ArrayList<Genome> clusterOrigin = new ArrayList<>();
        ArrayList<String> resultStrings = new ArrayList<>();
        resultGenomes.add(contactTracing.getInfected());
        Genome reference = contactTracing.getInfected();

        while (true) {
            int[] infectedToOrigin = numMatches(reference,
                    contactTracing.getOrigin());
            Genome bestMatch = reference;
            int numMatches = 0;
            int mutations = 0;

            for (int i = 0; i < genomeCluster.size(); i++) {
                int[] result = numMatches(reference,
                        genomeCluster.get(i));
                if(result[0] > numMatches) {
                    numMatches = result[0];
                    bestMatch = genomeCluster.get(i);
                    mutations = result[1];
                }
            }
            if (infectedToOrigin[0] > numMatches) {
                if(infectedToOrigin[1] > 1) {
                    reference.setMutation(true);
                } else {
                    reference.setMutation(false);
                }
                resultGenomes.add(contactTracing.getOrigin());
                break;
            } else if (infectedToOrigin[0] == numMatches) {
                if(infectedToOrigin[1] > 1) {
                    reference.setMutation(true);
                } else {
                    reference.setMutation(false);
                }
                clusterOrigin.addAll(new ArrayList<Genome>(resultGenomes));
                resultGenomes.add(bestMatch);
                clusterOrigin.add(contactTracing.getOrigin());
                break;
            } else {
                if(mutations > 1) {
                    reference.setMutation(true);
                } else {
                    reference.setMutation(false);
                }
                resultGenomes.add(bestMatch);
                genomeCluster.remove(bestMatch);
                reference = bestMatch;
            }
        }

        String resultString = "";
        for (int i = 0; i < resultGenomes.size(); i++) {
            if (i == resultGenomes.size() - 1) {
                resultString += resultGenomes.get(i).toString();
            } else {
                resultString += (resultGenomes.get(i).toString());
                resultString += (" -> ");
            }
        }
        if(!resultString.equals("")) {
            resultStrings.add(resultString);
        }

        resultString = "";
        for (int i = 0; i < clusterOrigin.size(); i++) {
            if (i == clusterOrigin.size() - 1) {
                resultString += clusterOrigin.get(i).toString();
            } else {
                resultString += (clusterOrigin.get(i).toString());
                resultString += (" -> ");
            }
        }
        if(!resultString.equals("")) {
            resultStrings.add(resultString);
        }

        return resultStrings;
    }

    private int[] numMatches (Genome genome1, Genome genome2) {
       String[] genome1String = genome1.getGenome().split("-");
       String[] genome2String = genome2.getGenome().split("-");
       int numMatched = 0;
       int mutations = 0;

       for(int i = 0; i < genome1String.length; i++) {
           numMatched += getMatch(genome1String[i], genome2String[i]);
           if(isMutated(genome1String[i], genome2String[i])) {
               mutations++;
           }
       }

       int[] result = new int[] {numMatched, mutations};

       return result;
    }

    private int getMatch (String string1, String string2) {
       char[] string1Char = string1.toCharArray();
       char[] string2Char = string2.toCharArray();
       int numMatches = 0;

       for(int i = 0; i < string1Char.length; i++) {
           if(string1Char[i] == string2Char[i]) {
               numMatches++;
           }
       }

        return numMatches;
    }

    private boolean isMutated (String string1, String string2) {
        return string1.toCharArray()[0] != string2.toCharArray()[0];
    }
}
