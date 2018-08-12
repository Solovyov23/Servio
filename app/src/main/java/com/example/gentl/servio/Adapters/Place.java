package com.example.gentl.servio.Adapters;

import java.io.Serializable;

public class Place implements Serializable
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCorner() {
        return corner;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public int getShapeOrient() {
        return shapeOrient;
    }

    public void setShapeOrient(int shapeOrient) {
        this.shapeOrient = shapeOrient;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(int frameColor) {
        this.frameColor = frameColor;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    String name, code;
    int left, top, width, height, corner, shapeType, shapeOrient, color, style, frameColor, fontColor;

    public Place(String name, String code, int left, int top, int width, int height, int corner, int shapeType,
          int shapeOrient, int color, int style, int frameColor, int fontColor)
    {
        this.name = name;
        this.code = code;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.corner = corner;
        this.shapeType = shapeType;
        this.shapeOrient = shapeOrient;
        this.color = color;
        this.style = style;
        this.frameColor = frameColor;
        this.fontColor = fontColor;
    }
}
