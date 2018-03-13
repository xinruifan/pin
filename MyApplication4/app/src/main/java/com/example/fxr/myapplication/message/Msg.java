package com.example.fxr.myapplication.message;




public class Msg implements java.io.Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final int TYPE_RECEIVE = 0;
    public static final int TYPE_SEND = 1;
    private String myID;
    private String content;
    private int type;
    private String toWho;
    public Msg(String content, int type,String toWho,String myID)
    {
        this.myID=myID;
        this.content = content;
        this.type = type;
        this.toWho=toWho;
    }

    public String getContent()
    {
        return content;
    }

    public int getType()
    {
        return type;
    }
    public String getToWho()
    {
        return toWho;
    }
    public String getmyID()
    {
        return myID;
    }
}
