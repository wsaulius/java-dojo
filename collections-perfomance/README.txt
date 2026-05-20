Fibonacci functional interface demo

Contents:
- Common functional interface: FibonacciOperation<T extends Number>
- Sequence wrapper: FibonacciSequence extends Number
- Class-based implementation for full sequence
- Class-based implementation for nth number
- IoC-style runner with constructor injection
- Main demo class showing replacement of one dependency with a lambda

Compile:
  javac -d out $(find src/main/java -name "*.java")

Run:
  java -cp out org.example.FunctionalInterfaceDemo 10

Notes:
- This demo uses the convention where n=1 gives 0, n=2 gives 1, n=3 gives 1.
- FibonacciSequence extends Number only to satisfy the generic bound T extends Number.
