package com.vsu.cgcourse.math;

public abstract class AbstractMatrix<T> {

    private float[][] matrix;

    protected AbstractMatrix(int size) {
        this.matrix = new float[size][size];
    }

    protected AbstractMatrix(float[][] matrix) throws Exception {
        this.matrix = new float[matrix.length][matrix[0].length];
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                this.matrix[line][column] = matrix[line][column];
            }
        }
    }

    protected abstract AbstractMatrix<T> createMatrix(int line, int column);

    public float[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(AbstractMatrix<T> matrix) {
        this.matrix = matrix.matrix;
    }

    public float getElement(int line, int column) {
        return this.matrix[line][column];
    }

    public void setElement(int line, int column, float element) {
        this.matrix[line][column] = element;
    }

    public void unitMatrix() throws Exception {
        if (this.checkForNullMatrix()) {
            for (int line = 0; line < this.matrix.length; line++) {
                for (int column = 0; column < this.matrix[line].length; column++) {
                    if (line == column) {
                        this.matrix[line][column] = 1;
                    }
                }
            }
        } else {
            throw new Exception("You must use nullMatrix only");
        }
    }

    private boolean checkForNullMatrix() {
        for (int line = 0; line < this.matrix.length; line++) {
            for (int column = 0; column < this.matrix[line].length; column++) {
                if (this.matrix[line][column] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public AbstractMatrix<T> plus(AbstractMatrix<T> matrix) throws Exception {
        if (this.matrix.length != matrix.matrix.length || this.matrix[0].length != matrix.matrix[0].length) {
            throw new Exception("You must use matrix with same sizes");
        }
        AbstractMatrix<T> resultMatrix = createMatrix(this.matrix.length, this.matrix.length);
        for (int line = 0; line < this.matrix.length; line++) {
            for (int column = 0; column < this.matrix[line].length; column++) {
                resultMatrix.matrix[line][column] = this.matrix[line][column] + matrix.matrix[line][column];
            }
        }
        return resultMatrix;
    }

    public AbstractMatrix<T> sub(AbstractMatrix<T> matrix) throws Exception {
        if (this.matrix.length != matrix.matrix.length || this.matrix[0].length != matrix.matrix[0].length) {
            throw new Exception("You must use matrix with same sizes");
        }
        AbstractMatrix<T> resultMatrix = createMatrix(this.matrix.length, this.matrix.length);
        for (int line = 0; line < this.matrix.length; line++) {
            for (int column = 0; column < this.matrix[line].length; column++) {
                resultMatrix.matrix[line][column] = this.matrix[line][column] - matrix.matrix[line][column];
            }
        }
        return resultMatrix;
    }

    public AbstractMatrix<T> multiply(AbstractVector vector) throws Exception {
        if (this.matrix[0].length != vector.getVectorCoords().length) {
            throw new Exception("Size of vector must be equals size of matrix line");
        }
        AbstractMatrix<T> resultMatrix = createMatrix(this.matrix.length, this.matrix.length);
        for (int line = 0; line < this.matrix.length; line++) {
            for (int column = 0; column < this.matrix[line].length; column++) {
                resultMatrix.matrix[line][column] = this.matrix[line][column] * vector.getVectorCoords()[column];
            }
        }
        return resultMatrix;
    }

    public void multiply(AbstractMatrix<T> matrix) throws Exception {
        if (this.matrix.length == matrix.matrix.length) {
            for (int line = 0; line < this.matrix.length; line++) {
                if (this.matrix[line].length != matrix.matrix[line].length) {
                    throw new Exception("Size of matrix line must be equals size of this.matrix line");
                }
            }
        } else {
            throw new Exception("Size of matrix must be equals size of this.matrix");
        }
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                float var = 0F;
                for (int k = 0; k < this.matrix[i].length; k++) {
                    var += this.matrix[i][k] * matrix.matrix[k][j];
                }
                this.matrix[i][j] = var;
            }

        }
    }

    public AbstractMatrix<T> transposite() {
        AbstractMatrix<T> matrix = createMatrix(this.matrix.length, this.matrix[0].length);
        float n;
        for (int line = 0; line < this.matrix.length; line++) {
            for (int column = line + 1; column < this.matrix[0].length; column++) {
                n = this.matrix[line][column];
                this.matrix[line][column] = this.matrix[column][line];
                this.matrix[column][line] = n;
            }
        }
        matrix.matrix = this.matrix;
        return matrix;
    }

    public static float determinant(float[][] matrix) {
        float determinant = 0;
        int minus = 1;
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        for (int column = 0; column < matrix[0].length; column++) {

            determinant += (minus * matrix[0][column] * determinant(decomposition(matrix, 0, column)));
            minus = -minus;
        }
        return determinant;
    }

    public static float[][] decomposition(float[][] matrix, int x, int y) {
        float[][] resultMatrix = new float[matrix.length - 1][matrix[0].length - 1];
        int newLine = 0;
        int newColumn = 0;
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                if (line != x && column != y) {
                    resultMatrix[newLine][newColumn] = matrix[line][column];
                    newColumn++;
                }
            }
            if (line != x) {
                newLine++;
            }
            newColumn = 0;
        }
        return resultMatrix;
    }

    public AbstractMatrix<T> reverseMatrix() throws Exception {
        float determinant = determinant(this.matrix);
        if (determinant == 0) {
            throw new Exception("Determinant cant be 0");
        }
        AbstractMatrix<T> reverseMatrix = this.findAlgebraicComplements();
        reverseMatrix.transposite();
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                reverseMatrix.matrix[line][column] /= determinant;
            }
        }
        return reverseMatrix;
    }

    public AbstractMatrix<T> findAlgebraicComplements() {
        AbstractMatrix<T> resultMatrix = createMatrix(this.matrix.length, this.matrix[0].length);
        int minus = 1;
        for (int line = 0; line < this.matrix.length; line++) {
            for (int column = 0; column < this.matrix[line].length; column++) {
                resultMatrix.matrix[line][column] = minus * determinant(decomposition(this.matrix, line, column));
                minus = -minus;
            }
        }
        return resultMatrix;
    }

}
