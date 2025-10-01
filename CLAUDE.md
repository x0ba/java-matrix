# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Java matrix operations library implementing common linear algebra operations. Created as a learning project for brushing up on Java and matrix mathematics (MATH 18 at UCSD).

## Building and Running

This is a simple Java project with no build system (Maven/Gradle). Compile and run directly:

```bash
# Compile all Java files
javac *.java

# Run the interactive matrix calculator (recommended for students)
java MatrixCalculator

# Run the main example program (demonstrations)
java Main

# Run the test program (simple tests)
java Test
```

## Code Architecture

### Core Components

- **Matrix.java**: The main matrix library class (553 lines)
  - Core data structure: `double[][] data` with `rows` and `cols` dimensions
  - Numerical precision: `EPSILON = 1e-10` used throughout for floating-point comparisons

- **MatrixCalculator.java**: Interactive CLI for linear algebra students (600+ lines)
  - User-friendly menu-driven interface with comprehensive error handling
  - Named matrix storage system using HashMap
  - All Matrix operations accessible via numbered menu
  - Built-in help system explaining linear algebra concepts
  - Designed for students with minimal programming experience

- **Main.java**: Comprehensive examples demonstrating all matrix operations
  - 9 examples covering basic operations, multiplication, transpose, determinant, trace, RREF, solving linear systems, eigenvalues, and identity matrices

- **Test.java**: Simple test file for basic matrix creation and display

### Matrix Operations Implementation

**Basic Operations** (add, subtract, scale, multiply, transpose):
- Implemented as straightforward iterative algorithms
- All return new Matrix instances (immutable design)
- Dimension checking with IllegalArgumentException

**Advanced Operations**:
- **RREF** (Matrix.java:208-265): Gauss-Jordan elimination with pivot selection and near-zero cleanup
- **Determinant** (Matrix.java:273-314): LU decomposition with row pivoting
- **Eigenvalues** (Matrix.java:341-376): QR algorithm with up to 1000 iterations
- **Solve** (Matrix.java:386-442): Gaussian elimination with back substitution for Ax = b systems

**QR Decomposition** (Matrix.java:476-515):
- Private helper method using Gram-Schmidt orthogonalization
- Used by eigenvalue computation
- Returns internal QRDecomposition class

### Key Implementation Details

- All matrix operations validate dimensions before proceeding
- Row operations use array swapping for efficiency (no copying)
- Near-zero values cleaned up after RREF to avoid floating-point artifacts
- Determinant returns 0.0 if matrix is singular
- Eigenvalue computation may not converge for all matrices (iterative QR algorithm)
