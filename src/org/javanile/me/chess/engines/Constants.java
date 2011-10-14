/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanile.me.chess.engines;

/**
 *
 * @author cicciodarkast
 */
public class Constants {
    public static final String  START_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    
    public static final int     P_NONE  = 0;
    public static final int     P_WP    = 1;
    public static final int     P_WN    = 2;
    public static final int     P_WB    = 3;
    public static final int     P_WR    = 4;
    public static final int     P_WQ    = 5;
    public static final int     P_WK    = 6;    
    public static final int     P_BP    = 7;
    public static final int     P_BN    = 8;
    public static final int     P_BB    = 9;
    public static final int     P_BR    = 10;
    public static final int     P_BQ    = 11;
    public static final int     P_BK    = 12;
        
    public static final int     MK_WPS  = 0;
    public static final int     MK_WPD  = 1;
    public static final int     MK_WPCE = 2;
    public static final int     MK_WPCW = 3;
        
    public static final int     MK_BPS  = 4;
    public static final int     MK_BPD  = 5;
    public static final int     MK_BPCE = 6;
    public static final int     MK_BPCW = 7;
    
    public static final int     MK_LNN = 8;
    public static final int     MK_LNE = 9;
    public static final int     MK_LNW = 10;    
    public static final int     MK_LSS = 11;
    public static final int     MK_LSE = 12;
    public static final int     MK_LSW = 13;    
    public static final int     MK_LEE = 14;
    public static final int     MK_LWW = 15;
        
    public static final int     MK_SIMPLE = 10000 + 1;
 
    public static final int[][] _DELTA = {
        /*MK_WPS:*/ {-1,0},
        /*MK_WPD:*/ {-2,0},
        /*MK_WPCE:*/{-1,1},
        /*MK_WPCW:*/{-1,-1},        
        /*MK_BPS:*/ {1,0},
        /*MK_BPD:*/ {2,0},
        /*MK_BPCE:*/{1,1},
        /*MK_BPCW:*/{1,-1},
        /*MK_LNN:*/ {-1,0},
        /*MK_LNE:*/ {-1,1},
        /*MK_LNW:*/ {-1,-1},
        /*MK_LSS:*/ {1,0},
        /*MK_LSE:*/ {1,1},
        /*MK_LSW:*/ {1,-1},
        /*MK_LEE:*/ {0,1},
        /*MK_LWW:*/ {0,-1}    
    };
    
    public static final int[][] _GAMMA = {
        /*P_NONE*/  {},
        /*P_WP*/    {},
        /*P_WN*/    {},
        /*P_WB*/    {MK_LNE,MK_LNW,MK_LSE,MK_LSW},
        /*P_WR*/    {MK_LNN,MK_LWW,MK_LEE,MK_LSS},
        /*P_WQ*/    {MK_LNN,MK_LWW,MK_LEE,MK_LSS,MK_LNE,MK_LNW,MK_LSE,MK_LSW},
        /*P_WK*/    {},
        /*P_BP*/    {},
        /*P_BN*/    {},
        /*P_BB*/    {MK_LNE,MK_LNW,MK_LSE,MK_LSW},
        /*P_BR*/    {MK_LNN,MK_LWW,MK_LEE,MK_LSS},
        /*P_BQ*/    {MK_LNN,MK_LWW,MK_LEE,MK_LSS,MK_LNE,MK_LNW,MK_LSE,MK_LSW},
        /*P_BK*/    {}        
    };
    
    public static final int[] _VALUE = {
        /*P_NONE*/  0,
        /*P_WP*/    100,
        /*P_WN*/    290,
        /*P_WB*/    300,
        /*P_WR*/    500,
        /*P_WQ*/    900,
        /*P_WK*/    100000,
        /*P_BP*/   -100,
        /*P_BN*/   -290,
        /*P_BB*/   -300,
        /*P_BR*/   -500,
        /*P_BQ*/   -900,
        /*P_BK*/   -100000
    };
    
    public static final int[]   RULE = {};
    
    public static int[] DELTA(char p, int mk) {
        switch(p) {
            case 'p':return _DELTA[mk];         
            case 'n':return _DELTA[mk];
            case 'b':return _DELTA[mk];         
            case 'r':return _DELTA[mk];
            case 'q':return _DELTA[mk];         
            case 'k':return _DELTA[mk];
            case 'P':return _DELTA[mk];         
            case 'N':return _DELTA[mk];
            case 'B':return _DELTA[mk];         
            case 'R':return _DELTA[mk];
            case 'Q':return _DELTA[mk];         
            case 'K':return _DELTA[mk];                
        }
        return null;
    }
    
    public static int[] GAMMA(char p) {
        switch(p) {
            case 'p':return _GAMMA[P_BP];         
            case 'n':return _GAMMA[P_BN];         
            case 'b':return _GAMMA[P_BB];         
            case 'r':return _GAMMA[P_BR];         
            case 'q':return _GAMMA[P_BQ];         
            case 'k':return _GAMMA[P_BK];         
            case 'P':return _GAMMA[P_WP];         
            case 'N':return _GAMMA[P_WN];         
            case 'B':return _GAMMA[P_WB];         
            case 'R':return _GAMMA[P_WR];         
            case 'Q':return _GAMMA[P_WQ];         
            case 'K':return _GAMMA[P_WK];               
        }
        return null;
    }
    
    public static int VALUE(char p) {
        switch(p) {
            case 'p':return _VALUE[P_BP];         
            case 'n':return _VALUE[P_BN];         
            case 'b':return _VALUE[P_BB];         
            case 'r':return _VALUE[P_BR];         
            case 'q':return _VALUE[P_BQ];         
            case 'k':return _VALUE[P_BK];         
            case 'P':return _VALUE[P_WP];         
            case 'N':return _VALUE[P_WN];         
            case 'B':return _VALUE[P_WB];         
            case 'R':return _VALUE[P_WR];         
            case 'Q':return _VALUE[P_WQ];         
            case 'K':return _VALUE[P_WK];               
        }
        return 0;
    }
}
