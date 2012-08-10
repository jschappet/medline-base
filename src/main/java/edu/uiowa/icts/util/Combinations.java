package edu.uiowa.icts.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 *
 * @author Ray
 */
public class Combinations {
    // combinations
    private  List<String> combosL = new ArrayList<String>();
    private  LinkedHashMap<String, String> combosLHM = new LinkedHashMap<String, String>();

    // logger variables
    private static Logger log = Logger.getLogger(Combinations.class);
//    private static SimpleLayout layout = new SimpleLayout();
//    private static Appender appender = new ConsoleAppender(layout);
//    private static Level logLevel = Level.DEBUG;
//    static {
//        // setup logger
//        log.setLevel(logLevel);
//        log.addAppender(appender);
//    }

    /***************************************************************************
     *                        COMBINATIONS FROM A LIST                         *
     ***************************************************************************/
    /**
     * Computes all combinations given a passed list of Strings.
     * @param elements  the list of Strings to generate combinations from
     * @return          the generated combinations
     */
    public List<String> getCombinations(List<String> elements){
        return getCombinations(elements, 1, elements.size());
    }

    /**
     * Computes all combinations up to a given size for the passed list of Strings.
     * @param elements  the list of Strings to generate combinations from
     * @param end       the total number of elements in the generated combinations
     * @return          the generated combinations
     */
    public List<String> getCombinations(List<String> elements, int end){
        return getCombinations(elements, 1, end);
    }

    /**
     * Computes the desired length of combinations given a passed list of Strings.
     * @param elements  the list of Strings to generate combinations from
     * @param start     the starting size for the combinations (min = 1)
     * @param end       the total number of elements in the generated combinations
     * @return          the generated combinations
     */
    public List<String> getCombinations(List<String> elements, int start, int end){
        // clear existing results
        combosL.clear();

        // sort the collection and generate the combinations
        Collections.sort(elements);
        for (int i = start; i <= end; i++) {
            generate("", elements, 0, i);
        }
        return combosL;
    }

    /**
     * Recursive algorithm to generate the combinations.
     * @param prefix        the ongoing conjunction of values
     * @param elements      the set of all elements
     * @param index         the current index into the list
     * @param k             the number of remaining elements to select from
     */
    private void generate(String prefix, List<String> elements, int index, int k) {
        // when it reaches 0, return the result
        if (k == 0) {
    //        log.debug(prefix.substring(1, prefix.length()));
            combosL.add(prefix.substring(1, prefix.length()));

        // else get the next value
        } else {
            for (int i = index; i < elements.size(); i++) {
                generate(prefix + "|" + elements.get(i), elements, i+1, k - 1);
            }
        }
    }

    /***************************************************************************
     *                    COMBINATIONS FROM A LINKEDHASHMAP                    *
     ***************************************************************************/
    /**
     * Computes all combinations given a passed linked hash map of strings.
     * @param elements  the LinkedHashMap of String = String to generate combinations from
     * @param sortKey   true to sort on the key field, false if value
     * @return          the generated combinations
     */
    public LinkedHashMap<String, String> getCombinations(
            LinkedHashMap<String, String> elements, boolean sortKey){
        return getCombinations(elements, sortKey, 1, elements.size());
    }

    /**
     * Computes all combinations given a passed linked hash map of strings up to size end.
     * @param elements  the LinkedHashMap of String = String to generate combinations from
     * @param end       the total number of elements in the generated combinations
     * @param sortKey   true to sort on the key field, false if value
     * @return          the generated combinations
     */
    public LinkedHashMap<String, String> getCombinations(
            LinkedHashMap<String, String> elements, boolean sortKey, int end){
        return getCombinations(elements, sortKey, 1, end);
    }

    /**
     * Computes the desired length of combinations given a passed linked hash map of strings.
     * @param elements  the LinkedHashMap of String = String to generate combinations from
     * @param sortKey   true to sort on the key field, false if value
     * @param start     the starting size for the combinations (min = 1)
     * @param end       the total number of elements in the generated combinations
     * @return          the generated combinations
     */
    public LinkedHashMap<String, String> getCombinations(
            LinkedHashMap<String, String> elements, boolean sortKey,
            int start, int end){
        // clear existing results
        combosLHM.clear();

        // sort the collection and populate a list for each element
        LinkedHashMap<String, String> lhm = sortLHMStringString(elements, sortKey);
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();

        Iterator it = lhm.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }

        for (int i = start; i <= end; i++) {
            generate("", "", keys, values, 0, i);
        }

