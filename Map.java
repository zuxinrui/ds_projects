package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.Scanner;

/**
 * @author zuxinrui
 */

public class Map
{
    // 构建的总graph
    public ArrayList<Node> M = new ArrayList<Node>();
    // 将地铁站名称转换为int类型的ID
    public HashMap<String, Integer> Name2ID = new HashMap<>();
    // 站点名称对几号地铁线的数组的映射
    public HashMap<String, ArrayList<Integer>> Name2Line = new HashMap<>();
    // String类型的地铁站名称对graph中节点的映射
    public HashMap<String, Node> Name2Node = new HashMap<>();
    // 把所有站点的访问状态isVisited都变成false，注意每次主函数做最短路径之前都要调用一次本函数
    public void setNodeVisited ()
    {
        for (int i = 0; i < M.size(); i++)
        {
            M.get(i).Visited = false;
            M.get(i).Parent = new ArrayList<>();
            M.get(i).Time = 999;
        }
    }
    //判断一个节点是否属于graph
    public boolean isNewNode (Node temp)
    {
        for (int i = 0; i < M.size(); i++)
        {
            if (M.get(i).Name.equals(temp.Name))
            {
                return false;
            }
        }
        return true;
    }
    //旧节点匹配该temp节点编号
    public int getNodeNum (Node temp)
    {
        if (isNewNode(temp) == true)
        {
            return -1;
        }
        for (int i = 0; i < M.size(); i++)
        {
            if (M.get(i).Name.equals(temp.Name))
            {
                return M.get(i).ID;
            }
        }
        return -1;
    }
    // 把一个站点加入地图
    public void addNode (Node temp)
    {
        // 新站点给一个新ID，一般直接给index就行
        if (isNewNode(temp) == true)
        {
            temp.ID =  M.size();  //用graph当前的节点数量作为新节点的id
            M.add(temp);
        }
        //旧站点更新数据，防止数据重复
        else
        {
            int i = getNodeNum(temp);
            M.get(i).Distance.addAll(temp.Distance);
            M.get(i).Next.addAll(temp.Next);
            M.get(i).Line.addAll(temp.Line);
        }

    }

    //读取实现做好的txt文件，构造graph
    public void constructNodeMap () throws FileNotFoundException {
        for (int i = 1; i <= 17; i++) {
            File Temp = new File("map/line" + String.valueOf(i) + ".txt");
            //System.out.println(Temp.length());
            ArrayList<String> Data = new ArrayList<String>();
            //注意这里的file类不能直接传递给scanner类，应转换成fileinputstream类！
            FileInputStream fis = new FileInputStream(Temp);
            //InputStreamReader isr=new InputStreamReader(fis);
            //BufferedReader br=new BufferedReader(isr);
            //String result=br.readLine();
            //注意这里的file类不能直接传递给scanner类，应转换成fileinputstream类！
            Scanner input = new Scanner(fis);
            //注意指针只能用一次
            //input.nextLine();
            //System.out.println(input.hasNextLine());
            while (input.hasNextLine()) {
                Data.add(input.nextLine());  //在这里将scanner的指针向前移动，注意一个周期只能移动一次，否则将什么都传递不进来
            }
            //System.out.println(Data);
            // 行数（即节点数），储存每个名称和时刻
            int size = Data.size();
            String[] name = new String[size];
            int[] time = new int[size];
            //临时变量，用来将分割的数据传递到name和time中
            String[] t = new String[2];
            //临时节点，用来构造graph
            Node node = new Node();
            for (int j = 0; j < size; j++) {
                t = Data.get(j).split(" ");
                //System.out.println(t[0]);
                //在扫描txt文件的第一行时，也就是j=0时，由于第一行是'linex'没有实际信息，这里面默认事件信息为0
                if (j == 0) {
                    name[j] = t[0];
                    time[j] = 0;
                } else {
                    name[j] = t[0];
                    time[j] = Integer.valueOf(t[1]);
                }
            }
            //计算权重w，列出相邻节点，并标记所在线路
            for (int j = 0; j < size; j++) {

                node = new Node(name[j]);
                if (j != 0) {
                    node.Next.add(name[j - 1]);
                    node.Distance.add(Math.abs(time[j] - time[j - 1]));
                }
                if (j != size - 1) {
                    node.Next.add(name[j + 1]);
                    node.Distance.add(Math.abs(time[j + 1] - time[j]));
                }
                //标记所在线路，14、15号线由于是相应10号线和11号线的支路，所以直接-4
                if (i == 14 || i == 15) {
                    node.Line.add(i - 4);
                } else {
                    node.Line.add(i);
                }
                //加入节点至graph
                addNode(node);
            }
            //close()可以使缓存中的数据下落
            input.close();
        }
    }
    //构造node之间的关系
    public void constructRoadMap ()
    {
        for (int i = 0; i < M.size(); i++)
        {
            Name2ID.put(M.get(i).Name, M.get(i).ID);
        }
        for (int i = 0; i < M.size(); i++)
        {
            Name2Line.put(M.get(i).Name, M.get(i).Line);
        }

        for (int i = 0; i < M.size(); i++)
        {
            Name2Node.put(M.get(i).Name, M.get(i));
        }
        //此处用双循环构造每个节点与相邻节点的连接
        for (int i = 0; i < M.size(); i++)
        {
            Node node = M.get(i);
            for (int j = 0; j < node.Next.size(); j++)
            {
                node.NextNode.add(Name2Node.get(node.Next.get(j)));
            }
        }
    }
}
