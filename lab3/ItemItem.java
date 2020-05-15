public class ItemItem {
    private final int rows;
    private final int columns;
    private final int[][] userItemMatrix;
    private final float[][] transformedMatrix;

    public ItemItem(int rows, int columns, int[][] userItemMatrix) {
        this.rows = rows;
        this.columns = columns;
        this.userItemMatrix = userItemMatrix;
        this.transformedMatrix = Helper.transformMatrix(userItemMatrix, rows, columns, true);
    }

    public void predictItemValue(int I, int K, int J) {
        Helper.predictValue(this.userItemMatrix, this.transformedMatrix, this.rows, this.columns, I, K, J, true);
    }
}
