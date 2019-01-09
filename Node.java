package sample;

import java.util.ArrayList;

/**
 * @author zuxinrui
 */

public class Node
{
    //地铁站的编号
    public int ID = -1;
    //地铁站名称
    public String Name = "";
    //在djikstra算法中标记为未访问过
    public boolean Visited = false;
    //与某地铁站相邻的站点名称，类似于graph构建的临接表
    public ArrayList<String> Next = new ArrayList<>();
    //相邻站点节点
    public ArrayList<Node> NextNode = new ArrayList<>();
    //连线的权重w
    public ArrayList<Integer> Distance = new ArrayList<>();
    //地铁站所在的线路，换乘站会有多个线路
    public ArrayList<Integer> Line = new ArrayList<>();
    //表示与之前节点的路径
    public ArrayList<Node> Parent = new ArrayList<>();
    //初始化运行时间
    public int Time = 0;
    //初始化
    public Node ()
    {
        Next = new ArrayList<>();
        NextNode = new ArrayList<>();
        Distance = new ArrayList<>();
        Line = new ArrayList<>();
        Parent = new ArrayList<>();
    }
    public Node (String Name)
    {
        this.Name = Name;
        Next = new ArrayList<>();
        NextNode = new ArrayList<>();
        Distance = new ArrayList<>();
        Line = new ArrayList<>();
        Parent = new ArrayList<>();
    }
}
