/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sort;

import java.util.Vector;

/**
 *
 * @author samisaf
 */
public class QuickSort {
    public static Vector<Integer> sort(Vector<Integer> v){
        int x = v.size() / 2;
        return sort(selectSmaller(v, x));
    }
    
    
    private static Vector<Integer> selectSmaller(Vector<Integer> v, int num){
        Vector<Integer> temp = new Vector();
        for (int i: v)
            if (i <= num) temp.add(i);
        return temp;
    }
    
    private static Vector<Integer> selectBigger(Vector<Integer> v, int num){
        Vector<Integer> temp = new Vector();
        for (int i: v)
            if (i > num) temp.add(i);
        return temp;
    }
    
}
