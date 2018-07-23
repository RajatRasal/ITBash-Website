package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;

import java.util.Collections;
import java.util.Set;

public class RoundRobinScheduler implements Scheduler {

	private int chooseThreadInvokations = 0;
	private int t;

	@Override
	public int chooseThread(ConcurrentProgram program) throws DeadlockException {

		System.out.println();
		Set<Integer> enabledThreads = program.getEnabledThreadIds();
		System.out.println(enabledThreads);
		System.out.println("t : " + t);


		if (enabledThreads.size() == 0) {
			throw new DeadlockException();
		} else {
			if (chooseThreadInvokations == 0) {
				System.out.println("IF");
				t = Collections.min(enabledThreads);
			} else {
				System.out.println("ELSE");
						t = enabledThreads.stream()
						       .filter(id -> id > t)
						       .min((p1, p2) -> Integer.compare(p1, p2))
						       .orElse(Collections.min(enabledThreads));
			}
		}
		chooseThreadInvokations++;
		return t;

	}

}
