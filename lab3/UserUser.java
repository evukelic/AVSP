public class UserUser {
    private final int rows;
    private final int columns;
    private final int[][] userItemMatrix;
    private final float[][] transformedMatrix;

    public UserUser(int rows, int columns, int[][] userItemMatrix) {
        this.rows = rows;
        this.columns = columns;
        this.userItemMatrix = userItemMatrix;
        this.transformedMatrix = Helper.transformMatrix(userItemMatrix, rows, columns, false);
    }

    public void predictUserValue(int I, int K, int J) {
        Helper.predictValue(this.userItemMatrix, this.transformedMatrix, this.rows, this.columns, I, K, J, false);
    }
}
