package collections;

import collections.exceptions.InvalidWordException;
import collections.node.SuffixNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCompactWordTree implements CompactWordsSet {

  public static void main(String[] args) throws InvalidWordException {
    CompactWordsSet x = new SimpleCompactWordTree();
    final String validWordsShuffled[] = {"aa", "ba", "ab", "aa", "b", "a", "aa"};
    Arrays.asList(validWordsShuffled)
        .stream().forEach(i -> {
      try {
        x.add(i);
        System.out.println();
        System.out.println(x.contains(i));
        System.out.println();
      } catch (InvalidWordException e) {
        e.printStackTrace();
      }
    });
    System.out.println(x.uniqueWordsInAlphabeticOrder());
    System.out.println(x.uniqueWordsInAlphabeticOrder());
  	/*

    System.out.println(x.contains("aab"));
    x.remove(validWordsShuffled[0]);
    System.out.println(x.uniqueWordsInAlphabeticOrder());
    System.out.println(x.size());
    */
  }

  private volatile SuffixNode root = new SuffixNode(' ');
  private AtomicInteger size = new AtomicInteger(0);
  private final Lock lock = new ReentrantLock(); //root.lock;
  private Lock rootLock = new ReentrantLock();


  public SimpleCompactWordTree() {}

  @Override
  public boolean add(String word) throws InvalidWordException {
    //lock.lock();
    try {
      CompactWordsSet.checkIfWordIsValid(word);
      rootLock.lock();
      SuffixNode node = traverseTree(word, root);
      if (node.isWord) {
        return false;
      } else {
        size.incrementAndGet();
        return node.isWord();
      }
    } finally {
      //lock.unlock();
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    //lock.lock();
    try {
      CompactWordsSet.checkIfWordIsValid(word);
      rootLock.lock();
      SuffixNode n = traverseTree(word, root); //.isNotWord();
      if (!n.isWord) {
        return false;
      } else {
        size.getAndDecrement();
        return n.isNotWord();
      }
    } finally {
      //lock.unlock();
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    //lock.lock();
    try {
      CompactWordsSet.checkIfWordIsValid(word);
      rootLock.lock();
      return traverseTree(word, root).isWord;
    } finally {
      //lock.unlock();
    }
  }

  private SuffixNode traverseTree(String word, SuffixNode node) {
  	// lock.lock();
    /*
  	node.lock();
  	try {
      if (word.length() == 1) {
        return node.selectChild(word.charAt(0));
      } else {
      	SuffixNode child = node.selectChild(word.charAt(0));
      	child.lock();
        return traverseTree(word.substring(1), child);
      }
    } finally {
  	  lock.unlock();
    }
    */

    SuffixNode curr = node;
    curr.lock();
    rootLock.unlock();
    SuffixNode parent = null;
    //curr.lock();

    /*while (true) {
  	  if (word.length() == 1) {
  	    node =
      }
    }*/

    for (int i = 0; i < word.length(); i++) {
    	parent = curr;
      curr = curr.selectChild(word.charAt(i));
      curr.lock();
      parent.unlock();
    }
    return curr;
  }

  @Override
  public int size() {
    //lock.lock();
    try {
      return size.get();
    } finally {
      //lock.unlock();
    }
  }

  @Override
  public synchronized List<String> uniqueWordsInAlphabeticOrder() {
    return findWords(root, "");
  }

  private List<String> findWords(SuffixNode node, String wordSoFar) {
    //lock.lock();
    try {
      List<String> strings = new ArrayList();

      if (node.getNumberOfChildren() == 0) {
        return strings;
      }

      List<SuffixNode> children = node.getChildren();
      for (SuffixNode c : children) {
        String newWordSoFar = wordSoFar + Character.toString(c.val);
        if (c.isWord) {
          strings.add(newWordSoFar);
        }
        final List<String> subWords = findWords(c, newWordSoFar);
        strings.addAll(subWords);
      }
      return strings;
    } finally {
      //lock.unlock();
    }
  }
}
