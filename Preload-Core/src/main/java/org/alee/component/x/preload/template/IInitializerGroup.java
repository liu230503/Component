package org.alee.component.x.preload.template;

import java.util.Map;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: xxxx
 *
 *********************************************************/
public interface IInitializerGroup {

    void loadInto(Map<String, Class<? extends IInitializer>> classMap);
}
