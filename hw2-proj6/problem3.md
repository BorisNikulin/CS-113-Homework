# Problem 3

### A) Big O Rankings (Shortest to Longest)
1. O(0)
2. O(5)
3. O(2/N) (Can be 2nd (Faster than O(5) if $N > 2/5$))
6. O(log N)
4. O(N)
5. O(NM) (Can be 4th (Faster than O(N) if $M < 1$))
7. O(N log N)
8. O(N<sup>0.5</sup>)
9. O(N<sup>1.5</sup>)
10. O(N<sup>2</sup>)
11. O(N<sup>4</sup>)
12. O(2<sup>N</sup>)
13. O(Infinity)

### B) Complexity of Code
1. O(N) since for loop
2. O(N) * O(N) = O(N<sup>2</sup>) since two for loops
3. 2 for loops but the second runs for half the total so O(N<sup>2</sup>/2) or simply O(N<sup>2</sup>)
4. O((N*N)<sup>2</sup>) = O(N<sup>4</sup>) or simply O(N<sup>2</sup>)