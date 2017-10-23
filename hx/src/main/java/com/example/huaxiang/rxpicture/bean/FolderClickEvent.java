package com.example.huaxiang.rxpicture.bean;


import com.example.huaxiang.model.BaseInfo;

public class FolderClickEvent extends BaseInfo {

    private int position;
    private ImageFolder folder;

    public FolderClickEvent(int position, ImageFolder folder) {
        this.position = position;
        this.folder = folder;
    }

    public ImageFolder getFolder() {
        return folder;
    }

    public int getPosition() {
        return position;
    }
}
