# Overview

This is a basic solver for Sudoku puzzles which uses backtracking. I wrote it primarily to play around with writing functional Kotlin code -- sometimes even for the expense of readability -- that's the idea about playground code.

## Build

This is a standard gradle project. Use `gradle build`.

## Specify Sudoku to solve

For now, the filename has to be defined via the `main` method.

### File format

Quoting `Sudoku.read()`:

```kotlin
/**
 * Read a Sudoku input file by parsing the first 81 digits it can
 * read, ignoring all non-digit characters. The number 0 depicts
 * an empty cell.
 */
```
