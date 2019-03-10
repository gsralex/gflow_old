package com.gsralex.gflow.scheduler.client.action.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public class FlowGroup {


    private List<FlowItem> itemList = new ArrayList<>();
    private List<FlowDirect> directList = new ArrayList<>();

    private long id;
    private String name;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FlowItem action(long actionId) {
        FlowItem flowItem = new FlowItem();
        flowItem.actionId(actionId);
        itemList.add(flowItem);
        return flowItem;
    }

    public FlowDirect direct(int index) {
        FlowDirect flowDirect = new FlowDirect();
        flowDirect.index(index);
        directList.add(flowDirect);
        return flowDirect;
    }

    public List<FlowItem> getItemList() {
        return itemList;
    }

    public List<FlowDirect> getDirectList() {
        return directList;
    }

    public static void main(String[] args) {
        FlowGroup flowGroup = new FlowGroup();
        flowGroup.setId(1);
        flowGroup.setName("123123");

        flowGroup.action(1).parameter("123123").label("123123").index(1);
        flowGroup.action(2).parameter("123123132").label("123123").index(2);
        flowGroup.action(3).parameter("123123").label("123123").index(3);

        flowGroup.direct(0).next(2);
        flowGroup.direct(2).next(3, 4, 5);
        flowGroup.direct(3).next(5, 6);
        flowGroup.direct(4).next(5);
    }
}
