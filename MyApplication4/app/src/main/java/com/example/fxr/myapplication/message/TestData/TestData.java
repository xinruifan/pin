package com.example.fxr.myapplication.message.TestData;


import com.example.fxr.myapplication.message.model.ChatModel;
import com.example.fxr.myapplication.message.model.ItemModel;

import java.util.ArrayList;


/**
 * Created by：Administrator on 2015/12/21 16:43
 */
public class TestData {

    public static ArrayList<ItemModel> getTestAdData() {
        ArrayList<ItemModel> models = new ArrayList<>();
        ChatModel model = new ChatModel();
        model.setContent("你好啊");
        model.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/lwjBGmCd5X8y1.UUaZtivJ1gEt0Mu.6me0rn1KFwwQk!/b/dFoBAAAAAAAA&amp;bo=XwOAAgAAAAABAPk!&amp;rf=viewer_4");
        models.add(new ItemModel(ItemModel.CHAT_A, model));
        ChatModel model2 = new ChatModel();
        model2.setContent("你好");
        model2.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/lwjBGmCd5X8y1.UUaZtivJ1gEt0Mu.6me0rn1KFwwQk!/b/dFoBAAAAAAAA&amp;bo=XwOAAgAAAAABAPk!&amp;rf=viewer_4");
        models.add(new ItemModel(ItemModel.CHAT_B, model2));
        ChatModel model3 = new ChatModel();
        model3.setContent("你叫什么名字");
        model3.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/lwjBGmCd5X8y1.UUaZtivJ1gEt0Mu.6me0rn1KFwwQk!/b/dFoBAAAAAAAA&amp;bo=XwOAAgAAAAABAPk!&amp;rf=viewer_4");
        models.add(new ItemModel(ItemModel.CHAT_A, model3));
        ChatModel model4 = new ChatModel();
        model4.setContent("我叫黄星铭");
        model4.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/lwjBGmCd5X8y1.UUaZtivJ1gEt0Mu.6me0rn1KFwwQk!/b/dFoBAAAAAAAA&amp;bo=XwOAAgAAAAABAPk!&amp;rf=viewer_4");
        models.add(new ItemModel(ItemModel.CHAT_B, model4));
        ChatModel model5 = new ChatModel();
        model5.setContent("我叫彭浩");
        model5.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/lwjBGmCd5X8y1.UUaZtivJ1gEt0Mu.6me0rn1KFwwQk!/b/dFoBAAAAAAAA&amp;bo=XwOAAgAAAAABAPk!&amp;rf=viewer_4");
        models.add(new ItemModel(ItemModel.CHAT_A, model5));
        ChatModel model6 = new ChatModel();
        model6.setContent("好的");
        model6.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/lwjBGmCd5X8y1.UUaZtivJ1gEt0Mu.6me0rn1KFwwQk!/b/dFoBAAAAAAAA&amp;bo=XwOAAgAAAAABAPk!&amp;rf=viewer_4");
        models.add(new ItemModel(ItemModel.CHAT_B, model6));
        return models;
    }

}
