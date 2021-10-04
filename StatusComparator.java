package application;

import java.util.Comparator;

public class StatusComparator implements Comparator<Domain> {
	    @Override
	    public int compare(Domain domain1, Domain domain2) {
	       return domain1.getStatus()- domain2.getStatus();
	    }
	}



