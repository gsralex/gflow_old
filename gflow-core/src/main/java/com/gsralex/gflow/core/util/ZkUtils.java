package com.gsralex.gflow.core.util;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/2/18
 */
public class ZkUtils {

    private ZkUtils() {
    }

    public static boolean createZNode(String path, Object data, CreateMode mode, ZkClient zkClient) {
        List<ZkNode> list = listParentNode(path);
        for (ZkNode node : list) {
            if (node.isParent()) {
                if (!zkClient.exists(node.path)) {
                    //临时节点的不存在子节点，所以父级是持久化节点
                    zkClient.create(node.path, node.path, CreateMode.PERSISTENT);
                }
            } else {
                if (!zkClient.exists(node.path)) {
                    zkClient.create(node.path, data, mode);
                    return true;
                }
            }
        }
        return false;
    }


    public static class ZkNode {
        public ZkNode(String path, boolean parent) {
            this.path = path;
            this.parent = parent;
        }

        private String path;
        private boolean parent;

        public String getPath() {
            return path;
        }

        public boolean isParent() {
            return parent;
        }
    }


    public static List<ZkNode> listParentNode(String path) {
        //gflow/scheduler/master
        //
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        List<ZkNode> list = new ArrayList<>();
        int fromIndex = 1;
        while (true) {
            int index = path.indexOf("/", fromIndex);
            if (index != -1) {
                String parentPath = path.substring(0, index);
                fromIndex = index + 1;
                list.add(new ZkNode(parentPath, true));
            } else {
                list.add(new ZkNode(path, false));
                break;
            }
        }
        return list;
    }
}
