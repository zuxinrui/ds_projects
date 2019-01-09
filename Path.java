package sample;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author zuxinrui
 */

public class Path
{
    //实例化之前的Map类，得到包含多种信息的graph
    public Map map = new Map();
    public ArrayList<Node> m = map.M;
    public HashMap<String, Integer> name2id = map.Name2ID;
    public HashMap<String, ArrayList<Integer>> name2line = map.Name2Line;
    public HashMap<String, Node> name2node = map.Name2Node;
    //堆排序,初始化后续所需的数据结构，声明为public
    public BinaryHeap BH;
    public String Route;
    public int ShortestTime;
    public ArrayList<Node> RouteNodes = new ArrayList<>();
    //本方法输入路径，输出长度加一的最短路径
    public void search (String from, String to)
    {
        //初始化变量
        BH = new BinaryHeap(m);
        Route = "";
        ShortestTime = 0;
        //注意每次运行都要将所有节点设为未读
        map.setNodeVisited();
        Node start = name2node.get(from);
        start.Time = 0;
        //数组添加开始节点
        RouteNodes.add(start);
        int Key = BH.getIndex(start);
        BH.UpHeapify(Key);
        BH.BinaryHeapify(0);
        //删除最小的节点
        BH.DeleteMin();
        //在等于最后一个节点的名字之前，继续
        while (!RouteNodes.get(RouteNodes.size() - 1).Name.equals(to))
        {
            //下一个节点
            Node node = RouteNodes.get(RouteNodes.size() - 1);
            //下一个节点得到后，替换其相邻节点的最小权重w
            for (int i = 0; i < node.Next.size(); i++)
            {
                Node temp = node.NextNode.get(i);
                if (temp.Time > node.Time + node.Distance.get(i))  //目前的权重大于新节点计算的权重，进行更新
                {
                    temp.Time = node.Time + node.Distance.get(i);
                    temp.Parent = new ArrayList<>();
                    temp.Parent.addAll(node.Parent);
                    temp.Parent.add(node);
                    //新建临时变量，重新进行上滤排序
                    int index = BH.getIndex(temp);
                    BH.UpHeapify(index);
                }
            }
            RouteNodes.add(BH.DeleteMin());
            if (RouteNodes.get(RouteNodes.size() - 1).Name.equals("临港大道"))
            {
                int K = 0;
                RouteNodes.get(K);
            }
        }
    }
    //使用双循环，将公共线路算作一个
    public int MergeLine (Node n1, Node n2)
    {
        ArrayList<Integer> A1 = n1.Line;
        ArrayList<Integer> A2 = n2.Line;
        for (int i = 0; i < A1.size(); i++) {
            for (int j = 0; j < A2.size(); j++) {
                if (A1.get(i) == A2.get(j)) {
                    return A1.get(i);
                }
            }
        }
        return 0;
    }
    //得到规定格式的路径,输出为Route
    public void DisplayRoute (String from, String to)
    {
        Route = "";
        ShortestTime = 0;
        int Line = -1;
        //节点数组输入
        ArrayList<Node> parent = name2node.get(to).Parent;
        parent.add(name2node.get(to));
        for (int i = 0; i < parent.size() - 1; i++)
        {
            //注意合并重合的线路
            int temp = MergeLine(parent.get(i), parent.get(i + 1));
            if (temp != Line)
            {
                //更新路线
                Line = temp;
                //首个节点加上目前的路线编号
                Route += parent.get(i).Name + " - Line" + Line + " - ";
            }
            if (0 < i && i < parent.size() - 1 &&
                    parent.get(i).Name.equals("龙溪路"))  //龙溪路有10号线的分支
            {
                //如果前后节点分别是分支的两个起始站点，加上龙溪路换乘
                if (parent.get(i - 1).Name.equals("上海动物园") &&
                        parent.get(i + 1).Name.equals("龙柏新村"))
                {
                    Route += "龙溪路 - Line10 - ";
                }
                else if (parent.get(i - 1).Name.equals("龙柏新村") &&
                        parent.get(i + 1).Name.equals("上海动物园"))
                {
                    Route += "龙溪路 - Line10 - ";
                }
            }
            if (0 < i && i < parent.size() - 1 &&
                    parent.get(i).Name.equals("嘉定新城"))  //11号线的两个分支
            {
                if (parent.get(i - 1).Name.equals("白银路") &&
                        parent.get(i + 1).Name.equals("上海赛车场"))
                {
                    Route += "嘉定新城 - Line11 - ";
                }
                else if (parent.get(i - 1).Name.equals("上海赛车场") &&
                        parent.get(i + 1).Name.equals("白银路"))
                {
                    Route += "嘉定新城 - Line11 - ";
                }
            }
            if (0 < i && i < parent.size() - 1 &&
                    parent.get(i).Name.equals("广兰路"))  //2号线在广兰路进行换乘
            {
                if (parent.get(i - 1).Name.equals("金科路") &&
                        parent.get(i + 1).Name.equals("唐镇"))
                {
                    Route += "广兰路 - Line2 - ";
                }
                else if (parent.get(i - 1).Name.equals("唐镇") &&
                        parent.get(i + 1).Name.equals("金科路"))
                {
                    Route += "广兰路 - Line2 - ";
                }
            }
        }
        ShortestTime = name2node.get(to).Time;  //输出此时时间
    }
}
