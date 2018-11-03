package com.my.blog.website.config;

import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

/**
 * Created by imyzt on 2018/11/3 13:36 <br/>
 * 为图片增加zoom.js的支持
 */
public class ImageAttributeProvider implements AttributeProvider {

    @Override
    public void setAttributes(Node node, String s, Map<String, String> map) {
        if (node instanceof Image) {
            map.put("data-action", "zoom");
        }
    }
}
