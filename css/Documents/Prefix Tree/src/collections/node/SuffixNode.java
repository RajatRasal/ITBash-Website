package collections.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SuffixNode {

	public boolean isWord = false;
	public final char val;

	private ConcurrentHashMap<Character, SuffixNode> nextChars = new ConcurrentHashMap();
	private Lock lock = new ReentrantLock();

	public SuffixNode(char val) {
		this.val = val;
	}

	public void lock() {
		lock.lock();
	}

	public void unlock() {
		lock.unlock();
	}

	public boolean isWord() {
		isWord = true;
		return true;
	}

	public boolean isNotWord() {
		isWord = false;
		return true;
	}

	public SuffixNode selectChild(char c) {
		nextChars.putIfAbsent(c, new SuffixNode(c));
		return nextChars.get(c);
	}

	public ConcurrentHashMap<Character, SuffixNode> getNextChars() {
		return nextChars;
	}

	public int getNumberOfChildren() {
		return nextChars.size();
	}

	public List<SuffixNode> getChildren() {
		List<SuffixNode> children = new ArrayList();
		IntStream.range((int) 'a', (int) 'z')
				.forEach(i -> {
					nextChars.computeIfPresent((char) i, (k, v) -> {
						children.add(v);
						return v;
					});
				});
		return children;
	}

}
