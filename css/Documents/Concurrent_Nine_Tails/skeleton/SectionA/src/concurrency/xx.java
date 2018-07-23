package concurrency;

import java.util.Arrays;
import java.util.List;

public class xx {
	public static void main(String[] args) {

		List<Integer> x = Arrays.asList(new Integer[]{1,2,3,4,5});
		for (Integer y : x) {
			x.remove(y);
		}
	}
}
