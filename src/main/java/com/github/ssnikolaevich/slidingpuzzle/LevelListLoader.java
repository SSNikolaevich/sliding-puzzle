package com.github.ssnikolaevich.slidingpuzzle;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LevelListLoader {
    public static ArrayList<String> load(Element element) {
        ArrayList<String> levels = new ArrayList<>();
        NodeList nodeList = element.getElementsByTagName("level");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            String name = nodeList.item(i).getAttributes().getNamedItem("name").getTextContent();
            levels.add(name);
        }
        return levels;
    }
}
