package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class IPcomparator implements Comparator<Domain> {
	    @Override
	    public int compare(Domain domain1, Domain domain2) {
	    	 int[] aOct = Arrays.stream(domain1.getIp().split("\\.")).mapToInt(Integer::parseInt).toArray();
	    	    int[] bOct = Arrays.stream(domain2.getIp().split("\\.")).mapToInt(Integer::parseInt).toArray();
	    	    int r = 0;
	    	    for (int i = 0; i < aOct.length && i < bOct.length; i++) {
	    	        r = Integer.compare(aOct[i], bOct[i]);
	    	        if (r != 0) {
	    	            return r;
	    	        }
	    	    }
	    	    return r;
	    	
	    }
       
}
