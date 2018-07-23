package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null || word.length() == 0) {
      throw new InvalidWordException("Word is invalid");
    }
    final int a = 'a';
    final int z = 'z';
    for (int i : word.chars().toArray()) {
      if (Integer.compare(i, a) == -1 || Integer.compare(i, z) == 1) {
        throw new InvalidWordException("Word is invalid");
      }
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
