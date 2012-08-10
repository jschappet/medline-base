/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.icts.util;

/**
 *
 * @author Ray
 */
public class Mask {
    // need 404,970, 74^3 = 405,224, max of 15 characters for 5 combinations
    private int size = 3;
    private int max = 74;
    private char[] c = new char[max];
    // -1 so first can just be increment (no special cases)
    private int[] indices = {0,0,-1};
    
    public static void main(String args[]){
        Mask m = new Mask();
        //max = 5;
        for(int i=0; i<70; i++){
            System.out.println(m.getNext());
        }
    }
    
    public String getNext(){
        if(indices[2]+1 == max){
            indices[2] = 0;
            if(indices[1]+1 == max){
                indices[1] = 0;
                // cannot go over on 0
                indices[0]++;
            } else {
                indices[1]++;
            }
        } else {
            indices[2]++;
        }
        return "" + c[indices[0]] + c[indices[1]] + c[indices[2]];
    }
    
    public Mask(){
        c[0] = 'a';
        c[1] = 'b';
        c[2] = 'c';
        c[3] = 'd';
        c[4] = 'e';
        c[5] = 'f';
        c[6] = 'g';
        c[7] = 'h';
        c[8] = 'i';
        c[9] = 'j';
        c[10] = 'k';
        c[11] = 'l';
        c[12] = 'm';
        c[13] = 'n';
        c[14] = 'o';
        c[15] = 'p';
        c[16] = 'q';
        c[17] = 'r';
        c[18] = 's';
        c[19] = 't';
        c[20] = 'u';
        c[21] = 'v';
        c[22] = 'w';
        c[23] = 'x';
        c[24] = 'y';
        c[25] = 'z';
        c[26] = 'A';
        c[27] = 'B';
        c[28] = 'C';
        c[29] = 'D';
        c[30] = 'E';
        c[31] = 'F';
        c[32] = 'G';
        c[33] = 'H';
        c[34] = 'I';
        c[35] = 'J';
        c[36] = 'K';
        c[37] = 'L';
        c[38] = 'M';
        c[39] = 'N';
        c[40] = 'O';
        c[41] = 'P';
        c[42] = 'Q';
        c[43] = 'R';
        c[44] = 'S';
        c[45] = 'T';
        c[46] = 'U';
        c[47] = 'V';
        c[48] = 'W';
        c[49] = 'X';
        c[50] = 'Y';
        c[51] = 'Z';
        c[52] = '0';
        c[53] = '1';
        c[54] = '2';
        c[55] = '3';
        c[56] = '4';
        c[57] = '5';
        c[58] = '6';
        c[59] = '7';
        c[60] = '8';
        c[61] = '9';
        c[62] = '!';
        c[63] = '@';
        c[64] = '#';
        c[65] = '$';
        c[66] = '%';
        c[67] = '^';
        c[68] = '&';
        c[69] = '*';
        c[70] = '(';
        c[71] = ')';
        c[72] = '-';
        c[73] = '_';
    }
}
