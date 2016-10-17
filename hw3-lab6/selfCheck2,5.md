# Self-Check 2.5 Exercises p.98

1. The big-O of a get on a single-linked list is O(1) if given a node from the list, or O(n) if given an index.
2. The set is the same for get: given a node and data O(1), or given an index and data O(n).
3. The set is the same for get: given a node O(1), or given an index O(n).
4. `5 -> 10 -> 7 -> 30`
```
  int sum = 0;
  Node<Integer> nodeRef = head;
  while(nodeRef !=null)
  {
    int next = nodeRef.data;
    sum += next
    nodeRef = nodeRef.next();
  }
```
5.
  * This statement creates a new node and sets its next (probably to insert)
  * This statement removes the 2nd node after nodeRef
  * Traverses to the end of the list and adds a new node.
  * Tries to find Harry in the list and if it does adds Sally before Harry, but if Harry is not present, then it does nothing.
	

# Self-Check 2.5 Programming p.98 (just drawing the changes)

2.
```
  head -> a -> Tom -> b <- tail
  head -> a -> Tom -> b <- tail Bill -> Tom
  head -> a -> Bill -> Tom -> b <- tail
```
```
  head -> a -> Sam -> b <- tail
  head -> a -> Sam -> b <- tail Sue -> Sam
  head -> a -> Sue -> Sam -> b <- tail
```
```
  head -> a -> Bill -> Tom -> b <- tail
  head -> a -> (Bill.next) -> Tom -> b <- tail
  head -> a -> Tom -> b <- tail
```
```
  head -> a -> Sue -> Sam -> b <- tail
  head -> a -> Sue -> (Sam.next) -> b <- tail
  head -> a -> Sue -> b <- tail
```
