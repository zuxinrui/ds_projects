package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author zuxinrui
 */

public class Controller {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lb1;
    @FXML
    private Label lb2;
    @FXML
    private Button calculate;
    @FXML
    private ImageView icon;
    @FXML
    private TextField inputtf;
    @FXML
    private TextArea outputal;
    @FXML
    private TextField outputtime;
    @FXML
    private TextField caltime;
    @FXML
    private Label lb3;
    @FXML
    private Label lb4;

    @FXML
    public Path GraphMaker(ActionEvent event) throws FileNotFoundException
    {
        Path path = new Path();
        path.map.constructNodeMap();
        path.map.constructRoadMap();
        return path;
    }

    @FXML
    private void PathFinder(ActionEvent event) throws FileNotFoundException
    {
        Path path = new Path();
        path.map.constructNodeMap();
        path.map.constructRoadMap();
        //在每次点击事件后，重置所有节点的状态为false
        path.map.setNodeVisited();
        String input = inputtf.getText().trim();
        long start = System.currentTimeMillis();
        String[] strings = input.split(" ");
        String WholeRoute = "";
        int WholeTime = 0;
        //输入的每个节点，都计算一次最短路径
        for (int i = 0; i < strings.length - 1; i++)
        {
            path.search(strings[i], strings[i + 1]);
            path.DisplayRoute(strings[i], strings[i + 1]);
            WholeRoute += path.Route;
            WholeTime += path.ShortestTime;
        }
        WholeRoute = WholeRoute + strings[strings.length - 1];
        outputal.setText(WholeRoute);
        outputtime.setText("乘地铁共用时 " + WholeTime + " min");
        long end = System.currentTimeMillis();
        caltime.setText("计算耗费：" + (end - start) + "ms");
    }
}
