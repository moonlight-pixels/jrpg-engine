package com.github.jaystgelais.jrpg.map.actor;

public final class SpriteSetData {
    private static final int DEFAULT_UP_FACING_ROW_INDEX = 3;
    private static final int DEFAULT_RIGHT_FACING_ROW_INDEX = 2;
    private static final int DEFAULT_DOWN_FACING_ROW_INDEX = 0;
    private static final int DEFAULT_LEFT_FACING_ROW_INDEX = 1;
    private static final int DEFAULT_STANDING_COLUMN_INDEX = 1;

    private final String spriteSheetPath;
    private final int columns;
    private final int rows;
    private int upFacingRowIndex = DEFAULT_UP_FACING_ROW_INDEX;
    private int rightFacingRowIndex = DEFAULT_RIGHT_FACING_ROW_INDEX;
    private int downFacingRowIndex = DEFAULT_DOWN_FACING_ROW_INDEX;
    private int leftFacingRowIndex = DEFAULT_LEFT_FACING_ROW_INDEX;
    private int upFacingStandingIndex = DEFAULT_STANDING_COLUMN_INDEX;
    private int rightFacingStandingIndex = DEFAULT_STANDING_COLUMN_INDEX;
    private int downFacingStandingIndex = DEFAULT_STANDING_COLUMN_INDEX;
    private int leftFacingStandingIndex = DEFAULT_STANDING_COLUMN_INDEX;

    public SpriteSetData(final String spriteSheetPath, final int columns, final int rows) {
        this.spriteSheetPath = spriteSheetPath;
        this.columns = columns;
        this.rows = rows;
    }

    public String getSpriteSheetPath() {
        return spriteSheetPath;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getUpFacingRowIndex() {
        return upFacingRowIndex;
    }

    public void setUpFacingRowIndex(final int upFacingRowIndex) {
        this.upFacingRowIndex = upFacingRowIndex;
    }

    public int getRightFacingRowIndex() {
        return rightFacingRowIndex;
    }

    public void setRightFacingRowIndex(final int rightFacingRowIndex) {
        this.rightFacingRowIndex = rightFacingRowIndex;
    }

    public int getDownFacingRowIndex() {
        return downFacingRowIndex;
    }

    public void setDownFacingRowIndex(final int downFacingRowIndex) {
        this.downFacingRowIndex = downFacingRowIndex;
    }

    public int getLeftFacingRowIndex() {
        return leftFacingRowIndex;
    }

    public void setLeftFacingRowIndex(final int leftFacingRowIndex) {
        this.leftFacingRowIndex = leftFacingRowIndex;
    }

    public int getUpFacingStandingIndex() {
        return upFacingStandingIndex;
    }

    public void setUpFacingStandingIndex(final int upFacingStandingIndex) {
        this.upFacingStandingIndex = upFacingStandingIndex;
    }

    public int getRightFacingStandingIndex() {
        return rightFacingStandingIndex;
    }

    public void setRightFacingStandingIndex(final int rightFacingStandingIndex) {
        this.rightFacingStandingIndex = rightFacingStandingIndex;
    }

    public int getDownFacingStandingIndex() {
        return downFacingStandingIndex;
    }

    public void setDownFacingStandingIndex(final int downFacingStandingIndex) {
        this.downFacingStandingIndex = downFacingStandingIndex;
    }

    public int getLeftFacingStandingIndex() {
        return leftFacingStandingIndex;
    }

    public void setLeftFacingStandingIndex(final int leftFacingStandingIndex) {
        this.leftFacingStandingIndex = leftFacingStandingIndex;
    }
}