        return combosLHM;
    }

    /**
     * Recursive algorithm to generate the combinations.
     * @param prefixK       the ongoing conjunction of key entries
     * @param prefixV       the ongoing conjunction of value entries
     * @param keys          the set of all key elements
     * @param values        the set of all value elements
     * @param index         the current index into the lists
     * @param k             the number of remaining elements to select from
     */
    private void generate(String prefixK, String prefixV, List<String> keys,
            List<String> values, int index, int k) {
        // when it reaches 0, return the result
        if (k == 0) {
            log.debug(prefixK.substring(1, prefixK.length()) + " - " + prefixV.substring(1, prefixV.length()));
            combosLHM.put(prefixK.substring(1, prefixK.length()), prefixV.substring(1, prefixV.length()));

        // else get the next value
        } else {
            for (int i = index; i < keys.size(); i++) {
                generate(prefixK + "," + keys.get(i), prefixV + "," + values.get(i),
                        keys, values, i+1, k - 1);
            }
        }
    }

    /***************************************************************************
     *                        LINKEDHASHMAP SORT METHOD                        *
     ***************************************************************************/
    /**
     * Sorts a LinkedHashMap<String, String> by key or value
     * @param map   the map to sort
     * @param key   true for key false for value
     * @return      the sorted LinkedHashMap
     */
    private static LinkedHashMap<String, String> sortLHMStringString(LinkedHashMap map, boolean key) {
        List list = new LinkedList(map.entrySet());
        // sort on key
        if (key) {
            // sort list based on comparator
            Collections.sort(list, new Comparator() {

                public int compare(Object o1, Object o2) {
                    return ((Comparable) ((Map.Entry) (o1)).getKey()).compareTo(((Map.Entry) (o2)).getKey());
                }
            });
            // sort on value
        } else {
            // sort list based on comparator
            Collections.sort(list, new Comparator() {

                public int compare(Object o1, Object o2) {
                    return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
                }
            });
        }

        // put sorted list into map again
        LinkedHashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
    
    
    

    /***************************************************************************
     *                   FOR SETS TO LARGE TO FIT INTO MEMORY                  *
     ***************************************************************************/
    private  int totalCombos = 0;
    private  String prefixState = "";
    private  int indexState = 0;
    private  int kState = -1;
    private  boolean terminate = false;
    
    /**
     * Computes the desired length of combinations given a passed list of Strings.
     * @param elements  the list of Strings to generate combinations from
     * @param start     the starting size for the combinations (min = 1)
     * @param end       the total number of elements in the generated combinations
     * @param separator the separator value (e.g., ',' or '|')
     * @param max       the maximum number of combinations to generate at once
     * @return          the generated combinations
     */
    public  List<String> getCombinations(List<String> elements, 
            int start, int end, char separator, int max){
        // clear existing results
        combosL.clear();
        int k = (kState == -1) ? start : kState+1;
        
        // sort the collection and generate the combinations
        Collections.sort(elements);
        for (int i = k; i <= end; i++) {
            generate(prefixState, elements, indexState, i, separator, max);
            //log.debug(" done with gen: " + i);
            kState = i;
            prefixState = "";
            indexState = 0;
            if(terminate) break;
        }
        if(kState == end) terminate = true;
        else terminate = false;
        return combosL;
    }
    public  boolean getTerminate(){
        return terminate;
    }
    
    /**
     * Recursive algorithm to generate the combinations up to a certain number of combinations.
     * @param prefix        the ongoing conjunction of values
     * @param elements      the set of all elements
     * @param index         the current index into the list
     * @param k             the number of remaining elements to select from
     * @param separator     the separator value (e.g., ',' or '|')
     * @param max           the maximum number of combinations to generate at once
     */
    private  void generate(String prefix, List<String> elements, 
            int index, int k, char separator, int max) {
        //log.debug(" k: " + k + ", index: " + index);
        if(totalCombos >= max){
            prefixState = prefix;
            indexState = index;
            totalCombos = 0;
            terminate = true;
            
            if(k == 0){
                //log.debug(prefix.substring(1, prefix.length()));
                combosL.add(prefix.substring(1, prefix.length()));
            }
            
            //log.debug(" print state: " + prefixState + ", " + indexState + ", " + kState);
            
        // when it reaches 0, return the result
        } else if (k == 0) {
            //log.debug(prefix.substring(1, prefix.length()));
            combosL.add(prefix.substring(1, prefix.length()));
            totalCombos++;
            
        // else get the next value
        } else {
            for (int i = index; i < elements.size(); i++) {
                generate(prefix + separator + elements.get(i), elements, i+1, k-1, separator, max);
            }
        }
    }
    
    /**
     * N!/((N-K)!K!). (N choose K+1) = N!/((N-K)!K!) * (N-K)/(K+1)
     * @param N
     * @param K
     * @return 
     */
    public  BigInteger binomial(final int N, final int K) {
        BigInteger ret = BigInteger.ONE;
        for (int k = 0; k < K; k++) {
            ret = ret.multiply(BigInteger.valueOf(N-k)).divide(BigInteger.valueOf(k+1));
        }
        return ret;
    }

    /***************************************************************************
     *                                TEST MAIN                                *
     ***************************************************************************/
    public static  void main(String[] args) {
    	Combinations c = new Combinations();
        List<String> l = new ArrayList<String>();
        l.add("F1");
        l.add("S3");
        l.add("T4");
        l.add("F2");
        log.debug("All combinations from a list:");
        c.getCombinations(l);
        log.debug("-------------");
        log.debug("One-two combinations from a list:");
        c.getCombinations(l, 2);
        log.debug("-------------");
        log.debug("Two combinations from a list:");
        c.getCombinations(l, 2, 2);
        log.debug("-------------");

        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
        lhm.put("F2","v1");
        lhm.put("S4","v2");
        lhm.put("R3","v3");
        lhm.put("Y5","v4");
        lhm.put("A1","v5");
        lhm.put("Z6","v6");
        log.debug("All combinations from a linked hash map:");
        boolean sortOnKey = true;
        c.getCombinations(lhm, sortOnKey);
        log.debug("-------------");
        log.debug("One-three combinations from a linked hash map:");
        c.getCombinations(lhm, sortOnKey, 3);
        log.debug("-------------");
        log.debug("Three combinations from a linked hash map:");
        c.getCombinations(lhm, sortOnKey, 3, 3);
        log.debug("-------------");
    }
}
