/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author JY
 */
public class Test {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        int[] data = {-2,2,-3,4,-1,2,1,-5,3};
   
        System.out.println(maxSubArray(data));
             
                     
    }
    
    public static int maxSubArray(int[] n) {
        // write your code
    if (n == null) {
        return 0;
    }
    
    int max = Integer.MIN_VALUE;
    int sum = 0;
    int minisum = 0;
    
    for (int i = 0; i < n.length; i++) {
        sum += n[i];
        System.out.println("sum: " + sum);
        max = Math.max(max, sum - minisum);
        System.out.println("max: " + max);
        minisum = Math.min(sum, minisum);
        System.out.println("minisum" + minisum);
        
    }
    
    return max;
    }
}
